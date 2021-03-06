/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "frais_carnet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FraisCarnet.findAll", query = "SELECT f FROM FraisCarnet f"),
    @NamedQuery(name = "FraisCarnet.findById", query = "SELECT f FROM FraisCarnet f WHERE f.id = :id"),
    @NamedQuery(name = "FraisCarnet.findByMontant", query = "SELECT f FROM FraisCarnet f WHERE f.montant = :montant"),
    @NamedQuery(name = "FraisCarnet.findByDateOperation", query = "SELECT f FROM FraisCarnet f WHERE f.dateOperation = :dateOperation"),
    @NamedQuery(name = "FraisCarnet.findByHeure", query = "SELECT f FROM FraisCarnet f WHERE f.heure = :heure")})
public class FraisCarnet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double montant;
    @Column(name = "date_operation")
    @Temporal(TemporalType.DATE)
    private Date dateOperation;
    @Temporal(TemporalType.TIME)
    private Date heure;
    @JoinColumn(name = "idmois", referencedColumnName = "id_annee_mois")
    @ManyToOne(fetch = FetchType.LAZY)
    private AnneeMois idmois;
    @JoinColumn(name = "idclient", referencedColumnName = "idclient")
    @ManyToOne(fetch = FetchType.LAZY)
    private Client idclient;

    public FraisCarnet() {
    }

    public FraisCarnet(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }

    public AnneeMois getIdmois() {
        return idmois;
    }

    public void setIdmois(AnneeMois idmois) {
        this.idmois = idmois;
    }

    public Client getIdclient() {
        return idclient;
    }

    public void setIdclient(Client idclient) {
        this.idclient = idclient;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FraisCarnet)) {
            return false;
        }
        FraisCarnet other = (FraisCarnet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.FraisCarnet[ id=" + id + " ]";
    }
    
}
