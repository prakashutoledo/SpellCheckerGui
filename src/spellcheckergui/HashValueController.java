package spellcheckergui;
// Written By: Prakash Khadka and Tyler Vankainen
/* Allows user to check the words the whole document as aone and highlights the
   incorrect words and auto correct to the nearest matched words using hash 
   table.
*/
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HashValueController implements Initializable {

    @FXML
    private JFXTextField keyTF;
    @FXML
    private JFXButton    getHashValueButton;
    @FXML
    private TextFlow     textFlow;
    @FXML
    private JFXButton    mainButton;
    @FXML
    private JFXButton    createNewButtonAction;
    @FXML
    private JFXButton    openExistingButton;
    @FXML
    private AnchorPane   rootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void getHashValueButtonAction(ActionEvent event) {
        textFlow.getChildren().clear();
        HashTable<String, String> table = new HashTable<>();
        String temp;
        Text text, text1;
        if (keyTF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter in all fields");
            alert.showAndWait();
            loadWindow("Facts.fxml", "");
        } else {
            temp = keyTF.getText().toLowerCase();
            text1 = new Text(table.hashValue(temp) + "");
            text1.setFont(new Font("Times New Roman", 18));
            text1.setFill(Color.RED);
            text = new Text("Hash Value of " + temp + " :");
            text.setFont(new Font("Times New Roman", 18));
            textFlow.getChildren().addAll(text, text1);
            textFlow.setTextAlignment(TextAlignment.CENTER);
        }

    }

    @FXML
    private void mainButtonAction(ActionEvent event) {
        loadWindow("SpellCheckerGui.fxml", "");
        rootPane.getScene().getWindow().hide();
    }

    @FXML
    private void createNewButtonAction(ActionEvent event) {
        loadWindow("CreateOwn.fxml", "");
        rootPane.getScene().getWindow().hide();
    }

    @FXML
    private void openExistingButtonAction(ActionEvent event) {
        loadWindow("OpenExixting.fxml", "");
        rootPane.getScene().getWindow().hide();
    }

    void loadWindow(String title, String header) {
        Parent parent;
        Stage  stage;
        try {
            parent = FXMLLoader.load(getClass().getResource(title));
            stage  = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.setTitle(header);
            stage.show();
        } catch (IOException ex) {
        }
    }

}
