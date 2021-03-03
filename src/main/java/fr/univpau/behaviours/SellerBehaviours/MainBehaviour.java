package fr.univpau.behaviours.SellerBehaviours;

import fr.univpau.agents.SellerAgent;
import jade.core.behaviours.FSMBehaviour;
import fr.univpau.utils.Bid;
import fr.univpau.utils.Performatives;

public class MainBehaviour extends FSMBehaviour {
    private Bid e;
    private SellerAgent sellerAgent;

    public  MainBehaviour(SellerAgent sellerAgent, Bid enchere){
        super(sellerAgent);
        this.sellerAgent = sellerAgent;
        this.e =enchere;

        //definition des etats
        registerFirstState(new AnnounceBehaviour(this), "to_announce");
        registerState(new OneBidBehaviour(this), "to_bid_once");
        registerState(new SeveralBidBehaviour(this), "to_bid_several");
        registerState(new AttributeBehaviour(this), "to_attribute");
        registerState(new GiveBehaviour(this), "to_give");
        registerLastState(new PayBehaviour(this), "to_pay");

        //définition des transitions
        registerDefaultTransition("to_announce", "to_announce");
        registerTransition("to_announce","to_announce", Performatives.TO_ANNOUNCE);
        registerTransition("to_announce", "to_bid_once", Performatives.TO_BID);
        registerTransition("to_bid_once", "to_bid_several", Performatives.TO_BID);
        registerTransition("to_bid_several", "to_bid_several", Performatives.TO_BID);
        registerTransition("to_bid_several", "to_announce", Performatives.TO_ANNOUNCE);
        registerTransition("to_bid_once", "to_attribute", Performatives.TO_ATTRIBUTE);
        registerTransition("to_attribute", "to_give", Performatives.TO_GIVE);
        registerTransition("to_give", "to_pay", Performatives.TO_PAY);

    }

    @Override
    public int onEnd() {
        return super.onEnd();
	}
    
    public Bid getBid() {
        return e;
    }

    public SellerAgent getAgent() {
        return sellerAgent;
    }
}
