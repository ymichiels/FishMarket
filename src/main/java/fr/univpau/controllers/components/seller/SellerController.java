package fr.univpau.controllers.components.seller;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;

import javax.sound.midi.ControllerEventListener;

import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;

import fr.univpau.agents.SellerAgent;
import fr.univpau.containers.BuyerContainer;
import fr.univpau.containers.IController;
import fr.univpau.containers.SellerContainer;
import fr.univpau.controllers.components.bar.TitleSectionBarController;
import fr.univpau.controllers.main.MainController;
import fr.univpau.controllers.main.MainController.InputController;
import jade.gui.GuiEvent;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Window;
import javafx.util.Duration;

public class SellerController implements IController<SellerContainer> {
	private String name;
	private SellerAgent agent;
	private SellerContainer container;
	private ObservableList<JFXButton> observableListbuttons;

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
	@FXML
    private JFXButton buttonBack;
    @FXML
    private Label labelTitleSection;
    @FXML
    private Label labelUsername;
    @FXML
    private JFXButton buttonLogOut;
    @FXML
    private JFXButton buttonPopupInfoSection;
    @FXML
    private JFXButton buttonSaveSeller;
    @FXML
    private JFXTextField textFieldTimestamp;
    @FXML
    private JFXTextField textFieldInitialPrice;
    @FXML
    private JFXTextField textFieldStep;
    @FXML
    private JFXTextField textFieldFishName;
    
    public void initialize() throws IOException {
    	// initMain();
    	initButtonTitle();
    	this.setLabelTitleSection("Vente de poisson");
    }
    
    public JFXButton getBackButton() {
        return buttonBack;
    }

    protected void initButtonTitle() {
        SVGGlyph arrow = new SVGGlyph(0,
                "FULLSCREEN",
                "M402.746 877.254l-320-320c-24.994-24.992-24.994-65.516 0-90.51l320-320c24.994-24.992 65.516-24.992 90.51 0 24.994 24.994 "
                        + "24.994 65.516 0 90.51l-210.746 210.746h613.49c35.346 0 64 28.654 64 64s-28.654 64-64 64h-613.49l210.746 210.746c12.496 "
                        + "12.496 18.744 28.876 18.744 45.254s-6.248 32.758-18.744 45.254c-24.994 24.994-65.516 24.994-90.51 0z",
                Color.WHITE);
        arrow.setSize(20, 16);
        buttonBack.setGraphic(arrow);
        buttonBack.setRipplerFill(Color.WHITE);

        SVGGlyph help = new SVGGlyph(0,
                "FULLSCREEN",
                "M15.047 11.25q0.938-0.938 0.938-2.25 0-1.641-1.172-2.813t-2.813-1.172-2.813 1.172-1.172 2.813h1.969q0-0.797 0.609-1.406t1.406-0.609 1.406 0.609 0.609 1.406-0.609 1.406l-1.219 1.266q-1.172 1.266-1.172 2.813v0.516h1.969q0-1.547 1.172-2.813zM12.984 18.984v-1.969h-1.969v1.969h1.969zM12 2.016q4.125 0 7.055 2.93t2.93 7.055-2.93 7.055-7.055 2.93-7.055-2.93-2.93-7.055 2.93-7.055 7.055-2.93z",
                Color.WHITE);
        help.setSize(24, 24);
        buttonPopupInfoSection.setGraphic(help);
        buttonPopupInfoSection.setRipplerFill(Color.WHITE);
    }
    
    public void setLabelTitleSection(String label) {
    	labelTitleSection.setText(label);
    }
    
    public SellerController getController() {
    	return this.getController();
    }
    
    public void createEnchere() {
    	GuiEvent event = new GuiEvent((Object) this, 1);
    	Integer delay = new Integer(textFieldTimestamp.getText());
    	delay *= 1000;
    	event.addParameter((Object) new Integer(textFieldInitialPrice.getText()));
    	event.addParameter((Object) delay);
    	event.addParameter((Object) new Integer(textFieldStep.getText()));
    	event.addParameter((Object) textFieldFishName.getText());
    	container.getAgent().postGuiEvent(event);
    }
    
    public void initMain() {
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

    @Override
    public void setContainer(SellerContainer container) {
        this.container = container;
        System.out.println(container);
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
    
    public JFXHamburger getHamburgerSideMenu() {
        return hamburgerSideMenu;
    }
    
}