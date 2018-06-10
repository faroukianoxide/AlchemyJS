/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdk;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import static sdk.MainstageController.logInt;
import static sdk.MainstageController.newStage;
import static sdk.MainstageController.sdkState;
import static sdk.MainstageController.webengine;
import static sdk.Sdk.editorStage;
    
/**
 * FXML Controller class
 *
 * @author farouk
 */
public class NewController {

  
    @FXML
    private Button cancel;
    @FXML
    private TextField projectName;
    
    static String projectname;
    @FXML
    private Button save;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        save.setOnAction(e->save());
        cancel.setOnAction(e->newStage.close());
    }
    public void save(){
        if(projectName.getText().isEmpty()){
                //do nothing
        }
        else{
            projectname = projectName.getText().trim();
            
            try{
                File projectDir = new File("projects/"+projectname);
                projectDir.mkdir();
                ProjectProperty pp = new ProjectProperty();
                pp.setProjectName(projectname);

                File propFile = new File("projects/"+projectname+"/project.apf");
                FileOutputStream fs = new FileOutputStream(propFile);
                ObjectOutputStream os = new ObjectOutputStream(fs);
                os.writeObject(pp);
                os.close();
                new File("projects/"+projectname+"/www").mkdir();
                File index = new File("projects/"+projectname+"/www/index.html");
                index.createNewFile();
                editorStage.setTitle("Avatar Framework - "+projectname);
                //now save the new state of the sdk
                sdkState.setOpenedProject(projectname);
                MainstageController.projectName = projectname;
                newStage.close();
                //String page = "kiki/www/index.html";
                //URL url = this.getClass().getClassLoader().getResource(page);
                //page = url.toExternalForm();
                webengine.load(index.toURI().toURL().toExternalForm());
                logInt.setFill(Color.ORANGE);
                logInt.setText("Successfully created project "+projectname);
                Path enginePath = Paths.get("utils/sys.dll"); // the secretly renamed class file
            Path manifestPath = Paths.get("utils/config.dll"); // the secretly renamed manifest file
            Path launcherPath = Paths.get("utils/launch.dll");
            Path launcerNewPath = Paths.get("projects/"+projectname+"/dist/windows/"+projectname+".exe");
            //create engine path
            Path engineNewPath = Paths.get("projects/"+projectname+"/engine/Engine.class");
            //create manifest path  

            Path manifestNewPath = Paths.get("projects/"+projectname+"/properties.file");
            //www path
            File projectBuilds = new File("projects/"+projectname);
            File engineFolder = new File("projects/"+projectname+"/engine");
            File distFolder = new File("projects/"+projectname+"/dist/windows");            
            
                
                ///now start copying...
                projectBuilds.mkdirs();
                engineFolder.mkdirs();
                distFolder.mkdirs();
                Files.copy(manifestPath,manifestNewPath); // move manifest
                Files.copy(enginePath,engineNewPath);//move engine
                
                Files.copy(launcherPath,launcerNewPath); // move launcher
                Desktop de = Desktop.getDesktop();
                de.open(new File("projects/"+projectname));
            }catch(Exception e){
                logInt.setFill(Color.RED);
                logInt.setText("Project not created, sorry");
                                                                            
            }
        }
    }
                        
}

    
