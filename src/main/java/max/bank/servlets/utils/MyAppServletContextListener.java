package max.bank.servlets.utils;

import max.bank.servlets.seeds.ExchangeRateSeeder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyAppServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        servletContextEvent.getServletContext().setAttribute("emf", emf);

        ExchangeRateSeeder.seed(emf);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        EntityManagerFactory emf = (EntityManagerFactory)servletContextEvent.getServletContext().getAttribute("emf");
        emf.close();
    }
}

