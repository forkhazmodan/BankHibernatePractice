package max.bank.servlets.utils;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;


public class MyAppServletRequestEventsListener implements ServletRequestListener {
    public void requestInitialized(ServletRequestEvent event) {
        EntityManagerFactory emf = (EntityManagerFactory)event.getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();
        new DB(em);
    }

    public void requestDestroyed(ServletRequestEvent event) {

    }
}

