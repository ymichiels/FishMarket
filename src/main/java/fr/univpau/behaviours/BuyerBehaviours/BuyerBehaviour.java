package fr.univpau.behaviours.BuyerBehaviours;

import fr.univpau.agents.MarketAgent;
import fr.univpau.utils.MessageSender;
import fr.univpau.utils.Performatives;
import jade.core.behaviours.Behaviour;

import java.util.Calendar;

public class BuyerBehaviour extends Behaviour {
    private long clock = -1;
    private final MainBehaviour mb;
    private boolean fini = false;
    public static final int DELAI = 2000;

    public BuyerBehaviour(MainBehaviour behaviour) {
        this.mb = behaviour;
    }

    //pas tout compris mais en gros je crois que tant que le delai de 2s est pas passé, on spamme de messages (aucune idée de pourquoi)
    @Override
    public void action() {
        if (clock < 0) {
            clock = Calendar.getInstance().getTimeInMillis();
        } else if (clock + DELAI < Calendar.getInstance().getTimeInMillis()) {
            mb.getAgent().addBehaviour(new MessageSender(this.myAgent, Performatives.TO_PAY, new String[]{MarketAgent.NAME}, mb.getEnchere()));
            //TODO: update le GUI
            fini = true;
        }
    }

    @Override
    public boolean done() {
        return fini;
    }

    //retour de la performative pour la transition
    @Override
    public int onEnd() {
        return Performatives.TO_PAY;
    }
}
