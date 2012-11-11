package com.krc2.eulerrunner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.pm.PackageStats;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

public class MainActivity extends Activity
{

	private Problems problems = Problems.getInstance();
	EulerSolution solution = null;
	ListAdapter problemsListViewAdapter;
	private List<Map<String, Object>> problemList;
	private Map<String, Object> currentProblemMap = null;
	private String answer = null;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		problemList = new ArrayList<Map<String, Object>>();

		setContentView(R.layout.activity_main);

		final ListView problemsListView = (ListView) findViewById(R.id.problems_list_view);
		String[] from =
		{ Problems.NUMBER, Problems.SUMMARY };
		int[] to =
		{ android.R.id.text1, android.R.id.text2 };
		problemsListViewAdapter = new SimpleAdapter(this, problemList, android.R.layout.simple_list_item_activated_2, from, to);
		problemsListView.setAdapter(problemsListViewAdapter);
		problemsListView.setItemsCanFocus(true);
		problemsListView.setOnItemClickListener(new OnItemClickListener()
		{
			
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				parent.dispatchSetActivated(false);
				
				currentProblemMap = (Map<String, Object>) parent.getItemAtPosition(position);
				
				TwoLineListItem listItem = (TwoLineListItem) view;
				listItem.setActivated(true);
				
				setCurrentProblemData();
			}
		});
		
		AsyncTask<Void, Void, Void> problemLoader = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params)
			{
				loadProblems();
				return null;
			}
		
			@Override
			protected void onPostExecute(Void result)
			{
				problemList.clear();
				problemList.addAll(problems.getProblems());
				ProgressBar pBar = (ProgressBar) findViewById(R.id.problems_list_view_loading);
				problemsListView.requestLayout();
				pBar.setVisibility(View.GONE);
			}
		};
		problemLoader.execute((Void[])null);

		final Button runButton = (Button) findViewById(R.id.run_button);
		runButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (solution != null)
				{
					runButton.setEnabled(false);
					ProgressBar pBar = (ProgressBar) findViewById(R.id.answer_loading);
					pBar.setVisibility(View.VISIBLE);
					solution.execute((Void[])null);
				}
			}
		});
	}

	private void loadProblems()
	{
		InputStream is = getResources().openRawResource(R.raw.problems);
		problems.loadProblems(is);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void setCurrentProblemData() 
	{
		TextView problemNumberText = (TextView) findViewById(R.id.problem_number);
		problemNumberText.setText(String.valueOf(currentProblemMap.get(Problems.NUMBER)));
		
		WebView problemDescriptionWebView = (WebView) findViewById(R.id.problemDescriptionWebView);
		problemDescriptionWebView.loadData((String) currentProblemMap.get(Problems.DESCRIPTION), "text/html", null);
		
		TextView debugText = (TextView) findViewById(R.id.debug_text);
		debugText.setText("");
		
		TextView timeTaken = (TextView) findViewById(R.id.time_taken);
		timeTaken.setText("");
		
		TextView answer = (TextView) findViewById(R.id.answer);
		answer.setText("");

		String paddedNumber = String.valueOf( currentProblemMap.get(Problems.NUMBER) );
		while (paddedNumber.length() < 3)
		{
			paddedNumber = "0" + paddedNumber;
		}
		
		Class<?> x = null;
		Button button = (Button) findViewById(R.id.run_button);
		try
		{
			x = Class.forName("com.krc2.eulersolutions.Euler" + paddedNumber);
			solution = (EulerSolution) x.newInstance();
			solution.setContext(this);
			button.setEnabled(true);
		}
		catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			button.setEnabled(false);
		}
	}
	
	public void setAnswer(String answer)
	{
		this.answer  = answer;
		TextView answerView = (TextView) findViewById(R.id.answer);
		answerView.setText(answer);
		
		ProgressBar pBar = (ProgressBar) findViewById(R.id.answer_loading);
		pBar.setVisibility(View.GONE);
		
		Button button = (Button) findViewById(R.id.run_button);
		Class<?> x = null;

		String paddedNumber = String.valueOf( currentProblemMap.get(Problems.NUMBER) );
		while (paddedNumber.length() < 3)
		{
			paddedNumber = "0" + paddedNumber;
		}
		
		TextView timeTaken = (TextView) findViewById(R.id.time_taken);
		long time = solution.getEndTime() - solution.getStartTime();
		String durationString = "" + time + " ms";
		timeTaken.setText(durationString);
		
		try
		{
			x = Class.forName("com.krc2.eulersolutions.Euler" + paddedNumber);
			solution = (EulerSolution) x.newInstance();
			solution.setContext(this);
			button.setEnabled(true);
		}
		catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			button.setEnabled(false);
		}
	}
}
