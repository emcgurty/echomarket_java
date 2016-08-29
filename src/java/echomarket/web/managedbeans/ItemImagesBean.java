/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomarket.web.managedbeans;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

/**
 *
 * @author emm
 */
@Named
@ManagedBean(name = "itemImages")
@SessionScoped
public class ItemImagesBean extends AbstractBean implements Serializable {

    private String imageContentType;
    private Integer imageHeight;
    private Integer imageWidth;
    private String imageFileName;

    public ItemImagesBean() {
    }

    private void SaveImageFile(String filename) {
        // FileOutputStream fos = new FileOutputStream("C://borrowed_image.jpg");
        // fos.write(response);
        //fos.close();

    }

}
