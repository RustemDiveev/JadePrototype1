/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

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
    
    private class ReceiverSetupBehaviour extends SimpleBehaviour{
        
        private MessageTemplate mt;
        int state = 0;
        String msgContent;
        jade.core.AID senderAgentAID;
    
        public void action() {
            switch (state) {
                case 0: 
                    this.mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
                    ACLMessage msg = myAgent.receive(mt);
                    //
                    if (msg != null) {
                        msgContent = msg.getContent();
                        senderAgentAID = msg.getSender();
                        for (String service : AgentReceiverB.services) {
                            msg = new ACLMessage(ACLMessage.INFORM);
                            msg.addReceiver(senderAgentAID);
                            msg.setContent(service);
                            send(msg);
                        }
                    }
                    state++;
                //
                case 1:
                    this.mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                    msg = myAgent.receive(mt);
                    System.out.println(myAgent.getLocalName() + " terminating...");
                    state++;
            }
        
        }
    
        public boolean done() {return state == 2;}
    
    }
    
    
    
}
