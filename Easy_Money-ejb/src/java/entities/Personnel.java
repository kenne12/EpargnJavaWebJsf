/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author kenne
 */
@Entity
public class Personnel implements Serializable {

    @Id
    @SequenceGenerator(name = "personnel_sequence_id", sequenceName = "personnel_sequence_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personnel_sequence_id")
    @Column(name = "id_personnel")
    private Integer idPersonnel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_boutique", referencedColumnName = "id_boutique")
    private Boutique boutique;

    private String nom;
    private String prenom;

    public Personnel() {
    }

    public Personnel(Integer idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    public Personnel(Integer idPersonnel, Boutique boutique) {
        this.idPersonnel = idPersonnel;
        this.boutique = boutique;
    }

    public Personnel(Integer idPersonnel, String nom, String prenom, Boutique boutique) {
        this.idPersonnel = idPersonnel;
        this.nom = nom;
        this.prenom = prenom;
        this.boutique = boutique;
    }

    public Integer getIdPersonnel() {
        return idPersonnel;
    }

    public void setIdPersonnel(Integer idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.idPersonnel);
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
        final Personnel other = (Personnel) obj;
        return Objects.equals(this.idPersonnel, other.idPersonnel);
    }

    @Override
    public String toString() {
        return "Personnel{" + "idPersonnel=" + idPersonnel + ", nom=" + nom + ", prenom=" + prenom + '}';
    }

}
