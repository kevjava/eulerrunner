package com.krc2.eulersolutions;

import android.util.SparseArray;
import com.krc2.eulerrunner.EulerSolution;
import java.util.ArrayList;
import java.util.List;

public class Euler012 extends EulerSolution
{
	public Euler012()
	{
		super(12);
	}
	
	boolean[] sieve = null;
	List<Integer> primes = null;

	/*
	 * Divisor function
     * 28
	 * 2^2 * 7^1 <- Prime factorization
	 *   3 *   2 = 6 <-- Add one to each exponent, multiply together 
	 * 1, 2, 4, 7, 14, 28 <-- Divisors
	 */
	private int getFactors(final int num)
	{
		SparseArray<Integer> factors = new SparseArray<Integer>();
		int x = num;
		while (x > 1)
		{
			int factor = getFirstPrimeFactor(x);
			factors.put(factor, factors.get(factor, 0) + 1);
			x /= factor;
		}
		
		int divisors = 1;
		for (int i = 0; i < factors.size(); i++)
		{
			divisors *= (factors.get( factors.keyAt(i) ) + 1);
		}
		return divisors;
	}

	private int getFirstPrimeFactor(int num)
	{
		for (int x : primes)
		{
			if (num % x == 0)
			{
				return x;
			}
		}
		return 0;
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
	
	private List<Integer> populatePrimeList()
	{
		primes = new ArrayList<Integer>();
		for (int i = 0; i < sieve.length; i++)
		{
			if (sieve[i])
			{
				primes.add(i);
			}
		}
		return primes;
	}

	public String calculateAnswer()
	{
		final int MIN_FACTORS = 500;
		final int SIEVE_SIZE = 2000000;
		sieve = populatePrimeSieve(SIEVE_SIZE);
		primes = populatePrimeList();

		int i = 1;
		int triangle = i;
		while (true)
		{
			stopCheck();
			
			int divisors = getFactors(triangle);
			
			if (divisors >= MIN_FACTORS)
			{
				log("Number %d has %d factors.", triangle, divisors);
				return String.valueOf(triangle);
			}
			
			i += 1;
			triangle += i;
		}
	}
}
