package fr.univpau.behaviours.SellerBehaviours;

import fr.univpau.agents.MarketAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import fr.univpau.utils.MessageSender;
import fr.univpau.utils.Performatives;
import fr.univpau.utils.Steps;

import java.util.Calendar;

public class AnnounceBehaviour extends Behaviour {
    private boolean isFinished = false;
    private long clock = -1;
    private int step = Steps.STEP_ANNOUNCE;
    private MainBehaviour mb;

    public AnnounceBehaviour(MainBehaviour mb){
        this.mb = mb;
    }
    @Override
    public void action() {
        switch (step){
            case Steps.STEP_ANNOUNCE:
                mb.getAgent().addBehaviour(new MessageSender(mb.getAgent(), Performatives.TO_ANNOUNCE, new String[]{MarketAgent.NAME}, mb.getBid()));
                step = Steps.STEP_WAIT_FOR_BID;
                clock = Calendar.getInstance().getTimeInMillis();
                break;
            case Steps.STEP_WAIT_FOR_BID:
                MessageTemplate messageTemplate = MessageTemplate.and(MessageTemplate.MatchPerformative(Performatives.TO_BID),
                                                                       MessageTemplate.MatchInReplyTo(mb.getBid().getFishName()));
                ACLMessage msg = mb.getAgent().receive(messageTemplate);
                if(msg != null){
                    try {
                        mb.getAgent().addBid(msg.getContentObject());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    isFinished = true;
                }
        }

        // si temps d'attente trop long on baisse le prix
        if(clock >0 && (clock + mb.getBid().getWaitingTime()<Calendar.getInstance().getTimeInMillis())){
            //mb.getAgent().changeStatus(mg.getBid, Steps.STEP_REANNOUNCE_STATUS)
            mb.getBid().setPrice(mb.getBid().getPrice()-mb.getBid().getStep());
            step = Steps.STEP_ANNOUNCE;
            clock = -1;
        }
    }

    @Override
    public boolean done() {
        return isFinished;
    }

    @Override
    public int onEnd(){
        clock = -1;
        return Performatives.TO_BID;
    }
}
