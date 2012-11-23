package com.krc2.eulersolutions;

import com.krc2.eulerrunner.EulerSolution;
import java.util.ArrayList;

public class Euler014 extends EulerSolution
{
	private final int MAX_VALUE = 1000000;
	private int[] cache = new int[MAX_VALUE+1];
	
	public Euler014()
	{
		super(14);
	}
	
	public int getStepsToEnd(final int num)
	{
		long i = num;
		int steps = 1;
		while (i > 1)
		{
			i = (i % 2 == 0) ? (i/2) : (3*i+1);
			if (i < MAX_VALUE && cache[(int)i] > 0)
			{
				cache[num] = steps + cache[(int)i];
				return steps + cache[(int)i];
			}
			steps++;
		}
		cache[num] = steps;
		return steps;
	}
	
	public String calculateAnswer()
	{
		setProgressTotal(100);
		int maxSteps = 0;
		int maxI = 0;
		for (int i = 2; i < MAX_VALUE; i++)
		{
			stopCheck();
			if (i % (MAX_VALUE/100) == 0)
			{
				setProgress(i/(MAX_VALUE/100));
			}

			int steps = getStepsToEnd(i);
			if (steps > maxSteps) 
			{
				maxSteps = steps;
				maxI = i;
				log("%d took %d steps.", maxI, maxSteps);
			}
		}
		return String.valueOf(maxI);
	}
}
