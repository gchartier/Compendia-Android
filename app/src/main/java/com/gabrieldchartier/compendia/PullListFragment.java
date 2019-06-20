package com.gabrieldchartier.compendia;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PullListFragment extends Fragment
{
    // Constants
    private static final String TAG = "PullListFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pull_list, container, false);
        Log.d(TAG, "onCreateView: started");

        return view;
    }
}
