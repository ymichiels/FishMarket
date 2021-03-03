package fr.univpau.behaviours.BuyerBehaviours;

import fr.univpau.agents.BuyerAgent;
import fr.univpau.utils.Bid;
import fr.univpau.utils.Performatives;
import jade.core.behaviours.FSMBehaviour;

public class MainBehaviour extends FSMBehaviour {
    BuyerAgent ctx;
    Bid e;

    //fonctionne comme une machine à état
    public MainBehaviour(BuyerAgent agent, Bid e) {
        super(agent);
        this.ctx = agent;
        this.e = e;


        //ajout des états de la machine
        registerFirstState(new BidBehaviour(this), "Bid");
        registerState(new AttributionBehaviour(this), "Attribute");
        registerState(new GiveBehaviour(this), "Give");
        registerLastState(new BuyerBehaviour(this), "Pay");

        //définition des transitions
        registerDefaultTransition("Bid", "Bid");
        registerTransition("Bid", "Attribute", Performatives.TO_ATTRIBUTE);
        registerTransition("Attribute", "Give", Performatives.TO_GIVE);
        registerTransition("Give", "Pay", Performatives.TO_PAY);

    }

    public Bid getEnchere() {
        return this.e;
    }

    public BuyerAgent getAgent() {
        return this.ctx;
    }

    @Override
    public int onEnd() {
        return super.onEnd();
    }
}
