package org.example.conexaoBanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    public static Connection fazerConexao() {
        try {
            Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bibliotecaCrud",
                    "postgres", "12345");
            if (conexao != null) {
                System.out.println("Conexao bem sucedida");

            } else {
                System.out.println("Conexao com o banco falhou");
            }
            return conexao;
        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }
    }
}
