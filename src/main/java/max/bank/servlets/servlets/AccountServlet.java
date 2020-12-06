package max.bank.servlets.servlets;

import com.google.gson.Gson;
import max.bank.servlets.enums.Currency;
import max.bank.servlets.models.Account;
import max.bank.servlets.models.ExchangeRate;
import max.bank.servlets.models.User;
import max.bank.servlets.requests.CreateAccountRequest;
import max.bank.servlets.resources.BalanceResource;
import max.bank.servlets.utils.DB;
import max.bank.servlets.utils.Http;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/accounts")
public class AccountServlet extends HttpServlet {

    EntityManager em = DB.em();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = Http.getBody(req);
        Gson gson =  new Gson();
        CreateAccountRequest accountRequest = gson.fromJson(body, CreateAccountRequest.class);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            User user = em.find(User.class, accountRequest.getUser_id());
            Account account = new Account(accountRequest.getCurrency(), user);
            user.addAccount(account);

            transaction.commit();

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            Http.sendResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }

        Http.sendResponse(resp, HttpServletResponse.SC_OK, body);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{
            Long accountId = Long.parseLong(req.getParameter("accountId"));
            String toCurrencyString = req.getParameter("currency")!= null ? req.getParameter("currency") : Currency.UAH.getValue();
            Currency toCurrency = Currency.valueOf(toCurrencyString);

            TypedQuery<Account> accountQuery = em.createNamedQuery("getById", Account.class);
            accountQuery.setParameter("id", accountId);
            Account account = accountQuery.getSingleResult();

            ExchangeRate exchangeRate = em.createNamedQuery("selectByCombination", ExchangeRate.class)
                    .setParameter("from", account.getCurrency())
                    .setParameter("to", toCurrency)
                    .getSingleResult();

            Gson gson = new Gson();
            Long balance = (long)(account.getBalance() * exchangeRate.getRate());

            Http.sendResponse(resp, HttpServletResponse.SC_OK, gson.toJson(new BalanceResource(balance)));

        } catch (IllegalArgumentException e) {
            Http.sendResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
