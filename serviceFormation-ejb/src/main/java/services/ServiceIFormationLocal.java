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

/**
 *
 * @author Claire
 */
public interface ServiceIFormationLocal {
    public Iformation creerIFormation(String content);
    public String annulerIFormation(int idIFormation);
    public String choixSalleIformation(int idIformation, int idSalle, String dateDeb);
    public String choixFormateurIformation(int idIFormation, ArrayList<Formateur> listeFormateurs);
    public ArrayList<PlanningSalle> afficherPlanningSalles();
    public String afficherPlanningFormateurs();
}
