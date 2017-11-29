/*
 * Review.java
 */
package it.unitn.disi.buybuy.dao.entities;

import java.io.Serializable;

/**
 * The bean that map a {@code review} entity.
 * 
 * @author apello96
 */
public class Review implements Serializable{
    private Integer id;
    private String description;
    private Integer rating;
    private Item item;
    private User user;

    /**
     * Returns the primary key of this review entity.
     * @return the id of the review entity.
     * 
     * @author apello96
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * Sets the new primary key of this review entity.
     * @param id the new id of this review entity.
     * 
     * @author apello96
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the description of this review entity.
     * @return the description of this review entity.
     * 
     * @author apello96
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the new description of this review entity.
     * @param description the new description of this review entity.
     * 
     * @author apello96
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the rating of this review entity.
     * @return the rating of this review entity.
     * 
     * @author apello96
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Sets the new rating of this review entity.
     * @param rating the new rating of this review entity.
     * 
     * @author apello96
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * Returns the item of this review entity.
     * @return the item of this review entity.
     * 
     * @author apello96
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets the new item of this review entity.
     * @param item the new item of this review entity.
     * 
     * @author apello96
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Returns the user of this review entity.
     * @return the user of this review entity.
     * 
     * @author apello96
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the new user of this review entity.
     * @param user the new user of this review entity.
     * 
     * @author apello96
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    
}
