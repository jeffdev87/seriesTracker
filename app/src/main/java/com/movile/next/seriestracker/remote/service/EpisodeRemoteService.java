package com.movile.next.seriestracker.remote.service;

import com.movile.next.seriestracker.definition.ApiConfiguration;
import com.movile.next.seriestracker.model.Episode;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by movile on 20/06/15.
 */

public interface EpisodeRemoteService {

    @Headers({
            "trakt-api-version: " + ApiConfiguration.API_VERSION,
            "trakt-api-key: " + ApiConfiguration.API_KEY
    })

    @GET("/shows/{show}/seasons/{season}/episodes/{episode}?extended=full,images")
    void getEpisodeDetails(
            @Path("show") String show,
            @Path("season") Long season,
            @Path("episode") Long episode,
            Callback<Episode> callback);
}