package com.locacaoquadras.config;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionPoolConfig {

    // Banco H2 em arquivo, salvo em C:\Users\<seu usuario>\locacao_quadras.mv.db
    // INIT roda o schema.sql (src/main/resources) para criar as tabelas que ainda não existem.
    private static final String URL =
        "jdbc:h2:~/locacao_quadras;MODE=MySQL;DATABASE_TO_LOWER=TRUE;"
        + "INIT=RUNSCRIPT FROM 'classpath:schema.sql'";

    private static final String USUARIO = "sa";
    private static final String SENHA = "";

    private static BasicDataSource dataSource;

    private ConnectionPoolConfig() {
    }

    private static synchronized BasicDataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName("org.h2.Driver");
            dataSource.setUrl(URL);
            dataSource.setUsername(USUARIO);
            dataSource.setPassword(SENHA);
            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(10);
            dataSource.setMaxTotal(50);
            System.out.println("New connection pool created with successful");
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public static synchronized void close() {
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar o pool de conexões: " + e.getMessage());
            }
            dataSource = null;
        }
    }
}
