/*
 * Issue.java
 */
package it.unitn.disi.buybuy.dao.entities;

import java.io.Serializable;

/**
 * The bean that map a {@code issue} entity.
 * 
 * @author apello96
 */
public class Issue implements Serializable{
    
    public enum AdminChoice{REFUND, DO_NOTHING, BAD_REVIEWS, NOT_VALID}
    
    private Integer id;
    private String userDescription;
    private AdminChoice adminChoice;
    private Purchase purchase;

    /**
     * Returns the primary key of this issue entity.
     * @return the id of the issue entity.
     * 
     * @author apello96
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * Sets the new primary key of this issue entity.
     * @param id the new id of this issue entity.
     * 
     * @author apello96
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the user description of this issue entity.
     * @return the user description of this issue entity.
     * 
     * @author apello96
     */
    public String getUserDescription() {
        return userDescription;
    }

    /**
     * Sets the new user description of this issue entity.
     * @param userDescription the new user description of this issue entity.
     * 
     * @author apello96
     */
    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    /**
     * Returns the admin choice of this issue entity.
     * @return the admin choice of this issue entity.
     * 
     * @author apello96
     */
    public AdminChoice getAdminChoice() {
        return adminChoice;
    }

    /**
     * Sets the new admin choice of this issue entity.
     * @param adminChoice the new admin choice of this issue entity.
     * 
     * @author apello96
     */
    public void setAdminChoice(AdminChoice adminChoice) {
        this.adminChoice = adminChoice;
    }

    /**
     * Returns the purchase of this issue entity.
     * @return the purchase of this issue entity.
     * 
     * @author apello96
     */
    public Purchase getPurchase() {
        return purchase;
    }

    /**
     * Sets the new purchase of this issue entity.
     * @param purchase the new purchase of this issue entity.
     * 
     * @author apello96
     */
    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
