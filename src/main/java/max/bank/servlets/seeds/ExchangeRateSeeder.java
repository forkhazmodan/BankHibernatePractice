package max.bank.servlets.seeds;

import max.bank.servlets.enums.Currency;
import max.bank.servlets.models.ExchangeRate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateSeeder {

    public static List<ExchangeRate> getExchangeRateList(){
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        exchangeRateList.add(new ExchangeRate(Currency.EUR, Currency.EUR, 1D));
        exchangeRateList.add(new ExchangeRate(Currency.EUR, Currency.UAH, 34.30));
        exchangeRateList.add(new ExchangeRate(Currency.EUR, Currency.USD, 1.21));

        exchangeRateList.add(new ExchangeRate(Currency.USD, Currency.USD, 1D));
        exchangeRateList.add(new ExchangeRate(Currency.USD, Currency.UAH, 28.29));
        exchangeRateList.add(new ExchangeRate(Currency.USD, Currency.EUR, 0.82));

        exchangeRateList.add(new ExchangeRate(Currency.UAH, Currency.UAH, 1D));
        exchangeRateList.add(new ExchangeRate(Currency.UAH, Currency.EUR, 0.029));
        exchangeRateList.add(new ExchangeRate(Currency.UAH, Currency.USD, 0.035));

        return exchangeRateList;
    }

    public static void seed(EntityManagerFactory emf) {

        EntityManager em = emf.createEntityManager();

        List<ExchangeRate> exchangeRateList = ExchangeRateSeeder.getExchangeRateList();


        for (ExchangeRate exchangeRate: exchangeRateList) {

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            try {

                TypedQuery<ExchangeRate> query = em.createNamedQuery("selectByCombination", ExchangeRate.class);
                query.setParameter("from", exchangeRate.getFrom());
                query.setParameter("to", exchangeRate.getTo());
                List<ExchangeRate> result = query.getResultList();

                if(result.size() > 0) {
//                    em.merge(exchangeRate);
                } else {
                    em.persist(exchangeRate);
                }

                transaction.commit();

            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                System.out.println(e.getStackTrace());
                System.out.println(e.getMessage());
            }

        }


    }
}
