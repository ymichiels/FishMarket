package fr.univpau.behaviours.MarketBehaviours;

import fr.univpau.agents.MarketAgent;
import fr.univpau.utils.Bid;
import fr.univpau.utils.Performatives;
import fr.univpau.utils.RegisterBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.Serializable;

public class MarketBehaviour extends CyclicBehaviour {
    private final MarketAgent ctx;

    public MarketBehaviour(MarketAgent a) {
        this.ctx = a;
    }

    @Override
    public void action() {
        //réception d'un message
        ACLMessage msg = myAgent.receive();
        //si on reçoit bien un message
        if (msg != null) {
            switch (msg.getPerformative()) {
                case Performatives.TO_REGISTER:
                    switch (msg.getContent()) {
                        case RegisterBehaviour.VENDEUR:
                            ctx.addBuyer(msg.getSender().getLocalName());
                            break;
                        case RegisterBehaviour.ACHETEUR:
                            ctx.addSeller(msg.getSender().getLocalName());
                            break;
                    }
                    break;

                case Performatives.TO_ANNOUNCE:

                    try {
                        Serializable e = msg.getContentObject(); //récupère les infor sur l'enchère
                        ctx.addBid((Bid) e);
                    } catch (UnreadableException unreadableException) {
                        unreadableException.printStackTrace();
                    }
                    break;

                case Performatives.TO_ATTRIBUTE:
                    try {
                        ctx.toAttribute(msg.getContentObject());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    break;

                case Performatives.TO_BID:
                    try {
                        Serializable e = msg.getContentObject();
                        ctx.onNewBid(e);
                    } catch (UnreadableException unreadableException) {
                        unreadableException.printStackTrace();
                    }

                case Performatives.TO_GIVE:
                    try {
                        ctx.toGive(msg.getContentObject());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    break;

                case Performatives.TO_PAY:
                    try {
                        ctx.toPay(msg.getContentObject());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + msg.getPerformative());
            }
        } else {
            block();
        }
    }
}
