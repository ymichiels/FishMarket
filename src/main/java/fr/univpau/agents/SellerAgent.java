package fr.univpau.agents;

import java.io.Serializable;
import java.util.List;

import fr.univpau.behaviours.SellerBehaviours.MainBehaviour;
import fr.univpau.containers.SellerContainer;
import fr.univpau.controllers.SellerGUIController;
import fr.univpau.gui.SellerInterface;
import fr.univpau.utils.Bid;
import fr.univpau.utils.RegisterBehaviour;
import jade.core.Agent;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

public class SellerAgent extends GuiAgent {
    private String myName;
    private List<String[]> props;
    private List<Bid> encheres;
    private SellerContainer gui;
    private SellerGUIController controller;
    private Bid item;
    
    @Override
    protected void setup() {
        System.out.println("Initialisation de l'agent " + getAID().getName());
        gui = (SellerContainer)getArguments()[0];
        gui.setAgent(this);
        addBehaviour(new RegisterBehaviour(this));
    }

    @Override
    protected void takeDown() {
        System.out.println("Destruction de l'agent");
    }

    @Override
    protected void beforeMove() {
        try {
			System.out.println("Avant migration du container " + getAID().getName() + 
					" vers " + this.getContainerController().getContainerName());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }

    @Override
    protected void afterMove() {
    	try {
			System.out.println("Après migration du container " + getAID().getName() + 
					" vers " + this.getContainerController().getContainerName());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    
    public void addBid(Serializable s){
        Bid e = (Bid)s;
        props.add(new String[]{e.getFishName(), Integer.toString(e.getPrice()), e.getBuyerName()});
        if(encheres.contains(e)){
            int idx = encheres.indexOf(e);
            Bid enchere = encheres.get(idx);
            enchere.setBuyerName(e.getBuyerName());
            encheres.set(idx, enchere);
            controller.addToGUIList(e);
        }
    }
    
    public void setBid (Bid e) {
    	Bid item = e;
    }
    
    
    @Override
    protected void onGuiEvent(GuiEvent event) {
    	switch(event.getType()) {
    		case 1:
    			Integer prix = (Integer) event.getParameter(0);
    			Integer delay = (Integer) event.getParameter(1);
    			Integer step = (Integer) event.getParameter(2);
    			String name = (String) event.getParameter(3);
    			Bid bid = new Bid(getLocalName(), name, prix, delay, step );
    			if(encheres.contains(bid)) {
    				//TODO: afficher que l'enchère existe deja
    				System.out.println("l'enchère existe deja!");
    			}else {
    				encheres.add(bid);
    				addBehaviour(new MainBehaviour(this, bid));
    			}
    	}
    }
    
    
    
    
}
