package com.krc2.eulersolutions;

import com.krc2.eulerrunner.EulerSolution;

public class Euler004 extends EulerSolution
{
	public Euler004()
	{
		super(4);
	}
	
	private boolean isPalindrome(int number)
	{
		String sNumber = String.valueOf(number);
		String sReverse = "";
		for (int i = sNumber.length() - 1; i >= 0; i--)
		{
			sReverse += sNumber.charAt(i);
		}
		return sReverse.equals(sNumber);
	}

	public String calculateAnswer()
	{
		int answer = 0;
		setProgressTotal(500);
		for (int left = 999; left > 500; left--)
		{
			setProgress(500-(left-500));
			for (int right = 999; right > 500; right--)
			{
				int product = left * right;
				if (product > answer && isPalindrome(left*right)) 
				{
					answer = product;
					log("New best - %d.", answer);
				}
			}
		}
		return String.valueOf(answer);
	}
}
