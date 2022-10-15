package com.example.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonParser extends AsyncTask<String,String ,Void> {

    public List<Movies>moviedetails=new ArrayList<>();
    BufferedInputStream inputStream;
    JSONArray jsonArray;
    String result="";
    public ProgressDialog progressDialog;
    Activity activity;
    Context context;

    public JsonParser(Activity activity,Context context)
    {
        this.activity=activity;
        this.context=context;
        this.progressDialog=new ProgressDialog(this.context);
    }

    public void onPreExecute()
    {
        super.onPreExecute();
        progressDialog.dismiss();
        progressDialog.setMessage("Loading data.....");
        progressDialog.show();
        progressDialog.setOnCancelListener((dialogInterface -> {JsonParser.this.cancel(true);}));
    }

    protected Void doInBackground(String... strings)
    {
        HttpURLConnection httpURLConnection=null;
        try{
            URL url = new URL(Url.fetchdata);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            inputStream=new BufferedInputStream(httpURLConnection.getInputStream());
            result=readStream();
            result=result.substring(result.indexOf("(")+1,result.lastIndexOf(")"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private String readStream() throws IOException
    {
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder=new StringBuilder();
        String line;
        try
        {
            while((line=bufferedReader.readLine())!=null ){
                stringBuilder.append(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return stringBuilder.toString();
    }

    protected void onPostExecute(Void aVoid)
    {
        try
        {
            jsonArray =new JSONArray(result);
            if(jsonArray!=null)
            {
                for(int i=0;i<jsonArray.length();i++)
                {
                    Movies movies=new Movies();
                    movies.backdrop_path=Url.imagedata+jsonArray.getJSONObject(i).getString("backdrop_path");
                    movies.title=jsonArray.getJSONObject(i).getString("title");
                    movies.overview=jsonArray.getJSONObject(i).getString("overview");
                    moviedetails.add(movies);
                }
            }
            ListView listView;
            listView=(ListView)this.activity.findViewById(R.id.listview);
            CustomAdaptor adaptor= new CustomAdaptor(this.context,moviedetails);
            listView.setAdapter(adaptor);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


}
