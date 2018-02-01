package spellcheckergui;
// Written By: Prakash Khadka and Tyler Vankainen
/* Allows user to check the words the whole document as aone and highlights the
   incorrect words and auto correct to the nearest matched words using hash 
   table.
*/
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import java.io.File;
import java.io.FileNotFoundException;
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

public class OpenExixtingController implements Initializable {

    private JFXTextField             fiileNameText;
    @FXML
    private JFXButton                checkButton;
    @FXML
    private TextFlow                 originalTextFlow;
    @FXML
    private JFXButton                toMainButton;
    public  HashTable<String, String> corrector;
    File                              dictionaryFile;
    File                              documentFile;
    @FXML
    private Text                      originalText;
    @FXML
    private Text                      editedText;
    @FXML
    private JFXHamburger              hamBurger;
    @FXML
    private JFXDrawer                 drawer;
    @FXML
    private JFXButton                 clearButton;
    @FXML
    private VBox                      vBox;
    @FXML
    private AnchorPane                rootPane;
    private JFXButton                 correctButton;
    @FXML
    private Text                      text;

    @FXML
    private JFXButton                 correctButon;
     public  static HashTable<String, String> dictionary;
    @FXML
    private JFXButton factsButton;
    public  File      file;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HamburgerBasicCloseTransition transition;
       
        correctButon.setVisible(false);
        editedText.setVisible(false);
        originalText.setVisible(false);
        text.setVisible(false);
        drawer.setSidePane(vBox);
        transition = new HamburgerBasicCloseTransition(hamBurger);
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
    private void checkButtonAction(ActionEvent event) throws Exception {
        FileChooser                 fileChooser;
        Stage                       stage;
        FileChooser.ExtensionFilter extFilter;
        fileChooser =  new FileChooser();
        stage       = (Stage) rootPane.getScene().getWindow();
        extFilter   = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*"
                                                                     + ".txt");
        file        = fileChooser.showOpenDialog(stage);
        fileChooser.getExtensionFilters().add(extFilter);
        if (file != null) {
            originalText.setVisible(true);
            checkButton.setVisible(false);
            text.setVisible(true);
            correctButon.setVisible(true);
            dictionary = new HashTable<>(readCount("words_alpha.txt"));
            corrector  = new HashTable<>(count("Dict1.txt"));
            loadDictionary("words_alpha.txt");
            loadCorrector("Dict1.txt");
            spellChecker(file);
        }
    }

    @FXML
    private void toMainButtonAction(ActionEvent event) {
        loadWindow("SpellCheckerGui.fxml", "");
        rootPane.getScene().getWindow().hide();
    }

    public int readCount(String text) throws FileNotFoundException {
        Scanner  reader;
        int      count;
        reader = new Scanner(new File("Dict.txt"));
        count  = 0;
        while (reader.hasNext()) {
            reader.next();
            count = count + 1;
        }
        return count;
    }

    public int count(String text) throws FileNotFoundException {
        Scanner reader;
        int     count;
        String  line;
        reader  = new Scanner(new File(text));
        count   = 0;
        while (reader.hasNextLine()) {
            line  = reader.nextLine();
            count = count + 1;
        }
        return count;
    }

    public void loadDictionary(String text) throws FileNotFoundException {
        Scanner reader;
        int     count;
        String  word;
        reader  = new Scanner(new File(text));
        count   = 0;
        while (reader.hasNext()) {
            word  = reader.next().toLowerCase();
            dictionary.insert(word, Integer.toString(count));
            count = count + 1;
        }
    }

    public void spellChecker(File file) throws FileNotFoundException {
        Scanner reader;
        int     count;
        String  temp;
        String  edit;
        count = 0;
        edit  = "";
        reader = new Scanner(file);
        while (reader.hasNextLine()) {
            try {
                String Line = reader.nextLine();
                String[] words = Line.split(" ");
                for (String word : words) {
                    if (word.endsWith(",") || word.endsWith("?")
                            || word.endsWith(".")|| word.contains("'")
                            || word.endsWith(";")) {
                        temp = word.substring(0, word.length() - 1);
                        if (!dictionary.contains(temp.toLowerCase())) {
                            Text text4 = new Text(word + " ");
                            text4.setFill(Color.RED);
                            text4.setFont(new Font("Times New Roman", 20));
                            originalTextFlow.getChildren().add(text4);
                            count = count + 1;
                        } else {
                            Text text4 = new Text(word + " ");
                            text4.setFont(new Font("Times New Roman", 20));
                            originalTextFlow.getChildren().add(text4);
                        }
                    } else if (word.isEmpty() || word.equals("--")
                            || word.equals("\\") || word.equals(".")
                            || word.equals(":") || word.equals(";")
                            || word.equals("+") || word.equals("-")
                            || word.equals("$") || word.equals(";")) {
                        originalTextFlow.getChildren().add(new Text(word +
                                " "));
                    } else {
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
                System.out.println(ex.getMessage());
            }
        }
        if (count == 0) {
            text.setText("No Correction Needed!!!");
            correctButon.setVisible(false);
            editedText.setVisible(false);
            originalText.setVisible(false);
        }
        originalTextFlow.setTextAlignment(TextAlignment.CENTER);
    }

    @FXML
    private void clearButtonAction(ActionEvent event) {
        loadWindow("OpenExixting.fxml", "");
        rootPane.getScene().getWindow().hide();
    }

    public void loadWindow(String title, String header) {
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
    private void correctButtonAction(ActionEvent event) throws Exception {
        originalTextFlow.getChildren().clear();
        originalText.setVisible(false);
        editedText.setVisible(true);
        correctButon.setVisible(false);
        spellCorrector(file);
    }

    private void loadCorrector(String text) throws FileNotFoundException {
        Scanner  reader;
        int      count;
        String   line;
        String[] words;
        reader = new Scanner(new File(text));
        count  = 0;
        while (reader.hasNextLine()) {
            line  = reader.nextLine().toLowerCase();
            line  = line.trim();
            words = line.split(" ");
            corrector.insert(words[0], words[1]);
        }
    }

    private void spellCorrector(File file) throws FileNotFoundException {
        Scanner  reader;
        int      count;
        String   Line;
        String   temp;
        String[] words;
        reader = new Scanner(file);
        count  = 0;
        while (reader.hasNextLine()) {
            try {
                Line  = reader.nextLine();
                words = Line.split(" ");
                for (String word : words) {
                    if (word.endsWith(",") || word.endsWith("?") ||
                            word.endsWith(".")|| word.contains("'")
                            || word.endsWith(";")) {
                        temp = word.substring(0, word.length() - 1);
                        if (corrector.contains(temp.toLowerCase())) {
                            String t   = corrector.getValue(temp);
                            Text text4 = new Text(t + 
                                    word.charAt(word.length() - 1) + " ");
                            text4.setFill(Color.BLUE);
                            text4.setFont(new Font("Times New Roman", 20));
                            originalTextFlow.getChildren().add(text4);
                            count = count + 1;
                        } else {
                            Text text4 = new Text(word + " ");
                            text4.setFont(new Font("Times New Roman", 20));
                            originalTextFlow.getChildren().add(text4);
                        }
                    } else {
                        if (corrector.contains(word.toLowerCase())) {
                            Text text6 = new Text(corrector.getValue(word) 
                                    + " ");
                            text6.setFill(Color.BLUE);
                            text6.setFont(new Font("Times New Roman", 20));
                            originalTextFlow.getChildren().add(text6);
                            count = count + 1;
                        } else {
                            Text text4 = new Text(word + " ");
                            text4.setFont(new Font("Times New Roman", 20));
                            originalTextFlow.getChildren().add(text4);
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
    private void factsButtonAction(ActionEvent event) {
        loadWindow("Facts.fxml", "");
        rootPane.getScene().getWindow().hide();
    }
}
