/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomarket.hibernate;

import java.util.List;
import org.hibernate.Session;



public class TestHib {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from Countries ORDER BY id").list();
        
//        List result = session.createQuery("from ContactDescribes Where purposeType = :pt ORDER BY optionValue")
//                    .setParameter("pt", "borrower")
//                    .list();
        
        //List result = session.createQuery("from Categories ORDER BY category_type").list();
        session.getTransaction().commit();
        
        System.out.printf("SESE");
        
    }
}

