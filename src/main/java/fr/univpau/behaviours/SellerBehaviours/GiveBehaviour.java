package fr.univpau.behaviours.SellerBehaviours;

import fr.univpau.agents.MarketAgent;
import fr.univpau.utils.MessageSender;
import fr.univpau.utils.Performatives;
import jade.core.behaviours.Behaviour;

import java.util.Calendar;

public class GiveBehaviour extends Behaviour {
    private long clock = -1;
    private boolean isFinished = false;
    private final int DELAY = 2000;
    private final MainBehaviour mb;

    public GiveBehaviour(MainBehaviour mb) {
        this.mb = mb;
    }

    @Override
    public void action() {
        if (clock < 0) {
            clock = Calendar.getInstance().getTimeInMillis();
        }

        if (clock > 0 && (clock + DELAY < Calendar.getInstance().getTimeInMillis())) {
            //mb.getAgent().changeStatus(mb.getEnchere(), Steps.STEP_GIVE);
            mb.getAgent().addBehaviour(new MessageSender(mb.getAgent(), Performatives.TO_GIVE, new String[]{MarketAgent.NAME}, mb.getBid()));
            isFinished = true;
        }
    }

    @Override
    public boolean done() {
        return isFinished;
    }

    @Override
    public int onEnd() {
        return Performatives.TO_PAY;
    }
}
