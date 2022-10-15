package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class CustomAdaptor extends ArrayAdapter<Movies> {

    List<Movies>movielist;
    private Context context;

    public CustomAdaptor(Context context,List<Movies>movielist)
    {
        super(context,R.layout.singlemovie,movielist);
        this.context=context;
        this.movielist=movielist;
    }

    public View getView(int position, View convertview, ViewGroup parent)
    {
        LayoutInflater layoutInflater=(LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertview==null)
        {
            convertview=layoutInflater.inflate(R.layout.singlemovie,null);
        }
        TextView title=(TextView) convertview.findViewById(R.id.title_tv);
        TextView overview=(TextView) convertview.findViewById(R.id.overview_tv);
        ImageView imageView=(ImageView) convertview.findViewById(R.id.imageview);

        final Movies movies=movielist.get(position);
        title.setText(movies.getTitle());
        overview.setText(movies.getOverview());
        try{
            URL imageview =new URL(movies.getBackdrop_path());
            Glide.with(context).load(imageview).into(imageView);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return convertview;
    }

}
