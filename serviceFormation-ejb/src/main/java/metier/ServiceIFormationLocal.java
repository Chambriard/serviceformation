/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;


import entite.Formateur;
import entite.Iformation;
import java.util.ArrayList;

/**
 *
 * @author Claire
 */
public interface ServiceIFormationLocal {
    Iformation creerIFormation(int idFormation, int numEffectif, String codeClient);
    String annulerIFormation(int idIFormation);
    String choixSalleIformation(int idIFormation);
    String choixFormateurIformation(int idIFormation, ArrayList<Formateur> listeFormateurs);
}
