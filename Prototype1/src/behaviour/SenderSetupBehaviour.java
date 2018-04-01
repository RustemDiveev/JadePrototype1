/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviour;

import jade.core.AID;
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
 * Public class SenderSetupBehaviour.
 * This behaviour used by AgentSender to request AgentReceivers the target course.
 */
public class SenderSetupBehaviour extends SimpleBehaviour {
    // Agent identifier of useful AgentReceiver
    AID bestAgentReceiver;
    // Course to search
    String course = "Databases";
    // State of behaviour
    int state = 0;
    // Counter of replies from receiver agents
    int repliesCnt = 0;
    // Message template
    MessageTemplate mt;
    // Array of appropriate AgentsReceivers providing required service
    DFAgentDescription[] receiversArray = null;
    //
    String msgContent;
    //
    jade.core.AID receiverAID;
    //
    ACLMessage msg;
    
    public void action() {
        System.out.println("AgentSender has been initiated!");
        System.out.println("AgentSender " + myAgent.getLocalName() + " searches for services in DF");
        //
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("courses");
        dfd.addServices(sd);
        //
        System.out.println("AgentSender searched DF for " + "courses");
        switch(state) {
            case 0:
                //System.out.println("AgentSender state = " + state);
                //
                try {
                    //System.out.println("AgentSender " + myAgent.getLocalName() + " searches for services in DF");
                    DFAgentDescription[] result = DFService.search(myAgent, dfd);
                    if (result.length > 0) {
                        receiversArray = result;
                        System.out.println("AgentSender " + myAgent.getLocalName() + " found required service");
                        state++;
                    }
                } 
                catch (FIPAException fe) {
                    System.out.println("EXCEPTION!");
                    fe.printStackTrace();
                }
                //state = 0;
            case 1:
                    //System.out.println("AgentSender: Current state is " + state);
                    msg = new ACLMessage(ACLMessage.REQUEST);
                    if (receiversArray != null) {
                        for (int i = 0; i < receiversArray.length; i++) {
                            msg.addReceiver(receiversArray[i].getName());
                        }
                        msg.setContent(course);
                        myAgent.send(msg);
                        System.out.println("AgentSender " + myAgent.getLocalName() + " sends an ACLMessage");
                        state++;
                        }
            case 2:
               mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
               msg = myAgent.receive(mt);
               //
               if (msg != null) {
                   msgContent = msg.getContent();
                   receiverAID = msg.getSender();
                   if (msgContent.equals(course)) {
                       msg = new ACLMessage(ACLMessage.INFORM);
                       msg.addReceiver(receiverAID);
                       myAgent.send(msg);
                       System.out.println("Best propose made by " + receiverAID + " with a course of " + msgContent);
                       state++;
                   }
               }
        }
    }
    
    public boolean done() {return state == 1;}
}
