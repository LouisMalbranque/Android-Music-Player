package com.example.louis.musicplayertest.AsyncTasks;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.louis.musicplayertest.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAlbumImage extends AsyncTask<Object, Void, Bitmap> {

    ImageView bmImage;
    Bitmap final_img;

    protected Bitmap doInBackground(Object... params) {

        bmImage = (ImageView)params[2];
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            String artist = (String)params[0];
            String title  = (String)params[1];
            Log.d("URL Info:","http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=3f4e994433a83e0430651c89e567e0d0&artist="+artist+"&track="+title+"&format=json&autocorrect=1");
            URL url = new URL("http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=3f4e994433a83e0430651c89e567e0d0&artist="+artist+"&track="+title+"&format=json&autocorrect=1");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d("Response: ", "> " + line);
            }
             JSONObject songData = new JSONObject(buffer.toString().substring(buffer.indexOf("{"),buffer.lastIndexOf("}") + 1));
             Log.d("Données recupérées", songData.toString());
             String img_url = ((songData.getJSONObject("track")).getJSONObject("album")).getJSONArray("image").getJSONObject(3).getString("#text");
             Log.d("URL img",img_url);
             if(img_url!="")
             {
                 Bitmap img = getTrackImage((String)img_url);
                 return img;
             }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    protected Bitmap getTrackImage(String url) {
        String urldisplay = url;
        Bitmap bmp = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bmp;
    }


    protected void onPostExecute(Bitmap result) {

        if(result!=null)
        {
            bmImage.setImageBitmap(result);
        }
    }

}

