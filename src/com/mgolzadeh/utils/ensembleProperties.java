package com.mgolzadeh.utils;

import jade.util.leap.Serializable;

public class ensembleProperties implements Serializable {
	public static final long serialVersionUID = 1000080;
	public int numberOfEnsembleEpochs = 100;
	public int numberOfMLPEpochs = 200;
	public int numberOfEnsembles = 8; 
	public int numberOfNeurons = 6;
	public double lambda = .9;
	
	public ensembleProperties(int noee,int nome,int noe,int non, double la)
	{
		this.numberOfEnsembleEpochs = noee;
		this.numberOfMLPEpochs = nome;
		this.numberOfEnsembles = noe;
		this.numberOfNeurons = non;
		this.lambda = la;
	}
}
