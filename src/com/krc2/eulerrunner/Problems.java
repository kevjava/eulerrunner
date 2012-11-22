package com.krc2.eulerrunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Problems
{
	public static final String DESCRIPTION = "description";
	public static final String SUMMARY = "summary";
	public static final String NUMBER = "number";
	public static final String PROBLEMS = "problems";
	
	private static Problems instance;
	private List<Map<String, Object>> problemList;

	private Problems()
	{
		this.problemList = new ArrayList<Map<String, Object>>();
	}

	public void loadProblems(InputStream is)
	{
		String jsonString = readInputStream(is);
		try
		{
			JSONObject mainObject = new JSONObject(jsonString);
			JSONArray array = mainObject.getJSONArray(PROBLEMS);
			for (int i = 0; i < array.length(); i++)
			{
				JSONObject object = array.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>(); 
				map.put(NUMBER, object.getInt(NUMBER));
				map.put(SUMMARY, object.get(SUMMARY));
				map.put(DESCRIPTION, object.get(DESCRIPTION));

				problemList.add(map);
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
	
	public Map<String, Object> getProblem(final int problemNumber)
	{
		for (Map<String, Object> problem : problemList)
		{
			if ((Integer)problem.get(NUMBER) == problemNumber)
			{
				return problem;
			}
		}
		return null;
	}
	
	public int getIndexOfProblem(Map<String, Object> problemToFind)
	{
		for (int i = 0; i < problemList.size(); i++)
		{
			Map<String, Object> problem = problemList.get(i);
			if ((Integer)problem.get(NUMBER) == problemToFind.get(NUMBER))
			{
				return i;
			}
		}
		return 0;
	}
	
	public List<Map<String, Object>> getProblems()
	{
		return problemList;
	}
}
