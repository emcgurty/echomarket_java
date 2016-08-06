/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomarket.web.managedbeans;


import echomarket.hibernate.ItemConditions;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean(name = "itemc")
@RequestScoped
public class ItemConditionBean extends AbstractBean implements Serializable{

    private Integer id;
    private String condition;

    public ItemConditionBean() {
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
            result = session.createQuery("from ItemConditions").list();
        } catch (Exception e) {
            System.out.println("Error line 48 Itemm Conditions");
            e.printStackTrace();

        }
        tx.commit();

        return result;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
