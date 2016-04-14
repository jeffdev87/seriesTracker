package com.movile.next.seriestracker.asynctasks;

import com.movile.next.seriestracker.business.FetchLocalEpisodeDetails;
import com.movile.next.seriestracker.models.Episode;
import com.movile.next.seriestracker.ui.OnDataLoadedListener;

import android.content.Context;
import android.os.AsyncTask;

public class LoadEpisodeDataAsyncTask extends AsyncTask<Void, Void, Episode> {

    Context mContext;
    OnDataLoadedListener mEpisodeDetails;

    public LoadEpisodeDataAsyncTask(Context ctx, OnDataLoadedListener dl) {
        mContext = ctx;
        mEpisodeDetails = dl;
    }

    protected Episode doInBackground(Void... x) {
        Episode ep = null;

        FetchLocalEpisodeDetails fed = new FetchLocalEpisodeDetails();
        ep = fed.get(mContext);

        return ep;
    }

    protected void onPostExecute(Episode ep) {
        if ((mEpisodeDetails != null) && (ep != null))
            mEpisodeDetails.onEpisodeLoaded(ep);
        else
            System.out.println("Error");
    }
}