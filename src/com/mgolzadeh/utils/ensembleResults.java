package com.mgolzadeh.utils;

import com.mgolzadeh.predictors.Ensemble;
import jade.util.leap.Serializable;

public class ensembleResults implements Serializable{
	public static final long serialVersionUID = 1000070;
	public int typeof = 0;
	public ensembleProperties ensProb =null;
	public Ensemble ens = null;
	public double[] ErrRates = null;
	public double TestErr;
	
	public ensembleResults(int tyof,ensembleProperties enpr,Ensemble en, double[] err,double Terr)
	{
		this.typeof = tyof;
		this.ensProb = enpr;
		this.ens =en;
		this.ErrRates = err;
		this.TestErr = Terr;
	}

}
