/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

/**
 *
 * @author claire
 */
public class PlanningSalle {
    
    private Integer idSalle;
    private Integer idFormation;
    private String statut;
    private String dateDeb;
    private String dateFin;

    public PlanningSalle(Integer idSalle, Integer idFormation, String statut, String dateDeb, String dateFin) {
        this.idSalle = idSalle;
        this.idFormation = idFormation;
        this.statut = statut;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
    }

    public PlanningSalle(Integer idSalle, String statut, String dateDeb, String dateFin) {
        this.idSalle = idSalle;
        this.statut = statut;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
    }

    public void setIdSalle(Integer idSalle) {
        this.idSalle = idSalle;
    }

    public void setIdFormation(Integer idFormation) {
        this.idFormation = idFormation;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setDateDeb(String dateDeb) {
        this.dateDeb = dateDeb;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    
    public int getIdSalle() {
        return idSalle;
    }

    public int getIdFormation() {
        return idFormation;
    }

    public String getStatut() {
        return statut;
    }

    public String getDateDeb() {
        return dateDeb;
    }

    public String getDateFin() {
        return dateFin;
    }

    @Override
    public String toString() {
        return "{" + "idSalle:" + idSalle + ", idFormation:" + idFormation + ", statut:" + statut + ", dateDeb:" + dateDeb + ", dateFin:" + dateFin + '}';
    }
    
}
