package fr.univpau.behaviours.SellerBehaviours;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import fr.univpau.utils.Performatives;
import fr.univpau.utils.Steps;

public class PayBehaviour extends Behaviour {
    private MainBehaviour mb;
    private int step = Steps.STEP_WAIT_THUNE;
    private boolean isFinished = false;

    public PayBehaviour(MainBehaviour mb) {
        this.mb = mb;
    }

    @Override
    public void action() {
        switch(step){
            case Steps.STEP_WAIT_THUNE:
                MessageTemplate messageTemplate = MessageTemplate.and(MessageTemplate.MatchPerformative(Performatives.TO_PAY),
                                                                      MessageTemplate.MatchInReplyTo(mb.getBid().getFishName()));
                ACLMessage msg = mb.getAgent().receive(messageTemplate);
                if (msg != null){
                    //mb.getAgent().changeStatus(mb.getEnchere(), Steps.STEP_PAY);
                    isFinished = true;
                }else{
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
    public int onEnd(){
        return super.onEnd();
    }
}
