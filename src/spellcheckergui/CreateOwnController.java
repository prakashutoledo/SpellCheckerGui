package spellcheckergui;
// Written By: Prakash Khadka
/* Allows user to check the words the whole document as aone and highlights the
   incorrect words and auto correct to the nearest matched words using hash 
   table.
*/
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CreateOwnController implements Initializable {

    @FXML
    private JFXTextArea               textField;
    @FXML
    private JFXButton                 clearButton;
    @FXML
    private JFXButton                 mainButton;
    @FXML
    private JFXButton                 checkButton;
    @FXML
    private TextFlow                  originalTextFlow;
    @FXML 
    private JFXButton                 correctButon;
    @FXML
    private JFXHamburger              hamBurger;
    @FXML
    private JFXDrawer                 drawer;
    @FXML
    private VBox                      box;
    @FXML
    private AnchorPane                rootPane;
    public  HashTable<String, String> dictionary;
    public  HashTable<String, String> corrector;
    public  String                    textArea;
    @FXML
    private Text                      originalText;
    @FXML
    private Text                      correctedText;
    @FXML
    private Text                      clickText;
    @FXML
    private JFXButton                 saveTextButton;
    @FXML
    private Text                      noCorrectionText;
    @FXML
    private JFXButton factsButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        noCorrectionText.setVisible(false);
        correctButon.setVisible(false);
        originalText.setVisible(false);
        correctedText.setVisible(false);
        clickText.setVisible(false);
        drawer.setSidePane(box);
        HamburgerBackArrowBasicTransition transition; 
        transition = new HamburgerBackArrowBasicTransition(hamBurger);
        transition.setRate(-1);
        hamBurger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            if (drawer.isShown()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });

    }

    @FXML
    private void clearButtonAction(ActionEvent event) {
        loadWindow("CreateOwn.fxml", " ");
        rootPane.getScene().getWindow().hide();
    }

    @FXML
    private void openButtonAction(ActionEvent event) {
        loadWindow("OpenExixting.fxml", " ");
        rootPane.getScene().getWindow().hide();
    }

    @FXML
    private void mainButtonAction(ActionEvent event) {
        loadWindow("SpellCheckerGui.fxml", " ");
        rootPane.getScene().getWindow().hide();
    }

    @FXML
    private void checkButtonActoin(ActionEvent event) throws Exception {
        correctedText.setVisible(false);
        originalText.setVisible(true);
        textField.setVisible(false);
        clickText.setVisible(true);
        textArea = textField.getText();
        if (textField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter in all fields");
            alert.showAndWait();
            loadWindow("CreateOwn.fxml", "");
            rootPane.getScene().getWindow().hide();
        } else {
            dictionary = new HashTable<>(readCount("words_alpha.txt"));
            corrector  = new HashTable<>(count("Dict1.txt"));
            loadDictionary("words_alpha.txt");
            loadCorrector("Dict1.txt");
            spellChecker(textField.getText());
            checkButton.setVisible(false);

        }

    }

    @FXML
    private void correctButtonAction(ActionEvent event) throws FileNotFoundException {
        correctedText.setVisible(true);
        textField.setVisible(false);
        originalText.setVisible(false);
        correctButon.setVisible(false);
        textField.setEditable(false);
        clickText.setVisible(false);
        originalTextFlow.getChildren().clear();
        spellCorrector(textArea);
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

    public int readCount(String text) throws FileNotFoundException {
        Scanner reader;
        int     count;
        reader  = new Scanner(new File("words_alpha.txt"));
        count   = 0;
        while (reader.hasNext()) {
            reader.next();
            count = count + 1;
        }
        return count;
    }

    public int count(String text) throws FileNotFoundException {
        Scanner reader;
        int count = 0;
        reader = new Scanner(new File(text));
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            count = count + 1;
        }
        return count;
    }

    public void loadDictionary(String text) throws FileNotFoundException {
        Scanner reader;
        int count;
        String word;
        reader = new Scanner(new File(text));
        count  = 0;
        while (reader.hasNext()) {
            word = reader.next().toLowerCase();
            dictionary.insert(word, Integer.toString(count));
            count = count + 1;
        }
    }

    private void loadCorrector(String text) throws FileNotFoundException {
        Scanner  reader;
        int      count;
        String   line;
        String[] words;
        count  = 0;
        reader = new Scanner(new File(text));
        while (reader.hasNextLine()) {
            line  = reader.nextLine().toLowerCase();
            line  = line.trim();
            words = line.split(" ");
            corrector.insert(words[0], words[1]);
        }
    }

    public void spellChecker(String text) throws FileNotFoundException {
        Scanner reader;
        String edit, line;
        String[] words;
        int count;
        reader = new Scanner(text);
        count  = 0;
        edit   = "";
        while (reader.hasNextLine()) {
            try {
                line  = reader.nextLine();
                words = line.split(" ");
                for (String word : words) {
                    if (word.endsWith(",") || word.endsWith("?") || word.endsWith(".")
                            || word.contains("'") || word.endsWith(";")) {
                        String temp = word.substring(0, word.length() - 1);
                        if (!dictionary.contains(temp.toLowerCase())) {
                            Text text4 = new Text(word + " ");
                            text4.setFont(new Font("Times New Roman", 20));
                            text4.setFill(Color.RED);
                            originalTextFlow.getChildren().add(text4);
                            count = count + 1;
                        } else {
                            Text text4 = new Text(word + " ");
                            text4.setFont(new Font("Times New Roman", 20));
                            originalTextFlow.getChildren().add(text4);
                        }
                        
                    } else if (word.isEmpty()) { 
                        originalTextFlow.getChildren().add(new Text(" "));
                    }
                    else {
                        if (!dictionary.contains(word.toLowerCase())) {
                            Text text6 = new Text(word + " ");
                            text6.setFill(Color.RED);
                            text6.setFont(new Font("Times New Roman", 20));
                            originalTextFlow.getChildren().add(text6);
                            count = count + 1;
                        } else {
                            Text text6 = new Text(word + " ");
                            text6.setFont(new Font("Times New Roman", 20));
                            originalTextFlow.getChildren().add(text6);
                        }
                    }
                }
                originalTextFlow.getChildren().add(new Text("\n"));
            } catch (NoSuchElementException ex) {
            }
        }
        if (count == 0) {
            noCorrectionText.setVisible(true);
            correctedText.setVisible(false);
            correctButon.setVisible(false);
            originalText.setVisible(false);
            textField.setVisible(false);
            clickText.setVisible(false);
            saveTextButton.setVisible(true);
            correctButon.setVisible(false);
        } else {
            correctButon.setVisible(true);
            originalTextFlow.setTextAlignment(TextAlignment.CENTER);
        }
    }

    private void spellCorrector(String text) throws FileNotFoundException {
        Scanner  reader;
        int      count;
        String   Line, temp, t;
        String[] words;
        reader  = new Scanner(text);
        count   = 0;
        while (reader.hasNextLine()) {
            try {
                Line  = reader.nextLine();
                words = Line.split(" ");
                for (String word : words) {
                    if (word.endsWith(",") || word.endsWith("?") 
                            || word.endsWith(".") || word.contains("'") 
                            || word.endsWith(";")) {
                        temp = word.substring(0, word.length() - 1);
                        if (corrector.contains(temp.toLowerCase())) {
                            t = corrector.getValue(temp);
                            Text text4 = new Text(t + word.charAt(word.length()
                                                                   - 1) + " ");
                            text4.setFont(new Font("Times New Roman", 20));
                            text4.setFill(Color.BLUE);
                            originalTextFlow.getChildren().add(text4);
                        } else {
                            Text text4 = new Text(word + " ");
                            text4.setFont(new Font("Times New Roman", 20));
                            originalTextFlow.getChildren().add(new Text(word +
                                                                         " "));
                        }
                    } else {
                        if (corrector.contains(word.toLowerCase())) {
                            Text text6 = new Text(corrector.getValue(word) + 
                                                                      " ");
                            text6.setFill(Color.BLUE);
                            text6.setFont(new Font("Times New Roman", 20));
                            originalTextFlow.getChildren().add(text6);
                        } else {
                            Text text4 = new Text(word + " ");
                            text4.setFont(new Font("Times New Roman", 20));
                            originalTextFlow.getChildren().add(new Text(word + 
                                                                         " "));
                        }
                    }
                }
                originalTextFlow.getChildren().add(new Text("\n"));
            } catch (NoSuchElementException ex) {
            }
        }
        originalTextFlow.setTextAlignment(TextAlignment.CENTER);
    }

    @FXML
    private void saveTextButtonAction(ActionEvent event) {
        FileChooser fileChooser;
        FileChooser.ExtensionFilter extFilter;
        Stage stage;
        File file;
        fileChooser = new FileChooser();
        extFilter   = new FileChooser.ExtensionFilter("TXT files (*.txt)", 
                                                                "*.txt");
        stage       = (Stage) rootPane.getScene().getWindow();
        file        = fileChooser.showSaveDialog(stage);
        fileChooser.getExtensionFilters().add(extFilter);
        if (file != null) {
            SaveFile(textField.getText(), file);
        }
    }

    private void SaveFile(String content, File file) {
        try {
            FileWriter fileWriter;
            fileWriter = null;
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {

        }

    }

    @FXML
    private void factsButtonAction(ActionEvent event) {
        loadWindow("Facts.fxml", "");
        rootPane.getScene().getWindow().hide();
    }
}
