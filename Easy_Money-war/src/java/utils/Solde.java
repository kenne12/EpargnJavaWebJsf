package utils;

import entities.Client;

public class Solde {

    private Client client;
    private Integer montantVerse;
    private Integer montantRetire;
    private Integer carnet;
    private Integer commission;

    public Solde() {
    }

    public Solde(Client client, Integer montantVerse, Integer montantRetire) {
        /* 27 */ this.client = client;
        /* 28 */ this.montantVerse = montantVerse;
        /* 29 */ this.montantRetire = montantRetire;
    }

    public Client getClient() {
        /* 33 */ return this.client;
    }

    public void setClient(Client client) {
        /* 37 */ this.client = client;
    }

    public Integer getMontantVerse() {
        /* 41 */ return this.montantVerse;
    }

    public void setMontantVerse(Integer montantVerse) {
        /* 45 */ this.montantVerse = montantVerse;
    }

    public Integer getMontantRetire() {
        /* 49 */ return this.montantRetire;
    }

    public void setMontantRetire(Integer montantRetire) {
        /* 53 */ this.montantRetire = montantRetire;
    }

    public Integer getCarnet() {
        /* 57 */ return this.carnet;
    }

    public void setCarnet(Integer carnet) {
        /* 61 */ this.carnet = carnet;
    }

    public Integer getCommission() {
        /* 65 */ return this.commission;
    }

    public void setCommission(Integer commission) {
        /* 69 */ this.commission = commission;
    }
}
