package com.mgolzadeh.messages;

import java.io.Serializable;
import java.util.ArrayList;
import com.mgolzadeh.data.Example;

public class ENStoMBA implements Serializable{
	public static final long serialVersionUID=1000050;
	public String name;
	public String address;
	public transient int SSN;
	private ArrayList<Example> Datasetsel;
	private ArrayList<Example> Datasetall;
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public ENStoMBA(String n,String a,final int ssn,ArrayList<Example> sel,ArrayList<Example> all) {
		this.name = n;
		this.address = a;
		this.SSN = ssn;
		this.Datasetsel = sel;
		this.Datasetall = all;
	}
	
	public void setDatasetSel(ArrayList<Example> value)
	{
		this.Datasetsel = value;
	}
	public ArrayList<Example> getDatasetSel()
	{
		return this.Datasetsel;
	}
	
	public void setDatasetAll(ArrayList<Example> value)
	{
		this.Datasetall = value;
	}
	public ArrayList<Example> getDatasetAll()
	{
		return this.Datasetall;
	}
	
}
