package br.com.ifba.biblioteca.conf;

/*
 * @author guilhermeAmedrado
 */

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.ifba.biblioteca.usuario.entity.Administrador;
import br.com.ifba.biblioteca.pessoa.entity.NivelAcesso;
import br.com.ifba.biblioteca.pessoa.entity.StatusPessoa;
import br.com.ifba.biblioteca.pessoa.entity.TipoPerfil;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("--- [DataInitializer] Verificando migração de usuários... ---");

        //Garantir Schema e Limpar Constraints antigas (conflito de Enum)
        try {
            entityManager.createNativeQuery("ALTER TABLE usuario DROP CONSTRAINT IF EXISTS usuario_nivel_acesso_check").executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE usuario DROP CONSTRAINT IF EXISTS usuario_status_pessoa_check").executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE usuario DROP CONSTRAINT IF EXISTS usuario_tipo_perfil_check").executeUpdate();
        } catch (Exception e) {
            System.out.println("--- [DataInitializer] Aviso: Não foi possível dropar constraints (pode não existir). Ignorando. ---");
        }

        // CORREÇÃO DE SCHEMA: Tabela sugestoes_aquisicao apontando para tabela errada "usuarios" (plural)
        try {
            // Drop da constraint antiga (se existir)
            entityManager.createNativeQuery("ALTER TABLE sugestoes_aquisicao DROP CONSTRAINT IF EXISTS fkdmldtw710echbd2j5irexdgwe").executeUpdate();
            
            // Verifica se a constraint correta JÁ existe antes de tentar criar (para evitar Transaction Abort no Postgres)
            Long constraintsCount = ((Number) entityManager.createNativeQuery(
                "SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_name = 'fk_sugestao_usuario_correta'"
            ).getSingleResult()).longValue();

            if (constraintsCount == 0) {
                // Só cria se não existir
                entityManager.createNativeQuery("ALTER TABLE sugestoes_aquisicao ADD CONSTRAINT fk_sugestao_usuario_correta FOREIGN KEY (usuario_id) REFERENCES usuario(id)").executeUpdate();
                System.out.println("--- [DataInitializer] Schema de Sugestões corrigido (FK usuario criada). ---");
            } else {
                 // Já existe, não faz nada
                 // System.out.println("--- [DataInitializer] FK sugestão_usuario já existe. ---");
            }
        } catch (Exception e) {
             System.out.println("--- [DataInitializer] Nota: Verificação de Schema de Sugestões falhou (pode ser H2 ou erro de permissão): " + e.getMessage());
        }

        //Garantir Schema (Postgres não cria automaticamente se hbm2ddl não estiver update)
        // Cria tabelas das subclasses se não existirem
        entityManager.createNativeQuery(
            "CREATE TABLE IF NOT EXISTS administrador (" +
            "id BIGINT NOT NULL, " +
            "ultimo_acesso TIMESTAMP, " +
            "PRIMARY KEY (id), " +
            "CONSTRAINT fk_admin_usuario FOREIGN KEY (id) REFERENCES usuario (id))"
        ).executeUpdate();

        entityManager.createNativeQuery(
            "CREATE TABLE IF NOT EXISTS bibliotecario (" +
            "id BIGINT NOT NULL, " +
            "turno VARCHAR(255) NOT NULL, " +
            "matricula_funcionario VARCHAR(255) UNIQUE, " +
            "PRIMARY KEY (id), " +
            "CONSTRAINT fk_biblio_usuario FOREIGN KEY (id) REFERENCES usuario (id))"
        ).executeUpdate();

        //Verificar se existe algum usuário
        Long count = (Long) entityManager.createQuery("SELECT COUNT(u) FROM Usuario u").getSingleResult();
        
        if (count == 0) {
            System.out.println("--- [DataInitializer] Nenhum usuário encontrado. Criando Administrador Padrão... ---");
            Administrador admin = new Administrador();
            admin.setNomeCompleto("Administrador Padrão");
            admin.setCpf("000.000.000-00");
            admin.setTelefone("000000000");
            admin.setDataCadastro(LocalDate.now());
            admin.setNivelAcesso(NivelAcesso.TOTAL);
            admin.setStatusPessoa(StatusPessoa.ATIVO);
            admin.setTipoPerfil(TipoPerfil.ADMINISTRADOR);
            admin.setLogin("admin");
            admin.setSenha("123");
            
            entityManager.persist(admin);
            System.out.println("--- [DataInitializer] Administrador criado: Login 'admin', Senha '123' ---");
        } else {
             System.out.println("--- [DataInitializer] Usuários já existem no banco (" + count + "). Pulo criação do admin padrão. ---");
        }
        
        //Atualizar logins/senhas nulos (Lógica Java para compatibilidade H2/Postgres)
        java.util.List<br.com.ifba.biblioteca.usuario.entity.Usuario> usuariosSemLogin = entityManager.createQuery(
            "SELECT u FROM Usuario u WHERE u.login IS NULL OR u.senha IS NULL OR u.login = ''", br.com.ifba.biblioteca.usuario.entity.Usuario.class
        ).getResultList();

        for (br.com.ifba.biblioteca.usuario.entity.Usuario u : usuariosSemLogin) {
            if (u.getNomeCompleto() != null) {
                String primeiroNome = u.getNomeCompleto().split(" ")[0].toLowerCase();
                u.setLogin(primeiroNome);
            } else {
                 u.setLogin("user" + u.getId());
            }
            u.setSenha("123");
            entityManager.merge(u);
        }
        
        if (!usuariosSemLogin.isEmpty()) {
            System.out.println("--- [DataInitializer] Atualizados " + usuariosSemLogin.size() + " usuários com login/senha padrão. ---");
        }
        


        //Migrar Administradores (JOINED inheritance)
        int adminMigrados = entityManager.createNativeQuery(
            "INSERT INTO administrador (id) " +
            "SELECT u.id FROM usuario u " +
            "WHERE u.tipo_perfil = 'ADMINISTRADOR' " +
            "AND u.id NOT IN (SELECT a.id FROM administrador a)"
        ).executeUpdate();
        
        if (adminMigrados > 0) {
             System.out.println("--- [DataInitializer] Migrados " + adminMigrados + " Administradores. ---");
        }

        // Migrar Bibliotecarios (JOINED inheritance)
        int biblioMigrados = entityManager.createNativeQuery(
            "INSERT INTO bibliotecario (id, turno, matricula_funcionario) " +
            "SELECT u.id, 'Matutino', CONCAT('AUTO-', u.id) FROM usuario u " +
            "WHERE u.tipo_perfil = 'BIBLIOTECARIO' " +
            "AND u.id NOT IN (SELECT b.id FROM bibliotecario b)"
        ).executeUpdate();
        
        if (biblioMigrados > 0) {
             System.out.println("--- [DataInitializer] Migrados " + biblioMigrados + " Bibliotecários. ---");
        }
        
        // DEBUG: Listar usuários para conferência
        System.out.println("--- [DEBUG] LISTA DE USUÁRIOS NO BANCO ---");
        java.util.List<Object[]> users = entityManager.createNativeQuery("SELECT id, nome_completo, login, senha, tipo_perfil FROM usuario").getResultList();
        for (Object[] u : users) {
             System.out.println("ID: " + u[0] + " | Nome: " + u[1] + " | Login: [" + u[2] + "] | Senha: [" + u[3] + "] | Tipo: " + u[4]);
        }
        System.out.println("------------------------------------------");
        
        System.out.println("--- [DataInitializer] Concluído. ---");
    }
}
