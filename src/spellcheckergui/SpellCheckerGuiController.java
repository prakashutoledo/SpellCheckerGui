package spellcheckergui;
// Written By: Prakash Khadka
/* Allows user to check the words the whole document as aone and highlights the
   incorrect words and auto correct to the nearest matched words using hash 
   table.
*/
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.Shadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SpellCheckerGuiController implements Initializable {

    private Label         label;
    @FXML
    private AnchorPane    root;
    @FXML
    private JFXHamburger  hamBurger;
    @FXML
    private JFXDrawer     drawer;
    @FXML
    private MenuBar       menu;
    @FXML
    private MenuItem      openMenuItem;
    @FXML
    private CheckMenuItem blackMenuEffect;
    @FXML
    private CheckMenuItem blueMenuEffect;
    @FXML
    private CheckMenuItem sepiaToneMenuEffect;
    @FXML
    private CheckMenuItem glowToneMenuEffect;
    @FXML
    private CheckMenuItem shadowMenuEffect;
    @FXML
    private VBox          box;
    @FXML
    private JFXButton     openExistingButtion;
    @FXML
    private JFXButton     createNewButton;
    @FXML
    private CheckMenuItem greenMenuEffect;
    @FXML
    private CheckMenuItem tealMenuEffect;
    @FXML
    private MenuItem      noEffextMenu;
    @FXML
    private MenuItem      newMenuItem;
    @FXML
    private MenuItem      exitMenuItem;
    @FXML
    private MenuItem      helpMenu;
    @FXML
    private MenuItem      aboutMenu;
    @FXML
    private JFXButton     hashValueButton;
    @FXML
    private JFXButton     exitButton;
    @FXML
    private ImageView     mainImageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, 
                KeyCombination.CONTROL_ANY));
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.E, 
                KeyCombination.CONTROL_ANY));
        exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F4,
                KeyCombination.ALT_ANY));
    }

    @FXML
    private void setBackroundBlack(ActionEvent event) {
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, 
                null, null)));
    }

    @FXML
    private void setBackroundBlue(ActionEvent event) {
        root.setBackground(new Background(new BackgroundFill(Color.BLUE, 
                null, null)));
    }

    @FXML
    private void setImageSepiaTone(ActionEvent event) {
        mainImageView.setEffect(new SepiaTone());
    }

    @FXML
    private void setImageGlow(ActionEvent event) {
        mainImageView.setEffect(new Glow());
    }

    @FXML
    private void setImageShadow(ActionEvent event) {
        mainImageView.setEffect(new Shadow());
    }

    @FXML
    private void actionEvent1(ActionEvent event) {
        root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,
                null, null)));
        mainImageView.setEffect(null);
        
    }

    public void loadWindow(String title, String header) {
        Parent parent;
        Stage  stage;
        try {
            parent = FXMLLoader.load(getClass().getResource(title));
            stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.setTitle(header);
            stage.show();
        } catch (IOException ex) {
        }
    }

    @FXML
    private void openExistingButtion(ActionEvent event) {
        loadWindow("OpenExixting.fxml", " ");
    }

    @FXML
    private void createNewButtonAction(ActionEvent event) {
        loadWindow("CreateOwn.fxml", " ");
    }

    private void exitButtonAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void setBackroundGreen(ActionEvent event) {
        root.setBackground(new Background(new BackgroundFill(Color.GREEN, 
                null, null)));
    }

    @FXML
    private void setBackroundTeal(ActionEvent event) {
        root.setBackground(new Background(new BackgroundFill(Color.TEAL, 
                null, null)));
    }

    @FXML
    private void newMenuItemAction(ActionEvent event) {
        loadWindow("OpenExixting.fxml", " ");
    }

    @FXML
    private void openMenuItemAction(ActionEvent event) {
        loadWindow("CreateOwn.fxml", " ");
    }

    @FXML
    private void exitMenuItemAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void HelpMenuAction(ActionEvent event) {
        loadWindow("HelpWindow.fxml", " ");
    }

    @FXML
    private void aboutMenuAction(ActionEvent event) {
        loadWindow("AboutWindow.fxml", " ");
    }

    @FXML
    private void hashValueButtonAction(ActionEvent event) {
        loadWindow("HashValue.fxml", " ");
    }

  
}
