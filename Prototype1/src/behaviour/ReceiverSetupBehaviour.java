/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviour;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author HP
 */
public class ReceiverSetupBehaviour extends SimpleBehaviour{
    private MessageTemplate mt;
    int state = 0;
    String msgContent;
    
    public void action() {
        switch (state) {
            case 0: 
                this.mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
                ACLMessage msg = myAgent.receive(mt);
                //
                /*
                if (msg != null) {
                    msgContent = msg.getContent();
                    for (int i = 0; i < AgentReceiverA.)
                }
                */                                      
        }
        
    }
    
    public boolean done() {return state == 1;}
    
}
