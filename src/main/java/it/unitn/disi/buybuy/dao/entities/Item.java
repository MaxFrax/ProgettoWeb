/*
 * Item.java
 */
package it.unitn.disi.buybuy.dao.entities;

import java.io.Serializable;

/**
 * The bean that map a {@code item} entity.
 *
 * @author apello96
 */
public class Item implements Serializable {

    private Integer id;
    private String name;
    private String description;
    private Float price;
    private Category category;
    private Shop seller;
    private Integer rating;
    private Integer reviewCount;

    /**
     * Returns the primary key of this item entity.
     *
     * @return the id of the item entity.
     *
     * @author apello96
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the new primary key of this item entity.
     *
     * @param id the new id of this item entity.
     *
     * @author apello96
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the name of this item entity.
     *
     * @return the name of this item entity.
     *
     * @author apello96
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the new name of this item entity.
     *
     * @param name the new name of this item entity.
     *
     * @author apello96
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of this item entity.
     *
     * @return the description of this item entity.
     *
     * @author apello96
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the new description of this item entity.
     *
     * @param description the new description of this item entity.
     *
     * @author apello96
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the price of this item entity.
     *
     * @return the price of this item entity.
     *
     * @author apello96
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Sets the new price of this item entity.
     *
     * @param price the new price of this item entity.
     *
     * @author apello96
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * Returns the category of this item entity.
     *
     * @return the category of this item entity.
     *
     * @author apello96
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the new category of this item entity.
     *
     * @param category the new category of this item entity.
     *
     * @author apello96
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Returns the seller of this item entity.
     *
     * @return the seller of this item entity.
     *
     * @author apello96
     */
    public Shop getSeller() {
        return seller;
    }

    /**
     * Sets the new seller of this item entity.
     *
     * @param seller the new seller of this item entity.
     *
     * @author apello96
     */
    public void setSeller(Shop seller) {
        this.seller = seller;
    }
    
    public Integer getRating() {
        return this.rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public Integer getReviewCount() {
        return this.reviewCount;
    }
    
    public void setReviewCount(Integer count) {
        this.reviewCount = count;
    }
    
}
