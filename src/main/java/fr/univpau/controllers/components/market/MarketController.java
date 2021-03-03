package fr.univpau.controllers.components.market;

import com.jfoenix.controls.JFXButton;
import fr.univpau.containers.IController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MarketController implements IController<MarketController> {
    private MarketController container;

    @FXML
    private Label labelTitleSection;
    @FXML
    private JFXButton buttonPopupInfoSection;
    @FXML
    private JFXButton buttonBack;


    public void initialize() {

        this.setLabelTitleSection("Enchères de poisson");
    }


    public void setLabelTitleSection(String label) {
        labelTitleSection.setText(label);
    }

    @Override
    public void setContainer(MarketController container) {
        this.container = container;
        System.out.println(container);
    }
}