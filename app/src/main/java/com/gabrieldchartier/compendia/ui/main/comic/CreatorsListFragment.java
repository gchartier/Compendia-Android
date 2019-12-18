package com.gabrieldchartier.compendia.ui.main.comic;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gabrieldchartier.compendia.R;

public class CreatorsListFragment extends Fragment
{
    // Constants
    private static final String TAG = "CreatorsListFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView started");

        View view = inflater.inflate(R.layout.fragment_creators_list, container, false);

        return view;
    }
}
