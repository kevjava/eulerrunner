package com.krc2.eulersolutions;

import com.krc2.eulerrunner.EulerSolution;

public class Euler017 extends EulerSolution
{
	public Euler017()
	{
		super(17);
	}
	
	final String[] hundreds = {"", "one hundred ", "two hundred ", "three hundred ",
		"four hundred ", "five hundred ", "six hundred ", "seven hundred ", 
		"eight hundred ", "nine hundred "};
	final String[] tens = {"", "", "twenty ", "thirty ", "forty ", "fifty ", "sixty ", "seventy ", "eighty ", "ninety "};
	final String[] ones = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", 
		"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
	
	public String getWords(int i)
	{
		if (i > 1000)
		{
			throw new IllegalArgumentException("Number too big.");
		}
		else if (i == 1000)
		{
			return "one thousand";
		}

		String numberString = "";
		numberString = hundreds[i/100];
		if (i >= 100 && i % 100 != 0)
		{
			numberString += "and ";
		}
		
		if (i % 100 >= 20)
		{
			numberString += tens[ (i % 100) / 10 ];
			numberString += ones[ i % 10 ];
		}
		else
		{
			numberString += ones[ i % 100 ];
		}
		return numberString;
	}
	
	public int getLength(String string)
	{
		string = string.trim().replace(" ", "");
		return string.length();
	}
	
	public String calculateAnswer()
	{
		long total = 0;
		for (int i = 1; i <= 1000; i++)
		{
			String numberString = getWords(i);
			int len = getLength(numberString);
			total += len;
		}
		
		return String.valueOf(total);
	}
}
