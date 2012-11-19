package com.krc2.eulersolutions;

import com.krc2.eulerrunner.EulerSolution;

public class Euler005 extends EulerSolution
{
	public Euler005()
	{
		super(5);
	}
	
	private boolean checkMods(int number)
	{
		for (int i = 20; i > 1; i--)
		{
			if (number % i != 0)
			{
				return false;
			}
		}
		return true;
	}
	
	public String calculateAnswer()
	{
		int answer = 20;
		while (1 == 1) 
		{
			stopCheck();
			
			if (checkMods(answer))
			{
				return String.valueOf(answer);
			}
			
			if (answer % 1000000 == 0) log("%d", answer);
			answer += 20;
		}
	}
}
