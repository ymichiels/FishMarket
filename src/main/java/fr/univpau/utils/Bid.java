package fr.univpau.utils;

import java.io.Serializable;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//classe enchère, contient toutes les informations nécessaires à transmettre sur le réseau d'main.java.univpau.agents (nom du vendeur, price etc etc)
public class Bid extends RecursiveTreeObject<Bid> implements Serializable {
    public SimpleIntegerProperty price;
    public SimpleIntegerProperty waitingTime;
    public SimpleIntegerProperty step;
    public StringProperty sellerName;
    public StringProperty fishName;
    public StringProperty buyerName;
    public BooleanProperty isFinished;

    public Bid(String vendeur, String produit, int price, int delai, int step) {
        this.price = new SimpleIntegerProperty(price);
        this.waitingTime = new SimpleIntegerProperty(delai);
        this.step = new SimpleIntegerProperty(step);
        this.fishName = new SimpleStringProperty(produit);
        this.sellerName = new SimpleStringProperty(vendeur);
        isFinished = new SimpleBooleanProperty(false);
    }

    public int getPrice() {
        return price.get();
    }
    
    public SimpleIntegerProperty getPriceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);;
    }

    public int getWaitingTime() {
        return waitingTime.get();
    }
    
    public SimpleIntegerProperty getWaitingTimeProperty() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime.set(waitingTime);
    }

    public int getStep() {
        return step.get();
    }

    public SimpleIntegerProperty getStepProperty() {
        return step;
    }
    
    public void setStep(int step) {
        this.step.set(step);;
    }

    public String getSellerName() {
        return sellerName.get();
    }

    public StringProperty getSellerNameProperty() {
        return sellerName;
    }
    
    public void setSellerName(String sellerName) {
        this.sellerName.set(sellerName);
    }

    public String getFishName() {
        return fishName.get();
    }
    
    public StringProperty getFishNameProperty() {
        return fishName;
    }

    public void setFishName(String fishName) {
        this.fishName.set(fishName);;
    }

    public String getBuyerName() {
        return buyerName.get();
    }

    public StringProperty getBuyerNameProperty() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName.set(buyerName);;
    }

    public boolean isFinished() {
        return isFinished.get();
    }
    
    public BooleanProperty isFinishedProperty() {
        return isFinished;
    }

    public void setIsFinished(boolean finished) {
        isFinished.set(finished);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof Bid) {
            Bid e = (Bid) o;
            /*
            //si cette enchère n'a pas de vendeur et que l'autre en a un
          if(sellerName == null){
              if(e.getsellerName() != null){
                  return false;
              }
              //si j'ai un vendeur mais que l'autre enchère n'a pas le même vendeur
          }else{
              if(!sellerName.equals(e.getsellerName())){
                  return false;
              }
          }*/
            //si j'ai pas de vendeur ou que les vendeurs ne sont pas les mêmes d'une enchère à l'autre
            if (sellerName == null || !sellerName.equals(e.getSellerName())) {
                return false;
            }

          /*if(fishName == null){
              if(e.getfishName() != null){
                  return false;
              }
          }else{
              if(!fishName.equals(e.getfishName())){
                  return false;
              }
          }*/
            return fishName != null && fishName.equals(e.getFishName());
        }

        return false;
    }

    @Override
    public String toString() {
        return "Enchere: \n"
                + "lot: " + fishName + "\n"
                + "vendeur: " + sellerName + "\n"
                + "Acheteur: " + buyerName + "\n"
                + "price: " + price + "\n";
    }
}
