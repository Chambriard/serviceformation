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
public class CompteRendu {
    
    private String codeClient;
    private int effectif;
    private String dateDeb;
    private String dateFin;
    private String nomSalle;
    private int montantTotal;

    public CompteRendu() {
    }
    
    
    public CompteRendu(String codeClient, int effectif, String dateDeb, String dateFin, String nomSalle, int montantTotal) {
        this.codeClient = codeClient;
        this.effectif = effectif;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.nomSalle = nomSalle;
        this.montantTotal = montantTotal;
    }

    public String getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(String codeClient) {
        this.codeClient = codeClient;
    }

    public int getEffectif() {
        return effectif;
    }

    public void setEffectif(int effectif) {
        this.effectif = effectif;
    }

    public String getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(String dateDeb) {
        this.dateDeb = dateDeb;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public int getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(int montantTotal) {
        this.montantTotal = montantTotal;
    }

    @Override
    public String toString() {
        return "CompteRendu{" + "codeClient=" + codeClient + ", effectif=" + effectif + ", dateDeb=" + dateDeb + ", dateFin=" + dateFin + ", nomSalle=" + nomSalle + ", montantTotal=" + montantTotal + '}';
    }
    
}
