package com.movile.next.seriestracker.asynctasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.movile.next.seriestracker.EpisodeDetailsActivity;
import com.movile.next.seriestracker.ui.OnDataLoadedListener;

import java.io.IOException;
import java.net.URL;

/**
 * Created by movile on 20/06/15.
 */
public class RemoteImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    Context ctx = null;
    OnDataLoadedListener l = null;

    public RemoteImageAsyncTask(Context c, OnDataLoadedListener lm) {
        ctx = c;
        l = lm;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(new URL(url).openStream());
        } catch (IOException e) {
            Log.e(EpisodeDetailsActivity.TAG, "Error fetching image from " + url, e);
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null)
            l.onImageLoaded(bitmap);
        else
            Log.d(EpisodeDetailsActivity.TAG, "Error loading image into component.");
    }
}