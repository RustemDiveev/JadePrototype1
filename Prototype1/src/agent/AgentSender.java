/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import behaviour.SenderSetupBehaviour;
import jade.core.Agent;
import java.sql.Timestamp;

/**
 *
 * Agent sender class
 * includes setup and df registration
 */
public class AgentSender extends Agent{
    
    protected void setup() {
        addBehaviour(new SenderSetupBehaviour());
    }
    
    protected void takeDown() {
        System.out.println(new Timestamp(System.currentTimeMillis()) + ": AgentSender " + this.getLocalName() + " is terminating...");
    }
    
}
