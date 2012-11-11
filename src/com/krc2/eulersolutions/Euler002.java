package com.krc2.eulersolutions;

import com.krc2.eulerrunner.EulerSolution;

public class Euler002 extends EulerSolution
{
	public Euler002()
	{
		super(2);
	}
	
	long FIB_MAX = 4000000L;

	@Override
	public String calculateAnswer()
	{
		long answer = 0;
		long lastFib = 1;
		long thisFib = 1;

		while (thisFib < FIB_MAX)
		{
			if (thisFib % 2 == 0)
			{
				answer += thisFib;
			}
			
			long temp = thisFib;
			thisFib = lastFib + thisFib;
			lastFib = temp;
		}

		return String.valueOf(answer);
	}

}
