package com.example.yogenders.movinationfinal.Fragments;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogenders.movinationfinal.Adapters.MovieAdapter;
import com.example.yogenders.movinationfinal.Assets.Asset;
import com.example.yogenders.movinationfinal.Assets.ConnectionDetector;
import com.example.yogenders.movinationfinal.Assets.MovieFetch;
import com.example.yogenders.movinationfinal.Models.Movie;
import com.example.yogenders.movinationfinal.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MovieFragment extends Fragment {

    // Vars for Movie Grid
    public static GridView mGridView;
    public static ArrayList<Movie> mGridData = new ArrayList<>();
    public static MovieAdapter mMovieAdapter;

    public static ProgressBar progressBar;
    TextView error_msg_text;


    // Connection detector class
    public static ConnectionDetector connectionDetector ;


    // this interface works as communicator between MainActivity and Its Fragment
    fragmentCommunicatorInterface communicator;

    public interface  fragmentCommunicatorInterface{

        public void onItemClicked(Movie movieItem);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.movie_fragment,container, false);

        progressBar = (ProgressBar) fragmentView.findViewById(R.id.progressBar);
        error_msg_text = (TextView) fragmentView.findViewById(R.id.error_msg_text);

        // initialize grid data & view
        mGridView = (GridView) fragmentView.findViewById(R.id.container_grid);

        mMovieAdapter = new MovieAdapter(this.getActivity(),R.layout.movie_item,mGridData);
        mGridView.setAdapter(mMovieAdapter);

        // change number columns with orientation
        int ot = getResources().getConfiguration().orientation;
        mGridView.setNumColumns(ot == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);

        connectionDetector = new ConnectionDetector(getContext());

        if(connectionDetector.isConnectingToInternet())
        {
                if(savedInstanceState == null || !savedInstanceState.containsKey("imagesGridData")) {


                    if( Asset.MENU_ITEM_SELECTED != "FAV")
                    {
                        // starting a asynchronously data fetch
                        progressBar.setVisibility(View.VISIBLE);
                        updateGrid(false);
                    }
                    else
                    {
                        getFavData();
                    }

                }
                else
                {
                    mGridData = savedInstanceState.getParcelableArrayList("imagesGridData");
                    // restoring data from savedInstanceState usng Parcelable
                    mMovieAdapter.setGridData(mGridData);
                    progressBar.setVisibility(View.INVISIBLE);
                }


        }
        else {

            getFavData();
        }

        // click event for grid item
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie gridItem = mMovieAdapter.getItem(position);
                deliverData(gridItem);

            }
        });


        // implementing scroll event & infinite scroll on grid view
        mGridView.setOnScrollListener(new GridView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if(Asset.API_CALL)
                {
                    if (mGridView.getLastVisiblePosition() + 1 == totalItemCount ) {
                        updateGrid(false);
                    }
                }

            }
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
        });

        // set menu items visible
        setHasOptionsMenu(true);
        // return view
        return fragmentView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("imagesGridData", mGridData);

    }

    // creating menu items
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }


    // handle when a menu item is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_popular) {

            if (Asset.MENU_ITEM_SELECTED.equals("POPULAR"))
            {
                Toast.makeText(getActivity(), "Movies are already sorted by POPULAR",Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(connectionDetector.isConnectingToInternet())
                {
                    Asset.MENU_ITEM_SELECTED = "POPULAR";
                    Asset.resetPaging();
                    updateGrid(true);
                }
                else
                {
                    Toast.makeText(getActivity(), R.string.no_internet,Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }

        if (id == R.id.menu_top_rated) {
            if (Asset.MENU_ITEM_SELECTED.equals("TOP"))
            {
                Toast.makeText(getActivity(), "Movies are already sorted by TOP",Toast.LENGTH_SHORT).show();
            }
            else
            {

                if(connectionDetector.isConnectingToInternet())
                {
                    Asset.MENU_ITEM_SELECTED = "TOP";
                    Asset.resetPaging();
                    updateGrid(true);
                }
                else
                {
                    Toast.makeText(getActivity(), R.string.no_internet,Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }

        if( id == R.id.menu_fav )
        {
            getFavData();
        }


        return super.onOptionsItemSelected(item);
    }

    // retrive stored data from mysql
    protected void getFavData()
    {

        Asset.MENU_ITEM_SELECTED = "FAV";

        Movie getSavedItem = new Movie();
        List getSavedItemList = getSavedItem.getAllSavedItem();

        mGridData.clear();

        final int counter_data = getSavedItemList.size();



        if( counter_data == 0)
        {
            error_msg_text.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            mMovieAdapter.setGridData(mGridData);

            Toast.makeText(getActivity(), "No Favourites.",Toast.LENGTH_SHORT).show();

        }
        else
        {
            for (int i = 0; i < counter_data; i++)
            {
                Movie savedGI = (Movie) getSavedItemList.get(i);
                mGridData.add(savedGI);
                mMovieAdapter.setGridData(mGridData);
            }

            progressBar.setVisibility(View.INVISIBLE);
        }



    }


    // this function will update  gridView from menu
    protected  void updateGrid(Boolean clear)
    {

        error_msg_text.setVisibility(View.INVISIBLE);

        // parameter decides whether clear Adapter for not
        if(clear)
        {
            mMovieAdapter.clear();
        }


        MovieFetch movieFetchUpdate = new MovieFetch(getContext());
        movieFetchUpdate.execute(Asset.decideAPIURL(Asset.MENU_ITEM_SELECTED));
        Asset.API_CALL = false;

    }


    // this method communicates data for interface
    public void deliverData(Movie movieItem)
    {
        communicator.onItemClicked(movieItem);
    }


    // this method is override to create a interface object to implement multipane activity.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            communicator = (fragmentCommunicatorInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement fragmentCommunicatorInterface");
        }
    }




}
