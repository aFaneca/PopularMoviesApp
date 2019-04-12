package com.itsector.popularmoviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.itsector.popularmoviesapp.models.Movie;
import com.itsector.popularmoviesapp.views.adapters.MoviesListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int NUMBER_OF_COLUMNS = 2;
    private RecyclerView mMoviesList_recycler_view;
    private RecyclerView.Adapter mMoviesListAdapter;
    private List<Movie> mMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesList_recycler_view = (RecyclerView) findViewById(R.id.movie_list_recycler_view);

        mMoviesList = generateDataSet(20);
        mMoviesListAdapter = new MoviesListAdapter(mMoviesList, new MoviesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                Toast.makeText(getApplicationContext(), (String) movie.getOriginalTitle(), Toast.LENGTH_LONG).show();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
        mMoviesList_recycler_view.setLayoutManager(gridLayoutManager);
        mMoviesList_recycler_view.setAdapter(mMoviesListAdapter);
    }

    private List<Movie> generateDataSet(int q) {
        List<Movie> tempMoviesList = new ArrayList<>(q);


        for(int i = 0; i < q; i++){
            tempMoviesList.add(new Movie("Titulo " + i,  "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "", 2.5, "" ));
        }




        return tempMoviesList;
    }
}
