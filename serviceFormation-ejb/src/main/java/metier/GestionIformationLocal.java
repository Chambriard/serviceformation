/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import entite.Formation;
import entite.PlanningFormateur;
import entite.Iformation;
import entite.PlanningSalle;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ejb.Local;

/**
 *
 * @author Claire
 */
@Local
public interface GestionIformationLocal {
    
    public String creerIFormation(String content);
    public String annulerIFormation(int idIformation);
    public String choixFormateurIformation(String content);
    public String choixSalleIformation(String content);
    public ArrayList<PlanningSalle> afficherPlanningSalles();
    public ArrayList<PlanningFormateur> afficherPlanningFormateurs();
    public HashMap<Integer, Iformation> afficherInstances();
    public HashMap<Integer, Formation> afficherFormations();
    
}
