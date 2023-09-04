package org.example;


import org.example.conexaoBanco.ConexaoBanco;
import org.example.model.Autor;
import org.example.model.Emprestimo;
import org.example.model.Leitor;
import org.example.model.Livro;
import org.example.service.BibliotecaService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static org.example.service.BibliotecaService.adicionarAutor;
import static org.example.service.BibliotecaService.adicionarLeitor;

public class Main {
    public static void main(String[] args) {
        Connection conexao = ConexaoBanco.fazerConexao();
        BibliotecaService crud = new BibliotecaService();
        try {
            BibliotecaService.criarTabelas(conexao);

            Scanner scanner = new Scanner(System.in);
            int escolha;

            do {
                System.out.println("Menu:");
                System.out.println("1. Adicionar Leitor");
                System.out.println("2. Listar Leitores");
                System.out.println("3. Adicionar Livro");
                System.out.println("4. Adicionar Autor");
                System.out.println("5. Listar Livros");
                System.out.println("6. Realizar Empréstimo");
                System.out.println("7. Listar Empréstimos");
                System.out.println("8. Sair");
                System.out.print("Escolha uma opção: ");
                escolha = scanner.nextInt();

                switch (escolha) {
                    case 1:
                        System.out.println("Digite o nome do leitor:");
                        String nomeLeitor = scanner.next();
                        System.out.println("Digite o CPF do leitor:");
                        String cpfLeitor = scanner.next();
                        System.out.println("Digite o id do leitor: ");
                        int id = scanner.nextInt();

                        Leitor novoLeitor = new Leitor(id,nomeLeitor, cpfLeitor);
                        adicionarLeitor(conexao, novoLeitor);
                        break;
                    case 2:
                        System.out.println("Todos os leitores");
                        try {
                            List<Leitor> leitores = crud.listarLeitores(conexao);
                            if (leitores.isEmpty()) {
                                System.out.println("Não há leitores cadastrados.");
                            } else {
                                System.out.println("Lista de Leitores:");
                                for (Leitor leitor : leitores) {
                                    System.out.println("ID: " + leitor.id() + ", Nome: " + leitor.nome() + ", CPF: " + leitor.cpf());
                                }
                            }
                        } catch (SQLException ex) {
                            System.err.println("Erro ao listar leitores: " + ex.getMessage());
                        }
                        break;
                    case 3:
                        // Adicionar Livro
                        try {
                            System.out.println("Digite o título do livro:");
                            String tituloLivro = scanner.next();
                            System.out.println("Digite o nome do autor:");
                            String nomeAutor = scanner.next();

                            Autor autorExistente = crud.buscarAutorPorNome(conexao, nomeAutor);
                            if (autorExistente == null) {
                                System.out.println("Digite o cpf do autor: ");
                                String cpfAutor = scanner.nextLine();
                                System.out.println("Digite o id do Autor: ");
                                int idAutor = scanner.nextInt();
                                Autor novoAutor = new Autor(idAutor, nomeAutor, cpfAutor);
                                autorExistente = crud.adicionarAutor(conexao, novoAutor);

                                System.out.println("Digite o id: ");
                                int idLivro = scanner.nextInt();
                                // tive que colocar fulano, porque deu N erros e essa foi minha unica alternativa

                                Livro novoLivro = new Livro(idLivro, tituloLivro, "fulano" );

                                Livro livroAdicionado = crud.adicionarLivro(conexao, novoLivro, novoAutor);
                                if (livroAdicionado != null) {
                                    System.out.println("Livro adicionado com sucesso! ID do Livro: " + livroAdicionado.id());
                                } else {
                                    System.out.println("Erro ao adicionar o livro.");
                                }
                            }

                        } catch (SQLException ex) {
                            System.err.println("Erro ao adicionar livro: " + ex.getMessage());
                        }
                        break;

                    case 4:
                        System.out.println("Digite o nome do Autor:");
                        String nomeAuto = scanner.next();
                        System.out.println("Digite o CPF do Autor:");
                        String cpf = scanner.next();
                        System.out.println("Digite o id do Autor: ");
                        int idAutor = scanner.nextInt();

                        Autor autor = new Autor(idAutor, nomeAuto, cpf);
                        adicionarAutor(conexao, autor);
                    case 5:

                        try {
                            List<Livro> livros = crud.listarLivros(conexao);
                            if (livros.isEmpty()) {
                                System.out.println("Não há livros cadastrados.");
                            } else {
                                System.out.println("Lista de Livros:");
                                for (Livro livro : livros) {
                                    System.out.println("ID: " + livro.id() + ", Título: " + livro.titulo() + ", Autor: " + livro.autor());
                                }
                            }
                        } catch (SQLException ex) {
                            System.err.println("Erro ao listar livros: " + ex.getMessage());
                        }
                        break;

                    case 6:

                        try {
                            System.out.println("Digite o ID do leitor:");
                            int idLeitor = scanner.nextInt();
                            System.out.println("Digite o ID do livro:");
                            int idLivro = scanner.nextInt();

                            Leitor leitor = crud.buscarLeitorPorId(conexao, idLeitor);
                            Livro livro = crud.buscarLivroPorId(conexao, idLivro);

                            if (leitor != null && livro != null) {

                                Emprestimo emprestimo = new Emprestimo(idLeitor,leitor, livro);


                                Emprestimo emprestimoRealizado = crud.realizarEmprestimo(conexao, emprestimo);

                                if (emprestimoRealizado != null) {
                                    System.out.println("Empréstimo realizado com sucesso! ID do Empréstimo: " + emprestimoRealizado.id());
                                } else {
                                    System.out.println("Erro ao realizar o empréstimo.");
                                }
                            } else {
                                System.out.println("Leitor ou livro não encontrado.");
                            }
                        } catch (SQLException ex) {
                            System.err.println("Erro ao realizar empréstimo: " + ex.getMessage());
                        }
                        break;
                    case 7:
                        try {
                            List<Emprestimo> emprestimos = crud.listarEmprestimos(conexao);
                            if (emprestimos.isEmpty()) {
                                System.out.println("Não há empréstimos realizados.");
                            } else {
                                System.out.println("Lista de Empréstimos:");
                                for (Emprestimo emprestimo : emprestimos) {
                                    System.out.println("ID: " + emprestimo.id() + ", Leitor: " + emprestimo.leitor() + ", Livro: " + emprestimo.livro());
                                }
                            }
                        } catch (SQLException ex) {
                            System.err.println("Erro ao listar empréstimos: " + ex.getMessage());
                        }
                        break;

                    case 8:
                        System.out.println("Saindo do programa.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            } while (escolha != 7);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
