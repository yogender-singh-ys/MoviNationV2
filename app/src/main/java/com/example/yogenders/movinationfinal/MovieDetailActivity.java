package com.example.yogenders.movinationfinal;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yogenders.movinationfinal.Fragments.MovieDetailFragment;
import com.example.yogenders.movinationfinal.Models.Movie;

public class MovieDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        Intent intent = getIntent();
        Movie item = intent.getParcelableExtra("movieDetail");

        Bundle bundle = new Bundle();
        bundle.putParcelable("movieDetail", item);

        MovieDetailFragment fragObj = new MovieDetailFragment();
        fragObj.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.movie_detail_container, fragObj).commit();

    }
}
