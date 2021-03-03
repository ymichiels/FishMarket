package fr.univpau.agents;

import fr.univpau.behaviours.MarketBehaviours.MarketBehaviour;
import fr.univpau.containers.MarketContainer;
import fr.univpau.utils.Bid;
import fr.univpau.utils.MessageSender;
import fr.univpau.utils.Performatives;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MarketAgent extends GuiAgent {
    // Define agent properties here
    public static String NAME = "market";
    private List<String> vendeurs;
    private List<String> acheteurs;
    private List<Bid> encheres;
    private MarketContainer gui;

    // Put agent initializations here
    protected void setup() {
        System.out.println("Initialisation de l'agent " + getAID().getName());
        //initialisation des listes
        vendeurs = new ArrayList<>();
        acheteurs = new ArrayList<>();
        encheres = new ArrayList<>();
        gui = (MarketContainer) getArguments()[0];
        gui.setAgent(this);
        if (!getLocalName().equals(NAME)) {
            System.out.println("Le marché doit se nommer: " + NAME);
            doDelete();
            return;
        }
        addBehaviour(new MarketBehaviour(this));
    }

    @Override
    protected void takeDown() {
        System.out.println("Destruction de l'agent");
    }

    @Override
    protected void beforeMove() {
        try {
            System.out.println("Avant migration du container " + getAID().getName() +
                    " vers " + this.getContainerController().getContainerName());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    protected void afterMove() {
        try {
            System.out.println("Après migration du container " + getAID().getName() +
                    " vers " + this.getContainerController().getContainerName());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void addSeller(String name) {
        vendeurs.add(name);
    }

    public void addBuyer(String nom) {
        acheteurs.add(nom);
        //si y'a deja des enchères en cours, on lui envoie
        if (encheres.size() > 0) {
            for (Bid enchere : encheres) {
                addBehaviour(new MessageSender(this, Performatives.TO_ANNOUNCE, new String[]{nom}, enchere));
            }
        }
    }

    public void addBid(Bid e) {

        //si l'enchere est deja enregistree
        if (encheres.contains(e)) {
            //on met à jour la liste avec les nouvelles valeurs
            encheres.set(encheres.indexOf(e), e);
        } else {
            //sinon on insère tout simplement l'enchère dans la liste
            encheres.add(e);
        }

        if (acheteurs.size() > 0) {
            //si il existe des acheteurs, on les informes de la modification
            addBehaviour(new MessageSender(this, Performatives.TO_ANNOUNCE, acheteurs.toArray(new String[0]), e));
        }

        //TODO: mettre l'interface à jour
    }

    public void onNewBid(Serializable s) {
        Bid e = (Bid) s;
        if (encheres.contains(e)) {
            int idx = encheres.indexOf(e);
            Bid current = encheres.get(idx);
            if (current.getPrice() == e.getPrice()) {
                System.out.println("bid accetpé pour l'acheteur: " + e.getBuyerName());
                current.setBuyerName(e.getBuyerName());
                encheres.set(idx, current);
                //on envoie un message TO_BID pour update le tout chez les acheteurs et le svendeurs
                MessageSender msg = new MessageSender(this, Performatives.TO_BID, new String[]{current.getSellerName()}, current);
                msg.setSender(e.getFishName());
                addBehaviour(msg);
            }
        } else {
            //TODO: informer que l'enchere n'existe pas via un log ou autre
        }
    }

    public void toAttribute(Serializable s) {
        Bid e = (Bid) s;
        //si l'acheteur existe bien, on envoie un TO_ATTRIBUTE à tout le monde pour mettre a jour le bousain
        if (encheres.contains(e.getBuyerName())) {
            MessageSender msg = new MessageSender(this, Performatives.TO_ATTRIBUTE, new String[]{e.getBuyerName()}, e);
            msg.setSender(e.getSellerName() + e.getBuyerName());
            addBehaviour(msg);
        } else {
            //TODO: informer que l'acheteur existe pas via un log ou autre
        }
    }

    public void toGive(Serializable s) {
        Bid e = (Bid) s;
        // si l'acheteur existe bah on informe tout le monde pour mettre à jour les trucs
        if (acheteurs.contains(e.getBuyerName())) {
            MessageSender msg = new MessageSender(this, Performatives.TO_GIVE, new String[]{e.getBuyerName()}, e);
            msg.setSender(e.getSellerName() + e.getFishName());
            addBehaviour(msg);
        } else {
            //TODO: informer que le vendeur existe pas via un log ou autre
        }
    }

    public void toPay(Serializable s) {
        Bid e = (Bid) s;
        //si le vendeur existe
        if (vendeurs.contains(e.getSellerName())) {
            MessageSender msg = new MessageSender(this, Performatives.TO_PAY, new String[]{e.getSellerName()}, e);
            msg.setSender(e.getFishName());
            addBehaviour(msg);
            //si l'enchère existe également
            if (encheres.contains(e)) {
                Bid enchereFinie = encheres.get(encheres.indexOf(e));
                enchereFinie.setIsFinished(true);
                addBid(enchereFinie);
            }

        } else {
            //TODO: informer que le vedeur n'existe pas via un log ou autre
        }
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {

    }
}
