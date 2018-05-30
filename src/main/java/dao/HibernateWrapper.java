package dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mathenge on 11/1/2017.
 */
public abstract class HibernateWrapper {

    private static final String CONNECTION_URL = "jdbc:postgresql://ec2-54-204-46-236.compute-1.amazonaws.com:5432/d624mc20cgh1rh?sslmode=require";

    //private static final String CONNECTION_URL = System.getenv("JDBC_DATABASE_URL");


    public static SessionFactory sessionFactory = configureSessionFactory();

    static SessionFactory configureSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.setProperty("hibernate.connection.url", CONNECTION_URL);
            StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(ssrb.build());
        } catch (Exception ex) {
            Logger.getLogger(HibernateWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sessionFactory;
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }

}
