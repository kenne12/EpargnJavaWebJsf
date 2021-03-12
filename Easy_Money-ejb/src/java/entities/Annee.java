/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Annee.findAll", query = "SELECT a FROM Annee a"),
    @NamedQuery(name = "Annee.findByIdannee", query = "SELECT a FROM Annee a WHERE a.idannee = :idannee"),
    @NamedQuery(name = "Annee.findByNom", query = "SELECT a FROM Annee a WHERE a.nom = :nom"),
    @NamedQuery(name = "Annee.findByEtat", query = "SELECT a FROM Annee a WHERE a.etat = :etat")})
public class Annee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idannee;
    @Size(max = 20)
    private String nom;
    private Boolean etat;
    @OneToMany(mappedBy = "idannee", fetch = FetchType.LAZY)
    private List<AnneeMois> anneeMoisList;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut")
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;

    public Annee() {
    }

    public Annee(Integer idannee) {
        this.idannee = idannee;
    }

    public Integer getIdannee() {
        return idannee;
    }

    public void setIdannee(Integer idannee) {
        this.idannee = idannee;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    @XmlTransient
    public List<AnneeMois> getAnneeMoisList() {
        return anneeMoisList;
    }

    public void setAnneeMoisList(List<AnneeMois> anneeMoisList) {
        this.anneeMoisList = anneeMoisList;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idannee != null ? idannee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Annee)) {
            return false;
        }
        Annee other = (Annee) object;
        if ((this.idannee == null && other.idannee != null) || (this.idannee != null && !this.idannee.equals(other.idannee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Annee[ idannee=" + idannee + " ]";
    }

}
