package com.krc2.eulerrunner;

public class EulerProblem
{
	private int problemNumber;
	private String problemSummary;
	private String problemDescriptionHtml;

	public EulerProblem(int problemNumber, String problemSummary, String problemDescriptionHtml)
	{
		this.problemNumber = problemNumber;
		this.problemSummary = problemSummary;
		this.problemDescriptionHtml = problemDescriptionHtml;
	}

	public int getProblemNumber()
	{
		return problemNumber;
	}

	public void setProblemNumber(int problemNumber)
	{
		this.problemNumber = problemNumber;
	}

	public String getProblemSummary()
	{
		return problemSummary;
	}

	public void setProblemSummary(String problemSummary)
	{
		this.problemSummary = problemSummary;
	}

	public String getProblemDescriptionHtml()
	{
		return problemDescriptionHtml;
	}

	public void setProblemDescriptionHtml(String problemDescriptionHtml)
	{
		this.problemDescriptionHtml = problemDescriptionHtml;
	}
	
	@Override
	public String toString()
	{
		return String.format("Problem %d: %s\n%s", problemNumber, problemSummary, problemDescriptionHtml);
	}
}
