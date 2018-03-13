package com.mgolzadeh.featureselection;

import java.io.FileNotFoundException;

import net.sf.javaml.core.Dataset;


public class Featureselection  {
	//use voting for feature subset
	
	double[] fs;
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public void getBestFeatures(Dataset ds, int nof)
	{
		fs = new double[nof];
		double[] fs1 = featureRanking(ds);
		for(int i = 0;i<nof;i++)
		{
			System.out.println(fs1[i]);
		}
		
	}
	
	public double[] featureRanking(Dataset ds)
	{
		FeatureRanking fr = new FeatureRanking();
		return fr.RankFeatures(ds);
	}
	
	public double[] EnsembleRankingfs(Dataset ds)
	{
		EnsembleRanking er = new EnsembleRanking();
		return er.RankFeatures(ds);
	}
	
	public double[] featureScoring(Dataset ds) throws FileNotFoundException
	{
		ScoringFeatureSelection sfs = new ScoringFeatureSelection();
		return sfs.SelectFeatures(ds);
	}
}

