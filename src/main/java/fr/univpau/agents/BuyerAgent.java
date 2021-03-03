package fr.univpau.agents;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import fr.univpau.behaviours.BuyerBehaviours.BuyerListenerBehaviour;
import fr.univpau.behaviours.BuyerBehaviours.MainBehaviour;
import fr.univpau.containers.BuyerContainer;
import fr.univpau.controllers.BuyerGUIController;
import fr.univpau.gui.BuyerInterface;
import fr.univpau.utils.Bid;
import fr.univpau.utils.MessageSender;
import fr.univpau.utils.Performatives;
import fr.univpau.utils.RegisterBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;


public class BuyerAgent extends GuiAgent {

    private boolean isManual = true;
    private List<MainBehaviour> behaviours;
    private List<Bid> encheres;
    private List<Bid> subscribedEnchere;
    private boolean isStarted;
    private int bidLimit;
    private BuyerContainer gui;
    private BuyerGUIController controller;
    
    @Override
    protected void setup() {
        System.out.println("Initialisation de l'agent " + getAID().getName());
        addBehaviour(new RegisterBehaviour(this));
        addBehaviour(new BuyerListenerBehaviour(this));
    }

    @Override
    protected void takeDown() {
        System.out.println("Destruction de l'agent");
    }

    @Override
    protected void beforeMove() {
    	this.gui = (BuyerContainer) getArguments()[0];
    	gui.setAgent(this);
    	
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

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
    }
    
    public void addBid(Serializable s){
        Bid e = (Bid)s;

        if(encheres.contains(e)){
            encheres.set(encheres.indexOf(e), e);
            controller.updateGUIList(encheres.indexOf(e), e);
            if(subscribedEnchere.contains(e)){
                subscribedEnchere.set(subscribedEnchere.indexOf(e), e);
                controller.updateSubscribedGUIList(subscribedEnchere.indexOf(e), e);
                autoBid(e);
            }
        }else{
            encheres.add(e);
            controller.addGUIList(e);
        }
    }

    public void setSubsctiptions(Bid[] subs){
        subscribedEnchere.clear();
        subscribedEnchere.addAll(Arrays.asList(subs));
    }

    public void bindBehaviousToSubscriptions(){
        for(Bid e: subscribedEnchere){
            MainBehaviour mb = new MainBehaviour(this, e);
            behaviours.add(mb);
            addBehaviour(mb);

            autoBid(e);
        }
    }

    public void autoBid(Bid e){
        if(!isManual && e.getPrice() <= bidLimit && !e.isFinished()){
            e.setBuyerName(this.getLocalName());
            addBehaviour(new MessageSender(this, Performatives.TO_BID, new String[]{MarketAgent.NAME}, e));
            
        }
    }

    public boolean isManual(){
        return this.isManual;
    }
    
    public List<Bid> getSubscribedEncheres(){
    	return this.subscribedEnchere;
    }

}
