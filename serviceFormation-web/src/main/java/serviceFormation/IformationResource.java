/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceFormation;

import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.soap.SOAPException;
import services.ServiceIFormationLocal;

/**
 * REST Web Service
 *
 * @author Claire
 */
@Path("iformation")
@RequestScoped
public class IformationResource {

    @Context
    private UriInfo context;
    
    ServiceIFormationLocal iform;
    private Gson gson;

    /**
     * Creates a new instance of GenericResource
     */
    public IformationResource() {
        this.gson = new Gson();
        this.iform = lookupServicesIFormationLocal();
    }

    /**
     * Retrieves representation of an instance of fr.toulouse.miage.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    public Response getJson() {
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @Path("creerIForm")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String creerIFormation(String content) throws SOAPException {
        return this.gson.toJson(this.iform.creerIFormation(content));
    }
    
    
    @Path("annulerIForm")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String annulerIFormation(@QueryParam("idIForm") int idIForm) {
        return this.gson.toJson(this.iform.annulerIFormation(idIForm));
        
    }
    
    @Path("choixSalleIForm")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String choixSalleIformation(@QueryParam("idIForm") int idIForm, @QueryParam("idSalle") int idSalle, @QueryParam("dateDeb") String dateDeb) {
        return this.gson.toJson(this.iform.choixSalleIformation(idIForm, idSalle, dateDeb));
    }
    
    @Path("afficherPlanningSalles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String afficherPlanningSalles() {
        return this.gson.toJson(this.iform.afficherPlanningSalles());
    }
    
    
    @Path("choixFormateurIForm")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String choixFormateurIformation(@QueryParam("idIForm") int idIForm, @QueryParam("idFormateur") int idFormateur, @QueryParam("dateDeb") String dateDeb) {
        return this.gson.toJson(this.iform.choixFormateurIformation(idIForm, idFormateur, dateDeb));
    }
    
    @Path("afficherPlanningFormateurs")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String afficherPlanningFormateurs() {
        return this.gson.toJson(this.iform.afficherPlanningFormateurs());
    }
    
    @Path("afficherInstances")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String afficherInstances() {
        return this.gson.toJson(this.iform.afficherInstances());
    }
    
    @Path("afficherFormations")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String afficherFormations() {
        return this.gson.toJson(this.iform.afficherFormations());
    }
    
    private ServiceIFormationLocal lookupServicesIFormationLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ServiceIFormationLocal) c.lookup("java:global/serviceFormation-ear/serviceFormation-web-1.0-SNAPSHOT/ServiceIFormation!services.ServiceIFormationLocal");       
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
    
}

