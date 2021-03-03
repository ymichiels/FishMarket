package fr.univpau.utils;

import jade.lang.acl.ACLMessage;

//juste une classe servant de mapping entre les performatives JADE et celles du prof
public class Performatives {
    public static final int TO_REGISTER = ACLMessage.SUBSCRIBE;
    public static final int TO_ANNOUNCE = ACLMessage.CFP;
    public static final int TO_ATTRIBUTE = ACLMessage.ACCEPT_PROPOSAL;
    public static final int TO_GIVE = ACLMessage.AGREE;
    public static final int TO_BID = ACLMessage.PROPOSE;
    public static final int TO_PAY = ACLMessage.CONFIRM;
}
