package com.krc2.eulersolutions;

import com.krc2.eulerrunner.EulerSolution;

public class Euler010 extends EulerSolution
{
	public Euler010()
	{
		super(10);
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
			if (i % 10000 == 0) setProgress( (int) ( (100*i) / Math.sqrt(num) ) );
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
		final int PRIMES_BELOW = 2000000; 
		setProgressTotal(200);
		
		boolean[] sieve = populatePrimeSieve(PRIMES_BELOW);
		
		long total = 0;
		for (int i = 0; i < sieve.length; i++)
		{
			if (i % 10000 == 0) setProgress(100 + (int) (100.*( (float)i / (float)PRIMES_BELOW ) ) );
			if (sieve[i])
			{
				total += i;
			}
			
			stopCheck();
		}
		
		return String.valueOf(total);
	}
}
