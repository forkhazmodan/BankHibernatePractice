package max.bank.servlets.servlets;


import com.google.gson.Gson;
import max.bank.servlets.models.User;
import max.bank.servlets.utils.DB;
import max.bank.servlets.utils.Http;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/users")
public class UsersServlet extends HttpServlet {

    EntityManager em = DB.em();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = Http.getBody(req);
        Gson gson = new Gson();
        User user = gson.fromJson(body, User.class);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.persist(user);
            transaction.commit();
            Http.sendResponse(resp, HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            if (transaction.isActive())
                transaction.rollback();

            Http.sendResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

}
