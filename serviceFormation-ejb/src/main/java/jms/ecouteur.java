/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jms;

import entite.Iformation;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import services.ServiceIFormation;

/**
 *
 * @author alban
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "commercialDestQueue")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ecouteur implements MessageListener {
    private Gson gson;
    
    public ecouteur() {
        this.gson = new Gson();
    }
    
    @Override
    public void onMessage(Message message) {
        
        try {
            System.out.println(message.getBody(String.class));
            //on récupère la demande
            Iformation iform = this.gson.fromJson(message.getBody(String.class), Iformation.class);
            
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost("http://localhost:8080/serviceFormation-web/webresources/iformation/creerIForm");
                StringEntity params = new StringEntity(this.gson.toJson(iform));
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
            
        } catch (JMSException ex) {
            Logger.getLogger(ecouteur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
