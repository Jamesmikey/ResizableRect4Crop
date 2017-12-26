/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resizablerect4crop;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;



/**
 *
 * @author James
 */
public class CroppingRectangle{
  
    
       private double offsetX;
       private double offsetY;
       private double mouseX;
       private double mouseY;
       private double X;
       private double Y;
       private final double edges_rect_height=10;
       private final double edges_rect_width=10;
       private double offX;
       private double offY;
       private double cur_X;
       private double cur_Y;
       private boolean check_limit;  
       private Rectangle rect;
       private boolean check_final_limit=true;
       private Pane rectPane;
      

       
       
    

    
        
  
public Pane createCroppingRectangle(double re_x,double re_y,double or_rect_width, double or_rect_height, double MAX_REZ_WIDTH, double MAX_REZ_HEIGHT, double MIN_REZ_WIDTH, double MIN_REZ_HEIGHT, Node fitNode){
    
 //create an  image view now
        
        Bounds bounds=fitNode.getBoundsInParent();
        
        //creating Pane
        
           rectPane = new Pane();
           rectPane.setMaxWidth(bounds.getWidth());
           rectPane.setMaxHeight(bounds.getHeight());
       
           
         //check if given domensions given exceeds the dimension of an image
         
         
         if(or_rect_height>bounds.getHeight() || or_rect_width>bounds.getWidth()){
         or_rect_height=bounds.getHeight();
          
         or_rect_width=bounds.getWidth();
                   
         }
         
      
         if(!((re_x>0)  && ((re_x+or_rect_width)<bounds.getMaxX()))){
             re_x=bounds.getMinX();
             
         }
         
         if(!((re_y>0)  && ((re_y+or_rect_height)<bounds.getMaxY()))){
         re_y=bounds.getMinY();
         }
         
         
         
        rect=new Rectangle(0,0,or_rect_width,or_rect_height);
        rect.setLayoutX(re_x);
        rect.setLayoutY(re_y);
        rect.setStyle("-fx-stroke:white;");
        rect.setFill(Color.rgb(0,0,0,0));
        rect.setStrokeWidth(1);
        rect.setStrokeType(StrokeType.INSIDE);
        
        
        //Creating side rectangles
        Rectangle top_inv_rect=new Rectangle(bounds.getMinX(),bounds.getMinY());
        Rectangle left_inv_rect=new Rectangle(bounds.getMinX(),bounds.getMinY());
        Rectangle lower_inv_rect=new Rectangle(bounds.getMinX(),bounds.getMinY());
        Rectangle right_inv_rect=new Rectangle(bounds.getMinX(),bounds.getMinY());
        
        top_inv_rect.setStyle("-fx-fill:#000000;-fx-opacity:0.62");
        top_inv_rect.widthProperty().bind(rect.widthProperty().add(left_inv_rect.widthProperty()));
        top_inv_rect.heightProperty().bind(rect.layoutYProperty());
        
        
         
         left_inv_rect.setStyle("-fx-fill:#000000;-fx-opacity:0.62");
         left_inv_rect.layoutYProperty().bind(rect.layoutYProperty());
         left_inv_rect.heightProperty().bind(rect.heightProperty());
         left_inv_rect.widthProperty().bind(rect.layoutXProperty());
       
         
        lower_inv_rect.setStyle("-fx-fill:#000000;-fx-opacity:0.62");
        lower_inv_rect.layoutYProperty().bind((top_inv_rect.heightProperty().add(rect.heightProperty())));
        lower_inv_rect.heightProperty().bind(lower_inv_rect.layoutYProperty().multiply(-1).add(bounds.getMaxY()));
        lower_inv_rect.widthProperty().bind(left_inv_rect.widthProperty().add(rect.widthProperty()));
        
        right_inv_rect.setStyle("-fx-fill:#000000;-fx-opacity:0.62");
        right_inv_rect.layoutXProperty().bind(top_inv_rect.widthProperty());
        right_inv_rect.widthProperty().bind(top_inv_rect.widthProperty().multiply(-1).add(bounds.getMaxX()));
        right_inv_rect.setHeight(bounds.getMaxY());
        

        
        
        
        
        
        
        
        
//listerners
        
rect.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if((Double)newValue<MIN_REZ_WIDTH){
                rect.setWidth(MIN_REZ_WIDTH);
            }
            
            if((Double)newValue>MAX_REZ_WIDTH){
                rect.setWidth(MAX_REZ_WIDTH);
            }
            
            //Checking the right side of the triangle
            if((rect.getLayoutX()+(double)newValue)>(bounds.getMaxX())){
              rect.setLayoutX(bounds.getMaxX()-(rect.getWidth()));
            }else{
                    double diff=(double)oldValue-(double)newValue;
                    if(check_limit){
                        rect.setLayoutX(rect.getLayoutX()+diff);
                    } 
            }
        });
        
        rect.heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if((Double)newValue<MIN_REZ_HEIGHT){
                rect.setHeight(MIN_REZ_HEIGHT);
            }
            
            if((Double)newValue>MAX_REZ_HEIGHT){
                rect.setHeight(MAX_REZ_HEIGHT);
            }
            
            //Checking the right side of the triangle
            if((rect.getLayoutY()+rect.getHeight())>(bounds.getMaxY())){
                rect.setLayoutY(bounds.getMaxY()-(rect.getHeight()));
            }else{
                double diff=(double)oldValue-(double)newValue;
                if(check_limit){
                    rect.setLayoutY(rect.getLayoutY()+diff);
                }
            }
        });
         
         
rect.layoutXProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if((Double)newValue<(bounds.getMinX())){
                rect.setLayoutX(0);
                check_final_limit=false;
            }else{
                check_final_limit=true;
            }
            
            if(((Double)newValue+rect.getWidth())>(bounds.getMaxX())){
                rect.setLayoutX(bounds.getMaxX()-(rect.getWidth()));
            }
        });
         
         
rect.layoutYProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if((Double)newValue<(bounds.getMinY())){
                try {
                    rect.setLayoutY(bounds.getMinY());
                } catch (Exception e) {
                }
            }
            
            if(((Double)newValue+rect.getHeight())>(bounds.getMaxY())){
                rect.setLayoutY(bounds.getMaxY()-(rect.getHeight()));
            }
        });
         
         
      
       //creating rectangles for edges of resizable rectangle
    Rectangle middle_left=new Rectangle(0,0,edges_rect_width,edges_rect_height);
    middle_left.setStyle("-fx-stroke:#726e6e;-fx-fill:#fff9f9;");
   
    middle_left.setOnMouseEntered(e->{
            middle_left.setCursor(Cursor.H_RESIZE);
       });
       
            
    middle_left.setOnMousePressed(e->{
            cur_X=e.getSceneX();
            cur_Y=e.getSceneY();
    });
       

    middle_left.setOnMouseDragged(e->{
        offX=e.getSceneX()-cur_X;
        check_limit = true;
        rect.setWidth(rect.getWidth() - offX);
        check_limit = false;
        cur_X=e.getSceneX();
    });
       
       
     
       
    Rectangle upper_left=new Rectangle(0,0,edges_rect_width,edges_rect_height);
    upper_left.setStyle("-fx-stroke:#726e6e;-fx-fill:#fff9f9;");
        
    upper_left.setOnMouseEntered(e->{
        upper_left.setCursor(Cursor.SE_RESIZE);
    });
        
    upper_left.setOnMousePressed(e->{
        cur_X=e.getSceneX();
        cur_Y=e.getSceneY();
    });
       

    upper_left.setOnMouseDragged(e->{
           offX=e.getSceneX()-cur_X;
           offY=e.getSceneY()-cur_Y;
           check_limit=true;
           rect.setWidth(rect.getWidth()-offX);
           rect.setHeight(rect.getHeight()-offY);
           check_limit=false;
           cur_X=e.getSceneX();
           cur_Y=e.getSceneY();
    });
        
    
        
    Rectangle lower_left=new Rectangle(0,0,edges_rect_width,edges_rect_height);
    lower_left.setStyle("-fx-stroke:#726e6e;-fx-fill:#fff9f9;"); 
      
    lower_left.setOnMouseEntered(e->{
       lower_left.setCursor(Cursor.SW_RESIZE);
    });
        
    lower_left.setOnMousePressed(e->{
      cur_X=e.getSceneX();
      cur_Y=e.getSceneY();
    });
       

    lower_left.setOnMouseDragged(e->{
           offX=e.getSceneX()-cur_X;
           offY=e.getSceneY()-cur_Y;
           check_limit=true;
           rect.setWidth(rect.getWidth()-offX);
           check_limit=false;
           rect.setHeight(rect.getHeight()+offY);
           cur_X=e.getSceneX();
           cur_Y=e.getSceneY();
    });
        
    Rectangle upper_center=new Rectangle(0,0,edges_rect_width,edges_rect_height);
    upper_center.setStyle("-fx-stroke:#726e6e;-fx-fill:#fff9f9;");
    
    upper_center.setOnMouseEntered(e->{
       upper_center.setCursor(Cursor.S_RESIZE);
    });
        
    upper_center.setOnMousePressed(e->{
      cur_X=e.getSceneX();
      cur_Y=e.getSceneY();
    });
       

    upper_center.setOnMouseDragged(e->{
        offX=e.getSceneX()-cur_X;
        offY=e.getSceneY()-cur_Y;
        check_limit=true;
        rect.setHeight(rect.getHeight()-offY);
        check_limit=false;
        cur_Y=e.getSceneY();
    });
       
       
       
    Rectangle upper_right=new Rectangle(0,0,edges_rect_width,edges_rect_height);
    upper_right.setStyle("-fx-stroke:#726e6e;-fx-fill:#fff9f9;"); 
      
    upper_right.setOnMouseEntered(e->{
       upper_right.setCursor(Cursor.NE_RESIZE);
    });
        
    upper_right.setOnMousePressed(e->{
      cur_X=e.getSceneX();
      cur_Y=e.getSceneY();
   
    });
       

    upper_right.setOnMouseDragged(e->{
           offX=e.getSceneX()-cur_X;
           offY=e.getSceneY()-cur_Y; 
           rect.setWidth(rect.getWidth()+offX);
           check_limit=true;
           rect.setHeight(rect.getHeight()-offY);
           check_limit=false;
           cur_X=e.getSceneX();
           cur_Y=e.getSceneY();
    });
        
       
    Rectangle middle_right=new Rectangle(0,0,edges_rect_width,edges_rect_height);
    middle_right.setStyle("-fx-stroke:#726e6e;-fx-fill:#fff9f9;");
    
    middle_right.setOnMouseEntered(e->{
       middle_right.setCursor(Cursor.H_RESIZE);
    });
       
    middle_right.setOnMousePressed(e->{
      cur_X=e.getSceneX();
      cur_Y=e.getSceneY();
    });
       

    middle_right.setOnMouseDragged(e->{
           offX=e.getSceneX()-cur_X;
           offY=e.getSceneY()-cur_Y;
           rect.setWidth(rect.getWidth()+offX);
           cur_X=e.getSceneX();
    });
       
       
    Rectangle lower_right=new Rectangle(0,0,edges_rect_width,edges_rect_height);
    lower_right.setStyle("-fx-stroke:#726e6e;-fx-fill:#fff9f9;");
    
    lower_right.setOnMouseEntered(e->{
       lower_right.setCursor(Cursor.SE_RESIZE);
    });
       
    lower_right.setOnMousePressed(e->{
      cur_X=e.getSceneX();
      cur_Y=e.getSceneY();
      
    });
       

    lower_right.setOnMouseDragged(e->{
           offX=e.getSceneX()-cur_X;
           offY=e.getSceneY()-cur_Y;
           rect.setWidth(rect.getWidth()+offX);
           rect.setHeight(rect.getHeight()+offY);
           cur_X=e.getSceneX();
           cur_Y=e.getSceneY();
    });
        
    Rectangle lower_center=new Rectangle(0,0,edges_rect_width,edges_rect_height);
    lower_center.setStyle("-fx-stroke:#726e6e;-fx-fill:#fff9f9;");
    
    lower_center.setOnMouseEntered(e->{
       lower_center.setCursor(Cursor.S_RESIZE);
    });
       
    lower_center.setOnMousePressed(e->{
      cur_X=e.getSceneX();
      cur_Y=e.getSceneY();
    });
       

    lower_center.setOnMouseDragged(e->{
            offX=e.getSceneX()-cur_X;
            offY=e.getSceneY()-cur_Y;
           rect.setHeight(rect.getHeight()+offY);
           cur_Y=e.getSceneY();
    });
       
       
      
       
       
        Line line1=new Line();
        line1.setStyle("-fx-stroke:#e5e0e0;-fx-opacity:0.5");
        Line line2=new Line();
        line2.setStyle("-fx-stroke:#e5e0e0;-fx-opacity:0.5");
        Line line3=new Line();
        line3.setStyle("-fx-stroke:#e5e0e0;-fx-opacity:0.5");
        Line line5=new Line();
        line5.setStyle("-fx-stroke:#e5e0e0;-fx-opacity:0.5");
       
        
        //Binding the line with rect properties
        line1.startXProperty().bind(rect.layoutXProperty().add(rect.widthProperty().divide(3)));
        line1.startYProperty().bind(rect.layoutYProperty());
        line1.endXProperty().bind(rect.layoutXProperty().add(rect.widthProperty().divide(3)));
        line1.endYProperty().bind(rect.layoutYProperty().add(rect.heightProperty()));
       
        
        line3.startXProperty().bind(rect.layoutXProperty().add(rect.widthProperty().divide(3).multiply(2)));
        line3.startYProperty().bind(rect.layoutYProperty());
        line3.endXProperty().bind(rect.layoutXProperty().add(rect.widthProperty().divide(3).multiply(2)));
        line3.endYProperty().bind(rect.layoutYProperty().add(rect.heightProperty()));
        
        
      
        
        
        line2.startXProperty().bind(rect.layoutXProperty());
        line2.startYProperty().bind(rect.layoutYProperty().add(rect.heightProperty().divide(3)));
        line2.endXProperty().bind(rect.layoutXProperty().add(rect.widthProperty()));
        line2.endYProperty().bind(rect.layoutYProperty().add(rect.heightProperty().divide(3)));
  
        
        line5.startXProperty().bind(rect.layoutXProperty());
        line5.startYProperty().bind(rect.layoutYProperty().add(rect.heightProperty().divide(3).multiply(2)));
        line5.endXProperty().bind(rect.layoutXProperty().add(rect.widthProperty()));
        line5.endYProperty().bind(rect.layoutYProperty().add(rect.heightProperty().divide(3).multiply(2)));
        
      
        //Binding rectangles to the bigger rectangle
        middle_left.layoutXProperty().bind(rect.layoutXProperty().subtract(middle_left.widthProperty().divide(2)));
        middle_left.layoutYProperty().bind(rect.layoutYProperty().add((rect.heightProperty().divide(2)).subtract(middle_left.heightProperty().divide(2))));
        
        //binding upper left
        upper_left.layoutXProperty().bind(rect.layoutXProperty().subtract(upper_left.widthProperty().divide(2)));
        upper_left.layoutYProperty().bind(rect.layoutYProperty().subtract(upper_left.heightProperty().divide(2)));
        
        //binding the lowerleft
        lower_left.layoutXProperty().bind(rect.layoutXProperty().subtract(lower_left.widthProperty().divide(2)));
        lower_left.layoutYProperty().bind(rect.layoutYProperty().add(rect.heightProperty()).subtract(lower_left.heightProperty().divide(2)));
        
        
        //Binding the upper center
        upper_center.layoutXProperty().bind(rect.layoutXProperty().add(rect.widthProperty().divide(2)).subtract(upper_center.widthProperty().divide(2)));
        upper_center.layoutYProperty().bind(rect.layoutYProperty().subtract(upper_center.heightProperty().divide(2)));
       
        //Binding upper right
        upper_right.layoutXProperty().bind(rect.layoutXProperty().add(rect.widthProperty()).subtract(upper_right.widthProperty().divide(2)));
        upper_right.layoutYProperty().bind(rect.layoutYProperty().subtract(upper_right.heightProperty().divide(2)));
        
        //Binding middle right
        middle_right.layoutXProperty().bind(rect.layoutXProperty().add(rect.widthProperty()).subtract(middle_right.widthProperty().divide(2)));
        middle_right.layoutYProperty().bind(rect.layoutYProperty().add(rect.heightProperty().divide(2)).subtract(middle_right.heightProperty().divide(2)));
        
        //BInding the lower right
        lower_right.layoutXProperty().bind(rect.layoutXProperty().add(rect.widthProperty()).subtract(lower_right.widthProperty().divide(2)));
        lower_right.layoutYProperty().bind(rect.layoutYProperty().add(rect.heightProperty()).subtract(lower_right.heightProperty().divide(2)));
        
        //BInding the lower center
        lower_center.layoutXProperty().bind(rect.layoutXProperty().add(rect.widthProperty().divide(2)).subtract(lower_center.widthProperty().divide(2)));
        lower_center.layoutYProperty().bind(rect.layoutYProperty().add(rect.heightProperty()).subtract(lower_center.heightProperty().divide(2)));
        
        
    rect.setOnMouseEntered(e->{rect.setCursor(Cursor.MOVE);});
    rect.setOnMousePressed(e->{
        if(e.getButton()==MouseButton.PRIMARY){
            rect.setCursor(Cursor.MOVE);
            mouseX=e.getSceneX();
            mouseY=e.getSceneY();
            X=rect.getLayoutX();
            Y=rect.getLayoutY();
        }
    });
        
        
    rect.setOnMouseDragged(e->{
        if(e.getButton()==MouseButton.PRIMARY){
            offsetX=e.getSceneX()-mouseX;
            offsetY=e.getSceneY()-mouseY;
            X+=offsetX;
            Y+=offsetY;
            rect.relocate(offsetX+X, offsetY+Y);
            mouseX=e.getSceneX();
            mouseY=e.getSceneY();   
        }
    });
        
        rectPane.getChildren().addAll(FXCollections.observableArrayList(top_inv_rect,left_inv_rect,lower_inv_rect,right_inv_rect,rect,line1,line2,line3,line5,upper_right,lower_left,middle_left,
                                                                        upper_left,lower_center,upper_center,lower_right,middle_right));
   return rectPane;
}

//
public  Rectangle getRectBounds(){
return rect;
}
        
        
    
}

