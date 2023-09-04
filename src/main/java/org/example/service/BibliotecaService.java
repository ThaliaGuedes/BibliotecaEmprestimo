package org.example.service;

import org.example.model.Autor;
import org.example.model.Emprestimo;
import org.example.model.Leitor;
import org.example.model.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaService {
    /// criar tabelas para todos
    public static void criarTabelas(Connection conexao) throws SQLException {
        String criarTabelaLeitor = "CREATE TABLE IF NOT EXISTS leitor (" +
                "id SERIAL PRIMARY KEY," +
                "nome VARCHAR(255) NOT NULL," +
                "cpf VARCHAR(11) NOT NULL)";

        String criarTabelaLivro = "CREATE TABLE IF NOT EXISTS livro (" +
                "id SERIAL PRIMARY KEY," +
                "titulo VARCHAR(255) NOT NULL," +
                "id_autor INT NOT NULL)";

        String criarTabelaAutor = "CREATE TABLE IF NOT EXISTS autor (" +
                "id SERIAL PRIMARY KEY," +
                "nome VARCHAR(255) NOT NULL," +
                "cpf VARCHAR(11) NOT NULL)";

        String criarTabelaEmprestimo = "CREATE TABLE IF NOT EXISTS emprestimo (" +
                "id SERIAL PRIMARY KEY," +
                "id_leitor INT NOT NULL," +
                "id_livro INT NOT NULL," +
                "FOREIGN KEY (id_leitor) REFERENCES leitor(id)," +
                "FOREIGN KEY (id_livro) REFERENCES livro(id))";

        try (PreparedStatement stmtLeitor = conexao.prepareStatement(criarTabelaLeitor);
             PreparedStatement stmtLivro = conexao.prepareStatement(criarTabelaLivro);
             PreparedStatement stmtAutor = conexao.prepareStatement(criarTabelaAutor);
             PreparedStatement stmtEmprestimo = conexao.prepareStatement(criarTabelaEmprestimo)) {
            stmtLeitor.execute();
            stmtLivro.execute();
            stmtAutor.execute();
            stmtEmprestimo.execute();
        }
    }
    public static void adicionarLeitor(Connection conexao, Leitor leitor) throws SQLException {
        String sql = "INSERT INTO leitor (id, nome, cpf) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, leitor.id());
            stmt.setString(2, leitor.nome());
            stmt.setString(3, leitor.cpf());
            stmt.executeUpdate();
            System.out.println("Leitor adicionado com sucesso!");
        }
    }
    public static List<Leitor> listarLeitores(Connection conexao) throws SQLException {
        String sql = "SELECT * FROM leitor";
        List<Leitor> leitores = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                Leitor leitor = new Leitor(id, nome, cpf);
                leitores.add(leitor);
            }
        }
        for (Leitor leitor : leitores) {
            System.out.println("Leitor: ID = " + leitor.id() + ", Nome = " + leitor.nome() + ", CPF = " + leitor.cpf());
        }
        return leitores;
    }
    public static Autor adicionarAutor(Connection conexao, Autor autor) throws SQLException {
        String sql = "INSERT INTO leitor (id, nome, cpf) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, autor.id());
            stmt.setString(2, autor.nome());
            stmt.setString(3, autor.cpf());
            stmt.executeUpdate();
            System.out.println("Autor adicionado com sucesso!");
        }
        return autor;
    }


    public static Livro adicionarLivro(Connection conexao, Livro livro, Autor autor) throws SQLException {
        String sqlAutor = "INSERT INTO autor (id, nome, cpf) VALUES (?, ?, ?)";
        try (PreparedStatement stmtAutor = conexao.prepareStatement(sqlAutor)) {
            stmtAutor.setInt(1, autor.id());
            stmtAutor.setString(2, autor.nome());
            stmtAutor.setString(3, autor.cpf());
            stmtAutor.executeUpdate();
        }

        String sqlLivro = "INSERT INTO livro (id, titulo, id_autor) VALUES (?, ?, ?)";
        try (PreparedStatement stmtLivro = conexao.prepareStatement(sqlLivro)) {
            stmtLivro.setInt(1, livro.id());
            stmtLivro.setString(2, livro.titulo());
            stmtLivro.setInt(3, autor.id());
            stmtLivro.executeUpdate();
        }
        return livro;
    }

    public static List<Livro> listarLivros(Connection conexao) throws SQLException {
        String sql = "SELECT livro.id, livro.titulo, autor.nome FROM livro " +
                "JOIN autor ON livro.id_autor = autor.id";
        List<Livro> livros = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String nomeAutor = rs.getString("nome");
                Livro livro = new Livro(id, titulo, nomeAutor);
                livros.add(livro);
            }
        }

        for (Livro livro : livros) {
            System.out.println("Livro: ID = " + livro.id() + ", Título = " + livro.titulo() + ", Autor = " + livro.autor());
        }
        return livros;
    }
    public static Emprestimo realizarEmprestimo(Connection conexao, Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO emprestimo (id, id_leitor, id_livro) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, emprestimo.id());
            stmt.setInt(2, emprestimo.leitor().id());
            stmt.setInt(3, emprestimo.livro().id());
            stmt.executeUpdate();
            System.out.println("Empréstimo realizado com sucesso!");
        }
        return emprestimo;
    }

    public static List<Emprestimo> listarEmprestimos(Connection conexao) throws SQLException {
        String sql = "SELECT emprestimo.id, leitor.nome AS leitor_nome, livro.titulo AS livro_titulo " +
                "FROM emprestimo " +
                "JOIN leitor ON emprestimo.id_leitor = leitor.id " +
                "JOIN livro ON emprestimo.id_livro = livro.id";
        List<Emprestimo> emprestimos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nomeLeitor = rs.getString("leitor_nome");
                String tituloLivro = rs.getString("livro_titulo");
                Leitor leitor = new Leitor(0, nomeLeitor, "");
                Livro livro = new Livro(0, tituloLivro, "");
                Emprestimo emprestimo = new Emprestimo(id, leitor, livro);
                emprestimos.add(emprestimo);
            }
        }

        for (Emprestimo emprestimo : emprestimos) {
            System.out.println("Empréstimo: ID = " + emprestimo.id() + ", Leitor = " + emprestimo.leitor().nome() + ", Livro = " + emprestimo.livro().titulo());
        }
        return emprestimos;
    }
    public Autor buscarAutorPorNome(Connection conexao, String nomeAutor) throws SQLException {
        String sql = "SELECT id, nome, cpf FROM autor WHERE nome = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeAutor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    return new Autor(id, nome, cpf);
                }
            }
        }

        return null;
    }
    public Leitor buscarLeitorPorId(Connection conexao, int idLeitor) throws SQLException {
        String sql = "SELECT id, nome, cpf FROM leitor WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idLeitor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    return new Leitor(id, nome, cpf);
                }
            }
        }

        return null;
    }

    public Livro buscarLivroPorId(Connection conexao, int idLivro) throws SQLException {
        String sql = "SELECT id, titulo, id_autor FROM livro WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idLivro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String titulo = rs.getString("titulo");
                    String nomeAutor = rs.getString("Nome");


                    Autor autor = buscarAutorPorNome(conexao, nomeAutor);

                    if (autor != null) {
                        return new Livro(id, titulo, nomeAutor);
                    }
                }
            }
        }

        return null;
    }

}


