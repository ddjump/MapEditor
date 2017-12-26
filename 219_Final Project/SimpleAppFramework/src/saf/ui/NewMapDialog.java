package saf.ui;

import java.io.File;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import saf.AppTemplate;
import static saf.settings.AppStartupConstants.FILE_PROTOCOL;
import static saf.settings.AppStartupConstants.PATH_IMAGES;

public class NewMapDialog extends Stage {

    static final String CSS_PATH = "mp/css/mp_style.css";
    static final String BUTTONS = "buttons";
    static final String DIALOG = "dialog";
    static final String TEXT_FIELD = "text_field";

    AppTemplate app;

    Scene scene;
    GridPane gp;

    VBox box;
    HBox option;
    VBox control;

    Label regionName;
    Label parentRegionDirectory;
    Label mapCoordinates;
    Label parentRegionPath;
    Label mapCoordinatePath;

    TextField regionNameText;

    Button filebtn1;
    Button filebtn2;
    Button okbtn;
    Button cancelbtn;

    File locationPath;
    File selectedFile;
    
    String Region;
    String parentRegion;
    
    boolean moveOn;

    public NewMapDialog(AppTemplate TA) {

        app = TA;

        this.setTitle("New Map Dialog");

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

        regionName = new Label("Region Name:");
        regionNameText = new TextField();
        gp.add(regionName, 0, 1);
        gp.add(regionNameText, 1, 1);

        parentRegionDirectory = new Label("Region Parent Directory:");
        parentRegionPath = new Label("");
        filebtn1 = new Button();
        filebtn1.setDisable(true);
        setImage(filebtn1, "load.png");
        gp.add(parentRegionDirectory, 0, 2);
        gp.add(filebtn1, 1, 2);
        gp.add(parentRegionPath, 2, 2);
        
        mapCoordinates = new Label("Map Coordinates:");
        mapCoordinatePath = new Label("");
        filebtn2 = new Button();
        filebtn2.setDisable(true);
        setImage(filebtn2, "coordinates.png");
        gp.add(mapCoordinates, 0, 3);
        gp.add(filebtn2, 1, 3);
        gp.add(mapCoordinatePath, 2, 3);

        option = new HBox();

        okbtn = new Button("Submit");
        okbtn.setDisable(true);
        cancelbtn = new Button("Cancel");

        regionNameText.setMaxWidth(250.0);

        option.getChildren().addAll(okbtn, cancelbtn);
        option.setSpacing(10.0);
        option.setAlignment(Pos.CENTER);
        control.getChildren().addAll(gp);
        control.setAlignment(Pos.CENTER);
        control.setSpacing(20.0);

        box.getChildren().addAll(control);
        box.getChildren().addAll(new Label(""));
        box.getChildren().add(option);
        box.setSpacing(50.0);
        box.setAlignment(Pos.CENTER);

        scene = new Scene(box, 1000, 600);
        this.setScene(scene);
        
        
        setHandlers();
    }

    private void setHandlers() {
        
        filebtn1.setOnAction(e -> {
            DirectoryChooser fc = new DirectoryChooser();
            fc.setInitialDirectory(new File("./export/The World"));
            fc.setTitle("Select Parent Directory");
            File findFile = fc.showDialog(this);
            locationPath = findFile;
            if(locationPath != null) {
                parentRegion = locationPath.getPath();
                File newFile = new File(parentRegion + "/" + Region);
                newFile.mkdir();
                filebtn2.setDisable(false);
                parentRegionPath.setText(parentRegion);
            }
        });

        filebtn2.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("./raw_map_data"));
            fc.setTitle("Select Map Coordinates");
            File findFile = fc.showOpenDialog(this);
            selectedFile = findFile;
            if(selectedFile != null) {
                okbtn.setDisable(false);
                moveOn = true;
                mapCoordinatePath.setText(selectedFile.getPath());
            }
        });

        
        regionNameText.textProperty().addListener((observable, OldValue, newValue) -> {
            Region = newValue;
            if(regionNameText != null) {
                filebtn1.setDisable(false);
            } else
                filebtn1.setDisable(true);
        });
        
        okbtn.setOnAction(e -> {
            Region = regionNameText.getText();
            File newFile = new File(locationPath.getPath() + "/" + Region);
            this.close();
        });

        cancelbtn.setOnAction(e -> {
            moveOn = false;
            this.close();
        });
    }

    public void initStyle() {

        scene.getStylesheets().add(CSS_PATH);

        option.getStyleClass().add(BUTTONS);
        box.getStyleClass().add(DIALOG);
        regionNameText.getStyleClass().add(TEXT_FIELD);
        parentRegionDirectory.getStyleClass().add(TEXT_FIELD);
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

    /**
     * @return the Region
     */
    public String getRegion() {
        return Region;
    }

    /**
     * @param Region the Region to set
     */
    public void setRegion(String Region) {
        this.Region = Region;
    }

    /**
     * @return the parentRegion
     */
    public String getParentRegion() {
        return parentRegion;
    }

    /**
     * @param parentRegion the parentRegion to set
     */
    public void setParentRegion(String parentRegion) {
        this.parentRegion = parentRegion;
    }
    
    public File getSelectedFile() {
        return selectedFile;
    }
    
    public boolean getMoveOn() {
        return moveOn;
    }
}