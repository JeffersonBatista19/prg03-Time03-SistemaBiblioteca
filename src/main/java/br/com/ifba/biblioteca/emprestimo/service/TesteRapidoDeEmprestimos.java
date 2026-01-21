package br.com.ifba.biblioteca.emprestimo.service;

import br.com.ifba.biblioteca.emprestimo.controller.EmprestimoIController;
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.exemplar.entity.EstadoConservacao;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.exemplar.entity.StatusExemplar;
import br.com.ifba.biblioteca.exemplar.repository.ExemplarRepository;
import br.com.ifba.biblioteca.pessoa.entity.Cliente; 
import br.com.ifba.biblioteca.pessoa.entity.ClienteRepository;
import br.com.ifba.biblioteca.pessoa.entity.NivelAcesso; 
import br.com.ifba.biblioteca.pessoa.entity.StatusPessoa;
import br.com.ifba.biblioteca.pessoa.entity.TipoPerfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TesteRapidoDeEmprestimos implements CommandLineRunner {

    @Autowired
    private EmprestimoIController emprestimoController;

    @Autowired
    private ClienteRepository clienteRepository; // Repositório correto

    @Autowired
    private ExemplarRepository exemplarRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=============================================");
        System.out.println(">>> INICIANDO TESTE AUTOMÁTICO (CLIENTE) <<<");
        System.out.println("=============================================");

        try {
            // --------------------------------------------------
            // 1. CRIAR UM CLIENTE FAKE
            // --------------------------------------------------
            if (clienteRepository.count() == 0) { // Só cria se não tiver ninguém
                Cliente cli = new Cliente();
                cli.setNomeCompleto("Maria Teste");
                cli.setCpf("111.222.333-44");
                cli.setTelefone("71988887777");
                cli.setDataCadastro(LocalDate.now());
                
                // --- AJUSTE OS ENUMS CONFORME O QUE EXISTE NO SEU PROJETO ---
                // Se der erro vermelho aqui, apague a linha ou troque pelo valor certo
                cli.setNivelAcesso(NivelAcesso.RESTRITO); 
                
                cli.setTipoPerfil(TipoPerfil.CLIENTE);
                cli.setStatusPessoa(StatusPessoa.ATIVO);

                cli = clienteRepository.save(cli);
                System.out.println("✅ 1. Cliente criado com ID: " + cli.getId());
            } else {
                System.out.println("ℹ️ Já existem clientes no banco. Usando o primeiro encontrado.");
            }
            
            // Pega um cliente qualquer do banco para o teste
            Cliente clienteParaTeste = clienteRepository.findAll().get(0);

            // --------------------------------------------------
            // 2. CRIAR UM EXEMPLAR FAKE
            // --------------------------------------------------
            if (exemplarRepository.count() == 0) {
                Exemplar livro = new Exemplar();
                livro.setNumeroTombamento(2025);
                livro.setLocalizacaoFisica("Corredor B");
                
                // --- AJUSTE OS ENUMS ---
                livro.setConservacao(EstadoConservacao.BOM); // Se der erro, apague ou ajuste
                livro.setStatus(StatusExemplar.DISPONIVEL);

                livro = exemplarRepository.save(livro);
                System.out.println("✅ 2. Exemplar criado com ID: " + livro.getId());
            } else {
                 System.out.println("ℹ️ Já existem exemplares. Usando um disponível.");
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
                               " para Cliente ID " + clienteParaTeste.getId() + "...");

            Emprestimo emp = new Emprestimo();
            emp.setCliente(clienteParaTeste);   // <--- setCliente
            emp.setExemplar(exemplarParaTeste);
            
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