/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import behaviour.SenderSetupBehaviour;
import jade.core.Agent;

/**
 *
 * @author HP
 */
public class AgentSender extends Agent{
    
    protected void setup() {
        //SenderSetupBehaviour agentSenderBehaviour = new SenderSetupBehaviour();
        addBehaviour(new SenderSetupBehaviour(this));
        //this.doDelete();
        System.out.println("Agent has been initiated!");
    }
    
}
