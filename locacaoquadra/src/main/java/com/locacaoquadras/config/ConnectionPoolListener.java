package com.locacaoquadras.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

// Quando o Tomcat desliga ou troca o WAR, fecha o pool para liberar o arquivo do banco H2.
// Sem isso, a versão nova do site não consegue abrir o banco.
@WebListener
public class ConnectionPoolListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPoolConfig.close();
    }
}
