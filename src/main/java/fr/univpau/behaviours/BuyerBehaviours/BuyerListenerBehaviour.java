package fr.univpau.behaviours.BuyerBehaviours;

import fr.univpau.agents.BuyerAgent;
import fr.univpau.utils.Performatives;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.Serializable;

public class BuyerListenerBehaviour extends CyclicBehaviour {
    private final BuyerAgent agent;

    public BuyerListenerBehaviour(BuyerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        //reception des messages
        MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(Performatives.TO_ANNOUNCE);
        ACLMessage msg = agent.receive(messageTemplate);

        //si on reçoit bien un message
        if (msg != null) {

            try {
                Serializable e = msg.getContentObject();
                //TODO: ajouter une nouvelle encjère dans le tableau des enchères disponibles en appelant la fonction dans l'agent (une fois créée)
            } catch (UnreadableException unreadableException) {
                unreadableException.printStackTrace();
            }
        }
    }
}
