/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

/**
 *
 * @author Claire
 */
public class Formation {
    
    private int idFormation;
    private String nomFormation;
    private String descriptionFormation;
    private String niveau;
    private int duree; //en jours
    private int capMax;
    private int capMin;
    private int tarif;

    public Formation() {
    }

    public Formation(int idFormation, String nomFormation, String descriptionFormation, String niveau, int duree, int capMax, int capMin, int tarif) {
        this.idFormation = idFormation;
        this.nomFormation = nomFormation;
        this.descriptionFormation = descriptionFormation;
        this.niveau = niveau;
        this.duree = duree;
        this.capMax = capMax;
        this.capMin = capMin;
        this.tarif = tarif;
    }
    
    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public String getNomFormation() {
        return nomFormation;
    }

    public void setNomFormation(String nomFormation) {
        this.nomFormation = nomFormation;
    }

    public String getDescriptionFormation() {
        return descriptionFormation;
    }

    public void setDescriptionFormation(String descriptionFormation) {
        this.descriptionFormation = descriptionFormation;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getCapMax() {
        return capMax;
    }

    public void setCapMax(int capMax) {
        this.capMax = capMax;
    }

    public int getCapMin() {
        return capMin;
    }

    public void setCapMin(int capMin) {
        this.capMin = capMin;
    }

    public int getTarif() {
        return tarif;
    }

    public void setTarif(int tarif) {
        this.tarif = tarif;
    }
    
    
    
    
}
