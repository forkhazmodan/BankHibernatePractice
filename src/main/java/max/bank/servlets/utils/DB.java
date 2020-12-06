package max.bank.servlets.utils;

import javax.persistence.EntityManager;

public class DB {

    private static EntityManager em;

    public DB(EntityManager em) {
        this.em =  em;
    }

    public static EntityManager em() {
        return em;
    }
}
