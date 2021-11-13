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
        this.client = client;
        this.montantVerse = montantVerse;
        this.montantRetire = montantRetire;
    }

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

    public Integer getCarnet() {
        return this.carnet;
    }

    public void setCarnet(Integer carnet) {
        this.carnet = carnet;
    }

    public Integer getCommission() {
        return this.commission;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }
}
