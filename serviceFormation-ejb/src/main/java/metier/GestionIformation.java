package metier;

import com.google.gson.Gson;
import entite.PlanningFormateur;
import entite.Formation;
import entite.Iformation;
import entite.PlanningSalle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
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
    private ArrayList<PlanningFormateur> listePlanningFormateur;
    private int lastid;
    private Gson gson;

    /*
    * EN_ATTENTE : effectif demandé < à la moitié de la capacité minimale de la formation
    * EN_PROJET : effectif demandé >= à la moitié de la capacité minimale de la formation 
    *             et < à la capacité minimale de la formation
    * PLANIFIEE : effectif demandé >= à la capacité minimale de la formation
    */ 
    private enum listeEtats {EN_ATTENTE, EN_PROJET, PLANIFIEE};
  

    public GestionIformation() {
        this.listeIformation = initListeIformation();
        this.listeFormation = initListeFormation();
        this.listePlanningSalle = new ArrayList<>();
        this.listePlanningFormateur = new ArrayList<>();
        this.lastid = 4;
        this.gson = new Gson();
    }
    
    /*-------------------
    * Méthodes internes
    *--------------------*/
    
    private HashMap<Integer, Iformation> initListeIformation(){
        HashMap<Integer, Iformation> newListeIformation = new HashMap<Integer, Iformation>();
        Iformation iform0 = new Iformation(0, 0, 5, "123", null, "05/01/2020", "EN_ATTENTE", null);
        Iformation iform1 = new Iformation(1, 1, 12, "124", null, "07/12/2019", "EN_ATTENTE", null);
        Iformation iform2 = new Iformation(2, 1, 20, "125", 2, "01/01/2020", "PLANIFIEE", 1);
        Iformation iform3 = new Iformation(3, 2, 20, "126", 4, "25/10/2020", "PLANIFIEE", 4);
        Iformation iform4 = new Iformation(4, 2, 11, "126", 3, "11/02/2020", "EN_PROJET", 2);
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
    
    private String majStatutSalle(int idFormation, int idSalle, String statut, String dateDeb, String dateFin) {
        String res = null;
        //on récupère toutes les salles du planning
        try {
            PlanningSalle p = new PlanningSalle(idSalle, idFormation, statut, dateDeb, dateFin);
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost("http://localhost:8080/servicePatrimoine-web/webresources/patrimoine/changerStatut");
            StringEntity params = new StringEntity(this.gson.toJson(p));
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = client.execute(request); 
            BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
            res = rd.readLine();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    private String majStatutFormateur(int idIformation, int idFormateur, String statut, String dateDeb, String dateFin) {
        String res = null;
        //on récupère toutes les formateurs du planning
        try {
            PlanningFormateur p = new PlanningFormateur(idFormateur, idIformation, statut, dateDeb, dateFin);
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost("http://localhost:8080/serviceRessourceH-web/webresources/RH/changerStatut");
            StringEntity params = new StringEntity(this.gson.toJson(p));
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = client.execute(request);     
            BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
            res = rd.readLine();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    private void supprimerSallePlanning(int idSalle, String dateDeb) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(
                    "http://localhost:8080/servicePatrimoine-web/webresources/patrimoine/SupprimerSallePlan?idSalle="+ idSalle+"&dateDeb="+dateDeb);
            HttpResponse response = client.execute(request);         
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void supprimerFormateurPlanning(int idFormateur, String dateDeb) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(
                    "http://localhost:8080/serviceRessourceH-web/webresources/RH/SupprimerFormaPlan?idFormateur="+ idFormateur+"&dateDeb="+dateDeb);
            HttpResponse response = client.execute(request);         
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    private void ajouterFormateurPlan(PlanningFormateur plan) {
        String res = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost("http://localhost:8080/serviceRessourceH-web/webresources/RH/AjoutFormaPlan");
            StringEntity params = new StringEntity(this.gson.toJson(plan));
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = client.execute(request);     
            BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
            res = rd.readLine();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String ajouterSallePlan(PlanningSalle plan) {
        //on récupère toutes les salles du planning
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost("http://localhost:8080/servicePatrimoine-web/webresources/patrimoine/AjoutPlan");
            StringEntity params = new StringEntity(this.gson.toJson(plan));
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = client.execute(request); 
            BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Salle ajoutee au planning";
    }
    
    /*-------------------
    * Méthodes REST
    *--------------------*/
    
    @Override
    public String creerIFormation(String content) {
        Iformation iform = this.gson.fromJson(content, Iformation.class);
        String res = null;
        if(this.listeFormation.containsKey(iform.getIdformation())) {
            boolean existe = false;
            Iformation newIform = new Iformation();
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
                        newIform = instance;
                        res = newIform.toString();
                    }
                }
            }
            //si aucune instance correspondante n'a été trouvé, on crée une nouvelle instance
            if(!existe) {
                newIform = new Iformation(idFormation, lastid, effectif, codeClient, calculEtatIform(form, effectif));
                this.listeIformation.put(lastid, newIform);
                lastid++;
                res = newIform.toString();
            }
        }
        else {
            res = "Formation inexistante";
        }
        
        return res;
    }
    
    @Override
    public String annulerIFormation(int idIformation) { 
        String res = "Instance de formation inexistante";
        if(this.listeIformation.containsKey(idIformation)) {
            //on met à jour le planning de la salle et du formateur
            //s'il y a un formateur ou une salle affecté à l'instance de formation
            Iformation iform = this.listeIformation.get(idIformation);
            if(!iform.getEtat().equals(listeEtats.EN_ATTENTE.name())) {
                
                this.supprimerFormateurPlanning(iform.getIdformateur(), iform.getDateDeb());
                this.supprimerSallePlanning(iform.getIdsalle(), iform.getDateDeb());
            }
            listeIformation.remove(idIformation);
            res = "Instance de formation supprimee";
          
        }
        return res;
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
            if(!iform.getEtat().equals(listeEtats.EN_ATTENTE.name())) {
                iform.setDateDeb(dateDeb);
                iform.setIdsalle(idSalle);

                //calcul de la date de fin
                Formation f = this.listeFormation.get(iform.getIdformation());
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String newDateDeb = formatter.format(new Date(dateDeb));
                Date dateFin = new Date(newDateDeb);
                dateFin.setDate(dateFin.getDate() + f.getDuree());

                if(iform.getEtat().equals(listeEtats.PLANIFIEE.name())) {
                    PlanningSalle planForm = new PlanningSalle(iform.getIdsalle(), 
                            iform.getIdformation(), "affecte", iform.getDateDeb(), formatter.format(dateFin));
                    this.ajouterSallePlan(planForm);
                }
                else if(iform.getEtat().equals(listeEtats.EN_PROJET.name())) {
                    PlanningSalle planForm = new PlanningSalle(iform.getIdsalle(), 
                            iform.getIdformation(), "pressenti", iform.getDateDeb(), formatter.format(dateFin));
                    this.ajouterSallePlan(planForm);
                }
                else {
                //mise à jour du statut de la salle
                res = this.majStatutSalle(iform.getIdformation(), idSalle, "pressenti", dateDeb, formatter.format(dateFin));
                }
            }
            //le statut de la formation est en attente
            else {
                res = "Instance de formation en attente, impossible de choisir une salle";
            }
        }
        return res;
    } 
    
    
    @Override
    public ArrayList<PlanningSalle> afficherPlanningSalles() {
         URL url;
        //on récupère toutes les salles du planning
        try {
            HttpClient client = new DefaultHttpClient();
            //récupère toutes les salles du planning plus celles qui sont disponibles
            HttpGet request = new HttpGet("http://localhost:8080/servicePatrimoine-web/webresources/patrimoine/afficherPlanSalles");
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
        return this.listePlanningSalle;
    }
    
    @Override
    public String choixFormateurIformation(int idIformation, int idFormateur, String dateDeb) {
        String res = "";
        boolean formateurExiste = false;
        for(PlanningFormateur p : this.listePlanningFormateur) {
            if(p.getIdForm()== idFormateur)
                formateurExiste = true;  
        }
        //si le formateur n'existe pas
        if(!formateurExiste) {
             res = "Formateur inexistant";
        }
        //si l'instance de formation n'existe pas
        else if(!this.listeIformation.containsKey(idIformation)) {
            res = "Instance de formation inexistante";
        }
        //si le formateur et l'instance de formation existent 
        else {
            
            Iformation iform = listeIformation.get(idIformation);
            
            if(!iform.getEtat().equals(listeEtats.EN_ATTENTE.name())) {
                iform.setDateDeb(dateDeb);
                iform.setIdformateur(idFormateur);

                //calcul de la date de fin
                Formation f = this.listeFormation.get(iform.getIdformation());
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String newDateDeb = formatter.format(new Date(dateDeb));
                Date dateFin = new Date(newDateDeb);
                dateFin.setDate(dateFin.getDate() + f.getDuree());

                boolean dispo = false;
                for(PlanningFormateur plan : this.listePlanningFormateur) {
                    if(plan.getIdForm()==iform.getIdformateur() && plan.getStatut().equals("disponible")) {
                      dispo = true;  
                    }
                }
                //si le formateur n'est pas dans le planning, on l'ajoute
                if(dispo) {
                    if(iform.getEtat().equals(listeEtats.PLANIFIEE.name())) {
                        PlanningFormateur planForm = new PlanningFormateur(iform.getIdformateur(), 
                                iform.getIdformation(), "affecte", iform.getDateDeb(), formatter.format(dateFin));
                        this.ajouterFormateurPlan(planForm);
                        res = "Formateur ajoute au planning";
                    }
                    else if(iform.getEtat().equals(listeEtats.EN_PROJET.name())) {
                        PlanningFormateur planForm = new PlanningFormateur(iform.getIdformateur(), 
                                iform.getIdformation(), "pressenti", iform.getDateDeb(), formatter.format(dateFin));
                        this.ajouterFormateurPlan(planForm);
                        res = "Formateur ajoute au planning";
                    }


                }
                //si le formateur est dans le planning, on le met à jour
                else {
                    //mise à jour du statut du formateur
                    res = this.majStatutFormateur(iform.getIdformation(), iform.getIdformateur(), "pressenti", dateDeb, formatter.format(dateFin));
                }
            }
            //le statut de la formation est en attente
            else {
                res = "Instance de formation en attente, impossible de choisir un formateur";
            }
        }
        return res;
    } 
    
    @Override
    public ArrayList<PlanningFormateur> afficherPlanningFormateurs() {
        //on récupère toutes les salles du planning
        try {
            HttpClient client = new DefaultHttpClient();
            //récupère toutes les salles du planning plus celles qui sont disponibles
            HttpGet request = new HttpGet("http://localhost:8080/serviceRessourceH-web/webresources/RH/afficherPlanFormateurs");
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
            String line = rd.readLine();
            PlanningFormateur planningFormateur;
            PlanningFormateur[] liste = this.gson.fromJson(line, PlanningFormateur[].class);
            this.listePlanningFormateur.clear();
            for(PlanningFormateur p : liste) {
                this.listePlanningFormateur.add(p);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceIFormation.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return this.listePlanningFormateur;
    }   
    
    @Override
    public HashMap<Integer, Iformation> afficherInstances() {
        return this.listeIformation;
    }
    
    @Override
    public HashMap<Integer, Formation> afficherFormations() {
        return this.listeFormation;
    }
    
}
