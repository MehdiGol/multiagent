package com.mgolzadeh.featureselection;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.featureselection.ranking.RecursiveFeatureEliminationSVM;

public class FeatureRanking {
	public FeatureRanking()
	{
		
	}
	public double[] RankFeatures(Dataset data)
	{
		double[] ranks=null;
         //ranking OK
        RecursiveFeatureEliminationSVM svmrfe = new RecursiveFeatureEliminationSVM(0.2);
        /* Apply the algorithm to the data set */
        System.out.println("feature ranking started...");
        try
        {
        	svmrfe.build(data);
	        ranks = new double[svmrfe.noAttributes()];
	        for (int i = 0; i < svmrfe.noAttributes(); i++)
	        {
	        	ranks[i] = svmrfe.rank(i);
	            System.out.println(svmrfe.rank(i));
	        }
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        return ranks;
	}
}
