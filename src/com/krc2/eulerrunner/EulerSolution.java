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
		return Problems.getInstance().getProblem(problemNumber).get(Problems.DESCRIPTION).toString();
	}

	public String getProblemSummary()
	{
		return Problems.getInstance().getProblem(problemNumber).get(Problems.SUMMARY).toString();
	}

	public int getProblemNumber()
	{
		return problemNumber;
	}
	
}
