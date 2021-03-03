package fr.univpau.behaviours.BuyerBehaviours;

import fr.univpau.utils.Performatives;
import jade.core.behaviours.OneShotBehaviour;

public class AttributionBehaviour extends OneShotBehaviour {
    private final MainBehaviour mb;

    public AttributionBehaviour(MainBehaviour behaviour) {
        this.mb = behaviour;
    }

    @Override
    public void action() {
        //TODO: changer le status
    }

    //une fois l'opération terminée on retourne le code performative associé pour faire le changement d'état
    @Override
    public int onEnd() {
        return Performatives.TO_GIVE;
    }
}
