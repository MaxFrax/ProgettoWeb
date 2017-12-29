/*
 * User.java
 */
package it.unitn.disi.buybuy.dao.entities;

import java.io.Serializable;

/**
 * The bean that map a {@code user} entity.
 * 
 * @author apello96
 */
public class User implements Serializable{
    
    public enum Type {REGISTERED, SELLER, ADMINISTRATOR};
    
    private Integer id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String hashPassword;
    private Type type;

    /**
     * Returns the primary key of this user entity.
     * @return the id of the user entity.
     * 
     * @author apello96
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * Sets the new primary key of this user entity.
     * @param id the new id of this user entity.
     * 
     * @author apello96
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * Returns the name of this user entity.
     * @return the name of this user entity.
     * 
     * @author apello96
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the new name of this user entity.
     * @param name the new name of this user entity.
     * 
     * @author apello96
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the lastname of this user entity.
     * @return the lastname of this user entity.
     * 
     * @author apello96
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the new lastname of this user entity.
     * @param lastname the new lastname of this user entity.
     * 
     * @author apello96
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    /**
     * Returns the username of this user entity.
     * @return the username of this user entity.
     * 
     * @author apello96
     */    
    public String getUsername() {
        return username;
    }

    /**
     * Sets the new username of this user entity.
     * @param username the new username of this user entity.
     * 
     * @author apello96
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the email of this user entity.
     * @return the email of this user entity.
     * 
     * @author apello96
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the new email of this user entity.
     * @param email the new email of this user entity.
     * 
     * @author apello96
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the hash password of this user entity.
     * @return the hash password of this user entity.
     * 
     * @author apello96
     */
    public String getHashPassword() {
        return hashPassword;
    }

    /**
     * Sets the new hash password of this user entity.
     * @param hashPassword the new hash password of this user entity.
     * 
     * @author apello96
     */
    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    /**
     * Returns the type of this user entity.
     * @return the type of this user entity.
     * 
     * @author apello96
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the new type of this user entity.
     * @param type the new type of this user entity.
     * 
     * @author apello96
     */
    public void setType(Type type) {
        this.type = type;
    }
    
}
