package com.krc2.eulerrunner;

public abstract class EulerSolution
{
	private int problemNumber;

	public EulerSolution(int problemNumber)
	{
		this.problemNumber = problemNumber;
	}

	public String getDescriptionHtml()
	{
		return Problems.getInstance().getDescriptionHtml(problemNumber);
	}

	public String getProblemDescription()
	{
		return Problems.getInstance().getProblemDescription(problemNumber);
	}

	public int getProblemNumber()
	{
		return problemNumber;
	}
	
}
