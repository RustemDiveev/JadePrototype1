/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviour;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

/**
 *
 * @author HP
 */
public class SenderSetupBehaviour extends SimpleBehaviour {
    int state = 0;
    Agent agent;
    
    public SenderSetupBehaviour(Agent agent) {
        this.agent = agent;
    }
    
    public void action() {
        System.out.println(state);
        System.out.println(agent.getLocalName());
        //System.out.println(agent.getLocalName());
        state++;
    }
    
    /*
    private boolean finished = false;
    public boolean done() {return finished; }
    */
    
    //public boolean done() {return state>10;}
    public boolean done() {return state == 10;}
}
