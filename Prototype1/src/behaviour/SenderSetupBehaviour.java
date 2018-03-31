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
    String course = "NOSQL Databases";
    // State of behaviour
    int state = 0;
    // Counter of replies from receiver agents
    int repliesCnt = 0;
    // Message template
    MessageTemplate mt;
    // Array of appropriate AgentsReceivers providing required service
    DFAgentDescription[] receiversArray;
    
    public void action() {
        switch(state) {
            case 0:
                DFAgentDescription dfd = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("courses");
                dfd.addServices(sd);
                //
                try {
                    //
                    System.out.println("AgentSender searches for services in DF");
                    //
                    DFAgentDescription[] result = DFService.search(myAgent, dfd);
                    if (result.length > 0) {
                        this.receiversArray = result;
                        System.out.println("AgentSender found required service");
                        state++;
                    }
                } 
                catch (FIPAException fe) {
                    fe.printStackTrace();
                }                
        }
    }
    
    public boolean done() {return state == 1;}
}
