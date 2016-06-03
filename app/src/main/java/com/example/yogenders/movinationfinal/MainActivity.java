package com.example.yogenders.movinationfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.yogenders.movinationfinal.Fragments.MovieDetailFragment;
import com.example.yogenders.movinationfinal.Fragments.MovieFragment;
import com.example.yogenders.movinationfinal.Models.Movie;

public class MainActivity extends AppCompatActivity implements MovieFragment.fragmentCommunicatorInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Attaching fragment in MainActivity view
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieFragment())
                    .commit();

        }

    }

    // method decided to update data in multipane activity OR to start new activity
    @Override
    public void onItemClicked(Movie gridItem) {

        FrameLayout movieContainer = (FrameLayout)findViewById(R.id.movie_container);

        if(movieContainer != null)
        {
            Bundle bundle = new Bundle();
            bundle.putParcelable("movieDetail", gridItem);

            MovieDetailFragment fragObj = new MovieDetailFragment();
            fragObj.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.movie_container, fragObj).commit();

        }
        else
        {
            Intent intent = new Intent(getBaseContext(), MovieDetailActivity.class);
            intent.putExtra("movieDetail", gridItem);

            startActivity(intent);
        }


    }
}
