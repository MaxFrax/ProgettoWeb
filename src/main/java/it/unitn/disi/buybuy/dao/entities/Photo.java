/*
 * Photo.java
 */
package it.unitn.disi.buybuy.dao.entities;

import java.io.Serializable;

/**
 * The bean that map a {@code photo} entity.
 * 
 * @author apello96
 */
public class Photo implements Serializable{
    
    private Integer id;
    private String link;
    private Item item;
    private Shop shop;

    /**
     * Returns the primary key of this photo entity.
     * @return the id of the photo entity.
     * 
     * @author apello96
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * Sets the new primary key of this photo entity.
     * @param id the new id of this photo entity.
     * 
     * @author apello96
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the link of this photo entity.
     * @return the link of this photo entity.
     * 
     * @author apello96
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the new link of this photo entity.
     * @param link the new link of this photo entity.
     * 
     * @author apello96
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Returns the item of this photo entity.
     * @return the item of this photo entity.
     * 
     * @author apello96
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets the new item of this photo entity.
     * @param item link the new item of this photo entity.
     * 
     * @author apello96
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Returns the shop of this photo entity.
     * @return the shop of this photo entity.
     * 
     * @author apello96
     */
    public Shop getShop() {
        return shop;
    }

    /**
     * Sets the new shop of this photo entity.
     * @param shop the new shop of this photo entity.
     * 
     * @author apello96
     */
    public void setShop(Shop shop) {
        this.shop = shop;
    }
        
}
