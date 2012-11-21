package com.krc2.eulersolutions;

import com.krc2.eulerrunner.EulerSolution;

public class Euler009 extends EulerSolution
{
	public Euler009()
	{
		super(9);
	}

	class Triplet
	{
		public int a;
		public int b;
		public int c;

		public boolean sumsTo1000()
		{
			return a + b + c == 1000;
		}
		
		public boolean isPythagorean()
		{
			return a*a + b*b == c*c;
		}
		
		public int getProduct()
		{
			return a * b * c;
		}
	}

	public String calculateAnswer()
	{
		setProgressTotal(1000);
		
		Triplet t = new Triplet();
		for (int a = 0; a < 1000; a++)
		{
			setProgress(a);
			t.a = a;
			for (int b = a + 1; a + b < 1000; b++)
			{
				t.b = b;
				for (int c = b + 1; a + b + c <= 1000; c++)
				{
					stopCheck();

					t.c = c;
					if (t.sumsTo1000() && t.isPythagorean())
					{
						log("Found triplet: %d^2 + %d^2 = %d", t.a, t.b, t.c);						
						return String.valueOf(t.getProduct());
					}
				}
			}
		}

		log("Couldn't find one.");
		return "N/A";
	}
}
