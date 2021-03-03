package fr.univpau.containers;

import com.jfoenix.svg.SVGGlyphLoader;
import fr.univpau.agents.SellerAgent;
import fr.univpau.controllers.components.seller.SellerController;
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

public class SellerContainer extends Application {

    private SellerController gui;
    private SellerAgent agent;

    public static void main(String[] args) {
        launch(SellerContainer.class);
    }

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

        final URL locationSeller = getClass().getResource("/view/components/seller/Seller.fxml");
        final URL locationMain = getClass().getResource("/view/Main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(locationMain);

        Parent root = fxmlLoader.load();
        MainController controller = fxmlLoader.getController();
        controller.setContainer(this);

        controller.changeContent(locationSeller);

        System.out.println(controller);

        double width = 600;
        double height = 600;
        Scene scene = new Scene(root, width, height);

        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(
                SellerContainer.class.getResource("/css/style.css").toExternalForm());

        primaryStage.setTitle("Seller");
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
        primaryStage.show();

        // SellerInterface.start(primaryStage);
    }

    public void startContainer() {
        try {
            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl(false);
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            AgentContainer agentContainer = runtime.createAgentContainer(profile);
            AgentController agentController = agentContainer.createNewAgent("seller", "fr.univpau.agents.SellerAgent", new Object[]{this});
            agentController.start();
        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }

    public void setAgent(SellerAgent agent) {
        this.agent = agent;
    }

    public SellerAgent getAgent() {
        return agent;
    }
}
