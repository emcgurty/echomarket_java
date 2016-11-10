/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomarket.web.managedbeans;


import echomarket.hibernate.ItemConditions;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean(name = "itemc")
@RequestScoped
public class ItemConditionBean extends AbstractBean implements Serializable{

//    private Integer id;
    private String condition;

    public ItemConditionBean() {
    }

   public String getItemConditionName(String cid) {
        String returnString = null;
        List result = null;
        Session session;
        Transaction tx;
        session = null;
        tx = null;
        session = hib_session();
        
        try {
            tx = session.beginTransaction();
            result = session.createQuery("from ItemConditions WHERE id = :cid")
                    .setParameter("cid", cid)
                    .list();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            System.out.println("Error in getCategoryName");
            Logger.getLogger(ItemConditionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session = null;
            tx = null;
        }

        if (result.size() == 1) {
            ItemConditions retCat = (ItemConditions) result.get((0));
            returnString = retCat.getCondition();

        } else {

            returnString = "Not found";
        }

        result = null;
        return returnString;
    }  

    public ItemConditions[] buildItemConditionsArray() {
        ItemConditions[] catArray = null;
        List cat_list = null;
        cat_list = cat_list();
        int size_of_list = cat_list.size();
        catArray = new ItemConditions[size_of_list];
        for (int i = 0; i < size_of_list; i++) {
            ItemConditions cArray = (ItemConditions) cat_list.get(i);
            catArray[i] = new ItemConditions(cArray.getId(), cArray.getCondition());
        }
        return catArray;
    }

    private List cat_list() {

        List result = null;
        Session session = hib_session();
        Transaction tx = session.beginTransaction();

        try {
            result = session.createQuery("from ItemConditions order by id").list();
        } catch (Exception e) {
            System.out.println("Error line 48 Itemm Conditions");
            e.printStackTrace();

        }
        tx.commit();

        return result;
    }

//    /**
//     * @return the id
//     */
//    public Integer getId() {
//        return id;
//    }
//
//    /**
//     * @param id the id to set
//     */
//    public void setId(Integer id) {
//        this.id = id;
//    }

    /**
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

}
