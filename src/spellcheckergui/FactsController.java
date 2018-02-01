package spellcheckergui;
// Written By: Prakash Khadka and Tyler Vankainen
/* Allows user to check the words the whole document as aone and highlights the
   incorrect words and auto correct to the nearest matched words using hash 
   table.
*/
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static spellcheckergui.OpenExixtingController.dictionary;

public class FactsController implements Initializable {

    @FXML
    private TextFlow textFlow;
    @FXML
    private JFXButton mainButton;
    @FXML
    private ImageView rootPane;
    @FXML
    private JFXButton existingButton;
    @FXML
    private JFXButton createNewButton;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dictionary.getCollision();
        dictionary.getMaxChain();
        Text text, text1, text3, text4;
        text  = new Text("Total Collisions Number : ");
        text.setFont(new Font("Times New Roman", 18));
        text1 = new Text(dictionary.getCollision() + "\n");
        text1.setFont(new Font("Times New Roman", 18));
        text1.setFill(Color.RED);
        text3 = new Text("Maximum Chain Length : " );
        text3.setFont(new Font("Times New Roman", 18));
        text4 = new Text(dictionary.getMaxChain() + " ");
        text4.setFont(new Font("Times New Roman", 18));
        text4.setFill(Color.RED);
        textFlow.getChildren().addAll(text, text1, text3, text4);
        textFlow.setTextAlignment(TextAlignment.CENTER);
        
    }

    @FXML
    private void mainButtonAction(ActionEvent event) {
        loadWindow("SpellCheckerGui.fxml", "");
        rootPane.getScene().getWindow().hide();
    }

    void loadWindow(String title, String header) {
        Parent parent;
        Stage stage;
        try {
            parent = FXMLLoader.load(getClass().getResource(title));
            stage  = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.setTitle(header);
            stage.show();
        } catch (IOException ex) {
        }
    }

    @FXML
    private void existingButtonAction(ActionEvent event) {
        loadWindow("OpenExixting.fxml","");
        rootPane.getScene().getWindow().hide();
    }

    @FXML
    private void createNewButtonAction(ActionEvent event) {
        loadWindow("CreateOwn.fxml","");
        rootPane.getScene().getWindow().hide();
    }
}
