package com.krc2.eulerrunner;

import java.io.InputStream;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

public class MainActivity extends Activity
{

	private Problems problems = Problems.getInstance();
	private Map<String, Object> currentProblemMap = null;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		loadProblems();

		setContentView(R.layout.activity_main);

		ListView problemsListView = (ListView) findViewById(R.id.problems_list_view);
		String[] from =
		{ Problems.NUMBER, Problems.SUMMARY };
		int[] to =
		{ android.R.id.text1, android.R.id.text2 };
		ListAdapter problemsListViewAdapter = new SimpleAdapter(this, problems.getProblems(), android.R.layout.simple_list_item_activated_2, from, to);
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
	}
}
