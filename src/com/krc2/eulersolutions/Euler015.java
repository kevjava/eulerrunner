package com.krc2.eulersolutions;

import com.krc2.eulerrunner.EulerSolution;
import java.util.ArrayList;
import java.util.List;

public class Euler015 extends EulerSolution
{
	public Euler015() 
	{
		super(15);
	}
	
	public List<Long> getNextRow(List<Long> thisRow)
	{
		List<Long> nextRow = new ArrayList<Long>();
		nextRow.add(1L);
		for (int i = 0; i < thisRow.size() - 1; i++)
		{
			nextRow.add( thisRow.get(i) + thisRow.get(i+1) );
		}
		nextRow.add(1L);
		return nextRow;
	}

	/**
     * Use Pascal's Triangle to solve.
     */
	public String calculateAnswer()
	{
		int i = 1;
		List<Long> thisList = new ArrayList<Long>();
		thisList.add(1L);
		thisList.add(1L);
		
		while (i < 40)
		{
			stopCheck();
			i++;
			thisList = getNextRow(thisList);
			log("%d: %s.", i, thisList.toString());
		}
		return String.valueOf( thisList.get( thisList.size() / 2 ) );
	}
}
