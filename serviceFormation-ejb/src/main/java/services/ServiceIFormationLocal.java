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

/**
 *
 * @author Claire
 */
public interface ServiceIFormationLocal {
    public String creerIFormation(String content);
    public String annulerIFormation(int idIFormation);
    public String choixSalleIformation(int idIformation, int idSalle, String dateDeb);
    public String choixFormateurIformation(int idIformation, int idFormateur, String dateDeb);
    public ArrayList<PlanningSalle> afficherPlanningSalles();
    public ArrayList<PlanningFormateur> afficherPlanningFormateurs();
    public HashMap<Integer, Iformation> afficherInstances();
    public HashMap<Integer, Formation> afficherFormations();
}
