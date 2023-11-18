package utils;

import entities.Client;

public class Solde {

    private Client client;
    private Integer montantVerse;
    private Integer montantRetire;
    private Integer fraisCarnet;
    private Integer commission;

    public Solde() {
        this.initAmounts();
    }
    
    public Solde(Client client) {
        this.client = client;
        this.initAmounts();
    }


    /*public Solde(Client client, Integer montantVerse, Integer montantRetire) {
        this.client = client;
        this.montantVerse = montantVerse;
        this.montantRetire = montantRetire;
    }*/

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getMontantVerse() {
        return this.montantVerse;
    }

    public void setMontantVerse(Integer montantVerse) {
        this.montantVerse = montantVerse;
    }

    public Integer getMontantRetire() {
        return this.montantRetire;
    }

    public void setMontantRetire(Integer montantRetire) {
        this.montantRetire = montantRetire;
    }

    public Integer getFraisCarnet() {
        return fraisCarnet;
    }

    public void setFraisCarnet(Integer fraisCarnet) {
        this.fraisCarnet = fraisCarnet;
    }


    public Integer getCommission() {
        return this.commission;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }
    
    private void initAmounts() {
        this.fraisCarnet = 0;
        this.montantVerse = 0;
        this.montantRetire = 0;
        this.commission = 0;
    }
}
