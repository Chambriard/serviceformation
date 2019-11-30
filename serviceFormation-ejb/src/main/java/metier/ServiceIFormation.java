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
import entite.Planning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import table.InstanceTable;
/**
 *
 * @author Claire
 */
@Stateless
public class ServiceIFormation implements ServiceIFormationLocal{

    private HashMap<Integer, Iformation> listeIformation;
    private HashMap<Integer, Formation> listeFormation;
    private HashMap<Integer, Planning> listePlannings;
    private int lastid;
    private enum listeEtats {EN_ATTENTE, EN_PROJET, PLANIFIEE};
    private Gson gson;
    
   
    
    /**
     * Constructeur par défaut de l'exposition
     */
    public ServiceIFormation() {
        InstanceTable table = new InstanceTable();
        this.listeIformation = table.initListeIformation();
        this.listeFormation = table.initListeFormation();
        this.lastid = 4;
        gson = new Gson();
     
    }
    
    
    @Override
    public Iformation creerIFormation(int idFormation, int effectif, String codeClient) {
        boolean existe = false;
        Iformation res = new Iformation();
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
        
        for(Iformation instance : listeIformation.values()) {
            System.out.println(instance);
        }
        
        return res;
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
         HttpGet request = new HttpGet("http://localhost:8080/servicePatrimoine-web/resources/patrimoine");
         HttpResponse response = client.execute(request);
         BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
         String line = "";
         Planning planning;
         while ((line = rd.readLine()) != null) {
            planning = gson.fromJson(line, Planning.class);
           //listePlannings.put(planning.getIdSalle(), planning);
           System.out.println(planning);
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
