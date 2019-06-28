package com.gabrieldchartier.compendia;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gabrieldchartier.compendia.models.Collection;
import com.gabrieldchartier.compendia.models.Comic;
import com.gabrieldchartier.compendia.util.TempCollection;

import java.util.ArrayList;
import java.util.UUID;

public class OtherVersionsFragment extends Fragment implements View.OnClickListener
{
    // Constants
    private static final String TAG = "OtherVersionsFragment";

    // TODO change all variables preceded by m to actual names
    // Variables
    private FragmentInfoRelay mInterface;
    private LinearLayoutManager otherVersionsLayoutManager;
    private RecyclerViewAdapter adapter;
    private ArrayList<Comic> otherVersionComics = new ArrayList<>();
    private Collection collection;
    private String comicTitle;

    // Widgets
    private TextView fragmentHeader;
    private RecyclerView otherVersionsRecyclerView;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mInterface = (FragmentInfoRelay) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        // Variables
        Bundle bundle = this.getArguments();
        collection = Collection.getInstance();

        // Retrieve bundle if it is not null
        if(otherVersionComics != null)
            otherVersionComics.clear();
        if(bundle != null)
        {
            String[] comicIDs;
            comicTitle = bundle.getString(getString(R.string.intent_comic_title));
            comicIDs = bundle.getStringArray(getString(R.string.intent_other_versions));
            if(comicIDs != null)
                for(String s : comicIDs)
                {
                    //TODO request comic data from repository. This is placeholder stuff
                    otherVersionComics.add(collection.getComicByID(UUID.fromString(s)));
                }
        }
        else
            Log.d(TAG, "Bundle was null");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView: started");
        View view;

        view = inflater.inflate(R.layout.fragment_other_versions, container, false);

        initializeFragmentToolbar(view);
        setViews(view);
        setViewData();
        if(adapter == null)
            initRecyclerView();
        setWidgetListeners();

        return view;
    }

    private void initializeFragmentToolbar(View view)
    {
        Toolbar toolbar = view.findViewById(R.id.otherVersionsToolbar);
        ActionBar actionBar = null;
        if(getActivity() != null)
        {
            if(toolbar != null)
                ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            else
                Log.e(TAG, "Toolbar is blank");
            actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        }
        else
        {
            Log.e(TAG, "Activity was null");
        }
        if(actionBar != null)
        {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        else
            Log.e(TAG, "Support Action Bar was null");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.submit_report_tool_bar_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                mInterface.onBackPressed();
                return true;
            case R.id.subMenuSubmitReport:
                //TODO submit report
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setViews(View view)
    {
        fragmentHeader = view.findViewById(R.id.comicDetailFragmentHeader);
        otherVersionsRecyclerView = view.findViewById(R.id.otherVersionsRecyclerView);
    }

    private void setWidgetListeners()
    {

    }

    private void setViewData()
    {
        fragmentHeader.setText(getString(R.string.tag_fragment_other_versions));
    }

    // Initialize the recycler views
    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView: init RecyclerView");

        otherVersionsLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        otherVersionsRecyclerView.setLayoutManager(otherVersionsLayoutManager);
        adapter = new RecyclerViewAdapter(getActivity(), otherVersionComics);
        otherVersionsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v)
    {

    }
}
