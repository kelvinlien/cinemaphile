package com.example.myapplication.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class getImageArrayFromURL extends AsyncTask<String, Integer, Bitmap[]>
{

    @Override
    protected Bitmap[] doInBackground(String... strings) {
        URL url = null;
        Bitmap[] bitray = new Bitmap[strings.length];
        for (int i = 0; i < strings.length; i++)
        {
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bmp = null;
            try {
                assert url != null;
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitray[i] = bmp;
        }
        return bitray;
    }
}
