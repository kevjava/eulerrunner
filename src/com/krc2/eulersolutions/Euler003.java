package com.krc2.eulersolutions;

import java.util.ArrayList;
import java.util.List;

import com.krc2.eulerrunner.EulerSolution;

public class Euler003 extends EulerSolution
{
	public Euler003()
	{
		super(3);
	}
	
	@Override
	public String calculateAnswer()
	{
		long answer = 0;
		
		final long num = 600851475143L;
		List<Long> factors = getPrimeFactors(num);
		
		answer = factors.get(factors.size()-1);
		return String.valueOf(answer);
	}

	private List<Long> getPrimeFactors(final long num)
	{
		List<Long> factors = new ArrayList<Long>();
		boolean [] sieve = populatePrimeSieve( (int) (Math.ceil(Math.sqrt(num))) );
		log("Done populating prime sieve.");
		
		long numLeft = num;
		while (numLeft > 1)
		{
			stopCheck();
			long factor = getFirstPrimeFactor(sieve, numLeft);
			numLeft /= factor;
			factors.add(factor);
		}
		
		log("Factors: %s", factors.toString());
		return factors;
	}
	
	private boolean[] populatePrimeSieve(final int num)
	{
		boolean[] sieve = new boolean[num+1];
		for (int i = 0; i < num; i++)
		{
			sieve[i] = true;
		}
		sieve[0] = false;
		sieve[1] = false;
		
		for (int i = 2; i < Math.sqrt(num); i++)
		{
			for (int j = 2; j <= num/i; j++)
			{
				sieve[i*j] = false;
			}
			stopCheck();
		}
		
		return sieve;
	}
	
	private long getFirstPrimeFactor(final boolean[] sieve, final long num)
	{
		for (int i = 2; i < sieve.length; i++)
		{
			if (sieve[i] && num % i == 0)
			{
				return i;
			}
		}
		
		return num;
	}
}
