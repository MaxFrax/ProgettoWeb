package it.unitn.disi.buybuy.types;

import it.unitn.disi.buybuy.dao.entities.Item;
import it.unitn.disi.buybuy.dao.entities.Retailer;


public class ItemRetailerPair {
    
    Item key;
    Retailer value;
    
    public ItemRetailerPair(Item key, Retailer value) {
        this.key = key;
        this.value = value;
    }
    
    public Item getKey() {
        return this.key;
    }
    
    public Retailer getValue() {
        return this.value;
    }
    
    public void setKey(Item key) {
        this.key = key;
    }
    
    public void setValue(Retailer value) {
        this.value = value;
    }
    
}
