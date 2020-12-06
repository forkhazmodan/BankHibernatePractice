package max.bank.servlets.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import max.bank.servlets.exceptions.InsufficientFunds;
import max.bank.servlets.models.Account;
import max.bank.servlets.models.ExchangeRate;
import max.bank.servlets.models.Transaction;
import max.bank.servlets.requests.CreateAccountRequest;
import max.bank.servlets.requests.CreateTransactionRequest;
import max.bank.servlets.utils.DB;
import max.bank.servlets.utils.Http;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {

    EntityManager em = DB.em();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = Http.getBody(req);
        Gson gson =  new Gson();
        CreateTransactionRequest transactionRequest = gson.fromJson(body, CreateTransactionRequest.class);


        EntityTransaction dbTransaction = em.getTransaction();
        dbTransaction.begin();

        try {

            TypedQuery<Account> accountFromQuery = em.createNamedQuery("getById", Account.class);
            TypedQuery<Account> accountToQuery = em.createNamedQuery("getById", Account.class);
            accountFromQuery.setParameter("id", transactionRequest.getFromAccount());
            accountToQuery.setParameter("id", transactionRequest.getToAccount());

            Account accountFrom = accountFromQuery.getSingleResult();
            Account accountTo = accountToQuery.getSingleResult();

            if(accountFrom.getBalance() < transactionRequest.amount) {
                throw new InsufficientFunds("Insufficient funds.", 400);
            }

            TypedQuery<ExchangeRate> exchangeRateQuery = em.createNamedQuery("selectByCombination", ExchangeRate.class);
            exchangeRateQuery.setParameter("from", accountFrom.getCurrency());
            exchangeRateQuery.setParameter("to", accountTo.getCurrency());


            ExchangeRate exchangeRate = exchangeRateQuery.getSingleResult();
            Transaction transaction = new Transaction(accountFrom, accountTo, transactionRequest.amount);

            accountFrom.setBalance(accountFrom.getBalance() - transactionRequest.amount);
            accountTo.setBalance(accountTo.getBalance() + (long)(transactionRequest.amount * exchangeRate.getRate()));

            transaction.setFromAccount(accountFrom);
            transaction.setToAccount(accountTo);

            em.persist(transaction);
            em.merge(accountFrom);
            em.merge(accountTo);

            dbTransaction.commit();

            Gson gson2 = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();


            Http.sendResponse(resp, HttpServletResponse.SC_OK, gson2.toJson(transaction));

        } catch (InsufficientFunds e) {
            if (dbTransaction.isActive()) {
                dbTransaction.rollback();
            }

            Http.sendResponse(resp, e.getCode(), e.getMessage());
        } catch (PersistenceException e) {
            Http.sendResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }


    }
}
