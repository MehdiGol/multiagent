package com.mgolzadeh.messages;

import java.io.Serializable;

import net.sf.javaml.core.Dataset;

public class DatasetSender implements Serializable{
	public static final long serialVersionUID = 10000500;
	public String name;
	public String address;
	public transient int SSN;
	private Dataset data;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public DatasetSender(String n,String a,final int ssn,Dataset dset) {
		this.name = n;
		this.address = a;
		this.SSN = ssn;
		this.data = dset;
	}
	
	public void setDataset(Dataset value)
	{
		this.data = value;
	}

	public Dataset getDataset()
	{
		return this.data;
	}
}
