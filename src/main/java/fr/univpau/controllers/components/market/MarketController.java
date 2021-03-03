package fr.univpau.controllers.components.market;

import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;

import fr.univpau.containers.MarketContainer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class MarketController {
	private MarketContainer container;
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
    
    public void setContainer(MarketContainer controller) {
    	this.container = controller;
    }
}