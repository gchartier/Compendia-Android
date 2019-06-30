package com.gabrieldchartier.compendia.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabrieldchartier.compendia.recycler_views.ComicCoversAdapter;
import com.gabrieldchartier.compendia.R;
import com.gabrieldchartier.compendia.models.Comic;

import java.util.ArrayList;

public class CollectionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    // Constants
    private static final String TAG = "CollectionFragment";

    // Widgets
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecentlyAddedRecyclerView;

    // Variables
    private ArrayList<Comic> mComics = new ArrayList<>();

    // Recycler Views
    private LinearLayoutManager mRecentlyAddedLayoutManager;
    private ComicCoversAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView: started");

        // Inflate the view and set the widgets
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        mRecentlyAddedRecyclerView = view.findViewById(R.id.recentlyAddedRecyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        // Populate the comic data
        //TODO getComics();

        return view;
    }

    // Initialize the recycler views
    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView: init RecyclerView");

        mRecentlyAddedLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecentlyAddedRecyclerView.setLayoutManager(mRecentlyAddedLayoutManager);
        adapter = new ComicCoversAdapter(getActivity(), mComics);
        mRecentlyAddedRecyclerView.setAdapter(adapter);
    }

    // Swipe refresh from the top calls this
    @Override
    public void onRefresh()
    {
        // Update the collection here
        onRefreshComplete();
    }

    // Notifies the recycler views about changed data and stops refresh
    private void onRefreshComplete()
    {
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    // Scroll to the top of the collection recycler view
    public void scrollToTop()
    {
        mRecentlyAddedRecyclerView.smoothScrollToPosition(0);
    }
//TODO
//    // Get the comics in the collection
//    private void getComics()
//    {
//        Comics comics = new Comics();
//        if(mComics != null)
//            mComics.clear();
//        try
//        {
//            Collections.addAll(mComics, comics.COMICS);
//        }
//        catch(NullPointerException error)
//        {
//            Log.d(TAG, "Error in add all comics: " + error);
//        }
//        if(adapter == null)
//            initRecyclerView();
//    }
}
