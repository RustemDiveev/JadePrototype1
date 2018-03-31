/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 *
 * AgentReceiverB
 *      universities
 */
public class AgentReceiverB extends Agent{
    
    String serviceType = "universities";
    
    protected void setup() {
        
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceType);
        sd.setName(getLocalName());
        dfd.addServices(sd);
        
    }
    
}
