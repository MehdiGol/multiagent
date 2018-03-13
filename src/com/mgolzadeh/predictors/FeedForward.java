package com.mgolzadeh.predictors;

import org.encog.Encog;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.util.simple.EncogUtility;

import com.mgolzadeh.utils.systemOut;

public class FeedForward {
	
	//Using encog
	BasicNetwork network;
    
	public FeedForward()
	{ 
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public BasicNetwork train(MLDataSet mds,int hn)
	{
		
		BasicNetwork network = EncogUtility.simpleFeedForward(mds.getInputSize(), hn, 0, 1, false);
		network.reset();

		final Backpropagation train = new Backpropagation(network, mds, 0.25, 0.01);
		train.setBatchSize(1);
		
		
		int noe = 10000;
		int epoch = 0;
		do
		{
			train.iteration();
		}while(epoch++<noe);
		
		Encog.getInstance().shutdown();
		return network;
	}
	
	public void evaluate(MLDataSet dataSettest)
	{
		EncogUtility.evaluate(network, dataSettest);
	}

}
