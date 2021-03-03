package fr.univpau.gui;

import fr.univpau.agents.SellerAgent;
import fr.univpau.utils.Bid;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SellerInterface {
    private static SellerAgent _seller;
    private static TableView<Bid> tableViewSeller;

    private static Bid toSell;

    private static VBox itemToSell;
    private static HBox vendeur;
    private static HBox lot;
    private static HBox attente;
    private static VBox prix;
    private static HBox acheteur;

    private static Label prix_lot;
    private static Label nom_acheteur;

    public SellerInterface() {
    }

    public void setSellerAgent(SellerAgent s) {
        _seller = s;
    }

    public static void start(Stage stage) throws Exception {

        stage.setTitle("Interface vendeur");
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        GridPane gridPane = new GridPane();

        // <------ SETTINGS ITEMS TO SELL ------>

        Label lblItem = new Label("Bien a vendre");
        lblItem.setPrefWidth(550);
        lblItem.setAlignment(Pos.CENTER);
        lblItem.setPadding(new Insets(0, 0, 10, 0));

        HBox name_item = new HBox();
        Label name_lbl = new Label("Nom Poisson : ");
        name_lbl.setPrefWidth(250);
        name_lbl.setPrefHeight(25);
        name_lbl.setAlignment(Pos.CENTER_RIGHT);
        TextField name_input = new TextField();
        name_item.setPadding(new Insets(10, 0, 10, 0));
        name_item.getChildren().addAll(name_lbl, name_input);

        HBox waiting_offer_item = new HBox();
        Label waiting_lbl = new Label("Temps d'attente d'une offre (s) : ");
        waiting_lbl.setPrefWidth(250);
        waiting_lbl.setPrefHeight(25);
        waiting_lbl.setAlignment(Pos.CENTER_RIGHT);
        TextField waiting_input = new TextField();
        waiting_offer_item.setPadding(new Insets(10, 0, 10, 0));
        waiting_offer_item.getChildren().addAll(waiting_lbl, waiting_input);

        HBox price_item = new HBox();
        Label price_lbl = new Label("Prix Initial (€) : ");
        price_lbl.setPrefWidth(250);
        price_lbl.setPrefHeight(25);
        price_lbl.setAlignment(Pos.CENTER_RIGHT);
        TextField price_input = new TextField();
        price_item.setPadding(new Insets(10, 0, 10, 0));
        price_item.getChildren().addAll(price_lbl, price_input);

        HBox price_varMore_item = new HBox();
        Label price_varMore_lbl = new Label("Pas variation hausse (€) : ");
        price_varMore_lbl.setPrefWidth(250);
        price_varMore_lbl.setPrefHeight(25);
        price_varMore_lbl.setAlignment(Pos.CENTER_RIGHT);
        TextField price_varMore_input = new TextField();
        price_varMore_item.setPadding(new Insets(10, 0, 10, 0));
        price_varMore_item.getChildren().addAll(price_varMore_lbl, price_varMore_input);

        HBox price_varLess_item = new HBox();
        Label price_varLess_lbl = new Label("Pas variation baisse (€) : ");
        price_varLess_lbl.setPrefWidth(250);
        price_varLess_lbl.setPrefHeight(25);
        price_varLess_lbl.setAlignment(Pos.CENTER_RIGHT);
        TextField price_varLess_input = new TextField();
        price_varLess_item.setPadding(new Insets(10, 0, 10, 0));
        price_varLess_item.getChildren().addAll(price_varLess_lbl, price_varLess_input);

        HBox btn_item = new HBox();
        btn_item.setPrefWidth(550);
        Button btn = new Button("Valider");
        btn.setAlignment(Pos.CENTER);
        btn_item.setPadding(new Insets(10, 0, 10, 0));
        btn_item.getChildren().add(btn);

        gridPane.add(lblItem, 0, 0);
        gridPane.add(name_item, 0, 1);
        gridPane.add(waiting_offer_item, 0, 2);
        gridPane.add(price_item, 0, 3);
        gridPane.add(price_varMore_item, 0, 4);
        gridPane.add(price_varLess_item, 0, 5);
        gridPane.add(btn_item, 0, 6);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (name_input.getText().isEmpty()) {
                    System.out.println("Veuillez remplir le champ : \"Nom Poisson\"");
                } else if (waiting_input.getText().isEmpty()) {
                    System.out.println("Veuillez remplir le champ : \"Temps d'attente d'une offre (s)\"");
                } else if (price_input.getText().isEmpty()) {
                    System.out.println("Veuillez remplir le champ : \"Prix Initial (€)\"");
                } else if (price_varMore_input.getText().isEmpty()) {
                    System.out.println("Veuillez remplir le champ : \"Pas variation hausse (€)\"");
                } else if (price_varLess_input.getText().isEmpty()) {
                    System.out.println("Veuillez remplir le champ : \"Pas variation baisse (€)\"");
                } else { // Tous les champs sont remplis
                    try {
                        int nb = Integer.parseInt(waiting_input.getText());
                    } catch (NumberFormatException e) {
                        System.out.println("Le champ \"Temps d'attente d'une offre (s)\" n'est pas un entier");
                    }
                    try {
                        int nb = Integer.parseInt(price_input.getText());
                    } catch (NumberFormatException e) {
                        System.out.println("Le champ \"Prix Initial (€)\" n'est pas un entier");
                    }
                    try {
                        int nb = Integer.parseInt(price_varMore_input.getText());
                    } catch (NumberFormatException e) {
                        System.out.println("Le champ \"Pas variation hausse (€)\" n'est pas un entier");
                    }
                    try {
                        int nb = Integer.parseInt(price_varLess_input.getText());
                    } catch (NumberFormatException e) {
                        System.out.println("Le champ \"Pas variation baisse (€)\" n'est pas un entier");
                    }

                    gridPane.getChildren().removeAll(lblItem, name_item, waiting_offer_item, price_item, price_varMore_item, price_varLess_item, btn_item);

                    toSell = new Bid(_seller.getLocalName(), name_input.getText(), Integer.parseInt(price_input.getText()), Integer.parseInt(waiting_input.getText()), 0);

                    // <------ TABLE INITIALIZATION ------>

                    Label testtxt = new Label("Liste des acheteurs");
                    testtxt.setPrefWidth(250);
                    testtxt.setAlignment(Pos.CENTER);
                    tableViewSeller = new TableView<Bid>();

                    TableColumn<Bid, String> acheteur_colonne = new TableColumn<>("Acheteur");
                    acheteur_colonne.setCellValueFactory(new PropertyValueFactory<>("acheteur_courant"));
                    acheteur_colonne.setPrefWidth(150);

                    TableColumn<Bid, Integer> prix_colonne = new TableColumn<>("Prix €");
                    prix_colonne.setCellValueFactory(new PropertyValueFactory<>("prix"));
                    prix_colonne.setPrefWidth(99);

                    tableViewSeller.getColumns().add(acheteur_colonne);
                    tableViewSeller.getColumns().add(prix_colonne);
                    tableViewSeller.setMaxSize(250, 550);
                    tableViewSeller.setPrefHeight(550);
                    tableViewSeller.setEditable(true);

                    // <------ TESTING TABLE ------>
                    //Enchere test = new Enchere("Inaki", "Patate x12", 10, 0, 1);
                    //test.setAcheteur_courant("Yan Michiels");
                    //tableViewSeller.getItems().add(test);

                    // <------ SELLING ITEM ------>

                    Label itemtxt = new Label("Lot a vendre");
                    itemtxt.setPrefWidth(250);
                    itemtxt.setAlignment(Pos.CENTER);
                    itemtxt.setPadding(new Insets(0, 0, 0, 50));

                    itemToSell = new VBox();
                    itemToSell.setPrefWidth(300);
                    itemToSell.setAlignment(Pos.TOP_CENTER);
                    itemToSell.setPadding(new Insets(10, 0, 0, 50));

                    // Nom vendeur
                    vendeur = new HBox();
                    Label lbl_vendeur = new Label("Nom vendeur : ");
                    lbl_vendeur.setPrefWidth(150);
                    lbl_vendeur.setAlignment(Pos.CENTER_RIGHT);
                    Label nom_vendeur = new Label(toSell.getSellerName());
                    nom_vendeur.setPrefWidth(150);
                    nom_vendeur.setAlignment(Pos.CENTER);
                    vendeur.setPadding(new Insets(10, 0, 10, 0));
                    vendeur.getChildren().addAll(lbl_vendeur, nom_vendeur);

                    // Nom Lot
                    lot = new HBox();
                    Label lbl_lot = new Label("Nom lot : ");
                    lbl_lot.setPrefWidth(150);
                    lbl_lot.setAlignment(Pos.CENTER_RIGHT);
                    Label nom_lot = new Label(toSell.getFishName());
                    nom_lot.setPrefWidth(150);
                    nom_lot.setAlignment(Pos.CENTER);
                    lot.setPadding(new Insets(10, 0, 10, 0));
                    lot.getChildren().addAll(lbl_lot, nom_lot);

                    // Delai Attente
                    attente = new HBox();
                    Label lbl_att = new Label("Délai attente : ");
                    lbl_att.setPrefWidth(150);
                    lbl_att.setAlignment(Pos.CENTER_RIGHT);
                    Label delai_att = new Label(toSell.getWaitingTime() + "s");
                    delai_att.setPrefWidth(150);
                    delai_att.setAlignment(Pos.CENTER);
                    attente.setPadding(new Insets(10, 0, 10, 0));
                    attente.getChildren().addAll(lbl_att, delai_att);

                    // Prix Lot
                    prix = new VBox();
                    HBox prix_actuel = new HBox();
                    Label lbl_prix = new Label("Prix actuel : ");
                    lbl_prix.setPrefWidth(150);
                    lbl_prix.setAlignment(Pos.CENTER_RIGHT);
                    prix_lot = new Label(toSell.getPrice() + "€");
                    prix_lot.setPrefWidth(150);
                    prix_lot.setAlignment(Pos.CENTER);
                    prix_actuel.setPadding(new Insets(0, 0, 10, 0));
                    prix_actuel.getChildren().addAll(lbl_prix, prix_lot);
                    HBox var_more = new HBox();
                    Label lbl_more = new Label("Pas hausse prix : ");
                    lbl_more.setPrefWidth(150);
                    lbl_more.setAlignment(Pos.CENTER_RIGHT);
                    Label nb_more = new Label(price_varMore_input.getText() + "€");
                    nb_more.setPrefWidth(150);
                    nb_more.setAlignment(Pos.CENTER);
                    var_more.setPadding(new Insets(10, 0, 10, 0));
                    var_more.getChildren().addAll(lbl_more, nb_more);
                    HBox var_less = new HBox();
                    Label lbl_less = new Label("Pas baisse prix : ");
                    lbl_less.setPrefWidth(150);
                    lbl_less.setAlignment(Pos.CENTER_RIGHT);
                    Label nb_less = new Label(price_varLess_input.getText() + "€");
                    nb_less.setPrefWidth(150);
                    nb_less.setAlignment(Pos.CENTER);
                    var_less.setPadding(new Insets(10, 0, 0, 0));
                    var_less.getChildren().addAll(lbl_less, nb_less);
                    prix.setPadding(new Insets(10, 0, 10, 0));
                    prix.getChildren().addAll(prix_actuel, var_more, var_less);

                    // Nom Acheteur actuel
                    acheteur = new HBox();
                    Label lbl_acheteur = new Label("Acheteur actuel : ");
                    lbl_acheteur.setPrefWidth(150);
                    lbl_acheteur.setAlignment(Pos.CENTER_RIGHT);
                    nom_acheteur = new Label(toSell.getBuyerName());
                    nom_acheteur.setPrefWidth(150);
                    nom_acheteur.setAlignment(Pos.CENTER);
                    acheteur.setPadding(new Insets(10, 0, 10, 0));
                    acheteur.getChildren().addAll(lbl_acheteur, nom_acheteur);

                    itemToSell.getChildren().addAll(vendeur, lot, attente, prix, acheteur);
                    _seller.setBid(toSell);

                    gridPane.add(testtxt, 0, 0);
                    gridPane.add(tableViewSeller, 0, 1);
                    gridPane.add(itemtxt, 1, 0);
                    gridPane.add(itemToSell, 1, 1);
                }
            }
        });

        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        vBox.getChildren().add(gridPane);
        borderPane.setCenter(vBox);

        Scene scene = new Scene(borderPane, 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void addToList(Bid e) {
        Platform.runLater(() -> {
            tableViewSeller.getItems().add(e);
            updateItem(e);
        });
    }

    public void updateItem(Bid e) {
        Platform.runLater(() -> {
            prix_lot.setText(e.getPrice() + " €");
            nom_acheteur.setText(e.getBuyerName());
        });
    }

}
