package fr.univpau.behaviours.SellerBehaviours;

import fr.univpau.agents.MarketAgent;
import fr.univpau.utils.MessageSender;
import fr.univpau.utils.Performatives;
import jade.core.behaviours.OneShotBehaviour;

public class AttributeBehaviour extends OneShotBehaviour {

    private final MainBehaviour mb;

    public AttributeBehaviour(MainBehaviour mb) {
        this.mb = mb;
    }

    @Override
    public void action() {
        //mb.getAgent().changeStatus(mb.getEnchere(), Steps.STEP_ATTRIBUTE);
        mb.getAgent().addBehaviour(new MessageSender(mb.getAgent(), Performatives.TO_ATTRIBUTE, new String[]{MarketAgent.NAME}, mb.getBid()));
    }

    @Override
    public int onEnd() {
        return Performatives.TO_GIVE;
    }
}
