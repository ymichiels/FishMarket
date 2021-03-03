package fr.univpau.containers;

import java.io.IOException;
import java.net.URL;

import com.jfoenix.svg.SVGGlyphLoader;

import fr.univpau.agents.BuyerAgent;
import fr.univpau.controllers.components.buyer.BuyerController;
import fr.univpau.controllers.main.MainController;
import fr.univpau.gui.BuyerInterface;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BuyerContainer extends Application {
    private BuyerAgent agent;
    private BuyerController gui;
    
    public void start(Stage primaryStage) throws Exception {
    	startContainer();
    	/*new Thread(() -> {
            try {
                SVGGlyphLoader.loadGlyphsFont(MarketContainer.class.getResourceAsStream("/svg/icomoon.svg"),
                        "icomoon.svg");
            } catch (IOException ioExc) {
                ioExc.printStackTrace();
            }
        }).start();
    	
    	final URL locationBuyer = getClass().getResource("/view/components/buyer/Buyer.fxml");
    	final URL locationMain = getClass().getResource("/view/Main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(locationMain);

        Parent root = (Parent)fxmlLoader.load();
        // gui = (BuyerController) fxmlLoader.getController();
        // gui.setContainer(this);
        MainController controller = (MainController)fxmlLoader.getController();
        controller.changeContent(locationBuyer);
        
        double width = 600;
        double height = 600;
        Scene scene = new Scene(root, width, height);
        
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(
        		SellerContainer.class.getResource("/css/style.css").toExternalForm());

        primaryStage.setTitle("Buyer");
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
        primaryStage.show();*/
    	
    	BuyerInterface.start(primaryStage);
    }
    
    public void startContainer() {
    	  try {
              Runtime runtime = Runtime.instance();
              Profile profile = new ProfileImpl(false);
              profile.setParameter(Profile.MAIN_HOST, "localhost");
              AgentContainer agentContainer = runtime.createAgentContainer(profile);
              AgentController agentController = agentContainer.createNewAgent("buyer", "fr.univpau.agents.BuyerAgent", new Object[]{this});
              agentController.start();
          } catch (ControllerException e) {
              e.printStackTrace();
          }
    }
    public static void main(String[] args) {
    	launch(BuyerContainer.class);
    }
    
    public BuyerAgent getAgent() {
    	return this.agent;
    }
    
    public BuyerController getGui() {
    	return this.gui;
    }
    
    public void setAgent(BuyerAgent agent) {
    	this.agent = agent;
    }
}