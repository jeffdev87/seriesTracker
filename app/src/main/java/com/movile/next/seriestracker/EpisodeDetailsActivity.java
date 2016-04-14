package com.movile.next.seriestracker;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.movile.next.seriestracker.asynctasks.LoadEpisodeDataAsyncTask;
import com.movile.next.seriestracker.asynctasks.RemoteImageAsyncTask;
import com.movile.next.seriestracker.loaders.EpisodeDetailsLoaderManager;
import com.movile.next.seriestracker.model.Episode;
import com.movile.next.seriestracker.model.Images;
import com.movile.next.seriestracker.presenter.EpisodeDetailsPresenter;
import com.movile.next.seriestracker.utils.FormatUtil;
import com.movile.next.seriestracker.view.EpisodeDetailsView;

public class EpisodeDetailsActivity extends ActionBarActivity implements EpisodeDetailsView {

    public static final String TAG = "SeriesTrackerApp";

    EpisodeDetailsPresenter mEpisodeDetailsPresenter = null;

    String show = "breaking-bad",
           season = "1",
           episode = "7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEpisodeDetailsPresenter = new EpisodeDetailsPresenter(this);

        setContentView(R.layout.episode_details_activity);

        getEpisodeDataRetrovit();
    }

    private void getEpisodeDataLoaderCallback() {
        //getLoaderManager().initLoader(0, null, new EpisodeDetailsLoaderManager(this, this)).forceLoad();
    }

    private void getEpisodeDataRetrovit() {
        mEpisodeDetailsPresenter.getEpisodeDetailsRetrofit(show, Long.getLong(season), Long.getLong(episode));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.episode_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayEpisode(Episode ep) {

        TextView episodeTitle = (TextView) findViewById(R.id.episode_details_title);
        TextView episodeDate = (TextView) findViewById(R.id.episode_details_time_text);
        TextView episodeSummary = (TextView) findViewById(R.id.episode_details_summary_main_text);

        episodeTitle.setText(ep.title());
        episodeDate.setText(FormatUtil.formatDate(FormatUtil.formatDate(ep.firstAired())));
        episodeSummary.setText(ep.overview());

        String imageUrl = ep.images().screenshot().get(Images.ImageSize.THUMB);

        displayImage(imageUrl);
    }

    public void displayImage (String imageUrl) {

        ImageView episodeDetailsMainImageView = (ImageView)
                findViewById(R.id.episode_details_main_image_overlay);

        Glide
            .with(this)
            .load(imageUrl)
                .placeholder(R.drawable.season_details_show_placeholder)
            .centerCrop()
            .into(episodeDetailsMainImageView);
    }
}
