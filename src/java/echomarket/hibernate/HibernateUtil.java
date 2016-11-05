package echomarket.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
     
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            // loads configuration and mappings
            Configuration configuration = new Configuration().configure();
            System.out.println("Passed line 19 in Hib Util");
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            System.out.println("Passed line 21 in Hib Util"); 
            // builds a session factory from the service registry
            try {
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch(Exception e)          {
                System.out.println("Error at line 22 in Hib UTIL");
                e.printStackTrace();
                
            }
        }
         
        return sessionFactory;
    }
}