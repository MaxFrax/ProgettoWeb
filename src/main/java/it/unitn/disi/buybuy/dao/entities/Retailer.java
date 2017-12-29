/*
 * Retailer.java
 */
package it.unitn.disi.buybuy.dao.entities;

import java.io.Serializable;

/**
 * The bean that map a {@code retailer} entity.
 * 
 * @author apello96
 */
public class Retailer implements Serializable{
    private Integer id;
    private Float latitude;
    private Float longitude;
    private String postalCode;
    private String city;
    private String streetName;
    private Integer streetNumber;
    private String province;
    private String openTimetable;
    private Shop shop;

    /**
     * Returns the primary key of this retailer entity.
     * @return the id of the retailer entity.
     * 
     * @author apello96
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * Sets the new primary key of this retailer entity.
     * @param id the new id of this retailer entity.
     * 
     * @author apello96
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * Returns the latitude of this retailer entity.
     * @return the latitude of this retailer entity.
     * 
     * @author apello96
     */
    public Float getLatitude() {
        return latitude;
    }
    
    /**
     * Sets the new latitude of this Retailer entity.
     * @param latitude the new latitude of this retailer entity.
     * 
     * @author apello96
     */
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    /**
     * Returns the longitude of this retailer entity.
     * @return the longitude of this retailer entity.
     * 
     * @author apello96
     */
    public Float getLongitude() {
        return longitude;
    }
    
    /**
     * Sets the new longitude of this Retailer entity.
     * @param longitude the new latitude of this retailer entity.
     * 
     * @author apello96
     */
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
    
    /**
     * Returns the postal code of this retailer entity.
     * @return the postal code of this retailer entity.
     * 
     * @author apello96
     */
    public String getPostalCode() {
        return postalCode;
    }
    
    /**
     * Sets the new postal code of this Retailer entity.
     * @param postalCode the new postal code of this retailer entity.
     * 
     * @author apello96
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    /**
     * Returns the city of this retailer entity.
     * @return the city of this retailer entity.
     * 
     * @author apello96
     */
    public String getCity() {
        return city;
    }
    
    /**
     * Sets the new city of this Retailer entity.
     * @param city the new city of this retailer entity.
     * 
     * @author apello96
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the street name of this retailer entity.
     * @return the street name of this retailer entity.
     * 
     * @author apello96
     */
    public String getStreetName() {
        return streetName;
    }
    
    /**
     * Sets the new street name of this Retailer entity.
     * @param streetName the new street name of this retailer entity.
     * 
     * @author apello96
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    
    /**
     * Returns the street number of this retailer entity.
     * @return the street number of this retailer entity.
     * 
     * @author apello96
     */
    public Integer getStreetNumber() {
        return streetNumber;
    }
    
    /**
     * Sets the new street number of this Retailer entity.
     * @param streetNumber the new street number of this retailer entity.
     * 
     * @author apello96
     */
    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * Returns the province of this retailer entity.
     * @return the province of this retailer entity.
     * 
     * @author apello96
     */
    public String getProvince() {
        return province;
    }
    
    /**
     * Sets the new province of this Retailer entity.
     * @param province the new province of this retailer entity.
     * 
     * @author apello96
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Returns the open timetable of this retailer entity.
     * @return the open timetable of this retailer entity.
     * 
     * @author apello96
     */
    public String getOpenTimetable() {
        return openTimetable;
    }
    
    /**
     * Sets the new open timetable of this Retailer entity.
     * @param openTimetable the new open timetable of this retailer entity.
     * 
     * @author apello96
     */
    public void setOpenTimetable(String openTimetable) {
        this.openTimetable = openTimetable;
    }
    
    /**
     * Returns the shop of this retailer entity.
     * @return the shop of this retailer entity.
     * 
     * @author apello96
     */
    public Shop getShop() {
        return shop;
    }
    
    /**
     * Sets the new shop of this Retailer entity.
     * @param shop the new shop of this retailer entity.
     * 
     * @author apello96
     */
    public void setShop(Shop shop) {
        this.shop = shop;
    }    
}