package com.example.yogenders.movinationfinal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yogenders.movinationfinal.Adapters.VideoAdapter;
import com.example.yogenders.movinationfinal.Assets.Asset;
import com.example.yogenders.movinationfinal.Assets.VideoFetch;
import com.example.yogenders.movinationfinal.Models.Movie;
import com.example.yogenders.movinationfinal.Models.Video;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {

    private ArrayList<Video> mVideoData;
    private VideoAdapter videoAdapter;
    private ListView videoListview;
    private Movie item;

    public static ProgressBar progressBar;
    public static TextView no_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        no_video = (TextView) findViewById(R.id.error_msg_text);

        videoListview = (ListView) findViewById(R.id.video_container);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            item = extras.getParcelable("movieDetail");
        }

        progressBar.setVisibility(View.VISIBLE);

        // binding video data
        mVideoData = new ArrayList<>();
        videoAdapter = new VideoAdapter(this,R.layout.video_item,mVideoData);
        new VideoFetch(videoAdapter,mVideoData,videoListview,getBaseContext()).execute(Asset.getVideoFetchUrl(item.getMovieKeyId()));

        videoListview.setAdapter(videoAdapter);

        videoListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Video item = videoAdapter.getItem(position);
                String key = item.getKey();
                watchYoutubeVideo(key);
            }
        });


        videoListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                Video item = videoAdapter.getItem(pos);
                String key = item.getKey();
                shareTextUsingChooser(key);
                return true;
            }
        });

    }


    // intent handle for youtube
    public void watchYoutubeVideo(String id){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }
    }

    // share video using text
    public void shareTextUsingChooser(String id) {
        Intent sendIntent = new Intent();
        // Set the action to be performed i.e 'Send Data'
        sendIntent.setAction(Intent.ACTION_SEND);
        // Add the text to the intent
        sendIntent.putExtra(Intent.EXTRA_TEXT, "http://www.youtube.com/watch?v=" + id);
        // Set the type of data i.e 'text/plain'
        sendIntent.setType("text/plain");
        // Shows chooser (List of Apps) that can handle Text data; You have to choose one among them
        startActivity(Intent.createChooser(sendIntent,"Share Youtube Trailer"));
    }
}
