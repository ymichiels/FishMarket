package fr.univpau.controllers.sidemenu;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class SideMenuController {

    @FXML
    private Label labelHome;
    @FXML
    private Label labelInfos;
    @FXML
    private JFXListView<Label> sideList;
    @FXML
    private StackPane root;

    public void initialize() {
        sideList.propagateMouseEventsToParent();
        sideList.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
        });
       
    }

}
