/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdk;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;



import static sdk.Sdk.editorStage;

/**
 * FXML Controller class
 *
 * @author farouk
 */
public class MainstageController {
    
    public static Stage activateStage;

    private String dir;
    private int trials = 0;
    private boolean activated = true;
    
    @FXML
    private WebView webview;
    @FXML
    private MenuItem openProject;
    @FXML
    private MenuItem newProject;
    @FXML
    private MenuItem closeProject;
    
    static SdkState sdkState;
    static Stage newStage;
    static WebEngine webengine;
    @FXML
    private Button refresh;
    public static String projectName;
    @FXML
    private MenuItem buildWindows;
    private String page;
    @FXML
    private Text log;
    static Text logInt;
    private ProgressBar progress;
    private MenuItem compile;
    @FXML
    private MenuItem buildLinux;
    @FXML
    private MenuItem buildMac;
    @FXML
    private MenuItem buildAndroid;
    @FXML
    private MenuItem about;
    @FXML
    private BorderPane mainBP;   
    @FXML
    private MenuItem help;
    
    
    
    public void initialize() {
        
       logInt = log;
       sdkState = new SdkState(); 
       //webview.setZoom(0);
       webengine = webview.getEngine();
       page = "resources/index.html";
       URL url = this.getClass().getClassLoader().getResource(page);
       page = url.toExternalForm();
       webengine.load(page);
       webengine.setOnAlert(JSHandlers::alertHandler);
       webengine.setPromptHandler(JSHandlers.getPromptHandler());
       webengine.setJavaScriptEnabled(true);
       webview.setContextMenuEnabled(false);
       
       
       
       openProject.setOnAction(e->openProject());
       closeProject.setOnAction(e->closeProject());
       refresh.setOnAction(e->webengine.load(webengine.getLocation()));
       help.setOnAction(e->help());
       buildWindows.setOnAction(e->buildProject("windows"));
       about.setOnAction(e->about());
    }
     
    public void openProject(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Alchemy project");
        fileChooser.setInitialDirectory(new File("projects/"));  
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Alchemy Project Files","*.apf*"));
        File chosenFile = fileChooser.showOpenDialog(new Stage());
        try{
            String fileLocation = chosenFile.toURI().toURL().toExternalForm();
            String index = fileLocation.replace("project.apf","www/index.html");
            FileInputStream fs = new FileInputStream(chosenFile);
            ObjectInputStream os = new ObjectInputStream(fs);
            ProjectProperty pp = (ProjectProperty) os.readObject();
            projectName = pp.getProjectName();
            os.close();
            //show loading screen
            sdkState.setOpenedProject(projectName);
            editorStage.setTitle("Alchemy Framework - "+projectName);
            log.setFill(Color.ORANGE);
            log.setText("Loading Project "+projectName+"...");
            webengine.load(index);
            //when page finishes loading
            log.setText("Successfully loaded project "+projectName);
        }catch(Exception e){
            log.setFill(Color.RED);
            log.setText("No project was opened");
        }
    }
    
    @FXML
    private void newProject(ActionEvent action) throws IOException{
        //show stage and project properties
        newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("new.fxml"));
        Scene scene = new Scene(root,600,250);
        newStage.setScene(scene);
        newStage.setTitle("Create A New Alchemy Project");
        newStage.setResizable(false);
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.initOwner(editorStage);
        newStage.show();
        
    }
    private void closeProject(){
        
        editorStage.setTitle("Alchemy Framework");
        try{
            page = "resources/index.html";
            URL url = this.getClass().getClassLoader().getResource(page);
            page = url.toExternalForm();
            webengine.load(page);
            logInt.setFill(Color.ORANGE);
            if(projectName.equals(null))
                logInt.setText("");
            else
                logInt.setText("Project Closed");
            projectName = null;
        }catch(NullPointerException e){
                
        }
        
}
    
        public void buildProject(String platform){
            //progress.setProgress(-1);
            logInt.setFill(Color.ORANGE);
            logInt.setText("Building project for windows...");
            Path buildPath = Paths.get("utils/build.dll");
            Path buildNewPath = Paths.get("projects/"+projectName+"/build.exe");
            try{
                Files.deleteIfExists(buildNewPath);
                Files.copy(buildPath,buildNewPath);
                
            }catch(Exception e){
                log.setFill(Color.RED);
                log.setText("This project cannot be built");
                
            }
    }
    
    private void about() {
        Stage aboutStage = new Stage();
        VBox root = new VBox();
        root.setPadding(new Insets(10,0,0,10));
        
        Scene scene = new Scene(root);
        scene.setFill(Color.GRAY);
        Text name = new Text("Create Multi Platform native apps with web technology"
                + "\nBy Farouk Ibrahim May 2018, University of Ibadan, Nigeria"
                + "\nWhatsApp: +234 81 333 64 900 or Call: +234 90 209 37 360 Mail: ifarouk696@gmail.com"
                + "\n For any Contract or Donation reach me @ ifarouk696@gmail.com "
                + "\nUpgrade to the pro version to enjoy more features"
                + "\nFeedBacks will be appreciated. Thanks");
        root.getChildren().addAll(name);
        aboutStage.setScene(scene);
        aboutStage.setTitle("About");
        aboutStage.setResizable(false);
        aboutStage.initModality(Modality.WINDOW_MODAL);
        aboutStage.initOwner(editorStage);
        aboutStage.show();
    }
    private void help(){
        try{
            logInt.setFill(Color.ORANGE);
            logInt.setText("Opening guide file...");
            Desktop des = Desktop.getDesktop();
            des.open(new File("utils/guide.pdf"));
        }catch(Exception e){
           logInt.setFill(Color.RED);
           logInt.setText("The guide file could not be found");
        }
        
    }
    
    
    
}
