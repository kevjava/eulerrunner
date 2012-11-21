package com.krc2.eulersolutions;

import com.krc2.eulerrunner.EulerSolution;

public class Euler007 extends EulerSolution
{

	public Euler007()
	{
		super(7);
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

	public String calculateAnswer()
	{
		setProgressTotal(200);
		final int MAX = 120000;
		final int PRIME_TO_FIND = 10001;
		boolean[] sieve = populatePrimeSieve(MAX);
		setProgress(100);		
		int index = 0;
		int value = 0; 
		for (int i = 0; i < MAX; i++)
		{
			if (i % 1000 == 0)
			{
				setProgress(100 + i/(MAX/100));
			}

			if (sieve[i])
			{
				stopCheck();
				index++;
				value = i;
				if (index == PRIME_TO_FIND)
				{
					return String.valueOf(value);
				}
			}
		}
		
		return String.format("N/A.  Found %d primes.", index);
	}
	
}
