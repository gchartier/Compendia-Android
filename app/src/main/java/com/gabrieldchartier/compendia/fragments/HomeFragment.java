package com.gabrieldchartier.compendia.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gabrieldchartier.compendia.R;
import com.gabrieldchartier.compendia.models.Collection;
import com.gabrieldchartier.compendia.models.NewReleases;
import com.gabrieldchartier.compendia.models.PullList;
import com.gabrieldchartier.compendia.recycler_views.ComicCoversAdapter;
import com.gabrieldchartier.compendia.models.Comic;
import java.util.List;

public class HomeFragment extends Fragment
{
    //Constants
    private static final String TAG = "HomeFragment";

    //Widgets
    private RecyclerView mNewReleasesRecyclerView;
    private RecyclerView mPullListRecyclerView;
    private TextView homeOwnedNum;
    private TextView homeReadNum;
    private TextView homeReviewsNum;

    //Variables
    private LinearLayoutManager mNewReleasesLayoutManager;
    private LinearLayoutManager mPullListLayoutManager;
    private ComicCoversAdapter newReleasesAdapter;
    private ComicCoversAdapter pullListAdapter;
    private PullList pullList;
    private NewReleases newReleases;
    private Collection collection;
    private List<Comic> closestWeekPullList;
    private List<Comic> thisWeeksNewReleases;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        newReleases = NewReleases.getInstance();
        pullList = PullList.getInstance();
        collection = Collection.getInstance();

        closestWeekPullList = pullList.getClosestWeek();
        thisWeeksNewReleases = newReleases.getThisWeek();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView: started");

        // Inflate the view and set the widgets
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeViews(view);
        initRecyclerView();
        setViewData();

        return view;
    }

    private void initializeViews(View view)
    {
        mNewReleasesRecyclerView = view.findViewById(R.id.homeNewReleasesRecyclerView);
        mPullListRecyclerView = view.findViewById(R.id.homeFeaturedBoxRecyclerView);
        //homeNoPullListText = view.findViewById(R.id.homeNoPullListText);
        homeOwnedNum = view.findViewById(R.id.homeOwnedNum);
        homeReadNum = view.findViewById(R.id.homeReadNum);
        homeReviewsNum = view.findViewById(R.id.homeReviewsNum);
    }

    // Initialize the recycler views
    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView: init RecyclerView");

        mNewReleasesLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mPullListLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mNewReleasesRecyclerView.setLayoutManager(mNewReleasesLayoutManager);
        mPullListRecyclerView.setLayoutManager(mPullListLayoutManager);
        newReleasesAdapter = new ComicCoversAdapter(getActivity(), thisWeeksNewReleases);
        pullListAdapter = new ComicCoversAdapter(getActivity(), closestWeekPullList);
        mNewReleasesRecyclerView.setAdapter(newReleasesAdapter);
        mPullListRecyclerView.setAdapter(pullListAdapter);
    }

    private void setViewData()
    {
        homeOwnedNum.setText(String.valueOf(collection.getComics().size()));
        homeReadNum.setText(String.valueOf(collection.getReadBox().getComicsInBox().size()));
        homeReviewsNum.setText(String.valueOf(collection.getReviews().size()));
    }
}