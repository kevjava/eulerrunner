package com.krc2.eulersolutions;

import com.krc2.eulerrunner.EulerSolution;
import java.math.BigInteger;

public class Euler016 extends EulerSolution
{
	public Euler016()
	{
		super(16);
	}
	
	public String calculateAnswer()
	{
		int EXPONENT = 1000;
		BigInteger num = new BigInteger("2").pow(EXPONENT);
		log("Number: %s (%d digits)", num.toString(), num.toString().length());
		
		long sum = 0L;
		while (num.compareTo(BigInteger.TEN) >= 0)
		{
			stopCheck();
			BigInteger digit = num.mod(BigInteger.TEN);
			sum += digit.longValue();
			num = num.divide(BigInteger.TEN);
//			log("sum: %d, num: %s", sum, num.toString());
		}
		sum += num.longValue();
		return String.valueOf(sum);
	}
}
