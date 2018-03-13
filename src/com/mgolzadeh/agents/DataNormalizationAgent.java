package com.mgolzadeh.agents;



import java.io.IOException;

import org.jfree.ui.RefineryUtilities;

import com.mgolzadeh.data.ConvertToJMLDataset;
import com.mgolzadeh.data.DataReader;
import com.mgolzadeh.messages.DatasetSender;
import com.mgolzadeh.utils.DynamicValues;
import com.mgolzadeh.utils.systemOut;
import com.mgolzadeh.visualization.VisualizeData;

import net.sf.javaml.core.Dataset;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class DataNormalizationAgent extends Agent {
	public static final long serialVersionUID = 10000400;
	
	///////////////////////////////////////////////////////////////////////////////////////////
	protected void setup() 
    {
        addBehaviour( new dataVisualize( this ) );
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////
	//This class aims data visualization
    class dataVisualize extends SimpleBehaviour 
    {   
    	public static final long serialVersionUID = 10000401;
        public dataVisualize(Agent a ) {
        	super(a); 
        }
        
        public void action() 
        {
        	Dataset Data = null;
        	
        	
        	MessageTemplate msgtmp =
 			       MessageTemplate.and(  
 				           MessageTemplate.MatchPerformative( ACLMessage.INFORM ),
 				           MessageTemplate.MatchSender( new AID( "dpAgent", AID.ISLOCALNAME))) ;
        	
        	ACLMessage msg = blockingReceive(msgtmp);
        	
            if (msg!=null)
            {
                systemOut.println( " - " +
                   myAgent.getLocalName() + " <- " +
                   msg.getContent());
                
                boolean show = false;
                if(show)
                {
	                double[] real = new double[DataReader.DataList.size()];
	                for (int i=0; i<DataReader.DataList.size(); i++)
	        		{
	                	real[i] = DataReader.DataList.get(i).target;
	    			}
	                
	    			final VisualizeData showRes = new VisualizeData("DATA",real);
	    			showRes.pack();
	    			RefineryUtilities.centerFrameOnScreen(showRes);
	    			showRes.setVisible(true);
                }
                
                DataReader.DataList = DataReader.shuffle(DynamicValues.randomizer);
                
                DataReader.normalizeZero();
                
                
                DataReader.trainingFraction = DynamicValues.TPFFS;
                DataReader.showMinMax();
                Data = ConvertToJMLDataset.Convert(DataReader.getTrainingData(),DataReader.numInputs);
                systemOut.println("trainginDatasize " + Data.size());
                DatasetSender s = new DatasetSender("dn","dnagent",11,Data);
                
                ACLMessage dataNormalMessage1 = new ACLMessage(ACLMessage.INFORM);
                dataNormalMessage1.addReceiver(new AID("fsAgent1",AID.ISLOCALNAME));
                
                ACLMessage dataNormalMessage2 = new ACLMessage(ACLMessage.INFORM);
                dataNormalMessage2.addReceiver(new AID("fsAgent2",AID.ISLOCALNAME));
                
                ACLMessage dataNormalMessage3 = new ACLMessage(ACLMessage.INFORM);
                dataNormalMessage3.addReceiver(new AID("fsAgent3",AID.ISLOCALNAME));
                
                try {
                	dataNormalMessage1.setContentObject(s);
                	dataNormalMessage2.setContentObject(s);
                	dataNormalMessage3.setContentObject(s);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
                
                send(dataNormalMessage1);
                send(dataNormalMessage2);
                send(dataNormalMessage3);
                
            }  
        	
        }
        public boolean finish = false;
        public boolean done()
        {
        	return finish;
        }
        
    } 

}
