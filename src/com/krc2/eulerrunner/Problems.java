package com.krc2.eulerrunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.util.SparseArray;

public class Problems
{
	private static Problems instance;
	private SparseArray<EulerProblem> problemArray;

	private Problems()
	{
		this.problemArray = new SparseArray<EulerProblem>();
	}

	public void loadProblems(InputStream is)
	{
		String jsonString = readInputStream(is);
		try
		{
			JSONObject mainObject = new JSONObject(jsonString);
			JSONArray array = mainObject.getJSONArray("problems");
			for (int i = 0; i < array.length(); i++)
			{
				JSONObject object = array.getJSONObject(i);
				int problemNumber = object.getInt("number");
				String problemSummary = object.getString("summary");
				String problemDescriptionHtml = object.getString("description");
				EulerProblem problem = new EulerProblem(problemNumber, problemSummary, problemDescriptionHtml);
				Log.i("DELETEME", "Parsed problem: " + problem);
				problemArray.put(problemNumber, problem);
			}
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String readInputStream(InputStream is)
	{
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try
		{
			Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1)
			{
				writer.write(buffer, 0, n);
			}
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String jsonString = writer.toString();
		return jsonString;
	}

	public static Problems getInstance()
	{
		if (instance == null)
		{
			instance = new Problems();
		}
		return instance;
	}

	public String getProblemDescription(int problemNumber)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescriptionHtml(int problemNumber)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
