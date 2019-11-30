/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table;


import entite.Formation;
import entite.Iformation;
import java.util.HashMap;

/**
 *
 * @author Claire
 */
public class InstanceTable {
    
     public HashMap<Integer, Iformation> initListeIformation(){
         HashMap<Integer, Iformation> newListeIformation = new HashMap<Integer, Iformation>();
         Iformation iform1 = new Iformation(0, 0, 5, "123", 3, "05012020", "EN_ATTENTE", 3);
         Iformation iform2 = new Iformation(1, 1, 12, "123", 3, "07122019", "EN_ATTENTE", 1);
         Iformation iform3 = new Iformation(2, 1, 20, "123", 3, "25122019", "PLANIFIEE", 3);
         Iformation iform4 = new Iformation(3, 2, 11, "123", 3, "11022020", "EN_PROJET", 2);
         newListeIformation.put(0, iform1);
         newListeIformation.put(1, iform2);
         newListeIformation.put(2, iform3);
         newListeIformation.put(3, iform4);
         
         return newListeIformation;
    }
    
     public HashMap<Integer, Formation> initListeFormation(){
         HashMap<Integer, Formation> newListeFormation = new HashMap<Integer, Formation>();
         Formation form1 = new Formation(0, "Formation 0", "Cette formation est super !", "difficile", 3, 40, 30, 50);
         Formation form2 = new Formation(1, "Formation 1", "Cette formation est bof !", "facile", 3, 50, 30, 70);
         Formation form3 = new Formation(2, "Formation 2", "Cette formation est bien !", "moyen", 5, 20, 10, 30);
         Formation form4 = new Formation(3, "Formation 3", "Cette formation est nulle !", "facile", 2, 40, 20, 70);
         newListeFormation.put(0, form1);
         newListeFormation.put(1, form2);
         newListeFormation.put(2, form3);
         newListeFormation.put(3, form4);
         
         return newListeFormation;
    }
}
