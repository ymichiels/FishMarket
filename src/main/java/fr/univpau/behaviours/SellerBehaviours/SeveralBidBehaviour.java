package fr.univpau.behaviours.SellerBehaviours;

import fr.univpau.agents.SellerAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import fr.univpau.utils.Performatives;
import fr.univpau.utils.Steps;

import java.util.Calendar;

public class SeveralBidBehaviour extends Behaviour {
    private MainBehaviour mb;
    private int step = Steps.STEP_WAIT_FOR_BID;
    private boolean isFinished = false;
    private long clock = -1;
    public SeveralBidBehaviour(MainBehaviour mb){
        this.mb = mb;
    }
    @Override
    public void action() {
        //mb.getAgent().changeStatus(mb.getEnchere(), Steps.STEP_SEVERAL_BID);
        switch(step){
            case Steps.STEP_WAIT_FOR_BID:
                MessageTemplate messageTemplate = MessageTemplate.and(MessageTemplate.MatchPerformative(Performatives.TO_BID),
                                                                        MessageTemplate.MatchInReplyTo(mb.getBid().getFishName()));
                ACLMessage msg = mb.getAgent().receive(messageTemplate);
                if(msg != null){
                    try{
                        mb.getAgent().addBid(msg.getContentObject());
                        clock = Calendar.getInstance().getTimeInMillis();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    isFinished = true;
                }
                break;
            default:
                break;
        }

        if(clock <0){
            clock = Calendar.getInstance().getTimeInMillis();
        }

        if (clock > 0 && (clock+mb.getBid().getWaitingTime() < Calendar.getInstance().getTimeInMillis())){
            //mb.getAgent().changeStatus(mb.getEnchere(), Steps.STEP_REANOUNCE_STATUS);
            isFinished = true;
        }
    }

    @Override
    public boolean done() {
        return isFinished;
    }

    @Override
    public int onEnd(){
        clock = -1;
        step = Steps.STEP_WAIT_FOR_BID;
        mb.getBid().setPrice(mb.getBid().getPrice()+mb.getBid().getStep());
        return Performatives.TO_ANNOUNCE;
    }
}
