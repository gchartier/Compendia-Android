package com.gabrieldchartier.compendia.ui.main.search;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gabrieldchartier.compendia.R;

public class SearchFragment extends Fragment
{
    // Constants
    private static final String TAG = "SearchFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView: started");

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        return view;
    }
}
