package fr.univpau.controllers.main;

import com.jfoenix.controls.*;
import fr.univpau.containers.IController;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Window;
import javafx.util.Duration;
import fr.univpau.controllers.sidemenu.SideMenuController;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainController {
    private IController subController;
    private Object container;

    @FXML    
    private StackPane root;
    @FXML    
    private StackPane hamburgerSideMenuContainer;
    @FXML
    private JFXHamburger hamburgerSideMenu;
    @FXML
    private StackPane optionsBurger;
    @FXML
    private JFXRippler optionsRippler;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXPopup toolbarPopup;

    private ObservableList<JFXButton> observableListbuttons;
    
    public void initialize() {
        observableListbuttons = FXCollections.observableArrayList();

        // init the title hamburger icon
        final JFXTooltip burgerTooltip = new JFXTooltip("Ouvrir le menu latéral");

        drawer.setOnDrawerOpening(e -> {
            final Transition animation = hamburgerSideMenu.getAnimation();
            burgerTooltip.setText("Fermer le menu latéral");
            animation.setRate(1);
            animation.play();
        });
        drawer.setOnDrawerClosing(e -> {
            final Transition animation = hamburgerSideMenu.getAnimation();
            burgerTooltip.setText("Ouvrir le menu latéral");
            animation.setRate(-1);
            animation.play();
        });
        hamburgerSideMenuContainer.setOnMouseClicked(e -> {
            if (drawer.isClosed() || drawer.isClosing()) {
                drawer.open();
            } else {
                drawer.close();
            }
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/popup/MainPopup.fxml"));
        loader.setController(new InputController());
        try {
            toolbarPopup = new JFXPopup(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        optionsBurger.setOnMouseClicked(e ->
                toolbarPopup.show(optionsBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15));
        JFXTooltip.setVisibleDuration(Duration.millis(3000));
        JFXTooltip.install(hamburgerSideMenuContainer, burgerTooltip, Pos.BOTTOM_CENTER);
        
    }

    public void setContainer(Object obj) {
        this.container = obj;
        if(this.subController != null) {
            this.subController.setContainer(obj);
        }
    }

    public static final class InputController {
        @FXML
        private JFXListView<?> toolbarPopupList;

        // close application
        @FXML
        private void submit() {
            int selectionList = toolbarPopupList.getSelectionModel().getSelectedIndex();
            if (selectionList == 2) {
                Platform.exit();
            }
        }
    }

    private void initContent() {
    }

    private void initSideBar() {
    }
    
    public void changeContent(URL url) throws IOException {
    	drawer.getChildren().clear();
    	FXMLLoader loader = new FXMLLoader(url);
    	drawer.getChildren().add(loader.load());
    	Object controller = loader.getController();
    	if(controller instanceof IController) {
    	    subController = (IController) controller;
    	    if(this.container != null) {
    	        subController.setContainer(this.container);
            }
        }
    }
    
    public JFXHamburger getHamburgerSideMenu() {
        return hamburgerSideMenu;
    }

}