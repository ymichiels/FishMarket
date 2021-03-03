package fr.univpau.controllers.components.bar;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

public class ToolActionBarController {

    @FXML
    private JFXButton buttonCancel;
    @FXML
    private JFXButton buttonValidate;

    public JFXButton getButtonCancel() {
        return buttonCancel;
    }

    public JFXButton getButtonValidate() {
        return buttonValidate;
    }

}
