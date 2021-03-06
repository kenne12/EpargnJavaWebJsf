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
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findByIdclient", query = "SELECT c FROM Client c WHERE c.idclient = :idclient"),
    @NamedQuery(name = "Client.findByNom", query = "SELECT c FROM Client c WHERE c.nom = :nom"),
    @NamedQuery(name = "Client.findByPrenom", query = "SELECT c FROM Client c WHERE c.prenom = :prenom"),
    @NamedQuery(name = "Client.findByCni", query = "SELECT c FROM Client c WHERE c.cni = :cni"),
    @NamedQuery(name = "Client.findByContact", query = "SELECT c FROM Client c WHERE c.contact = :contact"),
    @NamedQuery(name = "Client.findBySolde", query = "SELECT c FROM Client c WHERE c.solde = :solde"),
    @NamedQuery(name = "Client.findByProfession", query = "SELECT c FROM Client c WHERE c.profession = :profession"),
    @NamedQuery(name = "Client.findByCarnet", query = "SELECT c FROM Client c WHERE c.carnet = :carnet"),
    @NamedQuery(name = "Client.findByFraiscarnet", query = "SELECT c FROM Client c WHERE c.fraiscarnet = :fraiscarnet"),
    @NamedQuery(name = "Client.findByNumerocarnet", query = "SELECT c FROM Client c WHERE c.numerocarnet = :numerocarnet"),
    @NamedQuery(name = "Client.findByEtat", query = "SELECT c FROM Client c WHERE c.etat = :etat")})
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idclient;
    @Size(max = 2147483647)
    private String nom;
    @Size(max = 2147483647)
    private String prenom;
    @Size(max = 2147483647)
    private String cni;
    @Size(max = 2147483647)
    private String contact;
    private Integer solde;
    @Size(max = 2147483647)
    private String profession;
    private Boolean carnet;
    private Integer fraiscarnet;
    private Integer numerocarnet;
    private Boolean etat;
    @OneToMany(mappedBy = "idclient", fetch = FetchType.LAZY)
    private List<Retrait> retraitList;
    @OneToMany(mappedBy = "idclient", fetch = FetchType.LAZY)
    private List<FraisCarnet> fraisCarnetList;
    @OneToMany(mappedBy = "idclient", fetch = FetchType.LAZY)
    private List<Versement> versementList;

    public Client() {
    }

    public Client(Integer idclient) {
        this.idclient = idclient;
    }

    public Integer getIdclient() {
        return idclient;
    }

    public void setIdclient(Integer idclient) {
        this.idclient = idclient;
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

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getSolde() {
        return solde;
    }

    public void setSolde(Integer solde) {
        this.solde = solde;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Boolean getCarnet() {
        return carnet;
    }

    public void setCarnet(Boolean carnet) {
        this.carnet = carnet;
    }

    public Integer getFraiscarnet() {
        return fraiscarnet;
    }

    public void setFraiscarnet(Integer fraiscarnet) {
        this.fraiscarnet = fraiscarnet;
    }

    public Integer getNumerocarnet() {
        return numerocarnet;
    }

    public void setNumerocarnet(Integer numerocarnet) {
        this.numerocarnet = numerocarnet;
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
        hash += (idclient != null ? idclient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.idclient == null && other.idclient != null) || (this.idclient != null && !this.idclient.equals(other.idclient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Client[ idclient=" + idclient + " ]";
    }
    
}
