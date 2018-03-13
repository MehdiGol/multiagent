package com.mgolzadeh.utils;

import java.io.*;

public class systemOut {

	public static boolean wantLog = true;
	static final PrintStream originalOut = System.out;
	public static FileOutputStream Outputfile;
	public static BufferedOutputStream buffer ;
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static void changeOut(String fname,boolean log) throws FileNotFoundException
	{
		wantLog = log;

	    Outputfile = new FileOutputStream("log/"+fname+".txt");
		buffer = new BufferedOutputStream(Outputfile);
		
		PrintStream dummyStream    = new PrintStream(new OutputStream(){
		    public void write(int b) {
		        //NO-OP
		    }
		});
		if(wantLog)
		{
			System.setOut(new PrintStream(buffer, true));
		}else{
			System.setOut(dummyStream);
		}

	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static void changeOut()
	{
		PrintStream dummyStream    = new PrintStream(new OutputStream(){
		    public void write(int b) {
		    }
		});
		System.setOut(dummyStream);
	}
	
	public static void changeBack()
	{
		System.setOut(originalOut);
	}
	
	public static void print()
	{		
		print("");
	}
	
	public static void print(String msg)
	{
		originalOut.print(msg);
	}
	
	public static void println()
	{
		println("");
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static void println(String msg)
	{
		originalOut.println(msg);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	public static void writeData(String filename,String content) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("log/"+filename+".txt"))) {
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
