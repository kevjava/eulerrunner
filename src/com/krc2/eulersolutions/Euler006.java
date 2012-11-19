package com.krc2.eulersolutions;

import com.krc2.eulerrunner.EulerSolution;

public class Euler006 extends EulerSolution
{
	public Euler006()
	{
		super(6);
	}

	private long getSumOfSquares(final int max)
	{
		int sumOfSquares = 0;
		for (int i = 1; i <= max; i++)
		{
			setProgress(i);
			sumOfSquares += (i*i);
		}
		log("Sum of squares: %d", sumOfSquares);
		return sumOfSquares;
	}
	
	private long getSquareOfSums(final int max)
	{
	    long sum = 0;
		for (int i = 1; i <= max; i++)
		{
			setProgress(max+i);
			sum += i;
		}
		log("Sums: %d", sum);
		long squareOfSums = sum*sum;
		log("Square of sums: %d", squareOfSums);
		return squareOfSums;
	}
	
	public String calculateAnswer()
	{
		final int MAX = 100;
		setProgressTotal(2*MAX);
		long sumOfSquares = getSumOfSquares(MAX);
		long squareOfSums = getSquareOfSums(MAX);
		return String.valueOf(Math.abs(squareOfSums - sumOfSquares));
	}
}
