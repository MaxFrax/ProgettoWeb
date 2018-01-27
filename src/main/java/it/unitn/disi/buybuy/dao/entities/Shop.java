/*
 * Shop.java
 */
package it.unitn.disi.buybuy.dao.entities;

import java.io.Serializable;

/**
 * The bean that map a {@code shop} entity.
 * 
 * @author apello96
 */
public class Shop implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private String website;
    private Integer rating;
    private User owner;

    /**
     * Returns the primary key of this shop entity.
     * @return the id of the shop entity.
     * 
     * @author apello96
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * Sets the new primary key of this shop entity.
     * @param id the new id of this shop entity.
     * 
     * @author apello96
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the name of this shop entity.
     * @return the name of this shop entity.
     * 
     * @author apello96
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the new name of this shop entity.
     * @param name the new name of this shop entity.
     * 
     * @author apello96
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of this shop entity.
     * @return the description of this shop entity.
     * 
     * @author apello96
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the new description of this shop entity.
     * @param description the new description of this shop entity.
     * 
     * @author apello96
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the website of this shop entity.
     * @return the website of this shop entity.
     * 
     * @author apello96
     */
    public String getWebsite() {
        return website;
    }
/**
     * Sets the new website of this shop entity.
     * @param website the new website of this shop entity.
     * 
     * @author apello96
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Returns the rating of this shop entity.
     * @return the rating of this shop entity.
     * 
     * @author apello96
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Sets the new rating of this shop entity.
     * @param rating the new rating of this shop entity.
     * 
     * @author apello96
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * Returns the owner of this shop entity.
     * @return the owner of this shop entity.
     * 
     * @author apello96
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Sets the new owner of this shop entity.
     * @param owner the new owner of this shop entity.
     * 
     * @author apello96
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    
}
