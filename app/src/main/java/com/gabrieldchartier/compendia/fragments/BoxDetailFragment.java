package com.gabrieldchartier.compendia.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gabrieldchartier.compendia.R;

public class BoxDetailFragment extends Fragment
{
    // Constants
    private static final String TAG = "BoxDetailFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView started");

        View view = inflater.inflate(R.layout.fragment_box_detail, container, false);

        return view;
    }
}
