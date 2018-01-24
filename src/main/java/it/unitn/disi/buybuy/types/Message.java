package it.unitn.disi.buybuy.types;

import java.io.Serializable;

public class Message implements Serializable {

    public enum Type {
        INFO, ERROR
    };
    private Type type;
    private String text;
    
    public Message() {
        this.type = Type.INFO;
        this.text = null;
    }
    
    public Message(String text) {
        this.type = Type.INFO;
        this.text = text;
    }
    
    public Message(Type type) {
        this.type = type;
    }
    
    public Message(String text, Type type) {
        this.text = text;
        this.type = type;
    }
                
    public void setType(Type type) {
        this.type = type;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public String getText() {
        return this.text;
    }
    
    @Override
    public String toString() {
        return this.text;
    }
    
}
