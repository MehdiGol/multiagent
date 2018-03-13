package com.mgolzadeh.agents;



import java.io.IOException;
import java.util.ArrayList;

import org.jfree.ui.RefineryUtilities;

import com.mgolzadeh.messages.KDAtoMVA;
import com.mgolzadeh.messages.MEAtoMVA;
import com.mgolzadeh.messages.MEAtoRFE;
import com.mgolzadeh.messages.MVAtoKDA;
import com.mgolzadeh.messages.RFEtoMEA;
import com.mgolzadeh.utils.ensembleProperties;
import com.mgolzadeh.utils.ensembleResults;
import com.mgolzadeh.utils.modelResults;
import com.mgolzadeh.utils.systemOut;
import com.mgolzadeh.visualization.myApplication;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class DataVisualizationAgent extends Agent {
	public static final long serialVersionUID = 10000400;
	
	///////////////////////////////////////////////////////////////////////////////////////////
	myApplication myApp;
	public ArrayList<modelResults> models;
	public ArrayList<int[]> selLists;
	protected void setup() 
    {
        addBehaviour( new dataPrepare( this ) );
        addBehaviour( new setSpecEnsembleBehaviour( this ) );
        addBehaviour( new setKDBehaviour( this ) );
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////
    class dataPrepare extends SimpleBehaviour 
    {   
    	public static final long serialVersionUID = 10000401;
    	DataVisualizationAgent dv;
        public dataPrepare(DataVisualizationAgent a ) {
        	super(a); 
        	dv = a;
        }
        
        public void action() 
        {
        	MessageTemplate EvalAgentMessage =
 			       MessageTemplate.and(  
 			       MessageTemplate.and( MessageTemplate.MatchPerformative( ACLMessage.INFORM ),
 			    		   				MessageTemplate.MatchConversationId("dataReadyForVisualize")),
 				           				MessageTemplate.MatchSender( new AID( "meAgent", AID.ISLOCALNAME))) ;
        	
        	ACLMessage msg = receive(EvalAgentMessage);
            if (msg!=null)
            {
            	systemOut.println("Data Visualization started...");
                
                try {
                	models = ((MEAtoMVA)(msg.getContentObject())).getResults();
                	selLists = ((MEAtoMVA)(msg.getContentObject())).getFeatureList();
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
                
                myApp = new myApplication(dv);
                myApp.pack();
                RefineryUtilities.centerFrameOnScreen(myApp);
                myApp.setVisible(true);
                
            }  
        }
        boolean finish = false;
        public boolean done()
        {
        	return finish;
        }
        
    } // ----------- End myBehaviour
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
    public void SendMessageToModelEvaluation(ensembleProperties ensProp)
    {
    	systemOut.println("Message Sent from vis to me" );
    	
    	RFEtoMEA Message = new RFEtoMEA("me","meAgent", 19, ensProp );
    	ACLMessage RequestForEnsem = new ACLMessage(ACLMessage.REQUEST);
    	RequestForEnsem.addReceiver(new AID("meAgent",AID.ISLOCALNAME));
    	RequestForEnsem.setConversationId("RequestforSpecialEns");
    	try {
    		RequestForEnsem.setContentObject(Message);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	send(RequestForEnsem);
    }
    
	
	///////////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<ensembleResults> ensembleResult =null;
	private int[] selectedList= null;
    class setSpecEnsembleBehaviour extends TickerBehaviour 
    {   
		public static final long serialVersionUID = 10000301;
        public setSpecEnsembleBehaviour(Agent a) { 
            super(a,1000);  
        } 
        public void onTick() 
        {
        	
        	MessageTemplate SpecialEnsDone =
 			       MessageTemplate.and(  
 			    		   MessageTemplate.and( MessageTemplate.MatchPerformative( ACLMessage.INFORM ),MessageTemplate.MatchConversationId("SpecialEnsembleBuildDone")),
 				           MessageTemplate.or(MessageTemplate.MatchSender( new AID( "mbAgent", AID.ISLOCALNAME)),
 				           MessageTemplate.MatchSender( new AID( "meAgent", AID.ISLOCALNAME)))) ;
        	
        	ACLMessage msg= receive(SpecialEnsDone);
            if (msg!=null)
            {
                systemOut.println( msg.getConversationId()+ " - " + myAgent.getLocalName() );
                
                try {
                	ensembleResult = ((MEAtoRFE)(msg.getContentObject())).getResults();
                	selectedList = ((MEAtoRFE)(msg.getContentObject())).getFeatureList();
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
                
                myApp.addComponent(ensembleResult);
                
            }
        }
    }
    
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////
    
    public void SendMessageToKnowledgeDiscovery(int min , int max , int[] params, ensembleProperties ensProp)
    {
    	systemOut.println("Message Sent from vis to kd" );
    	
    	MVAtoKDA Message = new MVAtoKDA("kd","kdAgent", 30, selLists.get(3),params, ensProp ,min,max);
    	ACLMessage RequestForEnsem = new ACLMessage(ACLMessage.REQUEST);
    	RequestForEnsem.addReceiver(new AID("kdAgent",AID.ISLOCALNAME));
    	RequestForEnsem.setConversationId("RequestforKD");
    	try {
    		RequestForEnsem.setContentObject(Message);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	send(RequestForEnsem);
    }
    
	///////////////////////////////////////////////////////////////////////////////////////////
	//Defines a behavior for Knowledge discovery agent
    private ArrayList<ArrayList<ensembleResults>> KDresults =null;
    class setKDBehaviour extends TickerBehaviour 
    {   
		public static final long serialVersionUID = 10000301;
        public setKDBehaviour(Agent a) { 
            super(a,1000);  
        } 
        public void onTick() 
        {
        	
        	MessageTemplate KDDone =
 			       MessageTemplate.and(  
 			    		   MessageTemplate.and( MessageTemplate.MatchPerformative( ACLMessage.INFORM ),MessageTemplate.MatchConversationId("KDModelsBuilt")),
 				           MessageTemplate.or(MessageTemplate.MatchSender( new AID( "kdAgent", AID.ISLOCALNAME)),
 				           MessageTemplate.MatchSender( new AID( "meAgent", AID.ISLOCALNAME)))) ;
        	
        	ACLMessage msg= receive(KDDone);
            if (msg!=null)
            {
                systemOut.println( msg.getConversationId()+ " - " + myAgent.getLocalName() );
                
                try {
                	KDresults = ((KDAtoMVA)(msg.getContentObject())).getResults();
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
               
                myApp.addSensitivityAnalysis(KDresults);
                
            }
        }
    }
	
}
