package fr.univpau.controllers;

import fr.univpau.agents.MarketAgent;
import fr.univpau.gui.MarketInterface;
import javafx.application.Platform;
import fr.univpau.utils.Bid;

public class MarketGUIController {
	private MarketInterface _marketGui;
	private MarketAgent _market;
	
	public MarketGUIController(MarketInterface gui, MarketAgent m) {
		_marketGui = gui;
		_market = m;
	}
	
	public void addGUIList(Bid e) {
		Platform.runLater(() -> {
			_marketGui.addItemList(e);
		});
	}
	
	public void updateGUIList(int index, Bid e) {
		Platform.runLater(() -> {
			_marketGui.updateItemList(index, e);
		});
	}
	public void deleteGUIList(int index) {
		Platform.runLater(() -> {
			_marketGui.deleteItemList(index);
		});
	}
}
