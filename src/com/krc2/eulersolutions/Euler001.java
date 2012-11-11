
package com.krc2.eulersolutions;

import com.krc2.eulerrunner.EulerSolution;

public class Euler001 extends EulerSolution
{

	public Euler001()
	{
		super(1);
	}

	@Override
	public String calculateAnswer()
	{
		long answer = 0;
		for (int i = 0; i < 1000; i++)
		{
			if (i % 3 == 0 || i % 5 == 0)
			{
				answer += i;
			}
		}
		
		return String.valueOf(answer);
	}

}
