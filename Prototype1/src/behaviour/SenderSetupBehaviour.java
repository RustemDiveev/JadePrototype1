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
import java.sql.Timestamp;

/**
 *
 * Public class SenderSetupBehaviour.
 * This behaviour used by AgentSender to request AgentReceivers the target course.
 */
public class SenderSetupBehaviour extends SimpleBehaviour {
    // Agent identifier of useful AgentReceiver
    AID bestAgentReceiver;
    // Course to search
    String course = "Parallel programming";
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
        //System.out.println("Current state is " + state);
        //
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("courses");
        dfd.addServices(sd);
        //
        switch(state) {
            case 0:
                while (state == 0) {
                    System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentSender " + myAgent.getLocalName() + " has been initiated!");
                    System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentSender " + myAgent.getLocalName() + " searches for services in DF");
                    try {
                        DFAgentDescription[] result = DFService.search(myAgent, dfd);
                        //System.out.println("result.length is " + result.length);
                        if (result.length > 0) {
                            receiversArray = result;
                            System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentSender " + myAgent.getLocalName() + " found required service");
                            for (int i = 0; i < result.length; i++) {
                                int j = i + 1;
                                String AIDstr = result[0].getName().toString();
                                System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentSender " + myAgent.getLocalName() + " found services: " + j + " " + AIDstr);
                            }
                            state++;
                        } else {
                            try {
                                System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentSender " + myAgent.getLocalName() + "sleeps for 10 seconds and continues to search df");
                                Thread.sleep(10000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } 
                    catch (FIPAException fe) {
                        System.out.println(new Timestamp(System.currentTimeMillis()) + ": EXCEPTION!");
                        fe.printStackTrace();
                    }
                    
                }
            case 1:
                    //System.out.println("AgentSender: Current state is " + state);
                    msg = new ACLMessage(ACLMessage.REQUEST);
                    if (receiversArray != null) {
                        String AIDReceivers = "";
                        for (int i = 0; i < receiversArray.length; i++) {
                            msg.addReceiver(receiversArray[i].getName());
                            AIDReceivers = AIDReceivers + receiversArray[i].getName().toString() + " ";
                        }
                        msg.setContent(course);
                        myAgent.send(msg);
                        System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentSender " + myAgent.getLocalName() + " sends an ACLMessage.REQUEST to " + "\n" + AIDReceivers);
                        state++;
                        }
            case 2:
               mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
               msg = myAgent.receive(mt);
               //
               if (msg != null) {
                   msgContent = msg.getContent();
                   receiverAID = msg.getSender();
                   System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentSender " + myAgent.getLocalName() + " receives an ACLMessage from " + "\n" + 
                                      receiverAID.toString() + " with content of " + msgContent);
                   if (msgContent.equals(course)) {
                       msg = new ACLMessage(ACLMessage.INFORM);
                       msg.addReceiver(receiverAID);
                       msg.setContent("Terminate!");
                       myAgent.send(msg);
                       System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentSender " + myAgent.getLocalName() + " sent an ACLMessage.INFORM to " + receiverAID);
                       System.out.println(new Timestamp(System.currentTimeMillis()) + ": Best propose made by " + receiverAID + "\n" + " with a course of " + msgContent);
                       state++;
                       //myAgent.doDelete();
                   }
               }
        }
    }
    
    public boolean done() {return state == 3;}
}
