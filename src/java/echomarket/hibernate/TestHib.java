/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * This is essentially a junk test file
 */
package echomarket.hibernate;

//import echomarket.web.managedbeans.ApplicationServlet;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.hibernate.Session;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.Query;
import org.hibernate.Transaction;

public class TestHib {

    public static void main(String[] args) {

        List result = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        String absoluteWebPath = ectx.getRealPath("/");
        System.out.printf(absoluteWebPath);
        String uid = "73de2c9f-a109-4ded-b8df-96b515e63ece";
        String bid = "7f5172fc-8d8f-494a-92e0-7cbc0d67cefe";
       // Borrowers bb = new Borrowers(bid,uid);
        //ItemImages ii = new ItemImages(bid);
        //Object obj = bb.getItemImages();
//        
//        
//        //String hql = "SELECT b.first_name, i.image_file_name FROM borrowers as b JOIN item_images as i  ON b.user_id = i.borrower_id  where  b.user_id = '" + bid + "'";
//        String hql = "from Borrowers p join p.itemImages" ;
////        Borrowers bb = (Borrowers) session
////                .createQuery(hql)
////                .setParameter("bid", bid);
////        ItemImages ii = (ItemImages) session.load(ItemImages.class, bid);
        System.out.printf(bid);
        System.out.printf("");

//for (Object[] aRow : listResult) {
//    Product product = (Product) aRow[0];
//    Category category = (Category) aRow[1];
//    System.out.println(product.getName() + " - " + category.getName());
//}
//        String q = null;
//        String bid = "58305897-0ace-45ea-8472-b52743440bdb";
//        try {
//        result = session.createQuery(hql)
////                .setParameter("bid", bid)
//                .list();
//            tx.commit();
//        } catch (Exception e) {
//            
//            System.out.printf("QUERY ERROR");
//                     e.printStackTrace();
//        }

//        List asdas = result;
//        ApplicationServlet as = new ApplicationServlet();
//        
//        ServletContext context =  getServletContext();  
//        String s = getServletContext().getInitParameter("app_pw");
//     OutputStream out = null;
//        InputStream filecontent = null;
//        //String sPath1 = new File(".").getCanonicalPath();
//        String sPath2 = "/images/borrower_images/";
//        String sPath3 = "fef1a1b7-3154-42c9-a416-e6a2e1caf188" ;
//        //String sPath4 = getFileName(ui);
//        File files = new File(sPath2 + sPath3);
//        if (!files.exists()) {
//            if (files.mkdirs()) {
//            }
//        }
////        Date dd = new Date();
//
//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        //session.beginTransaction();
//        Transaction tx = session.beginTransaction();
//        ItemImages ii = new ItemImages(UUID.randomUUID().toString(), null, null, null, null, null, null, dd, dd, dd, "temp", null, null);
//        session.save(ii);
//        tx.commit();
////        List result = session.createQuery("from Countries ORDER BY id").list();
////        
//////        List result = session.createQuery("from ContactDescribes Where purposeType = :pt ORDER BY optionValue")
//////                    .setParameter("pt", "borrower")
//////                    .list();
////        
////        //List result = session.createQuery("from Categories ORDER BY category_type").list();
////        session.getTransaction().commit();
////        
////        System.out.printf("SESE");
//        
//         List result = null;
//        //Session session = hib_session();
//        //
//
//        try {
//            result = session.createQuery("from Purpose").list();
////                    .setParameter("pt", "lender")
////                    .list();
//        } catch (Exception e) {
//            System.out.println("Error at line 57 in CDBeans");
//            e.printStackTrace();
//
//        }
//        session.getTransaction().commit();
//ServletContext application= getServletContext();  
//        
//
//        String hostname = ctx.getInitParameter("my.config.hostname");
//        Borrowers borrowers = new Borrowers();
//        Addresses addr = new Addresses();
////set the stock and stockDailyRecords  data
//
//        addr.setBorrowers(borrowers);
//        borrowers.getAddresses().add(addr);
//
//        session.save(borrowers);
//        session.save(addr);
//
//Stock stock = new Stock();
//StockDailyRecord stockDailyRecords = new StockDailyRecord();
////set the stock and stockDailyRecords  data
//
//stockDailyRecords.setStock(stock);
//stock.getStockDailyRecords().add(stockDailyRecords);
//
//session.save(stock);
//session.save(stockDailyRecords);
    }
}
