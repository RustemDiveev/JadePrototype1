/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import jade.core.Agent;
import jade.domain.DFService;
import static jade.domain.DFService.deregister;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class AgentTest extends Agent{
    
    String serviceType = "courses";
    
    protected void setup() {
        System.out.println("Test agent is initializing!");
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceType);
        sd.setName(getLocalName());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
            System.out.println("Test agent registers on df!");
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        try {
            System.out.println("Sleep for 5 seconds");
            Thread.sleep(5000);
            System.out.println("Stop sleeping!");
        } catch (InterruptedException ex) {
            Logger.getLogger(AgentTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        doDelete();
    }
    
    protected void takeDown() {
        System.out.println("Test agent is terminating!");
        try { 
            System.out.println("Deregister test agent from df");
            DFService.deregister(this); 
        }
        catch (Exception e) { e.printStackTrace();}
    }
    
}
