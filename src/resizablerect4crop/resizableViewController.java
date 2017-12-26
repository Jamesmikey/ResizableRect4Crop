/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resizablerect4crop;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author James
 */
public class resizableViewController implements Initializable {

private Pane cropPane;
private CroppingRectangle cropRect;
private Rectangle rect;
@FXML
private ImageView imageView;
@FXML
private Label labelX;
@FXML
private Label labelY;
@FXML
private Label labelW;
@FXML
private Label labelH;

@FXML
private StackPane stackpane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cropRect=new CroppingRectangle();
           cropPane=cropRect.createCroppingRectangle(0,0,250,330,240,330,132,185,this.imageView);
           stackpane.getChildren().add(cropPane);
           rect=cropRect.getRectBounds();
           
           labelX.textProperty().bind(rect.layoutXProperty().asString());
           labelY.textProperty().bind(rect.layoutYProperty().asString());
           labelW.textProperty().bind(rect.widthProperty().asString());
           labelH.textProperty().bind(rect.heightProperty().asString());
    }    
    
}
