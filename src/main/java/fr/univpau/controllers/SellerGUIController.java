package fr.univpau.controllers;

import fr.univpau.agents.SellerAgent;
import fr.univpau.gui.SellerInterface;
import javafx.application.Platform;
import fr.univpau.utils.Bid;

public class SellerGUIController {
	private SellerInterface _sellerGui;
	private SellerAgent _seller;
	
	public SellerGUIController(SellerInterface gui, SellerAgent b) {
		_sellerGui = gui;
		_seller = b;
	}
	
	public void addToGUIList(Bid e) {
		_sellerGui.addToList(e);
	}
	
}
