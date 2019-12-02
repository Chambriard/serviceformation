/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import entite.Formateur;
import entite.Iformation;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author Claire
 */
@Local
public interface GestionIformationLocal {
    
    public Iformation creerIFormation(String content);
    public String annulerIFormation(int idIformation);
    public String choixFormateurIformation(int idIFormation, ArrayList<Formateur> listeFormateurs);
    public String choixSalleIformation(int idIformation);
    
}