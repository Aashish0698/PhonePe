package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try
        {
            jsonparser =(JsonParser) new JsonParser(this,this).execute();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}