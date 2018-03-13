package com.mgolzadeh.evaluation;

import java.util.ArrayList;

import org.encog.ml.data.MLData;

import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.rbf.RBFNetwork;

import com.mgolzadeh.data.DataReader;
import com.mgolzadeh.data.Example;
import com.mgolzadeh.predictors.Ensemble;

public class calculateError {
	
	public static final int scale = 100;
	
	public static double MSE(BasicNetwork net,ArrayList<Example> mylist)
	{
		double mse=0, output=0;
		MLData outp = null;
		for(int p=0; p<mylist.size(); p++)
		{
			Example thisExample = (Example)mylist.get(p);
			MLData data = new BasicMLData(thisExample.inputs);
			outp = net.compute(data);
			output = outp.getData()[0];
			mse += (DataReader.DenormalizeTarget(thisExample.target) - DataReader.DenormalizeTarget(output))*(DataReader.DenormalizeTarget(thisExample.target) - DataReader.DenormalizeTarget(output));
		}

		return (mse/(double)mylist.size());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static double RMSE(BasicNetwork net,ArrayList<Example> mylist)
	{
		double mse=0, output=0;
		MLData outp = null;
		for(int p=0; p<mylist.size(); p++)
		{
			Example thisExample = (Example)mylist.get(p);
			MLData data = new BasicMLData(thisExample.inputs);
			outp = net.compute(data);
			output = outp.getData()[0];
			mse += (DataReader.DenormalizeTarget(thisExample.target) - DataReader.DenormalizeTarget(output))*(DataReader.DenormalizeTarget(thisExample.target) - DataReader.DenormalizeTarget(output));
		}

		return Math.sqrt(mse/(double)mylist.size());
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static double MAE(BasicNetwork net,ArrayList<Example> mylist)
	{
		double mse=0, output=0;
		MLData outp = null;
		for(int p=0; p<mylist.size(); p++)
		{
			Example thisExample = (Example)mylist.get(p);
			MLData data = new BasicMLData(thisExample.inputs);
			outp = net.compute(data);
			output = outp.getData()[0];
			mse += Math.abs(DataReader.DenormalizeTarget(thisExample.target) - DataReader.DenormalizeTarget(output));
		}

		return (mse/(double)mylist.size());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static double MAPE(BasicNetwork net,ArrayList<Example> mylist)
	{
		double mse=0, output=0;
		MLData outp ;
		for(int p=0; p<mylist.size(); p++)
		{
		
			Example thisExample = (Example)mylist.get(p);
			MLData data = new BasicMLData(thisExample.inputs);
			outp = net.compute(data);
			output = outp.getData()[0];
			if(DataReader.DenormalizeTarget(thisExample.target) != 0)
			{
				double av = DataReader.DenormalizeTarget(thisExample.target);
				double pv = DataReader.DenormalizeTarget(output);
				double res = (Math.abs(av - pv)/Math.abs(av));
				if(res<.4)
				{
					mse += res;
				}
			}
		}

		return (mse/(double)mylist.size())*scale;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static double MSE(RBFNetwork net,ArrayList<Example> mylist)
	{
		double mse=0, output=0;
		MLData outp = null;
		for(int p=0; p<mylist.size(); p++)
		{
			Example thisExample = (Example)mylist.get(p);
			MLData data = new BasicMLData(thisExample.inputs);
			outp = net.compute(data);
			output = outp.getData()[0];
			
			mse += (DataReader.DenormalizeTarget(thisExample.target) - DataReader.DenormalizeTarget(output))*(DataReader.DenormalizeTarget(thisExample.target) - DataReader.DenormalizeTarget(output));
		}

		return (mse/(double)mylist.size());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static double RMSE(RBFNetwork net,ArrayList<Example> mylist)
	{
		double mse=0, output=0;
		MLData outp = null;
		for(int p=0; p<mylist.size(); p++)
		{
			Example thisExample = (Example)mylist.get(p);
			MLData data = new BasicMLData(thisExample.inputs);
			outp = net.compute(data);
			output = outp.getData()[0];
			mse += (DataReader.DenormalizeTarget(thisExample.target) - DataReader.DenormalizeTarget(output))*(DataReader.DenormalizeTarget(thisExample.target) - DataReader.DenormalizeTarget(output));
		}

		return Math.sqrt(mse/(double)mylist.size());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static double MAE(RBFNetwork net,ArrayList<Example> mylist)
	{
		double mse=0, output=0;
		MLData outp = null;
		for(int p=0; p<mylist.size(); p++)
		{
			Example thisExample = (Example)mylist.get(p);
			MLData data = new BasicMLData(thisExample.inputs);
			outp = net.compute(data);
			output = outp.getData()[0];
			mse += Math.abs(DataReader.DenormalizeTarget(thisExample.target) - DataReader.DenormalizeTarget(output));
		}

		return (mse/(double)mylist.size());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static double MAPE(RBFNetwork net,ArrayList<Example> mylist)
	{
		double mse=0, output=0;
		MLData outp = null;
		for(int p=0; p<mylist.size(); p++)
		{
			Example thisExample = (Example)mylist.get(p);
			MLData data = new BasicMLData(thisExample.inputs);
			outp = net.compute(data);
			output = outp.getData()[0];
			if(DataReader.DenormalizeTarget(thisExample.target) != 0)
			{
				double av = DataReader.DenormalizeTarget(thisExample.target);
				double pv = DataReader.DenormalizeTarget(output);
				double res = (Math.abs(av - pv)/Math.abs(av));
				if(res<.4)
				{
					mse += res;
				}
			}
		}
		return (mse/(double)mylist.size())*scale;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static double MSE(Ensemble net,ArrayList<Example> mylist)
	{
		double mse=0, output=0;
		for(int p=0; p<mylist.size(); p++)
		{
			Example thisExample = (Example)mylist.get(p);
			output = net.ensembleOutput(thisExample.inputs);
			mse += (DataReader.DenormalizeTarget(thisExample.target) - DataReader.DenormalizeTarget(output))*(DataReader.DenormalizeTarget(thisExample.target) - DataReader.DenormalizeTarget(output));
		}

		return (mse/(double)mylist.size());
	}
	public static double RMSE(Ensemble net,ArrayList<Example> mylist)
	{
		return Math.sqrt(MSE(net,mylist));
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static double MAE(Ensemble net,ArrayList<Example> mylist)
	{
		double mse=0, output=0;
		for(int p=0; p<mylist.size(); p++)
		{
			Example thisExample = (Example)mylist.get(p);
			output = net.ensembleOutput(thisExample.inputs);
			mse += Math.abs(DataReader.DenormalizeTarget(thisExample.target) - DataReader.DenormalizeTarget(output));
		}
		return (mse/(double)mylist.size());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static double MAPE(Ensemble net,ArrayList<Example> mylist)
	{
		double mse=0, output=0;
		for(int p=0; p<mylist.size(); p++)
		{
			Example thisExample = (Example)mylist.get(p);
			output = net.ensembleOutput(thisExample.inputs);
			if(DataReader.DenormalizeTarget(thisExample.target) != 0)
			{
				double av = DataReader.DenormalizeTarget(thisExample.target);
				double pv = DataReader.DenormalizeTarget(output);
				double res = (Math.abs(av - pv)/Math.abs(av));
				if(res<.3)
				{
					mse += res;
				}
			}
		}

		return (mse/(double)mylist.size())*scale;
	}
}
