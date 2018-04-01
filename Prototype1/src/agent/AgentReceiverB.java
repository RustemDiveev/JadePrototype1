/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * AgentReceiverB
 *      universities
 *      	Russian Economical University
 *          High School of Economics
 */
public class AgentReceiverB extends Agent{
    
    String serviceType = "universities";
    
    private static String[] services = new String[2];
    
    private void populateServices() {
        services[0] = "Russian Economical University";
        services[1] = "High School of Economics";
    }
    
    protected void setup() {
        
        populateServices();
        
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceType);
        sd.setName(getLocalName());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
		//
        System.out.println("AgentReceiverB " + getLocalName() + " initialized and registered on df.");
        addBehaviour(new ReceiverSetupBehaviour());
        
    }
    
    protected void takeDown() {
        try {
            System.out.println("AgentReceiverB " + getLocalName() + " says goodbye!");
            DFService.deregister(this);
        } catch (FIPAException fe ) {
            fe.printStackTrace();
        }
    }
    
    private class ReceiverSetupBehaviour extends SimpleBehaviour{
        
        private MessageTemplate mt;
        int state = 0;
        String msgContent;
        jade.core.AID senderAgentAID;
        ACLMessage msg;
    
        public void action() {
            System.out.println("AgentReceiverB " + getLocalName() + " waits for ACL Message");
            switch (state) {
                case 0: 
                    this.mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
                    while (state == 0) {
                        msg = myAgent.receive(mt);
			//
			if (msg != null) {
                            System.out.println("AgentReceiverB " + getLocalName() + " received an ACL Message");
                            msgContent = msg.getContent();
                            senderAgentAID = msg.getSender();
                            for (String service : AgentReceiverB.services) {
                                msg = new ACLMessage(ACLMessage.INFORM);
				msg.addReceiver(senderAgentAID);
				msg.setContent(service);
				send(msg);
                                state++;
                            }
                        }
                    }
                //
                case 1:
                    //System.out.println("Current state is " + state);
                    this.mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                    msg = myAgent.receive(mt);
                    state++;
                    myAgent.doDelete();
                }
            }
    
        public boolean done() {return state == 2;}
    
    }
    
}
