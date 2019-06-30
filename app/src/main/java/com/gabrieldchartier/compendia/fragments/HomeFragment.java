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
    private TextView homeNoPullListText;

    //Variables
    private LinearLayoutManager mNewReleasesLayoutManager;
    private LinearLayoutManager mPullListLayoutManager;
    private ComicCoversAdapter newReleasesAdapter;
    private ComicCoversAdapter pullListAdapter;
    private PullList pullList;
    private NewReleases newReleases;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        newReleases = NewReleases.getInstance();
        pullList = PullList.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView: started");

        // Inflate the view and set the widgets
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mNewReleasesRecyclerView = view.findViewById(R.id.homeNewReleasesRecyclerView);
        mPullListRecyclerView = view.findViewById(R.id.homePullListRecyclerView);
        homeNoPullListText = view.findViewById(R.id.homeNoPullListText);

        initRecyclerView();

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

        List<Comic> closestWeekPullList = pullList.getClosestWeek();
        if(closestWeekPullList.size() < 1)
            homeNoPullListText.setVisibility(View.VISIBLE);
        newReleasesAdapter = new ComicCoversAdapter(getActivity(), newReleases.getClosestWeek());
        pullListAdapter = new ComicCoversAdapter(getActivity(), closestWeekPullList);
        mNewReleasesRecyclerView.setAdapter(newReleasesAdapter);
        mPullListRecyclerView.setAdapter(pullListAdapter);
    }
}