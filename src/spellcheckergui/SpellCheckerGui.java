package spellcheckergui;
// Written By: Prakash Khadka
/* Allows user to check the words the whole document as aone and highlights the
   incorrect words and auto correct to the nearest matched words using hash 
   table.
*/
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpellCheckerGui extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        Scene  scene;
        root  = FXMLLoader.load(getClass().getResource("SpellCheckerGui.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

   
    public static void main(String[] args) {
        launch(args);
    }
    
}
