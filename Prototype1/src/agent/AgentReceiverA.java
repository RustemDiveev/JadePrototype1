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
import java.sql.Timestamp;

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
        System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentReceiverA " + getLocalName() + " initialized and registered on df.");
        System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentReceiverA " + getLocalName() + " provides service: " + serviceType + " with " + services[0] + " and " + services[1]);
        addBehaviour(new ReceiverSetupBehaviour());
    }
    
    protected void takeDown() {
        try {
            System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentReceiverA " + getLocalName() + " terminates and says goodbye!");
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
            System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentReceiverA " + getLocalName() + " waits for ACLMessage.REQUEST");
            switch (state) {
                case 0: 
                    this.mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
                    while (state == 0) {
                        //System.out.println("AgentReceiverA waits for ACL Message");
                        msg = myAgent.receive(mt);
                        //
                        if (msg != null) {
                            msgContent = msg.getContent();
                            System.out.println("AgentReceiverA msgContent is " + msgContent);
                            senderAgentAID = msg.getSender();
                            System.out.println("AgentReceiverA senderAgentAID is " + senderAgentAID.toString());
                            if (msgContent != null && senderAgentAID != null) {
                                System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentReceiverA " + getLocalName() + " received an ACLMessage.REQUEST from " + "\n" +
                                               senderAgentAID.toString() + " with content of " + msgContent);
                            }
                            for (String service : AgentReceiverA.services) {
                                msg = new ACLMessage(ACLMessage.INFORM);
                                msg.addReceiver(senderAgentAID);
                                msg.setContent(service);
                                send(msg);
                                System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentReceiverA " + getLocalName() + " sends an ACLMessage.INFORM to " + "\n" +
                                                   senderAgentAID.toString() + " with content of " + service);
                            }
                            state++;
                        }
                    }
                //
                case 1:
                    this.mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                    msg = myAgent.blockingReceive(mt);
                    //msg = myAgent.receive(mt);
                    msgContent = msg.getContent();
                    if (msgContent == null) {
                        System.out.println("msgContent is null");
                    } else {
                        System.out.println("msgContent is " + msgContent);
                    }
                    senderAgentAID = msg.getSender();
                    if (msgContent == null || senderAgentAID == null) {
                        System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentReceiverA " + getLocalName() + " received an ACLMessage.INFORM from " + "\n" +
                                        msg.getSender().toString() + " with content of " + msg.getContent());
                    } else {
                        System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentReceiverA " + getLocalName() + " received an ACLMessage.INFORM and terminating");
                    }
                    state++;
                    //myAgent.doDelete();
            }
            
        
        }
    
        public boolean done() {
            return state == 2;
        }
    
    }
    
}
