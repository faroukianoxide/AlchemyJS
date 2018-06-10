

package sdk;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Sdk extends Application{
    
    protected static Stage editorStage;
    private String mainView = "mainstage.fxml";
    
    
    @Override
    public void start(Stage primaryStage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(mainView));
        Scene scene = new Scene(root,1000,600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Alchemy Framework");
        primaryStage.show();
        editorStage = primaryStage;
    }
    
    public static void main(String[] args){
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
        Application.launch(args);
    }
}