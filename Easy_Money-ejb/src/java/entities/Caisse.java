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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caisse.findAll", query = "SELECT c FROM Caisse c"),
    @NamedQuery(name = "Caisse.findByIdcaisse", query = "SELECT c FROM Caisse c WHERE c.idcaisse = :idcaisse"),
    @NamedQuery(name = "Caisse.findByMontant", query = "SELECT c FROM Caisse c WHERE c.montant = :montant")})
public class Caisse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idcaisse;
    private Integer montant;

    public Caisse() {
    }

    public Caisse(Integer idcaisse) {
        this.idcaisse = idcaisse;
    }

    public Integer getIdcaisse() {
        return idcaisse;
    }

    public void setIdcaisse(Integer idcaisse) {
        this.idcaisse = idcaisse;
    }

    public Integer getMontant() {
        return montant;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcaisse != null ? idcaisse.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caisse)) {
            return false;
        }
        Caisse other = (Caisse) object;
        if ((this.idcaisse == null && other.idcaisse != null) || (this.idcaisse != null && !this.idcaisse.equals(other.idcaisse))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Caisse[ idcaisse=" + idcaisse + " ]";
    }
    
}
