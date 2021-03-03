package fr.univpau.behaviours.SellerBehaviours;

import fr.univpau.utils.Performatives;
import fr.univpau.utils.Steps;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.Calendar;

public class OneBidBehaviour extends Behaviour {
    private final MainBehaviour mb;
    private int step = Steps.STEP_WAIT_FOR_BID;
    private boolean isFinished = false;
    private long clock = -1;

    public OneBidBehaviour(MainBehaviour mb) {
        this.mb = mb;
    }

    @Override
    public void action() {
        //mb.getAgent().changeStatus(mb.getEnchere(), Steps.STEP_ONE_BID);
        switch (step) {
            case Steps.STEP_WAIT_FOR_BID:
                MessageTemplate messageTemplate = MessageTemplate.and(MessageTemplate.MatchPerformative(Performatives.TO_BID),
                        MessageTemplate.MatchInReplyTo(mb.getBid().getFishName()));
                ACLMessage msg = mb.getAgent().receive(messageTemplate);
                if (msg != null) {
                    try {
                        mb.getAgent().addBid(msg.getContentObject());
                        clock = Calendar.getInstance().getTimeInMillis();
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    step = Steps.END_WITH_BID;
                }

                //initialisation de la clock
                if (clock < 0) {
                    clock = Calendar.getInstance().getTimeInMillis();
                }

                //si trop d'attente on fini
                if (clock > 0 && (clock + mb.getBid().getWaitingTime() < Calendar.getInstance().getTimeInMillis())) {
                    isFinished = true;
                }
        }
    }

    @Override
    public int onEnd() {
        clock = -1;
        step = Steps.STEP_WAIT_FOR_BID;
        if (step == Steps.END_WITH_BID) {
            return Performatives.TO_BID;
        }
        return Performatives.TO_ATTRIBUTE;

    }

    @Override
    public boolean done() {
        return isFinished || step == Steps.END_WITH_BID;
    }
}
