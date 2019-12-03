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

    private Integer idIFormation;
    private Integer idFormation;
    private Integer numEffectif;
    private ArrayList<String> codeClients;
    private String codeClient;
    private Integer idSalle;
    private String dateDeb;
    private String etat;
    private Integer idFormateur;
    /*
    * EN_ATTENTE : effectif demandé < à la moitié de la capacité minimale de la formation
    * EN_PROJET : effectif demandé >= à la moitié de la capacité minimale de la formation 
    *             et < à la capacité minimale de la formation
    * PLANIFIEE : effectif demandé >= à la capacité minimale de la formation
    */   

    public Iformation() {
    }

    public Iformation(Integer idFormation, Integer numEffectif, String codeClient) {
        this.idFormation = idFormation;
        this.numEffectif = numEffectif;
        this.codeClient = codeClient;  
    }
    
    

    public Iformation(Integer idFormation, Integer idIFormation, Integer numEffectif, String codeClient, String etat) {
        this.idFormation = idFormation;
        this.idIFormation = idIFormation;
        this.numEffectif = numEffectif;
        this.etat = etat;
        this.codeClients = new ArrayList<String>();
        this.codeClients.add(codeClient);
    }

    public Iformation(Integer idIFormation, Integer idFormation, Integer numEffectif, String codeClient, Integer idSalle, String dateDeb, String etat, Integer idFormateur) {
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

    public Integer getIdiformation() {
        return idIFormation;
    }

    public void setIdiformation(Integer idIFormation) {
        this.idIFormation = idIFormation;
    }

    public Integer getIdformation() {
        return idFormation;
    }

    public void setIdformation(Integer idFormation) {
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

    public Integer getIdsalle() {
        return idSalle;
    }

    public void setIdsalle(Integer idSalle) {
        this.idSalle = idSalle;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Integer getIdformateur() {
        return idFormateur;
    }

    public void setIdformateur(Integer idFormateur) {
        this.idFormateur = idFormateur;
    }

   
    public Integer getIdIFormation() {
        return idIFormation;
    }

    public void setIdIFormation(Integer idIFormation) {
        this.idIFormation = idIFormation;
    }

    public String getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(String dateDeb) {
        this.dateDeb = dateDeb;
    }   

    public String getCodeClientProvisoire() {
        return codeClient;
    }

    public void setCodeClientProvisoire(String codeClient) {
        this.codeClient = codeClient;
    }

    @Override
    public String toString() {
        return "Iformation{" + "idIFormation:" + idIFormation + ", idFormation:" + 
                idFormation + ", numEffectif:" + numEffectif + ", codeClients:" + 
                codeClients + ", idSalle:" + idSalle + ", dateDeb:" + 
                dateDeb + ", etat:" + etat + ", idFormateur:" + idFormateur + "}";
    }
    
    

    
    
   
    
    
    
}
