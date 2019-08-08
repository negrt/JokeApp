package com.example.labwebserviceandasynctask;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JokeViewModel extends AndroidViewModel { // extending from AndroidViewModel because going to use AsyncTask

    // Attribute

    private MutableLiveData<String> resultJSON;
    private MutableLiveData<String> setup;
    private MutableLiveData<String> punchLine;

    // Constructor

    public JokeViewModel(@NonNull Application application) {
        super(application);
        resultJSON = null;
    }

    // Get & Set for setup and punchLine


    // Live Data

    public LiveData<String> getResultJSON()
    {
        if(resultJSON == null)
        {
            resultJSON = new MutableLiveData<String>();
        }
        return resultJSON;
    }

    public LiveData<String> getSetup()
    {
        if(setup == null)
        {
            setup = new MutableLiveData<String>();
        }
        return setup;
    }

    public LiveData<String> getPunchLine()
    {
        if(punchLine == null)
        {
            punchLine = new MutableLiveData<String>();
        }
        return punchLine;
    }


    // AsyncTask

    public void loadData()
    {
        // Instead of creating a whole new class for AsyncTask you can created an instance of AsyncTask in code/a method
        // This code creates the class then executes it
        AsyncTask<String, Void, String> asyncTask = new AsyncTask <String, Void, String>()
        {
            @Override
            protected String doInBackground(String... strings)
            {
                String result = "";

                // Initialize HttpURLConnection Obj to null
                HttpURLConnection urlConnection = null;

                // Call the web service

                try
                {
                    // URL for random joke api
                    String urlString = "https://official-joke-api.appspot.com/random_joke";

                    // Construct URL object with url string passed as parameter
                    URL url = new URL(urlString);

                    // Open the url connection for HttpURLConnection Obj
                    urlConnection = (HttpURLConnection)url.openConnection();

                    // Get the JSON  from .getInputStream and add it to Input Stream obj
                    // returns a string in JSON format
                    InputStream inStream = urlConnection.getInputStream();

                    // Get data from input stream here…
                    // Create BufferedReader Obj Construct with InputStreamReader parameter
                    BufferedReader buffReader = new BufferedReader(new InputStreamReader(inStream));

                    // Create StringBuilder obj to help read JSON data
                    StringBuilder total = new StringBuilder();

                    // Append data from BufferedReader to StringBuilder
                    for (String line; (line = buffReader.readLine()) != null; )
                    {
                        total.append(line).append('\n');
                    }

                    // Set the result string to the data in total
                    result = total.toString();

                }
                catch (Exception e)
                {
                    // Print error if connection could not be made
                    e.printStackTrace();
                }
                finally
                {
                    // If the url connection was open disconnect it
                    if (urlConnection != null)
                    {
                        urlConnection.disconnect();
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result)
            {
                super.onPostExecute(result);

                // onPostExecute should just set the value of the view model's resultJSON
                resultJSON.setValue(result);

                try
                {
                    String joke;
                    String punchline;

                    // Create an instance and pass in the JSON string
                    JSONObject jsonObject = new JSONObject(result);
                    joke = jsonObject.getString("setup");

                    // Create an instance and pass in the JSON string
                    JSONObject jsonObjectPunchline = new JSONObject(result);
                    punchline = jsonObjectPunchline.getString("punchline");

                    setup.setValue(joke);
                    punchLine.setValue(punchline);


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }








            }
        };

        // Now execute it
        asyncTask.execute();

    }
}
