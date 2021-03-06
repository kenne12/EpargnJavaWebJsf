/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mois.findAll", query = "SELECT m FROM Mois m"),
    @NamedQuery(name = "Mois.findByIdmois", query = "SELECT m FROM Mois m WHERE m.idmois = :idmois"),
    @NamedQuery(name = "Mois.findByNom", query = "SELECT m FROM Mois m WHERE m.nom = :nom"),
    @NamedQuery(name = "Mois.findByNumero", query = "SELECT m FROM Mois m WHERE m.numero = :numero")})
public class Mois implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idmois;
    @Size(max = 2147483647)
    private String nom;
    private Integer numero;
    @OneToMany(mappedBy = "idmois", fetch = FetchType.LAZY)
    private List<AnneeMois> anneeMoisList;

    public Mois() {
    }

    public Mois(Integer idmois) {
        this.idmois = idmois;
    }

    public Integer getIdmois() {
        return idmois;
    }

    public void setIdmois(Integer idmois) {
        this.idmois = idmois;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @XmlTransient
    public List<AnneeMois> getAnneeMoisList() {
        return anneeMoisList;
    }

    public void setAnneeMoisList(List<AnneeMois> anneeMoisList) {
        this.anneeMoisList = anneeMoisList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmois != null ? idmois.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mois)) {
            return false;
        }
        Mois other = (Mois) object;
        if ((this.idmois == null && other.idmois != null) || (this.idmois != null && !this.idmois.equals(other.idmois))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Mois[ idmois=" + idmois + " ]";
    }
    
}
