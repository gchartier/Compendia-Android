package com.gabrieldchartier.compendia.ui.main.comic;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gabrieldchartier.compendia.FragmentInterface;
import com.gabrieldchartier.compendia.recycler_views.OtherVersionRecyclerCategory;
import com.gabrieldchartier.compendia.recycler_views.OtherVersionsExpandableRecyclerAdapter;
import com.gabrieldchartier.compendia.R;
import com.gabrieldchartier.compendia.models.Collection;
import com.gabrieldchartier.compendia.models.Comic;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("FieldCanBeLocal")
public class OtherVersionsFragment extends Fragment
{
//    // Constants
//    private static final String TAG = "OtherVersionsFragment";
//
//    // TODO change all variables preceded by m to actual names
//    // Variables
//    private FragmentInterface mInterface;
//    private LinearLayoutManager otherVersionsLayoutManager;
//    private OtherVersionsExpandableRecyclerAdapter adapter;
//    private List<Comic> otherVersionComics = new ArrayList<>();
//    private Collection collection;
//
//    // Widgets
//    private TextView fragmentHeader;
//    private RecyclerView otherVersionsRecyclerView;
//
//    @Override
//    public void onAttach(Context context)
//    {
//        super.onAttach(context);
//        mInterface = (FragmentInterface) getActivity();
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
//        Bundle bundle = this.getArguments();
//        collection = new Collection();
//
//        // Retrieve bundle if it is not null
//        if(otherVersionComics != null)
//            otherVersionComics.clear();
//        if(bundle != null)
//        {
//            String[] comicIDs;
//            comicIDs = bundle.getStringArray(getString(R.string.intent_other_versions));
//            if(comicIDs != null)
//            {
//                Log.d(TAG, "OtherVersionID UUID" + UUID.fromString(comicIDs[0]));
//                for(String s : comicIDs)
//                {
//                    //TODO request comic data from repository. This is placeholder stuff
//                    otherVersionComics.add(collection.getComicByID(UUID.fromString(s)));
//                    Log.d(TAG, "OtherVersionID UUID 2" + collection.getComicByID(UUID.fromString(s)));
//                }
//            }
//        }
//        else
//            Log.d(TAG, "Bundle was null");
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//    {
//        Log.d(TAG, "onCreateView: started");
//        View view;
//
//        view = inflater.inflate(R.layout.fragment_other_versions, container, false);
//
//        initializeFragmentToolbar(view);
//        setViews(view);
//        setViewData();
//        initRecyclerView();
//        setWidgetListeners();
//
//        return view;
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
//    {
//        inflater.inflate(R.menu.submit_report_tool_bar_menu, menu);
//        super.onCreateOptionsMenu(menu,inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        switch (item.getItemId())
//        {
//            case android.R.id.home:
//                mInterface.onBackPressed();
//                return true;
//            case R.id.subMenuSubmitReport:
//                //TODO submit report
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    private void initializeFragmentToolbar(View view)
//    {
//        Toolbar toolbar = view.findViewById(R.id.otherVersionsToolbar);
//        ActionBar actionBar = null;
//        if(getActivity() != null)
//        {
//            if(toolbar != null)
//                ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//            else
//                Log.e(TAG, "Toolbar is blank");
//            actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
//        }
//        else
//        {
//            Log.e(TAG, "Activity was null");
//        }
//        if(actionBar != null)
//        {
//            actionBar.setDisplayShowTitleEnabled(false);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//        else
//            Log.e(TAG, "Support Action Bar was null");
//    }
//
//    private void setViews(View view)
//    {
//        fragmentHeader = view.findViewById(R.id.fragmentHeader);
//        otherVersionsRecyclerView = view.findViewById(R.id.otherVersionsRecyclerView);
//    }
//
//    private void setViewData()
//    {
//        fragmentHeader.setText(getString(R.string.tag_fragment_other_versions));
//    }
//
//    // Initialize the recycler views
//    private void initRecyclerView()
//    {
//        Log.d(TAG, "Initializing other versions recycler view");
//        List<OtherVersionRecyclerCategory> categories = getCategoryGroups(otherVersionComics);
//        otherVersionsLayoutManager = new LinearLayoutManager(getActivity());
//        adapter = new OtherVersionsExpandableRecyclerAdapter(categories, getActivity());
//        otherVersionsRecyclerView.setLayoutManager(otherVersionsLayoutManager);
//        otherVersionsRecyclerView.setAdapter(adapter);
//        adapter.expandAll(categories);
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
//                Comic clickedComic = collection.getComicByID(UUID.fromString(clickedItemID.getText().toString()));
//                if(clickedComic != null)
//                {
//                    Log.d(TAG, "Clicked Other Version Item " + clickedComic.getTitle());
//                    mInterface.inflateComicDetailFragment(clickedComic);
//                }
//                else
//                    Log.e(TAG,"Clicked Other Version Comic was null");
//            }
//        });
//    }
//
//    // Get other version category groups for the other version recycler view
//    private List<OtherVersionRecyclerCategory> getCategoryGroups(List<Comic> otherVersionComics)
//    {
//        List<OtherVersionRecyclerCategory> categories = new ArrayList<>();
//        List<Comic> reprints = new ArrayList<>();
//        List<Comic> variants = new ArrayList<>();
//        List<Comic> other = new ArrayList<>();
//        for(Comic c : otherVersionComics)
//        {
//            if(c.isVariant())
//                variants.add(c);
//            else if(c.isReprint())
//                reprints.add(c);
//            else
//                other.add(c);
//        }
//        if(reprints.size() > 0)
//            categories.add(new OtherVersionRecyclerCategory("Reprints", reprints));
//        if(variants.size() > 0)
//            categories.add(new OtherVersionRecyclerCategory("Variants", variants));
//        if(other.size() > 0)
//            categories.add(new OtherVersionRecyclerCategory("Other", other));
//
//        return categories;
//    }
}
