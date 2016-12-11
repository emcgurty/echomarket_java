package echomarket.hibernate;

public class Categories implements java.io.Serializable {

  private int id;
  private String categoryType;

  public Categories() {
  }

  public Categories(int id, String categoryType) {
    this.id = id;
    this.categoryType = categoryType;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCategoryType() {
    return this.categoryType;
  }

  public void setCategoryType(String categoryType) {
    this.categoryType = categoryType;
  }

}
