package com.gabrieldchartier.compendia;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OtherVersionsFragment extends Fragment implements View.OnClickListener
{
    // Constants
    private static final String TAG = "OtherVersionsFragment";

    // Variables
    FragmentInfoRelay mInterface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mInterface = (FragmentInfoRelay) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_other_versions, container, false);
        Log.d(TAG, "onCreateView: started");

        mInterface.initializeFragmentToolbar(R.id.otherVersionsToolbar, TAG);

        return view;
    }

    @Override
    public void onClick(View v)
    {

    }
}
