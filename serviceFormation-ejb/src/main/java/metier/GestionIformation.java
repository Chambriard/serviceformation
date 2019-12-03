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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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
    private ArrayList<PlanningSalle> listePlanningSalle;
    private int lastid;
    private enum listeEtats {EN_ATTENTE, EN_PROJET, PLANIFIEE};
    private Gson gson;

    public GestionIformation() {
        this.listeIformation = initListeIformation();
        this.listeFormation = initListeFormation();
        this.listePlanningSalle = new ArrayList<>();
        this.lastid = 4;
        this.gson = new Gson();
    }
    

    private HashMap<Integer, Iformation> initListeIformation(){
        HashMap<Integer, Iformation> newListeIformation = new HashMap<Integer, Iformation>();
        Iformation iform0 = new Iformation(0, 0, 5, "123", null, "05/01/2020", "EN_ATTENTE", null);
        Iformation iform1 = new Iformation(1, 1, 12, "124", null, "07/12/2019", "EN_ATTENTE", null);
        Iformation iform2 = new Iformation(2, 1, 20, "125", 2, "25/12/2019", "PLANIFIEE", 3);
        Iformation iform3 = new Iformation(3, 2, 20, "126", 3, "25/10/2020", "PLANIFIEE", 1);
        Iformation iform4 = new Iformation(4, 2, 11, "126", 1, "11/02/2020", "EN_PROJET", 2);
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
        //preévenir RH et patrimoine pour que la salle et le formateur redeviennent disponibles
    }
    
    @Override
    public String choixSalleIformation(int idIformation, int idSalle, String dateDeb) {
        String res = "";
        boolean salleExiste = false;
        for(PlanningSalle p : this.listePlanningSalle) {
            if(p.getIdSalle() == idSalle)
                salleExiste = true;
        }
        //si la salle n'existe pas
        if(!salleExiste) {
             res = "Salle inexistante";
        }
        //si l'instance de formation n'existe pas
        else if(!this.listeIformation.containsKey(idIformation)) {
            res = "Instance de formation inexistante";
        }
        //si la salle et l'instance de formation existent 
        else {
            Iformation iform = listeIformation.get(idIformation);
            iform.setDateDeb(dateDeb);
            iform.setIdsalle(idSalle);
            res = iform.toString();
            
            //calcul de la date de fin
            Formation f = this.listeFormation.get(iform.getIdformation());
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String newDateDeb = formatter.format(new Date(dateDeb));
            Date dateFin = new Date(newDateDeb);
            dateFin.setDate(dateFin.getDate() + f.getDuree());
                       
            //mise à jour du statut de la salle
            this.majStatutSalle(idIformation, idSalle, "pressenti", dateDeb, formatter.format(dateFin));
        }
        return res;
    } 
    
    private void majStatutSalle(int idIformation, int idSalle, String statut, String dateDeb, String dateFin) {
         URL url;
        //on récupère toutes les salles du planning
        try {
            PlanningSalle p = new PlanningSalle(idSalle, idIformation, statut, dateDeb, dateFin);
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost("http://localhost:8080/servicePatrimoine-web/webresources/modif");
            StringEntity params = new StringEntity(this.gson.toJson(p));
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = client.execute(request);         
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public ArrayList<PlanningSalle> afficherPlanningSalles() {
         URL url;
        //on récupère toutes les salles du planning
        try {
            HttpClient client = new DefaultHttpClient();
            //récupère toutes les salles du planning plus celles qui sont disponibles
            HttpGet request = new HttpGet("http://localhost:8080/servicePatrimoine-web/webresources/patrimoine/afficherSalles");
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
            String line = rd.readLine();
            PlanningSalle planningSalle;
            PlanningSalle[] liste = gson.fromJson(line, PlanningSalle[].class);
            this.listePlanningSalle.clear();
            for(PlanningSalle p : liste) {
                this.listePlanningSalle.add(p);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return listePlanningSalle;
    }
    
     @Override
    public String afficherPlanningFormateurs() {
        return "";
    }
    
    
    @Override
    public String choixFormateurIformation(int idIFormation, ArrayList<Formateur> listeFormateurs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
