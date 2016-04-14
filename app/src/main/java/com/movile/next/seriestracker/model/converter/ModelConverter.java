package com.movile.next.seriestracker.model.converter;

import com.google.gson.Gson;
import com.movile.next.seriestracker.model.Episode;
import com.movile.next.seriestracker.model.Season;
import com.movile.next.seriestracker.model.Show;

import java.io.Reader;

public class ModelConverter {

    private Gson mGson;

    public ModelConverter() {
        mGson = new Gson();
    }

    public Episode toEpisode(Reader reader) {
        return mGson.fromJson(reader, Episode.class);
    }

    public Show toShow(Reader reader) {
        return null;
    }

    public Season toSeason(Reader reader) {
        return null;
    }
}
