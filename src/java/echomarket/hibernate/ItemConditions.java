package echomarket.hibernate;
// Generated Aug 5, 2016 12:14:14 PM by Hibernate Tools 4.3.1

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ItemConditions generated by hbm2java
 */
@Entity
@Table(name = "borrowers")
public class ItemConditions implements java.io.Serializable {

    @Id
    private int id;
    private String condition;

    public ItemConditions() {
    }

    public ItemConditions(int id, String condition) {
        this.id = id;
        this.condition = condition;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

}
