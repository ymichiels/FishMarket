package fr.univpau.gui;

import fr.univpau.agents.BuyerAgent;
import fr.univpau.utils.Bid;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BuyerInterface {

    private BuyerAgent _buyer;
    private static TableView<Bid> tableViewBuyer;
    private static ObservableList<Bid> subscribedEncheres;

    public BuyerInterface() {
    }

    public void setBuyerAgent(BuyerAgent b) {
        _buyer = b;
    }

    public static void start(Stage stage) throws Exception {
        stage.setTitle("Interface Acheteur");
        BorderPane borderPane = new BorderPane();

        VBox vBox = new VBox();
        Label txt = new Label();
        txt.setText("Choisir les enchères auxquelles s'abonner");
        vBox.getChildren().add(txt);
        GridPane gridPane = new GridPane();

        // <------ TABLE INITIALIZATION ------>

        tableViewBuyer = new TableView<Bid>();

        TableColumn<Bid, String> vendeur_colonne = new TableColumn<>("Vendeur");
        vendeur_colonne.setCellValueFactory(new PropertyValueFactory<>("nom_vendeur"));

        TableColumn<Bid, String> nom_lot_colonne = new TableColumn<>("Nom Poisson");
        nom_lot_colonne.setCellValueFactory(new PropertyValueFactory<>("nom_poisson"));

        TableColumn<Bid, String> acheteur_courant_colonne = new TableColumn<>("Acheteur Courant");
        acheteur_courant_colonne.setCellValueFactory(new PropertyValueFactory<>("acheteur_courant"));

        TableColumn<Bid, Integer> prix_colonne = new TableColumn<>("Prix Actuel €");
        prix_colonne.setCellValueFactory(new PropertyValueFactory<>("prix"));

        TableColumn<Bid, Boolean> finie_colonne = new TableColumn<>("Terminée");
        finie_colonne.setCellValueFactory(new PropertyValueFactory<>("isFinished"));

        tableViewBuyer.getColumns().add(vendeur_colonne);
        tableViewBuyer.getColumns().add(nom_lot_colonne);
        tableViewBuyer.getColumns().add(acheteur_courant_colonne);
        tableViewBuyer.getColumns().add(prix_colonne);
        tableViewBuyer.getColumns().add(finie_colonne);
        tableViewBuyer.setEditable(true);
        tableViewBuyer.setPrefWidth(500);
        tableViewBuyer.setPrefHeight(500);
        tableViewBuyer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        gridPane.add(tableViewBuyer, 0, 0);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        vBox.getChildren().add(gridPane);
        Button subscribe = new Button("Valider");
        subscribe.setOnAction(new EventHandler<ActionEvent>() { // Une fois que les encheres sont selectionnées
            @Override
            public void handle(ActionEvent e) {
                txt.setText("Choisir l'enchere sur laquelle encherir");
                subscribedEncheres = tableViewBuyer.getSelectionModel().getSelectedItems();

                tableViewBuyer.getItems().clear();
                vBox.getChildren().remove(subscribe);

                tableViewBuyer.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                tableViewBuyer.getItems().addAll(subscribedEncheres);

                // <------ FOR MANUAL MODE ------>

                Button bid = new Button("Bid");
                bid.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Recuperer l'enchere cliquée
                        // tableViewBuyer.getSelectionModel().getSelectedItem();
                    }
                });
                bid.setDisable(true);
                vBox.getChildren().add(bid);
            }
        });
        vBox.getChildren().add(subscribe);
        borderPane.setCenter(vBox);

        Scene scene = new Scene(borderPane, 400, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void addItemList(Bid e) {
        Platform.runLater(() -> {
            tableViewBuyer.getItems().add(e);
        });
    }

    public void updateItemList(int index, Bid e) {
        Platform.runLater(() -> {
            tableViewBuyer.getItems().set(index, e);
        });
    }

    public void deleteItemList(int index) {
        Platform.runLater(() -> {
            tableViewBuyer.getItems().remove(index);
        });
    }

    public void updateSubscribedList(int index, Bid e) {
        Platform.runLater(() -> {
            tableViewBuyer.getItems().set(index, e);
        });
    }

    public void bid(Bid e) {
        Platform.runLater(() -> {
            if (_buyer.getSubscribedEncheres().contains(e)) {
                int index = _buyer.getSubscribedEncheres().indexOf(e);
                tableViewBuyer.getItems().set(index, e);
            }
        });
    }

}
