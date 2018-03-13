package com.mgolzadeh.data;


import java.io.*;
import java.util.*;

import com.mgolzadeh.utils.setPrecision;
import com.mgolzadeh.utils.systemOut;

public class DataReader
{
	public static ArrayList<Example> OriginalData = null;
	public static ArrayList<Example> DataList = null;
	
	public static Example maxs;
	public static Example mins;

	public static int numInputs    = -1;
	public static double trainingFraction = 0.01; // half training, half testing

//////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public static void ReadData ( String filename )
	{
		BufferedReader myReader = null;
		
		try
		{
			// Attempt to open the file
			//
			myReader = new BufferedReader( new FileReader( new File( filename ) ) );
		}
		catch( FileNotFoundException ex )
		{
			System.err.println( "Datafile '"+filename+"' not found." );
			System.out.print(ex);
			System.exit(1);
		}
		
		
		//Initialise the data structure to hold our training or testing examples
		//
		DataList = new ArrayList<Example>();
		

		try
		{
			// Loop round this while we have not reached the end of the file
			//
			HashMap<String,Integer> NonDoubleCol = new HashMap<>();
			int newval = 1;
			while (myReader.ready())
			{	
				//Read one line from the file (corresponding to one Example)
				//
				String line = myReader.readLine();
			
				//Break that line up into chunks separated by commas, spaces or tabs
				//
				StringTokenizer myTokenizer = new StringTokenizer( line, ", \t" );
				
				//Initialise a data structure to hold this particular Example
				//and take away 1 since the last item is the target
				//
				if(numInputs==-1) numInputs = myTokenizer.countTokens()-1;
				
				Example thisExample = new Example( numInputs );

				//Loop through each chunk of the line we read from the file, adding to our data structure
				//
				int attrib=0;
				
				
				while (attrib < numInputs)
				{
					String token = myTokenizer.nextToken();
					try
					{
						thisExample.inputs[attrib++] = Double.parseDouble( token );
					}catch(NumberFormatException e)
					{
						if(NonDoubleCol.containsKey(token))
						{
							thisExample.inputs[attrib++] = NonDoubleCol.get(token);
						}else{
							NonDoubleCol.put(token, newval);
							thisExample.inputs[attrib++] = newval++;
						}
					}
				}

				//Now read the target value
				//
				thisExample.target = Double.parseDouble( myTokenizer.nextToken() );

				//Add this Example to our list of examples
				//
				DataList.add( thisExample );
			}
		}
		catch (IOException ioe)
		{
			System.err.println( "IO Exception when reading datafile '"+filename+"'." );
			System.exit(1);
		}
		OriginalData = new ArrayList<Example>();
		for(int i = 0;i<DataList.size();i++)
		{
			Example newex = new Example(DataList.get(0).inputs.length);
			for(int j=0;j<newex.inputs.length;j++)
			{
				newex.inputs[j] = DataList.get(i).inputs[j];
			}
			newex.target = DataList.get(i).target;
			OriginalData.add(newex);
		}
		
	}
//////////////////////////////////////////////////////////////////////////////////////
	public static int numExamples()
	{
		return DataList.size();
	}
	
//////////////////////////////////////////////////////////////////////////////////////

	public void normalize()
	{
		ArrayList<Example> data = DataList;
		
		Example means = new Example(numInputs);
		Example stdev = new Example(numInputs);

		//FIND THE MEANS
		//
		for (int whichExample=0; whichExample<data.size(); whichExample++)
		{
			Example ex = (Example)data.get(whichExample);
			for (int i=0; i<means.inputs.length; i++)
				means.inputs[i] += ex.inputs[i];	
		}
		for (int i=0; i<means.inputs.length; i++)
			means.inputs[i] /= data.size();
		
		
		//FIND THE STDEVS
		//
		for (int whichExample=0; whichExample<data.size(); whichExample++)
		{
			Example ex = (Example)data.get(whichExample);
			for (int i=0; i<stdev.inputs.length; i++)
				stdev.inputs[i] += (ex.inputs[i]-means.inputs[i])*(ex.inputs[i]-means.inputs[i]);
		}
		for (int i=0; i<stdev.inputs.length; i++)
			stdev.inputs[i] = Math.sqrt( stdev.inputs[i] / (data.size()-1) );		
		
		
		//NORMALIZE
		//
		for (int whichExample=0; whichExample<data.size(); whichExample++)
		{
			Example ex = (Example)data.get(whichExample);
			for (int i=0; i<ex.inputs.length; i++)
				ex.inputs[i] = (ex.inputs[i]-means.inputs[i]) / stdev.inputs[i];	
		}
	}
	

//////////////////////////////////////////////////////////////////////////////////////

	public static ArrayList<Example> normalizeZero()
	{
		ArrayList<Example> data = DataList;
		
		maxs = new Example(numInputs);
		mins = new Example(numInputs);
		
		for(int j = 0;j<numInputs;j++)
		{
			mins.inputs[j] = getMin(data,j);
			maxs.inputs[j] = getMax(data,j);
		}
		maxs.target = getTargetMax(data);
		mins.target = getTargetMin(data);
		systemOut.println(maxs.toString());
		systemOut.println(mins.toString());
		
		System.out.println("Normalizing DS...");
		
		for(int i = 0;i<data.size();i++)
		{
			for(int j = 0;j<numInputs;j++)
			{
				((Example)data.get(i)).inputs[j] = normalizeNum(((Example)data.get(i)).inputs[j], mins.inputs[j], maxs.inputs[j]);
			}
			((Example)data.get(i)).target = normalizeNum(((Example)data.get(i)).target, mins.target, maxs.target);
		}
		return data;
	}
	public static double DenormalizeTarget(double p)
	{
		 return (p*((maxs.target)-(mins.target)))+mins.target;
	}
	public static double DenormalizeTarget(Example p)
	{
		 return (p.target*((maxs.target)-(mins.target)))+mins.target;
	}
	public static double normalizeNum(double x, double min , double max)
	{
		if(max -min == 0)
		{
			return 0;
		}else{
			return setPrecision.set8Precision(Math.abs((x-min)/(max-min)));
		}
	}
	private static double getMin(ArrayList<Example> data,int Col)
	{
		double min = ((Example)data.get(0)).inputs[Col];
		for(int i = 1;i<data.size();i++)
		{
			if(((Example)data.get(i)).inputs[Col] < min)
			{
				min = ((Example)data.get(i)).inputs[Col];
			}
		}
		return min;
	}	
	private static double getMax(ArrayList<Example> data, int Col)
	{
		double max = ((Example)data.get(0)).inputs[Col];
		for(int i = 1;i<data.size();i++)
		{
			if(((Example)data.get(i)).inputs[Col] > max)
			{
				max = ((Example)data.get(i)).inputs[Col];
			}
		}
		return max;
	}
	
	private static double getTargetMin(ArrayList<Example> data)
	{
		double min = ((Example)data.get(0)).target;
		for(int i = 1;i<data.size();i++)
		{
			if(((Example)data.get(i)).target < min)
			{
				min = ((Example)data.get(i)).target;
			}
		}
		return min;
	}	
	private static double getTargetMax(ArrayList<Example> data)
	{
		double max = ((Example)data.get(0)).target;
		for(int i = 1;i<data.size();i++)
		{
			if(((Example)data.get(i)).target > max)
			{
				max = ((Example)data.get(i)).target;
			}
		}
		return max;
	}
	public static void showMinMax()
	{
		Example max = new Example(numInputs);
		Example min = new Example(numInputs);
		
		for(int j = 0;j<numInputs;j++)
		{
			min.inputs[j] = getMin(DataList,j);
			max.inputs[j] = getMax(DataList,j);
		}
		max.target = getTargetMax(DataList);
		min.target = getTargetMin(DataList);
		System.out.println(max.toString());
		System.out.println(min.toString());
	}
//////////////////////////////////////////////////////////////////////////////////////
	
	public static ArrayList<Example> shuffle(long t)
	{
		ArrayList<Example> shuffled = new ArrayList<Example>();
		Random gen = new Random(t);
		while (DataList.size() > 0)
		{
			int eg = gen.nextInt(DataList.size());
			shuffled.add( DataList.get(eg) );
			DataList.remove(eg);
		}
		
		OriginalData = new ArrayList<Example>();
		for(int i = 0;i<shuffled.size();i++)
		{
			Example newex = new Example(shuffled.get(0).inputs.length);
			for(int j=0;j<newex.inputs.length;j++)
			{
				newex.inputs[j] = shuffled.get(i).inputs[j];
			}
			newex.target = shuffled.get(i).target;
			OriginalData.add(newex);
		}
		
		return shuffled;
	}
	
//////////////////////////////////////////////////////////////////////////////////////
	
	public static ArrayList<Example> getTrainingData()
	{
		int numTraining = (int)(DataList.size() * trainingFraction);
		
		ArrayList<Example> training = new ArrayList<Example>();
		
		for (int i=0; i<numTraining; i++)
			training.add( DataList.get(i) );

		return training;
	}
	
//////////////////////////////////////////////////////////////////////////////////////
	
	public static ArrayList<Example> getOriginalTrainingData()
	{
		int numTraining = (int)(OriginalData.size() * trainingFraction);
		
		ArrayList<Example> training = new ArrayList<Example>();
		
		for (int i=0; i<numTraining; i++)
			training.add( OriginalData.get(i) );
		
		return training;
	}

//////////////////////////////////////////////////////////////////////////////////////
	
	public static ArrayList<Example> getTestingData()
	{
		int numTraining = (int)(DataList.size() * trainingFraction);
		
		ArrayList<Example> testing = new ArrayList<Example>();
		
		for (int i=numTraining; i<DataList.size(); i++)
			testing.add( DataList.get(i) );
			
		return testing;
	}

//////////////////////////////////////////////////////////////////////////////////////
	
	public static ArrayList<Example> getOriginalTestingData()
	{
		int numTraining = (int)(OriginalData.size() * trainingFraction);
		
		ArrayList<Example> testing = new ArrayList<Example>();
		
		for (int i=numTraining; i<OriginalData.size(); i++)
			testing.add( OriginalData.get(i) );
		
		return testing;
	}
	
//////////////////////////////////////////////////////////////////////////////////////
	
	public static ArrayList<Example> getSelectedTrainingData(int[] selectionList)
	{
		int numTraining = (int)(DataList.size() * trainingFraction);
		
		ArrayList<Example> training = new ArrayList<Example>();
		
		for (int i=0; i<numTraining; i++)
		{
			Example t = new Example(selectionList.length);
			for(int j = 0;j<selectionList.length;j++)
			{
				//System.out.println(exampleList.get(i).toString());
				t.inputs[j] = DataList.get(i).inputs[selectionList[j]];
			}
			t.target = DataList.get(i).target;
			training.add(t);
		}
		return training;
	}

//////////////////////////////////////////////////////////////////////////////////////

	public static ArrayList<Example> getSelectedTestingData(int[] selectionList)
	{
		int numTraining = (int)(DataList.size() * trainingFraction);
		
		ArrayList<Example> training = new ArrayList<Example>();
		
		for (int i=numTraining; i<DataList.size(); i++)
		{
			Example t = new Example(selectionList.length);
			for(int j = 0;j<selectionList.length;j++)
			{
				t.inputs[j] = DataList.get(i).inputs[selectionList[j]];
			}
			t.target = DataList.get(i).target;
			//t.print();
			training.add(t);
		}
		
		return training;
	}

//////////////////////////////////////////////////////////////////////////////////////
	
	public static void printData()
	{
		for (int whichExample=0; whichExample<numExamples(); whichExample++)
		{
			Example thisExample = (Example)DataList.get(whichExample);
			thisExample.print();
		}
	}
	
	public static void printDataFirstRows(ArrayList<Example> Data)
	{
		for (int whichExample=0; whichExample<Data.size(); whichExample++)
		{

			Example thisExample = (Example)Data.get(whichExample);
			thisExample.print();
			if(whichExample == 5)
			{
				break;
			}
		}
	}

//////////////////////////////////////////////////////////////////////////////////////	

	public void addTargetNoise(double prob)
	{
		for (int whichExample=0; whichExample<DataList.size(); whichExample++)
		{
			//Retrieve the Example at index number 'whichExample'
			//
			Example ex = (Example)DataList.get(whichExample);
			
			if(Math.random() < prob)
			{
				//corrupt the data
				//
				if(ex.target==1) ex.target=0; else ex.target=1;
			}
		}
	}


//////////////////////////////////////////////////////////////////////////////////////	
	
	
}


