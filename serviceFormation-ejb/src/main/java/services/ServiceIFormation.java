/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entite.Formateur;
import entite.Iformation;
import entite.PlanningSalle;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import metier.GestionIformationLocal;
/**
 *
 * @author Claire
 */
@Stateless
public class ServiceIFormation implements ServiceIFormationLocal{

    @EJB
    private GestionIformationLocal gestionIformation;
   
    public ServiceIFormation() {
    }

    @Override
    public Iformation creerIFormation(String content) {
        return this.gestionIformation.creerIFormation(content);
    
    }

    @Override
    public String annulerIFormation(int idIFormation) {
        return this.gestionIformation.annulerIFormation(idIFormation);
    }

    @Override
    public String choixSalleIformation(int idIformation, int idSalle, String dateDeb) {
        return this.gestionIformation.choixSalleIformation(idIformation, idSalle, dateDeb);
    }
    
    @Override
    public ArrayList<PlanningSalle> afficherPlanningSalles() {
        return this.gestionIformation.afficherPlanningSalles();
    }
    
    @Override
    public String afficherPlanningFormateurs() {
        return this.gestionIformation.afficherPlanningFormateurs();
    }

    @Override
    public String choixFormateurIformation(int idIFormation, ArrayList<Formateur> listeFormateurs) {
        //return this.gestionIformation.choixFormateurIformation(idIFormation, listeFormateurs);
        return "salut 4";
    }
   
   
    
   
}
