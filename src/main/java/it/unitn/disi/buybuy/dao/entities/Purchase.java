/*
 * Purchase.java
 */
package it.unitn.disi.buybuy.dao.entities;

import java.io.Serializable;

/**
 * The bean that map a {@code purchase} entity.
 * 
 * @author apello96
 */
public class Purchase implements Serializable{
    
    private Integer id;
    private Item item;
    private User user;

    /**
     * Returns the primary key of this purchase entity.
     * @return the id of the purchase entity.
     * 
     * @author apello96
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * Sets the new primary key of this purchase entity.
     * @param id the new id of this purchase entity.
     * 
     * @author apello96
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the item of this purchase entity.
     * @return the item of this purchase entity.
     * 
     * @author apello96
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets the new item of this purchase entity.
     * @param item the new item of this purchase entity.
     * 
     * @author apello96
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Returns the user of this purchase entity.
     * @return the user of this purchase entity.
     * 
     * @author apello96
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the new user of this purchase entity.
     * @param user the new user of this purchase entity.
     * 
     * @author apello96
     */
    public void setUser(User user) {
        this.user = user;
    }
}
