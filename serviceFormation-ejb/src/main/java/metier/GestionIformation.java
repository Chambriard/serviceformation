/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import com.google.gson.Gson;
import entite.Formateur;
import entite.Formation;
import entite.Iformation;
import entite.PlanningSalle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import services.ServiceIFormation;

/**
 *
 * @author Claire
 */
@Stateless
public class GestionIformation implements GestionIformationLocal {
    
    private HashMap<Integer, Iformation> listeIformation;
    private HashMap<Integer, Formation> listeFormation;
    private HashMap<Integer, PlanningSalle> listePlannings;
    private int lastid;
    private enum listeEtats {EN_ATTENTE, EN_PROJET, PLANIFIEE};
    private Gson gson;

    public GestionIformation() {
        this.listeIformation = initListeIformation();
        this.listeFormation = initListeFormation();
        this.lastid = 4;
        this.gson = new Gson();
    }
    

    private HashMap<Integer, Iformation> initListeIformation(){
        HashMap<Integer, Iformation> newListeIformation = new HashMap<Integer, Iformation>();
        Iformation iform0 = new Iformation(0, 0, 5, "123", null, "05012020", "EN_ATTENTE", 3);
        Iformation iform1 = new Iformation(1, 1, 12, "124", null, "07122019", "EN_ATTENTE", 1);
        Iformation iform2 = new Iformation(2, 1, 20, "125", 2, "25122019", "PLANIFIEE", 3);
        Iformation iform3 = new Iformation(3, 2, 20, "126", 3, "25102020", "PLANIFIEE", 1);
        Iformation iform4 = new Iformation(4, 2, 11, "126", 1, "11022020", "EN_PROJET", 2);
        newListeIformation.put(0, iform0);
        newListeIformation.put(1, iform1);
        newListeIformation.put(2, iform2);
        newListeIformation.put(3, iform3);
        newListeIformation.put(4, iform4);

        return newListeIformation;
    }
    
    private HashMap<Integer, Formation> initListeFormation(){
        HashMap<Integer, Formation> newListeFormation = new HashMap<Integer, Formation>();
        Formation form0 = new Formation(0, "Formation 0", "Cette formation est super !", "difficile", 3, 40, 30, 50);
        Formation form1 = new Formation(1, "Formation 1", "Cette formation est bof !", "facile", 3, 50, 30, 70);
        Formation form2 = new Formation(2, "Formation 2", "Cette formation est bien !", "moyen", 5, 20, 10, 30);
        Formation form3 = new Formation(3, "Formation 3", "Cette formation est nulle !", "facile", 2, 40, 20, 70);
        newListeFormation.put(0, form0);
        newListeFormation.put(1, form1);
        newListeFormation.put(2, form2);
        newListeFormation.put(3, form3);
         
         return newListeFormation;
     
    }
     private String calculEtatIform(Formation form, int effectif) {
        int capMin = form.getCapMin();
        String res = "";
        if(effectif < capMin/2)
            res = listeEtats.EN_ATTENTE.name();
        else if(effectif >= capMin/2 & effectif < capMin)
            res = listeEtats.EN_PROJET.name();
        else
            res = listeEtats.PLANIFIEE.name();
        return res;
    }
     
    @Override
    public Iformation creerIFormation(String content) {
        Iformation iform = this.gson.fromJson(content, Iformation.class);
        boolean existe = false;
        Iformation res = new Iformation();
        int idFormation = iform.getIdformation();
        int effectif = iform.getNumeffectif();
        String codeClient = iform.getCodeClientProvisoire();
        Formation form = listeFormation.get(idFormation);
        for(Iformation instance : listeIformation.values()) {
            //on vérifie si une instance de cette formation n'existe pas déjà
            if(instance.getIdformation() == idFormation) {
                 int nouvelEffectif = instance.getNumeffectif() + effectif;
                //si la capacité max de la formation n'est pas atteinte, on ajoute l'effectif à 
                //l'effectif de l'instance déjà existante
                if(nouvelEffectif <= form.getCapMax()) {
                    instance.setNumeffectif(nouvelEffectif); 
                    instance.setEtat(calculEtatIform(form, nouvelEffectif));
                    instance.getCodeclient().add(codeClient);
                    existe = true;
                    res = instance;
                }
            }
        }
        //si aucune instance correspondante n'a été trouvé, on crée une nouvelle instance
        if(!existe) {
            res = new Iformation(idFormation, lastid, effectif, codeClient, calculEtatIform(form, effectif));
            this.listeIformation.put(lastid, res);
            lastid++;
        }
        
        for(Iformation i : listeIformation.values()) {
            System.out.println(i.toString());
        }
        System.out.println("------------------------");
        
        
        return res;
    }
    
    @Override
    public String annulerIFormation(int idIformation) { 
        listeIformation.remove(idIformation);
        for(Iformation instance : listeIformation.values()) {
            System.out.println(instance);
        }
        return "Instance de formation supprimée";
    }
    
    @Override
    public String choixSalleIformation(int idIformation) {
        URL url;
        //on récupère toutes les salles du planning
        try {
         HttpClient client = new DefaultHttpClient();
         HttpGet request = new HttpGet("http://localhost:8080/servicePatrimoine-web/webresources/patrimoine/afficherPlan");
         HttpResponse response = client.execute(request);
         BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
         String line = "";
         PlanningSalle planningSalle;
         while ((line = rd.readLine()) != null) {
            //planningSalle = gson.fromJson(line, PlanningSalle.class);
           //listePlannings.put(planning.getIdSalle(), planning);
           System.out.println(line);
         }
         
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        }	
        /*    
        Iformation iform = listeIformation.get(idIformation);
        Formation form = listeFormation.get(iform.getIdformation());
        int duree = form.getDuree();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
        Date dateDeb;
        Date dateFin;
        
        for(SallePlanning salle : listeSalles) {
            try {
              dateDeb  = formatter.parse(salle.getDateDeb());
              dateFin = formatter.parse(salle.getDateFin());
            } catch (ParseException ex) {
                Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //if(salle.getStatut().equals("disponible") & dateFin-dateDeb >= duree) {
                iform.setDateDeb(salle.getDateDeb());
                iform.setIdsalle(salle.getId());    
            //}
        }
    */
        return "Salle ajoutée à une instance de formation";
    }

    @Override
    public String choixFormateurIformation(int idIFormation, ArrayList<Formateur> listeFormateurs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
