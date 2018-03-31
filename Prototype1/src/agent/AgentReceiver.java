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
public class AgentReceiver extends Agent{
    
    String serviceType;
    
    protected void setup() {
        
        Object[] args = getArguments();
        
        if (args[0].toString() != null && !args[0].toString().isEmpty()) {
            this.serviceType = args[0].toString();
            
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setType(serviceType);
            sd.setName(getLocalName());
            dfd.addServices(sd);
        
            try {
                DFService.register(this, dfd);
            }
                catch (FIPAException fe) {fe.printStackTrace();}
        }    
    }   
    
    
}
