package fr.univpau.containers;

import com.jfoenix.svg.SVGGlyphLoader;
import fr.univpau.agents.MarketAgent;
import fr.univpau.controllers.components.market.MarketController;
import fr.univpau.controllers.main.MainController;
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

import java.io.IOException;
import java.net.URL;

public class MarketContainer extends Application {
    private MarketController gui;
    private MarketAgent agent;

    @Override
    public void start(Stage primaryStage) throws Exception {
        startContainer();
        new Thread(() -> {
            try {
                SVGGlyphLoader.loadGlyphsFont(MarketContainer.class.getResourceAsStream("/svg/icomoon.svg"),
                        "icomoon.svg");
            } catch (IOException ioExc) {
                ioExc.printStackTrace();
            }
        }).start();

        final URL locationMarket = getClass().getResource("/view/components/market/Market.fxml");
        final URL locationMain = getClass().getResource("/view/Main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(locationMain);

        Parent root = fxmlLoader.load();

        MainController controller = fxmlLoader.getController();
        controller.changeContent(locationMarket);

        double width = 600;
        double height = 600;
        Scene scene = new Scene(root, width, height);

        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(
                SellerContainer.class.getResource("/css/style.css").toExternalForm());

        primaryStage.setTitle("Market");
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
        primaryStage.show();

        // MarketInterface.start(primaryStage);
    }


    public void startContainer() {
        try {
            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl(false);
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            AgentContainer agentContainer = runtime.createAgentContainer(profile);
            AgentController agentController = agentContainer.createNewAgent("market", "fr.univpau.agents.MarketAgent", new Object[]{this});
            agentController.start();
        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(MarketContainer.class);
    }

    public MarketAgent getAgent() {
        return this.agent;
    }

    public MarketController getGui() {
        return this.gui;
    }

    public void setAgent(MarketAgent a) {
        this.agent = a;
    }
}