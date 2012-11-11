package com.krc2.eulerrunner;

import android.content.Context;
import android.os.AsyncTask;

public abstract class EulerSolution extends AsyncTask<Void, Integer, String>
{
	private int problemNumber;
	private Context context;

	public long getStartTime()
	{
		return startTime;
	}

	public long getEndTime()
	{
		return endTime;
	}

	private long startTime;
	private long endTime;
	private String answer;

	public EulerSolution(int problemNumber)
	{
		this.problemNumber = problemNumber;
	}
	
	public void setContext(Context context)
	{
		this.context = context;
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

	public abstract String calculateAnswer();
	
	@Override
	protected String doInBackground(Void... params)
	{
		startTime = System.currentTimeMillis();
		answer = calculateAnswer();
		endTime = System.currentTimeMillis();
		return answer;
	}
	
	protected void setProgress(int progress)
	{
		// TODO
	}
	
	protected void setProgressTotal(int progressTotal)
	{
		// TODO
	}
	
	@Override
	protected void onProgressUpdate(Integer... values)
	{
		super.onProgressUpdate(values);
		// TODO
	}

	protected void onPostExecute(String result)
	{
		if (context != null)
		{
			MainActivity activity = (MainActivity) context;
			activity.setAnswer(result);
		}
	}

	public String getAnswer()
	{
		// TODO Auto-generated method stub
		return answer;
	}
}
