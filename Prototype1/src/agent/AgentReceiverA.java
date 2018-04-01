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
 * AgentReceiverA
 *      courses
 *          Databases
 *          System administration
 */
public class AgentReceiverA extends Agent{
    
    String serviceType = "courses";
    
    private static String[] services = new String[2];
    
    private void populateServices() {
        services[1] = "Databases";
        services[0] = "System administration";
    }
    
    //private static int doDeleteFlg = 0;
    
    protected void setup() {
        populateServices();
        //
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
        System.out.println("AgentReceiverA " + getLocalName() + " initialized and registered on df.");
        addBehaviour(new ReceiverSetupBehaviour());
        /*
        if (doDeleteFlg == 1 ) {
            doDelete();
        }
        */
    }
    
    protected void takeDown() {
        try {
            System.out.println("AgentReceiverA " + getLocalName() + " says goodbye!");
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
            System.out.println("AgentReceiverA " + getLocalName() + " waits for ACL Message");
            switch (state) {
                case 0: 
                    this.mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
                    while (state == 0) {
                        //System.out.println("AgentReceiverA waits for ACL Message");
                        msg = myAgent.receive(mt);
                        //
                        if (msg != null) {
                            System.out.println("AgentReceiverA " + getLocalName() + " received an ACL Message");
                            msgContent = msg.getContent();
                            senderAgentAID = msg.getSender();
                            for (String service : AgentReceiverA.services) {
                                msg = new ACLMessage(ACLMessage.INFORM);
                                msg.addReceiver(senderAgentAID);
                                msg.setContent(service);
                                send(msg);
                            }
                            state++;
                        }
                    }
                //
                case 1:
                    this.mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                    msg = myAgent.receive(mt);
                    /*
                    System.out.println(myAgent.getLocalName() + " terminating...");
                    AgentReceiverA.doDeleteFlg = 1;
                    */
                    state++;
                    myAgent.doDelete();
            }
            
        
        }
    
        public boolean done() {
            return state == 2;
        }
    
    }
    
}
