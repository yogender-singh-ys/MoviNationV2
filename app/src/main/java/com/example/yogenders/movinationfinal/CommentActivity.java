package com.example.yogenders.movinationfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yogenders.movinationfinal.Adapters.CommentAdapter;
import com.example.yogenders.movinationfinal.Assets.Asset;
import com.example.yogenders.movinationfinal.Assets.CommentFetch;
import com.example.yogenders.movinationfinal.Models.Comment;
import com.example.yogenders.movinationfinal.Models.Movie;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    private ArrayList<Comment> mCommentData;
    private CommentAdapter commentAdapter;
    private ListView commentListView;
    Movie item;

    public static ProgressBar progressBar;
    public static TextView no_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        commentListView = (ListView) findViewById(R.id.comment_container);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        no_comment = (TextView) findViewById(R.id.error_msg_text);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            item = extras.getParcelable("movieDetail");
        }

        progressBar.setVisibility(View.VISIBLE);

        mCommentData = new ArrayList<Comment>();
        commentAdapter = new CommentAdapter(this,R.layout.comment_item,mCommentData);
        commentListView.setAdapter(commentAdapter);

        new CommentFetch(commentAdapter,mCommentData,commentListView,getBaseContext()).execute(Asset.getCommentFetchUrl(item.getMovieKeyId()));
    }
}
