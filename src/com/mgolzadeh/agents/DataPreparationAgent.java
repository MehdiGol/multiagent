package com.mgolzadeh.agents;

import com.mgolzadeh.data.*;
import com.mgolzadeh.utils.DynamicValues;
import com.mgolzadeh.utils.systemOut;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class DataPreparationAgent extends Agent {
	public static final long serialVersionUID = 10000500;
	
	///////////////////////////////////////////////////////////////////////////////////////////
	protected void setup() 
    {
        addBehaviour( new myBehaviour( this ) );
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////
    class myBehaviour extends OneShotBehaviour 
    {   
    	public static final long serialVersionUID = 10000501;
        public myBehaviour(Agent a) { 
            super(a);  
        }
        
        public void action() 
        {
        	systemOut.changeOut();
        	DataReader.ReadData(DynamicValues.DataAddress);
        	systemOut.println("Loading data from file...");
        	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            systemOut.println("Loading done...");
            
            ACLMessage dataPreparedMessage = new ACLMessage(ACLMessage.INFORM);
            dataPreparedMessage.addReceiver(new AID("dnAgent",AID.ISLOCALNAME));
            dataPreparedMessage.setContent("Data is ready for Normalization Process");
            send(dataPreparedMessage);
        }
        
    }
}
