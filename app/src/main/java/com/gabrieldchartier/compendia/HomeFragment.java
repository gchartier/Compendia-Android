package com.gabrieldchartier.compendia;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gabrieldchartier.compendia.models.Comic;
import com.gabrieldchartier.compendia.util.Comics;
import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment
{
    //Constants
    private static final String TAG = "HomeFragment";

    //Widgets
    private RecyclerView mNewReleasesRecyclerView;
    private RecyclerView mPullListRecyclerView;

    //Variables
    private ArrayList<Comic> mComics = new ArrayList<>();
    private LinearLayoutManager mNewReleasesLayoutManager;
    private LinearLayoutManager mPullListLayoutManager;
    private RecyclerViewAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView: started");

        // Inflate the view and set the widgets
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mNewReleasesRecyclerView = view.findViewById(R.id.homeNewReleasesRecyclerView);
        mPullListRecyclerView = view.findViewById(R.id.homePullListRecyclerView);

        // Get the comic data
        getComics();

        return view;
    }

    // Initialize the recycler views
    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView: init RecyclerView");

        mNewReleasesLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mPullListLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mNewReleasesRecyclerView.setLayoutManager(mNewReleasesLayoutManager);
        mPullListRecyclerView.setLayoutManager(mPullListLayoutManager);
        adapter = new RecyclerViewAdapter(getActivity(), mComics);
        mNewReleasesRecyclerView.setAdapter(adapter);
        mPullListRecyclerView.setAdapter(adapter);
    }

    // Get the comic data for the home page
    private void getComics()
    {
        Comics comics = new Comics();
        if(mComics != null)
            mComics.clear();
        try
        {
            Collections.addAll(mComics, comics.COMICS);
        }
        catch(NullPointerException error)
        {
            Log.d(TAG, "Error in add all comics: " + error);
        }
        if(adapter == null)
            initRecyclerView();
    }
}