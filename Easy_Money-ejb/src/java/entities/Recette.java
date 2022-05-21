/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enumeration.VersementState;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    //@SequenceGenerator(name = "recette_sequence_id", sequenceName = "recette_sequence_id", allocationSize = 1)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recette_sequence_id")
    @Column(name = "id_recette")
    private Integer idRecette;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_boutique", referencedColumnName = "id_boutique")
    private Boutique boutique;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_personnel", referencedColumnName = "id_personnel")
    private Personnel personnel;

    @Min(value = 1, message = "Amount can not be less than 1, please provide valid amount")
    private Integer montant;

    @Column(name = "date_operation", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOperation;

    @Column(name = "heure_operation", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date heureOperation;

    @Column(length = 100)
    private String observation;

    @Enumerated(EnumType.STRING)
    @Column(name = "versement_state")
    private VersementState versementState;

    public Recette() {
        this.montant = 0;
        this.versementState = VersementState.ATTENTE;
    }

    public Recette(Integer idRecette) {
        this.idRecette = idRecette;
        this.montant = 0;
        this.versementState = VersementState.ATTENTE;
    }

    public Recette(Boutique boutique, Personnel personnel) {
        this.boutique = boutique;
        this.personnel = personnel;
        this.montant = 0;
        this.versementState = VersementState.ATTENTE;
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

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public VersementState getVersementState() {
        return versementState;
    }

    public void setVersementState(VersementState versementState) {
        this.versementState = versementState;
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
