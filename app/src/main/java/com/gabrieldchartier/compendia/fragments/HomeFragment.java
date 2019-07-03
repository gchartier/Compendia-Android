package com.gabrieldchartier.compendia.fragments;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import com.gabrieldchartier.compendia.FragmentInterface;
import com.gabrieldchartier.compendia.R;
import com.gabrieldchartier.compendia.models.Collection;
import com.gabrieldchartier.compendia.models.ComicBox;
import com.gabrieldchartier.compendia.models.NewReleases;
import com.gabrieldchartier.compendia.models.User;
import com.gabrieldchartier.compendia.recycler_views.ComicCoversAdapter;
import com.gabrieldchartier.compendia.models.Comic;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class HomeFragment extends Fragment implements View.OnClickListener
{
    //Constants
    private static final String TAG = "HomeFragment";

    // Views
    private TextView collectedNum;
    private TextView readNum;
    private TextView reviewsNum;
    private ImageView settings;
    private TextView seeAllNewReleasesText;
    private ImageView seeAllNewReleasesButton;
    private RecyclerView newReleasesRecyclerView;
    private TextView seeAllBoxesText;
    private ImageView seeAllBoxesButton;
    private TextView featuredBoxName;
    private ImageView featuredBoxButton;
    private RecyclerView featuredBoxRecyclerView;
    private TextView comicBoxText1;
    private ImageView comicBoxButton1;
    private TextView comicBoxText2;
    private ImageView comicBoxButton2;
    private TextView comicBoxText3;
    private ImageView comicBoxButton3;

    //Variables
    private FragmentInterface activityFragmentInterface;
    private User user;
    private Collection collection;
    private NewReleases newReleases;
    private List<Comic> thisWeeksNewReleases;
    private LinearLayoutManager newReleasesLayoutManager;
    private ComicCoversAdapter newReleasesAdapter;
    private List<ComicBox> comicBoxes;
    private ComicBox featuredBox;
    private ComicBox readBox;
    private LinearLayoutManager featuredBoxLayoutManager;
    private ComicCoversAdapter featuredBoxAdapter;

    @Override
    public void onAttach(Context context)
    {
        Log.d(TAG, "onAttach started");
        super.onAttach(context);
        activityFragmentInterface = (FragmentInterface) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate started");
        super.onCreate(savedInstanceState);

        newReleases = NewReleases.getInstance();
        collection = Collection.getInstance();
        user = User.getInstance();
        thisWeeksNewReleases = newReleases.getThisWeek();
        featuredBox = collection.getComicBoxByName(user.getFeaturedBoxName());
        comicBoxes = collection.getComicBoxes();
        comicBoxes.remove(featuredBox);
        readBox = collection.getComicBoxByName(ComicBox.READ_BOX_NAME);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView started");

        // Inflate and set the view
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeViews(view);
        initRecyclerViews();
        setViewData();
        setViewListeners();

        return view;
    }

    private void initializeViews(View view)
    {
        Log.d(TAG, "initializing views");
        collectedNum = view.findViewById(R.id.homeCollectedNum);
        readNum = view.findViewById(R.id.homeReadNum);
        reviewsNum = view.findViewById(R.id.homeReviewsNum);
        settings = view.findViewById(R.id.homeSettingsButton);
        seeAllNewReleasesText = view.findViewById(R.id.homeSeeAllNewReleasesText);
        seeAllNewReleasesButton = view.findViewById(R.id.homeSeeAllNewReleasesButton);
        newReleasesRecyclerView = view.findViewById(R.id.homeNewReleasesRecyclerView);
        seeAllBoxesText = view.findViewById(R.id.homeSeeAllBoxesText);
        seeAllBoxesButton = view.findViewById(R.id.homeSeeAllBoxesButton);
        featuredBoxName = view.findViewById(R.id.homeFeaturedBoxHeader);
        featuredBoxButton = view.findViewById(R.id.homeFeaturedBoxButton);
        featuredBoxRecyclerView = view.findViewById(R.id.homeFeaturedBoxRecyclerView);
        comicBoxText1 = view.findViewById(R.id.homeComicBoxText1);
        comicBoxButton1 = view.findViewById(R.id.homeComicBoxButton1);
        comicBoxText2 = view.findViewById(R.id.homeComicBoxText2);
        comicBoxButton2 = view.findViewById(R.id.homeComicBoxButton2);
        comicBoxText3 = view.findViewById(R.id.homeComicBoxText3);
        comicBoxButton3 = view.findViewById(R.id.homeComicBoxButton3);
    }

    // Initialize the recycler views
    private void initRecyclerViews()
    {
        Log.d(TAG, "Initializing Recycler Views");

        // Initialize New Releases Recycler View
        newReleasesLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        newReleasesRecyclerView.setLayoutManager(newReleasesLayoutManager);
        newReleasesAdapter = new ComicCoversAdapter(getActivity(), thisWeeksNewReleases);
        newReleasesRecyclerView.setAdapter(newReleasesAdapter);

        // Initialize Featured Box Recycler View
        featuredBoxLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        featuredBoxRecyclerView.setLayoutManager(featuredBoxLayoutManager);
        if(featuredBox != null)
            featuredBoxAdapter = new ComicCoversAdapter(getActivity(), featuredBox.getComicsInBox());
        featuredBoxRecyclerView.setAdapter(featuredBoxAdapter);
    }

    // Set the data for the views on the screen
    private void setViewData()
    {
        Log.d(TAG, "setting view data");

        // Set header information
        collectedNum.setText(String.valueOf(collection.getComics().size()));
        if(readBox != null)
            readNum.setText(String.valueOf(readBox.getComicsInBox().size()));
        else
            readNum.setText(String.valueOf(0));
        reviewsNum.setText(String.valueOf(collection.getReviews().size()));

        // Set box information
        featuredBoxName.setText(user.getFeaturedBoxName());
        if(comicBoxes.size() > 0)
            comicBoxText1.setText(comicBoxes.get(0).getBoxName());
        if(comicBoxes.size() > 1)
            comicBoxText2.setText(comicBoxes.get(1).getBoxName());
        if(comicBoxes.size() > 2)
            comicBoxText3.setText(comicBoxes.get(2).getBoxName());
        else
        {
            comicBoxText3.setVisibility(View.GONE);
            comicBoxButton3.setVisibility(View.GONE);
        }
    }

    // Set the on click listeners for the views
    private void setViewListeners()
    {
        Log.d(TAG, "setting view listeners");

        settings.setOnClickListener(this);
        seeAllNewReleasesText.setOnClickListener(this);
        seeAllNewReleasesButton.setOnClickListener(this);
        seeAllBoxesText.setOnClickListener(this);
        seeAllBoxesButton.setOnClickListener(this);
        featuredBoxName.setOnClickListener(this);
        featuredBoxButton.setOnClickListener(this);
        comicBoxText1.setOnClickListener(this);
        comicBoxButton1.setOnClickListener(this);
        comicBoxText2.setOnClickListener(this);
        comicBoxButton2.setOnClickListener(this);
        comicBoxText3.setOnClickListener(this);
        comicBoxButton3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.homeSettingsButton)
        {
            Log.d(TAG, "settings clicked");
            activityFragmentInterface.inflateSettingsFragment();
        }
        else if(v.getId() == R.id.homeSeeAllNewReleasesText || v.getId() == R.id.homeSeeAllNewReleasesButton)
        {
            Log.d(TAG, "see all new releases clicked");
            activityFragmentInterface.inflateNewReleasesFragment();
        }
        else if(v.getId() == R.id.homeSeeAllBoxesText || v.getId() == R.id.homeSeeAllBoxesButton)
        {
            Log.d(TAG, "see all boxes clicked");
            activityFragmentInterface.inflateBoxesFragment();
        }
        else if(v.getId() == R.id.homeFeaturedBoxHeader || v.getId() == R.id.homeFeaturedBoxButton)
        {
            Log.d(TAG, "comic box detail " + featuredBox.getBoxName() + " clicked");
            activityFragmentInterface.inflateBoxDetailFragment(featuredBox);
        }
        else if(v.getId() == R.id.homeComicBoxText1 || v.getId() == R.id.homeComicBoxButton1)
        {
            Log.d(TAG, "comic box detail " + comicBoxes.get(0) + " clicked");
            activityFragmentInterface.inflateBoxDetailFragment(comicBoxes.get(0));
        }
        else if(v.getId() == R.id.homeComicBoxText2 || v.getId() == R.id.homeComicBoxButton2)
        {
            Log.d(TAG, "comic box detail " + comicBoxes.get(1) + " clicked");
            activityFragmentInterface.inflateBoxDetailFragment(comicBoxes.get(1));
        }
        else if(v.getId() == R.id.homeComicBoxText3 || v.getId() == R.id.homeComicBoxButton3)
        {
            Log.d(TAG, "comic box detail " + comicBoxes.get(2) + " clicked");
            activityFragmentInterface.inflateBoxDetailFragment(comicBoxes.get(2));
        }
    }
}