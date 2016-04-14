package com.movile.next.seriestracker.remote.http;

import android.content.Context;

import com.movile.next.seriestracker.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.MessageFormat;

/**
 * Created by movile on 20/06/15.
 */
public class HttpConnectionEpisodeDetails {

    Context mEpisodeDetailsContext = null;

    public HttpConnectionEpisodeDetails (Context epDetailsContext) {

        mEpisodeDetailsContext = epDetailsContext;
    }

    public HttpURLConnection configureConnection () {

        HttpURLConnection connection = null;

        //"https://api-v2launch.trakt.tv/shows/breaking-bad/seasons/1/episodes/2?extended=full,images";

        String urlTemplate = mEpisodeDetailsContext.getString(R.string.api_url_base) +
                             mEpisodeDetailsContext.getString(R.string.api_url_episode);

        String url = MessageFormat.format(urlTemplate, "breaking-bad", "3", "7");

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setReadTimeout(mEpisodeDetailsContext.getResources().getInteger(R.integer.api_timeout_read));
            connection.setConnectTimeout(R.integer.api_timeout_connect);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("trakt-api-version", Integer.toString(R.string.api_version));
            connection.setRequestProperty("trakt-api-key", Integer.toString(R.string.api_key));

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
