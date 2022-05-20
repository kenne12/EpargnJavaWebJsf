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
import javax.persistence.SequenceGenerator;

/**
 *
 * @author kenne
 */
@Entity
public class Boutique implements Serializable {

    @Id
    @SequenceGenerator(name = "boutique_sequence_id", sequenceName = "boutique_sequence_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boutique_sequence_id")
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_boutique")
    private Integer idBoutique;

    private String code;

    private String nom;

    public Boutique() {
    }

    public Boutique(Integer idBoutique) {
        this.idBoutique = idBoutique;
    }

    public Boutique(Integer idBoutique, String code, String nom) {
        this.idBoutique = idBoutique;
        this.code = code;
        this.nom = nom;
    }

    public Integer getIdBoutique() {
        return idBoutique;
    }

    public void setIdBoutique(Integer idBoutique) {
        this.idBoutique = idBoutique;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.idBoutique);
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
        final Boutique other = (Boutique) obj;
        return Objects.equals(this.idBoutique, other.idBoutique);
    }

    @Override
    public String toString() {
        return "Boutique{" + "idBoutique=" + idBoutique + ", code=" + code + ", nom=" + nom + '}';
    }

}
