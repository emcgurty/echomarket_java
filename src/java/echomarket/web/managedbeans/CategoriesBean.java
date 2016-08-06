/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomarket.web.managedbeans;

import echomarket.hibernate.Categories;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean(name = "cats")
@RequestScoped
public class CategoriesBean extends AbstractBean implements Serializable{

    private Integer id;
    private String categoryType;

    public CategoriesBean() {
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
     * @return the categoryType
     */
    public String getCategoryType() {
        return categoryType;
    }

    /**
     * @param categoryType the categoryType to set
     */
    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Categories[] buildCatArray() {
        Categories[] catArray = null;
        List cat_list = null;
        cat_list = cat_list();
        int size_of_list = cat_list.size();
        catArray = new Categories[size_of_list];
        for (int i = 0; i < size_of_list; i++) {
            Categories cArray = (Categories) cat_list.get(i);
            catArray[i] = new Categories(cArray.getId(), cArray.getCategoryType());
        }
        return catArray;
    }

    private List cat_list() {

        List result = null;
        Session session = hib_session();
        Transaction tx = session.beginTransaction();

        try {
            result = session.createQuery("from Categories").list();
        } catch (Exception e) {
            System.out.println("Error line 74 CatBean");
            e.printStackTrace();

        }
        tx.commit();

        return result;
    }

}
