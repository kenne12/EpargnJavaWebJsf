/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profession.findAll", query = "SELECT p FROM Profession p"),
    @NamedQuery(name = "Profession.findByIdpression", query = "SELECT p FROM Profession p WHERE p.idpression = :idpression"),
    @NamedQuery(name = "Profession.findByNom", query = "SELECT p FROM Profession p WHERE p.nom = :nom")})
public class Profession implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idpression;
    @Size(max = 2147483647)
    private String nom;

    public Profession() {
    }

    public Profession(Integer idpression) {
        this.idpression = idpression;
    }

    public Integer getIdpression() {
        return idpression;
    }

    public void setIdpression(Integer idpression) {
        this.idpression = idpression;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpression != null ? idpression.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profession)) {
            return false;
        }
        Profession other = (Profession) object;
        if ((this.idpression == null && other.idpression != null) || (this.idpression != null && !this.idpression.equals(other.idpression))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Profession[ idpression=" + idpression + " ]";
    }
    
}
