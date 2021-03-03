/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univpau.controllers.components.bar;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

// @ViewController(value = "/view/components/bar/TitleSectionBar.fxml", title = "Application de Handball")
public class TitleSectionBarMinController {

    @FXML
    private Label labelTitleSection;
    @FXML
    private Label labelUsername;
    @FXML
    private JFXButton buttonLogOut;
    @FXML
    private JFXButton buttonPopupInfoSection2;

    // @PostConstruct
    public void init() throws Exception {
        initButtonInfo();
    }

    protected void initButtonInfo() {
        SVGGlyph help = new SVGGlyph(0,
                "FULLSCREEN",
                "M15.047 11.25q0.938-0.938 0.938-2.25 0-1.641-1.172-2.813t-2.813-1.172-2.813 1.172-1.172 2.813h1.969q0-0.797 0.609-1.406t1.406-0.609 1.406 0.609 0.609 1.406-0.609 1.406l-1.219 1.266q-1.172 1.266-1.172 2.813v0.516h1.969q0-1.547 1.172-2.813zM12.984 18.984v-1.969h-1.969v1.969h1.969zM12 2.016q4.125 0 7.055 2.93t2.93 7.055-2.93 7.055-7.055 2.93-7.055-2.93-2.93-7.055 2.93-7.055 7.055-2.93z",
                Color.WHITE);
        help.setSize(24, 24);
        buttonPopupInfoSection2.setGraphic(help);
        buttonPopupInfoSection2.setRipplerFill(Color.WHITE);
    }

}
