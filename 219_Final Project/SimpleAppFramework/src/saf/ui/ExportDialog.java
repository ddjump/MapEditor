/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saf.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static saf.settings.AppStartupConstants.FILE_PROTOCOL;
import static saf.settings.AppStartupConstants.PATH_IMAGES;

public class ExportDialog extends Stage {
    
    static final String CSS_PATH = "mp/css/mp_style.css";
    static final String BUTTONS = "buttons";
    static final String DIALOG = "dialog";
    static final String TEXT_FIELD = "text_field";
    
    Scene scene;
    GridPane gp;
    
    VBox box;
    HBox option;
    VBox control;
    
    Label mapName;

    Button okbtn;

    public ExportDialog() {
        
        this.setTitle("Export Dialog");
        
        init(this);
        
        initStyle();
        
        setHandlers();
      
    }

    public void init(Stage owner) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        box = new VBox();
        control = new VBox();
        gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(10);
        gp.setVgap(10);
        
        mapName = new Label("You're File have been Exported");
        gp.add(mapName, 0, 1);
        
        option = new HBox();

        okbtn = new Button("okay");

        option.getChildren().add(okbtn);
        option.setAlignment(Pos.CENTER);
        
        control.getChildren().addAll(gp);
        control.setAlignment(Pos.CENTER);
        control.setSpacing(20.0);
        
        box.getChildren().add(control); 
        box.getChildren().addAll(new Label(""));
        box.getChildren().add(option);
        box.setAlignment(Pos.CENTER);
        
        scene = new Scene(box, 300, 200);
        this.setScene(scene);
    }
    
    private void setHandlers() {
        
        okbtn.setOnAction(e -> {
            this.close();
        });
    }
    
    public void initStyle() {
        
        scene.getStylesheets().add(CSS_PATH);
        
        option.getStyleClass().add(BUTTONS);
        box.getStyleClass().add(DIALOG);
    }
    
    public void setImage(ButtonBase button, String fileName) {
         
        // LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + fileName;
        Image buttonImage = new Image(imagePath);

        // SET THE IMAGE IN THE BUTTON
        button.setGraphic(new ImageView(buttonImage));
    }
    
    public void playDialog() {
        showAndWait();
    }
}

