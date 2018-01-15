package it.unitn.disi.buybuy.types;

public class Message {

    public enum Type {
        INFO, ERROR
    };
    public Type type;
    public String text;

    public Message() {
        this.type = Type.INFO;
    }
    
    public Message(String text) {
        this();
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
    
    @Override
    public String toString() {
        return this.text;
    }
    
}
