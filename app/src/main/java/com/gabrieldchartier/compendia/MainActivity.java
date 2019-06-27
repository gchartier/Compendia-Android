package com.gabrieldchartier.compendia;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gabrieldchartier.compendia.models.Comic;
import com.gabrieldchartier.compendia.models.FragmentTag;
import com.gabrieldchartier.compendia.util.PreferenceKeys;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements FragmentInfoRelay, BottomNavigationViewEx.OnNavigationItemSelectedListener
{
    // Constants
    private static final int BOTTOM_NAV_HOME = 0;
    private static final int BOTTOM_NAV_PULL_LIST = 1;
    private static final int BOTTOM_NAV_COLLECTION = 2;
    private static final int BOTTOM_NAV_SEARCH = 3;
    private static final String TAG = "MainActivity";

    // Widgets
    private BottomNavigationViewEx bottomNav;

    // Fragments
    HomeFragment homeFragment;
    PullListFragment pullListFragment;
    CollectionFragment collectionFragment;
    SearchFragment searchFragment;
    ComicDetailFragment comicDetailFragment;
    OtherVersionsFragment otherVersionsFragment;

    // Variables
    private ArrayList<String> fragmentTags = new ArrayList<>();
    private ArrayList<FragmentTag> fragments = new ArrayList<>();
    private ArrayList<String> hideNavBarFragments = new ArrayList<>();
    private int appExitAttempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavBarFragments.add(getString(R.string.tag_fragment_comic_detail));
        hideNavBarFragments.add(getString(R.string.tag_fragment_other_versions));
        isFirstLogin();
        initBottomNav();
        inflateHomeFragment();
    }

    // Initialize the bottom navigation view
    private void initBottomNav()
    {
        Log.d(TAG, "Building bottom nav view");
        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(this);
        bottomNav.enableAnimation(false);
    }

    // Hide the bottom navigation view
    public void hideBottomNav()
    {
        if(bottomNav != null)
            bottomNav.setVisibility(View.INVISIBLE);
    }

    // Show the bottom navigation view
    public void showBottomNav()
    {
        if(bottomNav != null)
            bottomNav.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.bottom_nav_home:
                Log.d(TAG, "Home bottom nav icon clicked");
                menuItem.setChecked(true);
                fragmentTags.clear();
                fragmentTags = new ArrayList<>();
                inflateHomeFragment();
                break;
            case R.id.bottom_nav_collection:
                Log.d(TAG, "Collection bottom nav icon clicked");
                menuItem.setChecked(true);
                inflateCollectionFragment();
                break;
            case R.id.bottom_nav_pull_list:
                Log.d(TAG, "Pull List bottom nav icon clicked");
                menuItem.setChecked(true);
                inflatePullListFragment();
                break;
            case R.id.bottom_nav_search:
                Log.d(TAG, "Search bottom nav icon clicked");
                menuItem.setChecked(true);
                inflateSearchFragment();
                break;
        }
        return false;
    }

    // Sets the visibility of the fragments in the back stack
    private void setFragmentVisibility(String tag)
    {
        // Toggle visibility of the bottom nav based on fragment tag
        if(hideNavBarFragments.contains(tag))
            hideBottomNav();
        else
            showBottomNav();

        // Show the fragment matching the tag passed in and hide the rest
        for(FragmentTag fTag : fragments)
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(tag.equals(fTag.getTag()))
                transaction.show(fTag.getFragment());
            else
                transaction.hide(fTag.getFragment());
            transaction.commit();
        }
        setBottomNavIcon(tag);
    }

    @Override
    public void onBackPressed()
    {
        int backStackCount = fragmentTags.size();

        if(backStackCount > 1)
        {
            // Scroll to the top of the screen on navigate back in the collection screen
            if(fragmentTags.get(backStackCount - 1).equals(getString(R.string.tag_fragment_collection)))
                collectionFragment.scrollToTop();

            setFragmentVisibility(fragmentTags.get(backStackCount - 2));
            fragmentTags.remove(fragmentTags.get(backStackCount - 1));
            appExitAttempts = 0;
        }
        else if(backStackCount == 1)
        {
            appExitAttempts++;
            Toast.makeText(this, getString(R.string.exit_toast), Toast.LENGTH_SHORT).show();
        }

        // Close app
        if(appExitAttempts >= 2)
            super.onBackPressed();
    }

    public void initializeFragmentToolbar(int toolbarID, String TAG)
    {
        Toolbar toolbar = findViewById(toolbarID);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayShowTitleEnabled(false);
        else
            Log.e(TAG, "Support Action Bar was null");
    }


    // Set the bottom nav icon if the tag passed in is associated with a bottom nav fragment
    private void setBottomNavIcon(String fragmentTag)
    {
        Menu bottomMenu = bottomNav.getMenu();
        if(fragmentTag.equals(getString(R.string.tag_fragment_home)))
            bottomMenu.getItem(BOTTOM_NAV_HOME).setChecked(true);
        else if(fragmentTag.equals(getString(R.string.tag_fragment_pull_list)))
            bottomMenu.getItem(BOTTOM_NAV_PULL_LIST).setChecked(true);
        else if(fragmentTag.equals(getString(R.string.tag_fragment_collection)))
            bottomMenu.getItem(BOTTOM_NAV_COLLECTION).setChecked(true);
        else if(fragmentTag.equals(getString(R.string.tag_fragment_search)))
            bottomMenu.getItem(BOTTOM_NAV_SEARCH).setChecked(true);
    }

    // Inflate the comic detail fragment and pass in the comic data
    @Override
    public void inflateComicDetailFragment(Comic comic)
    {
        // Remove comic detail fragment if it exists
        if(comicDetailFragment != null)
            getSupportFragmentManager().beginTransaction().remove(comicDetailFragment).commitAllowingStateLoss();

        // Create fragment, set the comic passed in, and add the fragment to the back stack
        comicDetailFragment = new ComicDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.intent_comic), comic);
        comicDetailFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content_frame, comicDetailFragment, getString(R.string.tag_fragment_comic_detail));
        transaction.commit();
        fragmentTags.add(getString(R.string.tag_fragment_comic_detail));
        fragments.add(new FragmentTag(comicDetailFragment, getString(R.string.tag_fragment_comic_detail)));
        setFragmentVisibility(getString(R.string.tag_fragment_comic_detail));
    }

    // Inflate the other versions fragment passing in the list of other comic versions
    @Override
    public void inflateOtherVersionsFragment(UUID[] otherVersions, String comicTitle)
    {
        // Remove comic detail fragment if it exists
        if(otherVersionsFragment != null)
            getSupportFragmentManager().beginTransaction().remove(otherVersionsFragment).commitAllowingStateLoss();

        // Create fragment, set the comic data passed in, and add the fragment to the back stack
        otherVersionsFragment = new OtherVersionsFragment();
        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.intent_other_versions), otherVersions);
        args.putString(getString(R.string.intent_comic_title), comicTitle);
        otherVersionsFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content_frame, otherVersionsFragment, getString(R.string.tag_fragment_other_versions));
        transaction.commit();
        fragmentTags.add(getString(R.string.tag_fragment_other_versions));
        fragments.add(new FragmentTag(otherVersionsFragment, getString(R.string.tag_fragment_other_versions)));
        setFragmentVisibility(getString(R.string.tag_fragment_other_versions));
    }

    // Inflate the home fragment
    public void inflateHomeFragment()
    {
        // If the fragment does not exist, create it and add it to the stack, otherwise re-add it
        if(homeFragment == null)
        {
            homeFragment = new HomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, homeFragment, getString(R.string.tag_fragment_home));
            transaction.commit();
            fragmentTags.add(getString(R.string.tag_fragment_home));
            fragments.add(new FragmentTag(homeFragment, getString(R.string.tag_fragment_home)));
        }
        else
        {
            fragmentTags.remove(getString(R.string.tag_fragment_home));
            fragmentTags.add(getString(R.string.tag_fragment_home));
        }
        setFragmentVisibility(getString(R.string.tag_fragment_home));
    }

    // Inflate the pull list fragment
    public void inflatePullListFragment()
    {
        // If the fragment does not exist, create it and add it to the stack, otherwise re-add it
        if(pullListFragment == null)
        {
            pullListFragment = new PullListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, pullListFragment, getString(R.string.tag_fragment_pull_list));
            transaction.commit();
            fragmentTags.add(getString(R.string.tag_fragment_pull_list));
            fragments.add(new FragmentTag(pullListFragment, getString(R.string.tag_fragment_pull_list)));
        }
        else
        {
            fragmentTags.remove(getString(R.string.tag_fragment_pull_list));
            fragmentTags.add(getString(R.string.tag_fragment_pull_list));
        }
        setFragmentVisibility(getString(R.string.tag_fragment_pull_list));
    }

    // Inflate the collection fragment
    public void inflateCollectionFragment()
    {
        // If the fragment does not exist, create it and add it to the stack, otherwise re-add it
        if(collectionFragment == null)
        {
            collectionFragment = new CollectionFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, collectionFragment, getString(R.string.tag_fragment_collection));
            transaction.commit();
            fragmentTags.add(getString(R.string.tag_fragment_collection));
            fragments.add(new FragmentTag(collectionFragment, getString(R.string.tag_fragment_collection)));
        }
        else
        {
            fragmentTags.remove(getString(R.string.tag_fragment_collection));
            fragmentTags.add(getString(R.string.tag_fragment_collection));
        }
        setFragmentVisibility(getString(R.string.tag_fragment_collection));
    }

    // Inflate the search fragment
    public void inflateSearchFragment()
    {
        // If the fragment does not exist, create it and add it to the stack, otherwise re-add it
        if(searchFragment == null)
        {
            searchFragment = new SearchFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, searchFragment, getString(R.string.tag_fragment_search));
            transaction.commit();
            fragmentTags.add(getString(R.string.tag_fragment_search));
            fragments.add(new FragmentTag(searchFragment, getString(R.string.tag_fragment_search)));
        }
        else
        {
            fragmentTags.remove(getString(R.string.tag_fragment_search));
            fragmentTags.add(getString(R.string.tag_fragment_search));
        }
        setFragmentVisibility(getString(R.string.tag_fragment_search));
    }

    // Run first login tutorial and welcome information
    private void isFirstLogin()
    {
        Log.d(TAG, "isFirstLogin: called");
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstLogin = preferences.getBoolean(PreferenceKeys.FIRST_TIME_LOGIN, true);

        if(isFirstLogin)
        {
            Log.d(TAG, "isFirstLogin if: called");

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(getString(R.string.first_time_user_message));
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Log.d(TAG, "Closing opening dialog");
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(PreferenceKeys.FIRST_TIME_LOGIN, false);
                    editor.apply();
                    dialog.dismiss();
                }
            });
            alertDialogBuilder.setIcon(R.mipmap.ic_compendia_icon);
            alertDialogBuilder.setTitle(" ");
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
