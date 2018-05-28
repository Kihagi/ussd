package dao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by mathenge on 11/6/2017.
 */
public class HibernateApplicationContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        HibernateWrapper.getSessionFactory();
    }

    public void contextDestroyed(ServletContextEvent event) {
        HibernateWrapper.closeSessionFactory();
    }
}
