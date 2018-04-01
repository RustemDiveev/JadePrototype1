/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 *
 * @author HP
 */
public class AgentReceiverAbstract extends Agent{
    //Some variables specific to receiver agent
    static String serviceType;
    static String[] services = new String[2];
    
    void populateServices(String a, String b) {
        this.services[0] = a;
        this.services[1] = b;
    }
    
    AgentReceiverAbstract(String serviceType, String a, String b) {
       this.serviceType = serviceType;
       populateServices(a,b);
    }
    
    protected void setup() {
        
    }
    
    void register() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceType);
        sd.setName(getLocalName());
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
    
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
    
}
