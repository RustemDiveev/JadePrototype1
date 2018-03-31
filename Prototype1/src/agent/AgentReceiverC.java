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
 * @author HP
 */
public class AgentReceiverC extends Agent{
        
    String serviceType = "courses";
    
    String[] services = new String[2];
    
    private void populateServices() {
        services[0] = "Parallel programming";
        services[1] = "Hardware";
    }
    
    protected void setup() {
        
        populateServices();
        //
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceType);
        sd.setName(getLocalName());
        dfd.addServices(sd);
        
    }
    
    
    
}
