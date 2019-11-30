/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.util.ArrayList;

/**
 *
 * @author Claire
 */
public class Iformation {

    private int idIFormation;
    private int idFormation;
    private int numEffectif;
    private ArrayList<String> codeClients;
    private int idSalle;
    private String dateDeb;
    private String etat;
    private int idFormateur;
    /*
    * EN_ATTENTE : effectif demandé < à la moitié de la capacité minimale de la formation
    * EN_PROJET : effectif demandé >= à la moitié de la capacité minimale de la formation 
    *             et < à la capacité minimale de la formation
    * PLANIFIEE : effectif demandé >= à la capacité minimale de la formation
    */   

    public Iformation() {
    }

    public Iformation(int idFormation, int idIFormation, int numEffectif, String codeClient, String etat) {
        this.idFormation = idFormation;
        this.idIFormation = idIFormation;
        this.numEffectif = numEffectif;
        this.etat = etat;
        this.codeClients = new ArrayList<String>();
        this.codeClients.add(codeClient);
    }

    public Iformation(int idIFormation, int idFormation, int numEffectif, String codeClient, int idSalle, String dateDeb, String etat, int idFormateur) {
        this.idIFormation = idIFormation;
        this.idFormation = idFormation;
        this.numEffectif = numEffectif;
        this.codeClients = new ArrayList<String>();
        this.codeClients.add(codeClient);
        this.idSalle = idSalle;
        this.dateDeb = dateDeb;
        this.etat = etat;
        this.idFormateur = idFormateur;
    }

    public int getIdiformation() {
        return idIFormation;
    }

    public void setIdiformation(int idIFormation) {
        this.idIFormation = idIFormation;
    }

    public int getIdformation() {
        return idFormation;
    }

    public void setIdformation(int idFormation) {
        this.idFormation = idFormation;
    }

    public Integer getNumeffectif() {
        return numEffectif;
    }

    public void setNumeffectif(Integer numEffectif) {
        this.numEffectif = numEffectif;
    }

    public ArrayList<String> getCodeclient() {
        return codeClients;
    }

    public void setCodeclient(ArrayList<String> codeClient) {
        this.codeClients = codeClient;
    }

    public int getIdsalle() {
        return idSalle;
    }

    public void setIdsalle(int idSalle) {
        this.idSalle = idSalle;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getIdformateur() {
        return idFormateur;
    }

    public void setIdformateur(int idFormateur) {
        this.idFormateur = idFormateur;
    }

   
    public int getIdIFormation() {
        return idIFormation;
    }

    public void setIdIFormation(int idIFormation) {
        this.idIFormation = idIFormation;
    }

    public String getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(String dateDeb) {
        this.dateDeb = dateDeb;
    }   

    @Override
    public String toString() {
        return "Iformation{" + "idIFormation=" + idIFormation + ", idFormation=" + idFormation + ", numEffectif=" + numEffectif + ", codeClients=" + codeClients + ", idSalle=" + idSalle + ", dateDeb=" + dateDeb + ", etat=" + etat + ", idFormateur=" + idFormateur + '}';
    }
    
   
    
    
    
}
