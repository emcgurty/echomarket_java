package echomarket.hibernate;

public class ItemToImages implements java.io.Serializable {

  private Items itm;
  private ItemImages itmImages;
  

  public ItemToImages() {
  }
    

   //  datatable does not support return Models/Entities.  Had to use list
  public ItemToImages(Items itm, ItemImages itmImages) {
    this.itm = itm;
    this.itmImages = itmImages;

  }

  /**
   * @return the itm
   */
  public Items getItm() {
    return itm;
  }

  /**
   * @param itm the itm to set
   */
  public void setItm(Items itm) {
    this.itm = itm;
  }

  /**
   * @return the itmImages
   */
  public ItemImages getItmImages() {
    return itmImages;
  }

  /**
   * @param itmImages the itmImages to set
   */
  public void setItmImages(ItemImages itmImages) {
    this.itmImages = itmImages;
  }

  
}
