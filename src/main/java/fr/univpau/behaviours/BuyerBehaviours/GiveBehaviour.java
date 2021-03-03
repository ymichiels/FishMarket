package fr.univpau.behaviours.BuyerBehaviours;

import fr.univpau.utils.Performatives;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GiveBehaviour extends Behaviour {
    private final MainBehaviour mb;
    private boolean fini = false;

    public GiveBehaviour(MainBehaviour behaviour) {
        this.mb = behaviour;
    }

    @Override
    public void action() {
        //idem que dans bd, on réagi seulement aux performatives de type TO_GIVE et dont inreplywith a contient nom_vendeut+poisson
        MessageTemplate messageTemplate = MessageTemplate.and(MessageTemplate.MatchPerformative(Performatives.TO_GIVE),
                MessageTemplate.MatchInReplyTo(mb.getEnchere().getSellerName()
                        + mb.getEnchere().getFishName()));
        ACLMessage msg = mb.getAgent().receive(messageTemplate);
        //si le message est reçu on fait des MAj dans le GUI et on a fini
        if (msg != null) {
            //TODO: changer le status de l'agent
            System.out.println(myAgent.getAID().getName() + ": produit reçu");
            fini = true;
        } else {
            block();
        }

    }

    @Override
    public boolean done() {
        return fini;
    }

    //on retourne toujours la performative pour dire qu'on a fini
    @Override
    public int onEnd() {
        return Performatives.TO_PAY;
    }
}
