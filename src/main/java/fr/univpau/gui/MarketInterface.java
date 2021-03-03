package fr.univpau.gui;

import fr.univpau.agents.MarketAgent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import fr.univpau.utils.Bid;

public class MarketInterface {
	private MarketAgent _market;
	private static TableView<Bid> tableViewMarket;
	
	public MarketInterface() {}
	
	public void setMarketAgent(MarketAgent m) {
		_market = m;
	}
	
	public static void start(Stage stage) throws Exception {
		stage.setTitle("Interface marché");
		BorderPane borderPane = new BorderPane();

		VBox vBox = new VBox();
		Label txt = new Label();
		txt.setText("Liste des enchères");
		vBox.getChildren().add(txt);
		GridPane gridPane = new GridPane();
		
		// <------ TABLE INITIALIZATION ------>
		
		tableViewMarket = new TableView<Bid>();
		
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
		
		tableViewMarket.getColumns().add(vendeur_colonne);
		tableViewMarket.getColumns().add(nom_lot_colonne);
		tableViewMarket.getColumns().add(acheteur_courant_colonne);
		tableViewMarket.getColumns().add(prix_colonne);
		tableViewMarket.getColumns().add(finie_colonne);
		tableViewMarket.setEditable(true);
		tableViewMarket.setPrefWidth(500);
		tableViewMarket.setPrefHeight(500);
		
		// <------ TESTING TABLE ------>
		//Enchere test = new Enchere("Inaki", "Patate x12", 10, 0, 1);
		//Enchere test2 = new Enchere("Inaki", "Tomate x10", 7, 0, 1);
		//test2.setAcheteur_courant("Yan Michiels");
		//tableViewMarket.getItems().add(test);
		//tableViewMarket.getItems().set(0, test2);
		
		gridPane.add(tableViewMarket, 0, 0);
		vBox.setPadding(new Insets(10));
		vBox.setSpacing(10);
		vBox.getChildren().add(gridPane);
		borderPane.setCenter(vBox);

		Scene scene = new Scene(borderPane, 600, 600);
		stage.setScene(scene);
		stage.show();	
	}
	
	public void addItemList(Bid e) {
		Platform.runLater(() -> {
			tableViewMarket.getItems().add(e);
		});
	}
	
	public void updateItemList(int index, Bid e) {
		Platform.runLater(() -> {
			tableViewMarket.getItems().set(index, e);
		});
	}
	
	public void deleteItemList(int index) {
		Platform.runLater(() -> {
			tableViewMarket.getItems().remove(index);
		});
	}
}
