package com.mgolzadeh.utils;

import jade.util.leap.Serializable;

import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.rbf.RBFNetwork;

import com.mgolzadeh.predictors.Ensemble;
import com.mgolzadeh.predictors.MLP;

public class modelResults implements Serializable{
	public static final long serialVersionUID = 1000060;
	public Ensemble ensNCL = null;
	public Ensemble ensBAG = null;
	public RBFNetwork net1 = null;
	public BasicNetwork net2 = null;
	
	public modelResults(Ensemble en,Ensemble eb, RBFNetwork rbf, BasicNetwork ff) {
		this.ensNCL = en;
		this.ensBAG = eb;
		this.net1 = rbf;
		this.net2 = ff;
	}
}
