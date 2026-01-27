package br.com.ifba.biblioteca.emprestimo.service;

import br.com.ifba.biblioteca.emprestimo.controller.EmprestimoIController;
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.exemplar.entity.EstadoConservacao;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.exemplar.entity.StatusExemplar;
import br.com.ifba.biblioteca.exemplar.repository.ExemplarRepository;

// Imports de Pessoa e Usuario
import br.com.ifba.biblioteca.pessoa.entity.NivelAcesso; 
import br.com.ifba.biblioteca.pessoa.entity.StatusPessoa;
import br.com.ifba.biblioteca.pessoa.entity.TipoPerfil;
import br.com.ifba.biblioteca.usuario.entity.Usuario;
import br.com.ifba.biblioteca.usuario.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

//@Component
public class TesteRapidoDeEmprestimos implements CommandLineRunner {

    @Autowired
    private EmprestimoIController emprestimoController;

    @Autowired
    private UsuarioRepository usuarioRepository; // Renomeado para UsuarioRepository

    @Autowired
    private ExemplarRepository exemplarRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=============================================");
        System.out.println(">>> INICIANDO TESTE AUTOMÁTICO (EMPRESTIMO) <<<");
        System.out.println("=============================================");

        try {
            // --------------------------------------------------
            // 1. CRIAR UM USUÁRIO FAKE (SE PRECISAR)
            // --------------------------------------------------
            Usuario usuarioTeste;

            if (usuarioRepository.count() == 0) {
                System.out.println("ℹ️ Nenhum usuário encontrado. Criando um novo...");
                Usuario novoUser = new Usuario() {};
                novoUser.setNomeCompleto("Maria Teste");
                novoUser.setCpf("111.222.333-44");
                novoUser.setTelefone("71988887777");
                novoUser.setDataCadastro(LocalDate.now());
                
                // Enums Corretos
                novoUser.setNivelAcesso(NivelAcesso.RESTRITO); 
                novoUser.setTipoPerfil(TipoPerfil.CLIENTE);
                novoUser.setStatusPessoa(StatusPessoa.ATIVO);

                usuarioTeste = usuarioRepository.save(novoUser);
                System.out.println("✅ 1. Usuário criado com ID: " + usuarioTeste.getId());
            } else {
                System.out.println("ℹ️ Já existem usuários no banco. Usando o primeiro encontrado.");
                usuarioTeste = usuarioRepository.findAll().get(0);
                System.out.println("✅ Usuário selecionado: " + usuarioTeste.getNomeCompleto());
            }
            
            // --------------------------------------------------
            // 2. CRIAR/BUSCAR UM EXEMPLAR FAKE
            // --------------------------------------------------
            if (exemplarRepository.count() == 0) {
                Exemplar livro = new Exemplar();
                livro.setNumeroTombamento(2025);
                livro.setLocalizacaoFisica("Corredor B");
                livro.setConservacao(EstadoConservacao.BOM); 
                livro.setStatus(StatusExemplar.DISPONIVEL);

                livro = exemplarRepository.save(livro);
                System.out.println("✅ 2. Exemplar criado com ID: " + livro.getId());
            }

            // Tenta pegar um exemplar DISPONIVEL
            Exemplar exemplarParaTeste = exemplarRepository.findAll().stream()
                    .filter(e -> e.getStatus() == StatusExemplar.DISPONIVEL)
                    .findFirst()
                    .orElse(null);

            if (exemplarParaTeste == null) {
                System.out.println("⚠️ Não há exemplares disponíveis para teste. Crie um novo ou devolva um livro.");
                return;
            }

            // --------------------------------------------------
            // 3. REALIZAR O EMPRÉSTIMO
            // --------------------------------------------------
            System.out.println(">>> Tentando emprestar Livro ID " + exemplarParaTeste.getId() + 
                               " para Usuário ID " + usuarioTeste.getId() + "...");

            Emprestimo emp = new Emprestimo();
            emp.setUsuario(usuarioTeste); // <--- MUDANÇA IMPORTANTE: setUsuario (não setCliente)
            emp.setExemplar(exemplarParaTeste);
            
            // O Controller/Service vai preencher as datas e status
            Emprestimo realizado = emprestimoController.save(emp);

            System.out.println("=============================================");
            System.out.println("🚀 SUCESSO! EMPRÉSTIMO REALIZADO!");
            System.out.println("ID do Empréstimo: " + realizado.getId());
            System.out.println("Devolução prevista: " + realizado.getDataDevolucaoPrevista());
            System.out.println("Status: " + realizado.getStatus());
            System.out.println("=============================================");

        } catch (Exception e) {
            System.out.println("❌ ERRO NO TESTE:");
            e.printStackTrace();
        }
    }
}