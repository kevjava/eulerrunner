package com.krc2.eulerrunner;

import android.content.Context;
import android.os.AsyncTask;

public abstract class EulerSolution extends AsyncTask<Void, Integer, String>
{
	private int problemNumber;
	private Context context;
	private boolean debug;
	
	class StoppedException extends RuntimeException{}

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
	private int progressTotal;

	public EulerSolution(int problemNumber)
	{
		this.problemNumber = problemNumber;
	}
	
	public void setContext(Context context)
	{
		this.context = context;
	}
	
	public void setDebug(boolean debug)
	{
		this.debug = debug;
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
	protected void onCancelled(String string)
	{
		// Do nothing.
	}
	
	@Override
	protected String doInBackground(Void... params)
	{
		log("Starting...");
		startTime = System.currentTimeMillis();
		try
		{
			answer = calculateAnswer();
			endTime = System.currentTimeMillis();
			log("Ended.");
		} 
		catch (StoppedException e) 
		{
			log("Cancelled.");			
			return "Cancelled.";
		}
		return answer;
	}
	
	protected void setProgress(int progress)
	{
		if (!debug)
			return;
		if (progressTotal > 0)
			publishProgress(progress);
	}
	
	protected void setProgressTotal(int progressTotal)
	{
		if (!debug)
			return;
		this.progressTotal = progressTotal;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values)
	{
		if (progressTotal > 0)
		{
			MainActivity activity = (MainActivity) context;
			activity.setProgressPercentage((100*values[0])/progressTotal);
		}
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
	
	class LogRunnable implements Runnable 
	{
		private Context context;
		private String message;
		private Object[] stuff;
		private long timestamp;

		public LogRunnable(Context context, long timestamp, String message, Object... stuff)
		{
			this.context = context;
			this.message = message;
			this.timestamp = timestamp;
			this.stuff = stuff;
		}
		
		public void run()
		{
			MainActivity activity = (MainActivity) context;
			activity.addDebugText(timestamp, String.format(message, stuff) + "\n");
		}
	}
	
	public void log(String message, Object... stuff)
	{
		if (this.debug)
		{   
			long timestamp = System.currentTimeMillis();
			MainActivity activity = (MainActivity) context;
			activity.runOnUiThread(new LogRunnable(context, timestamp, message, stuff));
		}
	}
	
	protected void stopCheck() throws StoppedException
	{
		if (isCancelled())
		{
			throw new StoppedException();
		}
	}
	
	protected void die()
	{
		throw new StoppedException();
	}
	
	protected void die(String s, Object... stuff)
	{
		log(s, stuff);
		die();
	}
}
