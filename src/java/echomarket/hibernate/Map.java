package echomarket.hibernate;

public class Map  implements java.io.Serializable {


     private int id;
     private String keyText;
     private String valueText;

    public Map() {
    }

    public Map(int id, String keyText, String valueText) {
       this.id = id;
       this.keyText = keyText;
       this.valueText = valueText;
    }
   
    public Map(String keyText, String valueText) {
       this.keyText = keyText;
       this.valueText = valueText;
    }
    
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getKeyText() {
        return this.keyText;
    }
    
    public void setKeyText(String keyText) {
        this.keyText = keyText;
    }
    public String getValueText() {
        return this.valueText;
    }
    
    public void setValueText(String valueText) {
        this.valueText = valueText;
    }




}


