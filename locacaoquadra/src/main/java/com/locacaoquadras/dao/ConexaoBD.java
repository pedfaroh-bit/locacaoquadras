package com.locacaoquadras.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBD {

    public static Connection getConexao() throws SQLException {
        Properties props = new Properties();
        try (InputStream in = ConexaoBD.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new SQLException("db.properties não encontrado");
            }
            props.load(in);
        } catch (IOException e) {
            throw new SQLException("Erro ao ler db.properties", e);
        }

        try {
            Class.forName(props.getProperty("db.driver", "org.postgresql.Driver"));
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do PostgreSQL não encontrado", e);
        }

        return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password"));
    }
}