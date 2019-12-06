/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entite.Formation;
import entite.PlanningFormateur;
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
    public String creerIFormation(String content) {
        return this.gestionIformation.creerIFormation(content);
    
    }

    @Override
    public String annulerIFormation(int idIFormation) {
        return this.gestionIformation.annulerIFormation(idIFormation);
    }
    
    @Override
    public String choixSalleIformation(String content) {
        return this.gestionIformation.choixSalleIformation(content);
    }
    
    @Override
    public ArrayList<PlanningSalle> afficherPlanningSalles() {
        return this.gestionIformation.afficherPlanningSalles();
    }
    
    @Override
    public ArrayList<PlanningFormateur> afficherPlanningFormateurs() {
        return this.gestionIformation.afficherPlanningFormateurs();
    }
    
    @Override
    public String choixFormateurIformation(String content) {
        return this.gestionIformation.choixFormateurIformation(content);
    }
    
    @Override
    public HashMap<Integer, Iformation> afficherInstances() {
        return this.gestionIformation.afficherInstances();
    }
    @Override
    public HashMap<Integer, Formation> afficherFormations() {
        return this.gestionIformation.afficherFormations();
    }
}
