package fr.univpau.utils;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class RegisterBehaviour extends OneShotBehaviour {
    private final Agent ctx;
    public static final String VENDEUR = "vendeur";
    public static final String ACHETEUR = "acheteur";

    public RegisterBehaviour(Agent a) {
        this.ctx = a;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(Performatives.TO_REGISTER);
        msg.addReceiver(new AID(ctx.getClass().getSimpleName(), AID.ISLOCALNAME));
        switch (ctx.getClass().getSimpleName()) {
            case "BuyerAgent":
                msg.setContent(ACHETEUR);
                break;
            case "SellerAgent":
                msg.setContent(VENDEUR);
                break;
        }
        ctx.send(msg);
    }
}
