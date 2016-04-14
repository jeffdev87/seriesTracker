package com.movile.next.seriestracker.loaders;

import android.content.Context;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.util.Log;

import com.movile.next.seriestracker.EpisodeDetailsActivity;
import com.movile.next.seriestracker.model.Episode;
import com.movile.next.seriestracker.model.converter.ModelConverter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import com.movile.next.seriestracker.remote.http.HttpConnectionEpisodeDetails;
import com.movile.next.seriestracker.view.EpisodeDetailsView;

/**
 * Created by william on 18/06/15.
 */
public class EpisodeDetailsLoaderManager extends AsyncTaskLoader<Episode>
        implements LoaderManager.LoaderCallbacks<Episode> {

    private Context mEpDetailsActivityContext = null;
    private EpisodeDetailsView mEpDetailsOnDataLoaded = null;

    public EpisodeDetailsLoaderManager (Context epDetailsActivityContext,
                                        EpisodeDetailsView epDetailsOnDataLoaded) {
        super(epDetailsActivityContext);

        mEpDetailsActivityContext = epDetailsActivityContext;
        mEpDetailsOnDataLoaded = epDetailsOnDataLoaded;
    }

    public Episode loadInBackground() {

        Episode episode = null;
        InputStreamReader reader = null;

        HttpConnectionEpisodeDetails httpConnectionEpDet = new
                HttpConnectionEpisodeDetails(mEpDetailsActivityContext);

        try {
            HttpURLConnection connection = httpConnectionEpDet.configureConnection();
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = connection.getInputStream();
                reader = new InputStreamReader(stream);
                episode = new ModelConverter().toEpisode(reader);
            }

        } catch (IOException e) {
            Log.e(EpisodeDetailsActivity.TAG, "Error loading remote content", e);
        }

        return episode;
    }

    public Loader<Episode> onCreateLoader(int id, Bundle bundle) {
        return new EpisodeDetailsLoaderManager(mEpDetailsActivityContext,
                                               mEpDetailsOnDataLoaded);
    }

    public void onLoadFinished(Loader<Episode> loader, Episode ep) {
        if ((mEpDetailsOnDataLoaded != null) && (ep != null))
            mEpDetailsOnDataLoaded.displayEpisode(ep);
        else
            System.out.println("Error");
    }

    public void onLoaderReset(Loader<Episode> loader) {
    }
}
