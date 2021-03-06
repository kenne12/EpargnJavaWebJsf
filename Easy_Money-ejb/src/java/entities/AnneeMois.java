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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "annee_mois")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnneeMois.findAll", query = "SELECT a FROM AnneeMois a"),
    @NamedQuery(name = "AnneeMois.findByIdAnneeMois", query = "SELECT a FROM AnneeMois a WHERE a.idAnneeMois = :idAnneeMois"),
    @NamedQuery(name = "AnneeMois.findByDateDebut", query = "SELECT a FROM AnneeMois a WHERE a.dateDebut = :dateDebut"),
    @NamedQuery(name = "AnneeMois.findByDateFin", query = "SELECT a FROM AnneeMois a WHERE a.dateFin = :dateFin"),
    @NamedQuery(name = "AnneeMois.findByEtat", query = "SELECT a FROM AnneeMois a WHERE a.etat = :etat")})
public class AnneeMois implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_annee_mois")
    private Integer idAnneeMois;
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    private Boolean etat;
    @OneToMany(mappedBy = "idmois", fetch = FetchType.LAZY)
    private List<Retrait> retraitList;
    @OneToMany(mappedBy = "idmois", fetch = FetchType.LAZY)
    private List<FraisCarnet> fraisCarnetList;
    @JoinColumn(name = "idannee", referencedColumnName = "idannee")
    @ManyToOne(fetch = FetchType.LAZY)
    private Annee idannee;
    @JoinColumn(name = "idmois", referencedColumnName = "idmois")
    @ManyToOne(fetch = FetchType.LAZY)
    private Mois idmois;
    @OneToMany(mappedBy = "idmois", fetch = FetchType.LAZY)
    private List<Versement> versementList;

    public AnneeMois() {
    }

    public AnneeMois(Integer idAnneeMois) {
        this.idAnneeMois = idAnneeMois;
    }

    public Integer getIdAnneeMois() {
        return idAnneeMois;
    }

    public void setIdAnneeMois(Integer idAnneeMois) {
        this.idAnneeMois = idAnneeMois;
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

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    @XmlTransient
    public List<Retrait> getRetraitList() {
        return retraitList;
    }

    public void setRetraitList(List<Retrait> retraitList) {
        this.retraitList = retraitList;
    }

    @XmlTransient
    public List<FraisCarnet> getFraisCarnetList() {
        return fraisCarnetList;
    }

    public void setFraisCarnetList(List<FraisCarnet> fraisCarnetList) {
        this.fraisCarnetList = fraisCarnetList;
    }

    public Annee getIdannee() {
        return idannee;
    }

    public void setIdannee(Annee idannee) {
        this.idannee = idannee;
    }

    public Mois getIdmois() {
        return idmois;
    }

    public void setIdmois(Mois idmois) {
        this.idmois = idmois;
    }

    @XmlTransient
    public List<Versement> getVersementList() {
        return versementList;
    }

    public void setVersementList(List<Versement> versementList) {
        this.versementList = versementList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnneeMois != null ? idAnneeMois.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnneeMois)) {
            return false;
        }
        AnneeMois other = (AnneeMois) object;
        if ((this.idAnneeMois == null && other.idAnneeMois != null) || (this.idAnneeMois != null && !this.idAnneeMois.equals(other.idAnneeMois))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AnneeMois[ idAnneeMois=" + idAnneeMois + " ]";
    }
    
}
