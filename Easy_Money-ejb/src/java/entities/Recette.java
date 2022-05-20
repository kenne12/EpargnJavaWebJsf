/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

/**
 *
 * @author kenne
 */
@Entity
public class Recette implements Serializable {

    @Id
    @SequenceGenerator(name = "recette_sequence_id", sequenceName = "recette_sequence_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recette_sequence_id")
    private Integer idRecette;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_boutique", referencedColumnName = "id_boutique")
    private Boutique boutique;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_personnel", referencedColumnName = "id_personnel")
    private Personnel personnel;

    @Min(value = 1, message = "Amount can not be less than 1, please provide valid amount")
    private Integer montant;

    @Column(name = "date_operation")
    @Temporal(TemporalType.DATE)
    private Date dateOperation;

    @Column(name = "heure_operation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date heureOperation;

    public Recette() {
    }

    public Recette(Integer idRecette) {
        this.idRecette = idRecette;
    }

    public Recette(Integer idRecette, Boutique boutique, Personnel personnel, Integer montant, Date dateOperation, Date heureOperation) {
        this.idRecette = idRecette;
        this.boutique = boutique;
        this.personnel = personnel;
        this.montant = montant;
        this.dateOperation = dateOperation;
        this.heureOperation = heureOperation;
    }

    public Recette(Integer idRecette, Integer idBoutique, Integer idPersonnel, Integer montant, Date dateOperation, Date heureOperation) {
        this.idRecette = idRecette;
        this.boutique = new Boutique(idBoutique);
        this.personnel = new Personnel(idPersonnel);
        this.montant = montant;
        this.dateOperation = dateOperation;
        this.heureOperation = heureOperation;
    }

    public Recette(Integer idRecette, Integer idBoutique, Integer idPersonnel, Integer montant, Date dateOperation) {
        this.idRecette = idRecette;
        this.boutique = new Boutique(idBoutique);
        this.personnel = new Personnel(idPersonnel);
        this.montant = montant;
        this.dateOperation = dateOperation;
    }

    public Integer getIdRecette() {
        return idRecette;
    }

    public void setIdRecette(Integer idRecette) {
        this.idRecette = idRecette;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public Integer getMontant() {
        return montant;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public Date getHeureOperation() {
        return heureOperation;
    }

    public void setHeureOperation(Date heureOperation) {
        this.heureOperation = heureOperation;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.idRecette);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Recette other = (Recette) obj;
        return Objects.equals(this.idRecette, other.idRecette);
    }

    @Override
    public String toString() {
        return "Recette{" + "idRecette=" + idRecette + ", boutique=" + boutique + ", personnel=" + personnel + ", montant=" + montant + ", dateOperation=" + dateOperation + ", heureOperation=" + heureOperation + '}';
    }

}
