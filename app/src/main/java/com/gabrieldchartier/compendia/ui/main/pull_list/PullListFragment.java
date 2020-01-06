package com.gabrieldchartier.compendia.ui.main.pull_list;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gabrieldchartier.compendia.FragmentInterface;
import com.gabrieldchartier.compendia.recycler_views.PullListExpandableRecyclerAdapter;
import com.gabrieldchartier.compendia.recycler_views.PullListRecyclerWeek;
import com.gabrieldchartier.compendia.R;
import com.gabrieldchartier.compendia.models.Comic;
import com.gabrieldchartier.compendia.models.PullList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("FieldCanBeLocal")
public class PullListFragment extends Fragment
{
//    // Constants
//    private static final String TAG = "PullListFragment";
//
//    // Variables
//    private FragmentInterface fragmentInterface;
//    private LinearLayoutManager pullListLayoutManager;
//    private PullListExpandableRecyclerAdapter adapter;
//    private PullList pullList;
//
//    // Widgets
//    private RecyclerView pullListRecyclerView;
//
//    @Override
//    public void onAttach(Context context)
//    {
//        super.onAttach(context);
//        fragmentInterface = (FragmentInterface) getActivity();
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//
//        setHasOptionsMenu(true);
//
//        // Variables
//        pullList = PullList.getInstance();
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//    {
//        View view = inflater.inflate(R.layout.fragment_pull_list, container, false);
//        Log.d(TAG, "onCreateView: started");
//
//        setViews(view);
//        setViewData();
//        initRecyclerView();
//        setWidgetListeners();
//
//        return view;
//    }
//
//    private void setViews(View view)
//    {
//        pullListRecyclerView = view.findViewById(R.id.pullListRecyclerView);
//    }
//
//    private void setViewData()
//    {
//
//    }
//
//    // Initialize the recycler views
//    private void initRecyclerView()
//    {
//        Log.d(TAG, "Initializing Pull List recycler view");
//        List<PullListRecyclerWeek> weeks = getWeekGroups(pullList.getComics(), pullList.getWeeks());
//        pullListLayoutManager = new LinearLayoutManager(getActivity());
//        adapter = new PullListExpandableRecyclerAdapter(weeks, getActivity());
//        pullListRecyclerView.setLayoutManager(pullListLayoutManager);
//        pullListRecyclerView.setAdapter(adapter);
//
//        // Expand all the weeks in the adapter
//        for (int i = adapter.getGroups().size()-1; i >=0 ; i--)
//            expandGroup(i);
//    }
//
//    private void setWidgetListeners()
//    {
//        adapter.setClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                TextView clickedItemID = v.findViewById(R.id.comicListItemID);
//                Comic clickedComic = pullList.getComicByID(UUID.fromString(clickedItemID.getText().toString()));
//                if(clickedComic != null)
//                {
//                    Log.d(TAG, "Clicked Pull List Item " + clickedComic.getTitle());
//                    fragmentInterface.inflateComicDetailFragment(clickedComic);
//                }
//                else
//                    Log.e(TAG,"Clicked Other Version Comic was null");
//            }
//        });
//    }
//
//    // Get pull list week groups for the pull list recycler view
//    private List<PullListRecyclerWeek> getWeekGroups(List<Comic> pullListComics, List<String> pullListWeeks)
//    {
//        List<PullListRecyclerWeek> weeks = new ArrayList<>();
//
//        for(String week : pullListWeeks)
//        {
//            List<Comic> tempPullListWeekComics = new ArrayList<>();
//            for(Comic c: pullListComics)
//            {
//                //Log.d(TAG, "Checking week " + week + " against comic date " + c.getReleaseDate());
//                if(c.getReleaseDate().equals(week))
//                    tempPullListWeekComics.add(c);
//            }
//            weeks.add(new PullListRecyclerWeek(week, tempPullListWeekComics));
//            Log.d(TAG,tempPullListWeekComics.toString());
//        }
//
//        return weeks;
//    }
//
//    public void expandGroup (int position)
//    {
//        if(adapter.isGroupExpanded(position))
//        {
//            return;
//        }
//        adapter.toggleGroup(position);
//    }
}
