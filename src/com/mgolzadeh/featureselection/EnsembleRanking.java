package com.mgolzadeh.featureselection;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.featureselection.ensemble.LinearRankingEnsemble;
import net.sf.javaml.featureselection.ranking.RecursiveFeatureEliminationSVM;

public class EnsembleRanking {
	
	public EnsembleRanking()
	{
		
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////
	public double[] RankFeatures(final Dataset data)
	{
		
		System.out.println("ensemble ranking...");
        //ensemble ranking OK
	      RecursiveFeatureEliminationSVM[] svmrfes = new RecursiveFeatureEliminationSVM[10];
	      for (int i = 0; i < svmrfes.length; i++)
	          svmrfes[i] = new RecursiveFeatureEliminationSVM(0.2);
	      final LinearRankingEnsemble ensemble = new LinearRankingEnsemble(svmrfes);
	      /* Build the ensemble */
	      
	      ensemble.build(data);
			  
	      double[] ranks = new double[ensemble.noAttributes()];
	      /* Print out the rank of each attribute */
	      for (int i = 0; i < ensemble.noAttributes(); i++)
	      {
	    	  ranks[i] = ensemble.rank(i);
	          System.out.println(ensemble.rank(i));  
	      }
	      
	      return ranks;
	}
}
