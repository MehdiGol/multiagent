package com.mgolzadeh.predictors;

import org.encog.engine.network.activation.ActivationFunction;
import org.encog.engine.network.activation.ActivationGaussian;
import org.encog.engine.network.activation.ActivationLOG;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.util.simple.EncogUtility;

public class BackpropMLP {
	
	//Using encog
	
	BasicNetwork method;
	int inputCount = 8;
    int outputCount = 1;
    String trainingSetFileName;
    String TestSetFileName;
    
	public BackpropMLP()
	{ 
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public BasicNetwork train(MLDataSet mds,String afunc,int hn)
	{
		System.out.println("train a mlp backpropagation with "+ mds.getInputSize()+" input neuron and " + hn+ " and "+ mds.getIdealSize()+" output neurons");
        method = new BasicNetwork();
        ActivationFunction af=null;
        switch(afunc)
        {
        case "Tah":
        	af = new ActivationTANH();
        	break;
        case "Sig":
        	af = new ActivationSigmoid();
        	break;
        case "Log":
        	af = new ActivationLOG();
        	break;
        case "Gus":
        	af = new ActivationGaussian();
        	break;
        }
        method.addLayer(new BasicLayer(null, true, mds.getInputSize()));
        method.addLayer(new BasicLayer(af, true, hn));
        method.addLayer(new BasicLayer(af, false, mds.getIdealSize()));
        method.getStructure().finalizeStructure();
        method.reset();

        MLTrain train = new ResilientPropagation(method, mds);
        
        int epoch = 1;
        do
        {
        	train.iteration();
        	System.out.println("Epoch #" + epoch + " - Error : "+ train.getError());
        	epoch++;
        }while(train.getError()>0.0055);
        
        return method;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public void evaluate(MLDataSet dataSettest)
	{
		EncogUtility.evaluate(method, dataSettest);
	}

}
