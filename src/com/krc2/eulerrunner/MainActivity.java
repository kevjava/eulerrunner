package com.krc2.eulerrunner;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
	SharedPreferences sharedPrefs = null;
	private final String TAG = MainActivity.class.getSimpleName();

	class TimeTickerUpdater implements Runnable
	{
		private TextView view;
		private long time;

		public TimeTickerUpdater(TextView view, long time)
		{
			this.view = view;
			this.time = time;
		}

		@Override
		public void run()
		{
			view.setText("" + (time / 1000) + " seconds");
		}
	}

	class TimeTicker extends AsyncTask<Long, Void, Void>
	{
		TextView view;
		Activity activity;

		public TimeTicker(Activity activity, TextView view)
		{
			this.activity = activity;
			this.view = view;
		}

		@Override
		public Void doInBackground(Long... params)
		{
			long start = params[0];
			while (!isCancelled())
			{
				if (!isCancelled())
				{
					long time = System.currentTimeMillis() - start;
					activity.runOnUiThread(new TimeTickerUpdater(view, time));
				}
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					// I should probably do something here.
				}
			}
			return (Void) null;
		}
	}

	private TimeTicker timeTicker;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

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
		problemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				parent.dispatchSetActivated(false);

				currentProblemMap = (Map<String, Object>) parent.getItemAtPosition(position);

				TwoLineListItem listItem = (TwoLineListItem) view;
				listItem.setActivated(true);
				listItem.setSelected(true);

				setCurrentProblemData();
			}
		});

		AsyncTask<Void, Void, Void> problemLoader = new AsyncTask<Void, Void, Void>()
		{

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

				boolean rememberProblem = sharedPrefs.getBoolean("pref_key_remember_problem", true);
				if (rememberProblem)
				{
					Log.d(TAG, "Remembering problem.");
					int savedProblem = sharedPrefs.getInt("pref_key_saved_problem", 0);
					if (savedProblem >= 0)
					{
						Log.d(TAG, "Scrolling to saved problem " + savedProblem);
						problemsListView.smoothScrollToPosition(savedProblem);
						problemsListView.setSelection(savedProblem);
					}
				}
				else
				{
					Log.d(TAG, "Not remembering problem");
				}
			}
		};
		problemLoader.execute((Void[]) null);

		final Button runButton = (Button) findViewById(R.id.run_button);
		final Button debugButton = (Button) findViewById(R.id.debug_button);
		final Button stopButton = (Button) findViewById(R.id.stop_button);
		final TextView debugText = (TextView) findViewById(R.id.debug_text);
		final TextView timeTaken = (TextView) findViewById(R.id.time_taken);

		runButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (solution != null)
				{
					runButton.setEnabled(false);
					debugButton.setEnabled(false);
					stopButton.setEnabled(true);
					debugText.setText("");
					timeTaken.setText("Running...");
					problemsListView.setEnabled(false);
					timeTicker = new TimeTicker(MainActivity.this, timeTaken);
					timeTicker.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, System.currentTimeMillis());

					ProgressBar pBar = (ProgressBar) findViewById(R.id.answer_loading);
					pBar.setIndeterminate(true);
					pBar.setVisibility(View.VISIBLE);
					solution.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
				}
			}
		});

		debugButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (solution != null)
				{
					runButton.setEnabled(false);
					debugButton.setEnabled(false);
					stopButton.setEnabled(true);
					debugText.setText("");
					timeTaken.setText("Running...");
					problemsListView.setEnabled(false);
					timeTicker = new TimeTicker(MainActivity.this, timeTaken);
					timeTicker.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, System.currentTimeMillis());

					ProgressBar pBar = (ProgressBar) findViewById(R.id.answer_loading);
					pBar.setIndeterminate(true);
					pBar.setVisibility(View.VISIBLE);
					solution.setDebug(true);
					solution.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
				}
			}
		});

		stopButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				solution.cancel(true);
				stopButton.setEnabled(false);
				runButton.setEnabled(true);
				debugButton.setEnabled(true);
				timeTaken.setText("Canceled.");
				problemsListView.setEnabled(true);
				if (timeTicker != null)
				{
					timeTicker.cancel(true);
				}

				ProgressBar pBar = (ProgressBar) findViewById(R.id.answer_loading);
				pBar.setVisibility(View.GONE);

				String paddedNumber = String.valueOf(currentProblemMap.get(Problems.NUMBER));
				while (paddedNumber.length() < 3)
				{
					paddedNumber = "0" + paddedNumber;
				}

				Class<?> x = null;
				try
				{
					x = Class.forName("com.krc2.eulersolutions.Euler" + paddedNumber);
					solution = (EulerSolution) x.newInstance();
					solution.setContext(MainActivity.this);
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
					runButton.setEnabled(false);
					debugButton.setEnabled(false);
					debugText.setText("No solution code found for this problem.");
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
	protected void onPause()
	{
		Log.v(TAG, "Pausing.");
		ListView problemsListView = (ListView) findViewById(R.id.problems_list_view);
		int savedProblem = problemsListView.getSelectedItemPosition();

		SharedPreferences.Editor editor = sharedPrefs.edit();
		if (sharedPrefs.getBoolean("pref_key_remember_problem", true))
		{
			editor.putInt("pref_key_saved_problem", savedProblem);
			Log.w(TAG, "Saved problem " + savedProblem);
		}
		else
		{
			editor.remove("pref_key_saved_problem");
			Log.w(TAG, "Clearing saved problem preference.");
		}
		editor.commit();

		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.menu_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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

		String paddedNumber = String.valueOf(currentProblemMap.get(Problems.NUMBER));
		while (paddedNumber.length() < 3)
		{
			paddedNumber = "0" + paddedNumber;
		}

		Class<?> x = null;
		Button runButton = (Button) findViewById(R.id.run_button);
		Button debugButton = (Button) findViewById(R.id.debug_button);
		try
		{
			x = Class.forName("com.krc2.eulersolutions.Euler" + paddedNumber);
			solution = (EulerSolution) x.newInstance();
			solution.setContext(this);
			runButton.setEnabled(true);
			runButton.setText("Run");
			debugButton.setEnabled(true);
			debugButton.setText("Debug");
			debugText.setText("Press 'Debug' or 'Run' to execute solution code for this problem.");
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
			runButton.setEnabled(false);
			debugButton.setEnabled(false);
			debugText.setText("No solution code found for this problem.");
		}
	}

	public void setAnswer(String answer)
	{
		if (timeTicker != null)
		{
			timeTicker.cancel(true);
		}

		TextView answerView = (TextView) findViewById(R.id.answer);
		answerView.setText(answer);

		ProgressBar pBar = (ProgressBar) findViewById(R.id.answer_loading);
		pBar.setVisibility(View.GONE);

		Button runButton = (Button) findViewById(R.id.run_button);
		Button debugButton = (Button) findViewById(R.id.debug_button);
		Button stopButton = (Button) findViewById(R.id.stop_button);
		Class<?> x = null;

		String paddedNumber = String.valueOf(currentProblemMap.get(Problems.NUMBER));
		while (paddedNumber.length() < 3)
		{
			paddedNumber = "0" + paddedNumber;
		}

		TextView timeTaken = (TextView) findViewById(R.id.time_taken);
		long time = solution.getEndTime() - solution.getStartTime();
		String durationString = "" + time + " ms";
		timeTaken.setText(durationString);

		ListView problemsListView = (ListView) findViewById(R.id.problems_list_view);
		problemsListView.setEnabled(true);

		try
		{
			x = Class.forName("com.krc2.eulersolutions.Euler" + paddedNumber);
			solution = (EulerSolution) x.newInstance();
			solution.setContext(this);
			runButton.setEnabled(true);
			debugButton.setEnabled(true);
			stopButton.setEnabled(false);
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
			runButton.setEnabled(false);
			debugButton.setEnabled(false);
		}
	}

	public void addDebugText(long timestamp, String text)
	{
		Date d = new Date(timestamp);
		String date = DateFormat.getDateFormat(this).format(d);
		String time = DateFormat.getTimeFormat(this).format(d);
		String ms = (new SimpleDateFormat("ss.SSS")).format(d).toString();

		String dateString = date + " " + time + ":" + ms;

		TextView debugText = (TextView) findViewById(R.id.debug_text);
		String newDebugText = debugText.getText().toString();
		final int MAX_LEN = 10000;
		if (newDebugText.length() > MAX_LEN)
		{
			int pos = newDebugText.indexOf('\n', newDebugText.length() - MAX_LEN);
			newDebugText = newDebugText.substring(pos);
		}
		debugText.setText(newDebugText);
		debugText.append(dateString + ": " + text);

		ScrollView scrollView = (ScrollView) findViewById(R.id.debug_scrollview);
		scrollView.fullScroll(View.FOCUS_DOWN);
	}

	public void setProgressPercentage(int percentage)
	{
		ProgressBar pBar = (ProgressBar) findViewById(R.id.answer_loading);
		if (pBar.isIndeterminate())
		{
			pBar.setIndeterminate(false);
		}
		pBar.setProgress(percentage);
	}
}
