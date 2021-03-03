package fr.univpau.behaviours.SellerBehaviours;

import fr.univpau.utils.Performatives;
import fr.univpau.utils.Steps;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PayBehaviour extends Behaviour {
    private final MainBehaviour mb;
    private final int step = Steps.STEP_WAIT_THUNE;
    private boolean isFinished = false;

    public PayBehaviour(MainBehaviour mb) {
        this.mb = mb;
    }

    @Override
    public void action() {
        switch (step) {
            case Steps.STEP_WAIT_THUNE:
                MessageTemplate messageTemplate = MessageTemplate.and(MessageTemplate.MatchPerformative(Performatives.TO_PAY),
                        MessageTemplate.MatchInReplyTo(mb.getBid().getFishName()));
                ACLMessage msg = mb.getAgent().receive(messageTemplate);
                if (msg != null) {
                    //mb.getAgent().changeStatus(mb.getEnchere(), Steps.STEP_PAY);
                    isFinished = true;
                } else {
                    block();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean done() {
        return isFinished;
    }

    @Override
    public int onEnd() {
        return super.onEnd();
    }
}
