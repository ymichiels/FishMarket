package fr.univpau.utils;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;


//comportement permettant d'envoyer un message sur le réseau
public class MessageSender extends OneShotBehaviour {
    private final Agent snd;
    private final String[] cibles;
    private final ACLMessage msg;
    private final Bid e;


    //constructeur basique avec un mssage vide
    public MessageSender(Agent sender, int performative, String[] destinataires) {
        snd = sender;
        cibles = destinataires;
        msg = new ACLMessage(performative);
        this.e = null;
    }

    //constructeur plus élaboré , permettant de transmettre un objet serialisable (j'ai mis une classe Enchere
    //pour le moment, mais ça peut changer, si on envoie juste des Integer par exemple
    public MessageSender(Agent sender, int performative, String[] destinataires, Bid e) {
        snd = sender;
        cibles = destinataires;
        msg = new ACLMessage(performative);
        this.e = e;
    }

    public void setSender(String name) {
        msg.setInReplyTo(name);
    }

    //on envoie le message ici
    @Override
    public void action() {
        for (String cible : cibles) {
            msg.addReceiver(new AID(cible, AID.ISLOCALNAME));
        }
        if (e != null) {
            try {
                msg.setContentObject(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        snd.send(msg);
    }
}
