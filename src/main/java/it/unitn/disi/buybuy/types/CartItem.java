package it.unitn.disi.buybuy.types;

import it.unitn.disi.buybuy.dao.entities.Item;
import java.io.Serializable;


public class CartItem implements Serializable {
    
    private Item item = null;
    private Integer quantity = null;

    public CartItem(Item item, Integer qty) {
        this.item = item;
        this.quantity = qty;
    }
    
    public void setItem(Item item) {
        this.item = item;
    }
    
    public Item getItem() {
        return this.item;
    }
    
    public void setQuantity(Integer qty) {
        this.quantity = qty;
    }
    
    public Integer getQuantity() {
        return this.quantity;
    }
    
}
