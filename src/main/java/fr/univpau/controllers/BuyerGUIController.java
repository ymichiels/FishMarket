package fr.univpau.controllers;

import fr.univpau.agents.BuyerAgent;
import fr.univpau.gui.BuyerInterface;
import fr.univpau.utils.Bid;
import javafx.application.Platform;

public class BuyerGUIController {
	private BuyerInterface _buyerGui;
	private BuyerAgent _buyer;
	
	public BuyerGUIController(BuyerInterface gui, BuyerAgent b) {
		_buyerGui = gui;
		_buyer = b;
	}
	
	public void addGUIList(Bid e) {
		Platform.runLater(() -> {
			_buyerGui.addItemList(e);
		});
	}
	
	public void updateGUIList(int index, Bid e) {
		Platform.runLater(() -> {
			_buyerGui.updateItemList(index, e);
		});
	}
	
	public void deleteGUIList(int index) {
		Platform.runLater(() -> {
			_buyerGui.deleteItemList(index);
		});
	}
	
	public void updateSubscribedGUIList(int index, Bid e) {
		Platform.runLater(() -> {
			_buyerGui.updateSubscribedList(index, e);
		});
	}
}
