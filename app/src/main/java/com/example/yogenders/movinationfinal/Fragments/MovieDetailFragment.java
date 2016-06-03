package com.example.yogenders.movinationfinal.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogenders.movinationfinal.Assets.Asset;
import com.example.yogenders.movinationfinal.CommentActivity;
import com.example.yogenders.movinationfinal.Models.Movie;
import com.example.yogenders.movinationfinal.R;
import com.example.yogenders.movinationfinal.VideoActivity;
import com.squareup.picasso.Picasso;


public class MovieDetailFragment extends Fragment {

    TextView title, overview, releaseDate, voteCount, average, popularCount;
    ImageView poster, poster_back;

    Button favBtn;
    Button videoBtn;
    Button commentBtn;

    private Movie item;


    public MovieDetailFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView;
        fragmentView = inflater.inflate(R.layout.movie_detail_fragment, container, false);

        title = (TextView) fragmentView.findViewById(R.id.main_title);
        overview = (TextView) fragmentView.findViewById(R.id.text_overview);
        releaseDate = (TextView) fragmentView.findViewById(R.id.text_release_date);
        average = (TextView) fragmentView.findViewById(R.id.average);
        voteCount = (TextView) fragmentView.findViewById(R.id.vote_count);
        popularCount = (TextView) fragmentView.findViewById(R.id.popular_vote);
        poster = (ImageView) fragmentView.findViewById(R.id.image_front);
        poster_back = (ImageView) fragmentView.findViewById(R.id.image_back);

        favBtn = (Button) fragmentView.findViewById(R.id.btn_fav);

        videoBtn = (Button) fragmentView.findViewById(R.id.btn_get_video);
        commentBtn = (Button) fragmentView.findViewById(R.id.btn_get_comments);

        item = getArguments().getParcelable("movieDetail");


        if (item != null) {

            title.setText(item.getTitle().replace("\n", "").replace("  ", ""));
            overview.setText(item.getOverview());
            releaseDate.setText("Release date : ".concat(item.getRelease_date()));
            average.setText("Average : ".concat(item.getVote_average().toString()));
            voteCount.setText("Vote : ".concat(item.getVote_count().toString()));
            popularCount.setText("Popularity : ".concat(item.getPopularity().toString()));

            Picasso.with(getContext())
                    .load(item.getImage())
                    .placeholder(R.drawable.preview)
                    .error(R.drawable.img_error)
                    .into(poster);

            Picasso.with(getContext()).load(item.getBackdropPath()).into(poster_back);

        } else {

        }

        // decide the text on activity at time load whether it is saved or not
        favCheck(item.getMovieKeyId());


        // on click on fav btn
        favBtn.setOnClickListener( new View.OnClickListener(){

            public void onClick(View v) {

                Movie  duplicateRecord = Movie.getMovie(item.getMovieKeyId());
                if(duplicateRecord == null){
                    item.save();
                    Toast.makeText(getContext(), R.string.save_fav, Toast.LENGTH_SHORT).show();

                }else {
                    Movie.deleteMovie(item.getMovieKeyId());
                    Toast.makeText(getContext(), R.string.remove_fave, Toast.LENGTH_SHORT).show();
                }
                favCheck(item.getMovieKeyId());

            }

        });



        // on click on video btn
        videoBtn.setOnClickListener( new View.OnClickListener(){

            public void onClick(View v) {


                if(MovieFragment.connectionDetector.isConnectingToInternet())
                {
                    Intent intent = new Intent(getContext(), VideoActivity.class);
                    intent.putExtra("movieDetail", item);

                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), R.string.no_internet,Toast.LENGTH_SHORT).show();
                }
            }

        });

        // on click on comment btn
        commentBtn.setOnClickListener( new View.OnClickListener(){

            public void onClick(View v) {

                if(MovieFragment.connectionDetector.isConnectingToInternet())
                {
                    Intent intent = new Intent(getContext(), CommentActivity.class);
                    intent.putExtra("movieDetail", item);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), R.string.no_internet,Toast.LENGTH_SHORT).show();
                }


            }

        });



        return fragmentView;

    }

    // change text on FAV button
    protected void favCheck(String movieKeyId)
    {
        Movie  duplicateRecord = Movie.getMovie(movieKeyId);
        if(duplicateRecord == null){
            favBtn.setText(R.string.add_to_fav);

        }else {
            favBtn.setText(R.string.remove_to_fav);
        }
    }


}
