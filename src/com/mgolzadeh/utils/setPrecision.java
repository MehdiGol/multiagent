package com.mgolzadeh.utils;

import java.math.BigDecimal;

public class setPrecision {

	///////////////////////////////////////////////////////////////////////////////////////////
	public static double set8Precision(double x)
	{
		return new BigDecimal(x).setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static double set6Precision(double x)
	{
		return new BigDecimal(x).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static double set5Precision(double x)
	{
		return new BigDecimal(x).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static double set4Precision(double x)
	{
		return new BigDecimal(x).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static double set3Precision(double x)
	{
		return new BigDecimal(x).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static double set2Precision(double x)
	{
		return new BigDecimal(x).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static double set1Precision(double x)
	{
		return new BigDecimal(x).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
