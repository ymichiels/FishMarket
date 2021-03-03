package fr.univpau.behaviours.BuyerBehaviours;

import fr.univpau.utils.Performatives;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BidBehaviour extends Behaviour {
    private final MainBehaviour ctx;
    private boolean fini = false;

    public BidBehaviour(MainBehaviour ctx) {
        this.ctx = ctx;
    }

    @Override
    public void action() {
        //on ne réagit que aux messages de type TO_ATTRIBUTE et dont l"atat inreplywith contient le nom du vendeur et le produit concaténnés
        MessageTemplate messageTemplate = MessageTemplate.and(MessageTemplate.MatchPerformative(Performatives.TO_ATTRIBUTE),
                MessageTemplate.MatchInReplyTo(ctx.getEnchere().getSellerName() + ctx.getEnchere().getFishName()));
        ACLMessage msg = ctx.getAgent().receive(messageTemplate);
        //si le message existe, on a fini
        if (msg != null) {
            fini = true;
        }
    }

    @Override
    public boolean done() {
        return fini;
    }

    //une fois ce comportement terminé, on retourne la performative associée pour déclencher la transition
    @Override
    public int onEnd() {
        return Performatives.TO_ATTRIBUTE;
    }
}
