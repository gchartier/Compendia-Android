package com.gabrieldchartier.compendia;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.gabrieldchartier.compendia.fragments.BoxDetailFragment;
import com.gabrieldchartier.compendia.fragments.BoxesFragment;
import com.gabrieldchartier.compendia.fragments.CollectionFragment;
import com.gabrieldchartier.compendia.fragments.ComicDetailFragment;
import com.gabrieldchartier.compendia.fragments.CreatorDetailFragment;
import com.gabrieldchartier.compendia.fragments.CreatorsListFragment;
import com.gabrieldchartier.compendia.fragments.FullCoverFragment;
import com.gabrieldchartier.compendia.fragments.HomeFragment;
import com.gabrieldchartier.compendia.fragments.NewReleasesFragment;
import com.gabrieldchartier.compendia.fragments.OtherVersionsFragment;
import com.gabrieldchartier.compendia.fragments.PullListFragment;
import com.gabrieldchartier.compendia.fragments.ReviewsFragment;
import com.gabrieldchartier.compendia.fragments.SearchFragment;
import com.gabrieldchartier.compendia.fragments.SettingsFragment;
import com.gabrieldchartier.compendia.models.Comic;
import com.gabrieldchartier.compendia.models.ComicBox;
import com.gabrieldchartier.compendia.models.ComicCreator;
import com.gabrieldchartier.compendia.models.CreatedFragment;
import com.gabrieldchartier.compendia.util.PreferenceKeys;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity
        extends AppCompatActivity
        implements FragmentInterface, BottomNavigationViewEx.OnNavigationItemSelectedListener
{
    // Constants
    private static final int BOTTOM_NAV_HOME = 0;
    private static final int BOTTOM_NAV_PULL_LIST = 1;
    private static final int BOTTOM_NAV_COLLECTION = 2;
    private static final int BOTTOM_NAV_SEARCH = 3;
    private static final String TAG = "MainActivity";

    // Views
    private BottomNavigationViewEx bottomNav;

    // Fragments
    private HomeFragment homeFragment;
    private PullListFragment pullListFragment;
    private CollectionFragment collectionFragment;
    private SearchFragment searchFragment;
    private List<ComicDetailFragment> comicDetailFragments = new ArrayList<>();
    private ComicDetailFragment comicDetailFragment;
    private OtherVersionsFragment otherVersionsFragment;
    private NewReleasesFragment newReleasesFragment;
    private SettingsFragment settingsFragment;
    private BoxesFragment boxesFragment;
    private BoxDetailFragment boxDetailFragment;
    private CreatorDetailFragment creatorDetailFragment;
    private CreatorsListFragment creatorsListFragment;
    private FullCoverFragment fullCoverFragment;
    private ReviewsFragment reviewsFragment;

    // Variables
    private ArrayList<String> fragmentTagsOnStack = new ArrayList<>();
    private ArrayList<CreatedFragment> fragments = new ArrayList<>();
    private ArrayList<String> hideNavBarFragments = new ArrayList<>();
    private int appExitAttempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavBarFragments.add(getString(R.string.tag_fragment_comic_detail));
        hideNavBarFragments.add(getString(R.string.tag_fragment_other_versions));
        hideNavBarFragments.add(getString(R.string.tag_fragment_new_releases));
        hideNavBarFragments.add(getString(R.string.tag_fragment_box_detail));
        hideNavBarFragments.add(getString(R.string.tag_fragment_boxes));
        hideNavBarFragments.add(getString(R.string.tag_fragment_settings));
        hideNavBarFragments.add(getString(R.string.tag_fragment_creators_list));
        hideNavBarFragments.add(getString(R.string.tag_fragment_creator_detail));
        hideNavBarFragments.add(getString(R.string.tag_fragment_reviews));
        hideNavBarFragments.add(getString(R.string.tag_fragment_full_cover));
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
            bottomNav.setVisibility(View.GONE);
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
                fragmentTagsOnStack.clear();
                fragmentTagsOnStack = new ArrayList<>();
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
        Log.d(TAG, "Setting fragment visibility for " + tag);

        // Toggle visibility of the bottom nav based on fragment tag
        if(hideNavBarFragments.contains(tag))
            hideBottomNav();
        else
            showBottomNav();

        //TODO ensure this works with the updated for loop
        // Show the fragment matching the tag passed in and hide the rest
        for(int i = 0; i < fragments.size(); i++)
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(tag.equals(fragments.get(i).getTag()))
            {
                Log.d(TAG, "In Visibility: Showing fragment " + fragments.get(i).getTag());
                transaction.show(fragments.get(i).getFragment());
            }
            else
            {
                Log.d(TAG, "In Visibility: Hiding fragment " + fragments.get(i).getTag());
                transaction.hide(fragments.get(i).getFragment());
            }
            transaction.commit();
        }
        setBottomNavIcon(tag);
    }

    @Override
    public void onBackPressed()
    {
        int backStackCount = fragmentTagsOnStack.size();

        Log.d(TAG, "onBackPressed stack count" + backStackCount);

        if(backStackCount > 1)
        {
            // Scroll to the top of the screen on navigate back in the collection screen
            if(fragmentTagsOnStack.get(backStackCount - 1).equals(getString(R.string.tag_fragment_collection)))
                collectionFragment.scrollToTop();

            Log.d(TAG, "Pre visibility fragments: " + fragments.toString());
            Log.d(TAG, "Pre visibility fragtags: " + fragmentTagsOnStack.toString());
            setFragmentVisibility(fragmentTagsOnStack.get(backStackCount - 2));
            if(fragmentTagsOnStack.get(backStackCount - 1).equals(getString(R.string.tag_fragment_comic_detail) + comicDetailFragments.size()))
            {
                Log.d(TAG, "Condition in on back pressed is true");
                for(CreatedFragment f : fragments)
                {
                    if(f.getTag().equals(getString(R.string.tag_fragment_comic_detail) + comicDetailFragments.size()))
                    {
                        Log.d(TAG, "Removing fragment " + f.getTag());
                        fragments.remove(f);
                        getSupportFragmentManager().beginTransaction().remove(comicDetailFragments.get(comicDetailFragments.size()-1)).commitAllowingStateLoss();
                        comicDetailFragments.remove(comicDetailFragments.size()-1);
                        Log.d(TAG, "onbackpressed: fragments " + fragments.toString());
                        Log.d(TAG, "onbackpressed: comicDetailFragments " + comicDetailFragments.toString());
                        Log.d(TAG, "onbackpressed: fragment manager " + getSupportFragmentManager().getFragments().toString());
                    }
                }
            }
            Log.d(TAG, "Removing fragment tag " + fragmentTagsOnStack.get(backStackCount - 1));
            fragmentTagsOnStack.remove(backStackCount - 1);
            Log.d(TAG, "Fragments after removal " + fragments.size());
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

        Log.d(TAG, "2 Fragments: " + fragments.toString());
        Log.d(TAG, "2 FTags: " + fragmentTagsOnStack.toString());
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

    // Fragment inflation methods
    // Inflate the comic detail fragment and pass in the comic data
    @Override
    public void inflateComicDetailFragment(Comic comic)
    {
        // Remove comic detail fragment if it exists
//        if(comicDetailFragments.size() > 0)
//            if(comicDetailFragments.get(comicDetailFragments.size()-1) != null)
//                getSupportFragmentManager().beginTransaction().remove(comicDetailFragments.get(comicDetailFragments.size()-1)).commitAllowingStateLoss();

        // Create fragment, set the comic data passed in, and add the fragment to the back stack
//        comicDetailFragments.add(new ComicDetailFragment());
//        Bundle args = new Bundle();
//        args.putParcelable(getString(R.string.intent_comic), comic);
//        comicDetailFragments.get(comicDetailFragments.size()-1).setArguments(args);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
//        transaction.add(R.id.main_content_frame, comicDetailFragments.get(comicDetailFragments.size()-1), getString(R.string.tag_fragment_comic_detail) + comicDetailFragments.size());
//        transaction.commit();
//        fragmentTagsOnStack.add(getString(R.string.tag_fragment_comic_detail) + comicDetailFragments.size());
//        fragments.add(new CreatedFragment(comicDetailFragment, getString(R.string.tag_fragment_comic_detail) + comicDetailFragments.size()));
//        setFragmentVisibility(getString(R.string.tag_fragment_comic_detail) + comicDetailFragments.size());
//        Log.d(TAG, "Fragments: " + fragments.toString());
//        Log.d(TAG, "FTags: " + fragmentTagsOnStack.toString());
//        // Remove comic detail fragment if it exists
//        //if(comicDetailFragment != null)
//            //getSupportFragmentManager().beginTransaction().remove(comicDetailFragment).commitAllowingStateLoss();
//
        //TODO bug here having to do with multiple comic detail fragments overwriting each other
        // Create fragment, set the comic passed in, and add the fragment to the back stack
        comicDetailFragments.add(new ComicDetailFragment());
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.intent_comic), comic);
        comicDetailFragments.get(comicDetailFragments.size()-1).setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.add(R.id.main_content_frame, comicDetailFragments.get(comicDetailFragments.size()-1), getString(R.string.tag_fragment_comic_detail)+comicDetailFragments.size());
        transaction.commit();
        fragmentTagsOnStack.add(getString(R.string.tag_fragment_comic_detail)+comicDetailFragments.size());
        //if(fragmentAlreadyExists(getString(R.string.tag_fragment_comic_detail)))
            fragments.add(new CreatedFragment(comicDetailFragments.get(comicDetailFragments.size()-1), getString(R.string.tag_fragment_comic_detail)+comicDetailFragments.size()));
        setFragmentVisibility(getString(R.string.tag_fragment_comic_detail)+comicDetailFragments.size());
    }

    // Inflate the other versions fragment passing in the list of other comic versions
    @Override
    public void inflateOtherVersionsFragment(String[] otherVersions, String comicTitle)
    {
        // Remove comic detail fragment if it exists
        if(otherVersionsFragment != null)
            getSupportFragmentManager().beginTransaction().remove(otherVersionsFragment).commitAllowingStateLoss();

        // Create fragment, set the comic data passed in, and add the fragment to the back stack
        otherVersionsFragment = new OtherVersionsFragment();
        Bundle args = new Bundle();
        Log.d(TAG, "OtherVersions = " + otherVersions[0]);
        args.putStringArray(getString(R.string.intent_other_versions), otherVersions);
        args.putString(getString(R.string.intent_comic_title), comicTitle);
        otherVersionsFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.add(R.id.main_content_frame, otherVersionsFragment, getString(R.string.tag_fragment_other_versions));
        transaction.commit();
        fragmentTagsOnStack.add(getString(R.string.tag_fragment_other_versions));
        fragments.add(new CreatedFragment(otherVersionsFragment, getString(R.string.tag_fragment_other_versions)));
        setFragmentVisibility(getString(R.string.tag_fragment_other_versions));
    }

    @Override
    public void inflateSettingsFragment()
    {
        // If the fragment does not exist, create it and add it to the stack, otherwise re-add it
        if(settingsFragment == null)
        {
            settingsFragment = new SettingsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, settingsFragment, getString(R.string.tag_fragment_settings));
            transaction.commit();
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_settings));
            fragments.add(new CreatedFragment(settingsFragment, getString(R.string.tag_fragment_settings)));
        }
        else
        {
            fragmentTagsOnStack.remove(getString(R.string.tag_fragment_settings));
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_settings));
        }
        setFragmentVisibility(getString(R.string.tag_fragment_settings));
    }

    @Override
    public void inflateBoxesFragment()
    {
        // If the fragment does not exist, create it and add it to the stack, otherwise re-add it
        if(boxesFragment == null)
        {
            boxesFragment = new BoxesFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, boxesFragment, getString(R.string.tag_fragment_boxes));
            transaction.commit();
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_boxes));
            fragments.add(new CreatedFragment(boxesFragment, getString(R.string.tag_fragment_boxes)));
        }
        else
        {
            fragmentTagsOnStack.remove(getString(R.string.tag_fragment_boxes));
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_boxes));
        }
        setFragmentVisibility(getString(R.string.tag_fragment_boxes));
    }

    @Override
    public void inflateBoxDetailFragment(ComicBox box)
    {
        // If the fragment does not exist, create it and add it to the stack, otherwise re-add it
        if(boxDetailFragment == null)
        {
            boxDetailFragment = new BoxDetailFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, boxDetailFragment, getString(R.string.tag_fragment_box_detail));
            transaction.commit();
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_box_detail));
            fragments.add(new CreatedFragment(boxDetailFragment, getString(R.string.tag_fragment_box_detail)));
        }
        else
        {
            fragmentTagsOnStack.remove(getString(R.string.tag_fragment_box_detail));
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_box_detail));
        }
        setFragmentVisibility(getString(R.string.tag_fragment_box_detail));
    }

    @Override
    public void inflateNewReleasesFragment()
    {
        // If the fragment does not exist, create it and add it to the stack, otherwise re-add it
        if(newReleasesFragment == null)
        {
            newReleasesFragment = new NewReleasesFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, newReleasesFragment, getString(R.string.tag_fragment_new_releases));
            transaction.commit();
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_new_releases));
            fragments.add(new CreatedFragment(newReleasesFragment, getString(R.string.tag_fragment_new_releases)));
        }
        else
        {
            fragmentTagsOnStack.remove(getString(R.string.tag_fragment_new_releases));
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_new_releases));
        }
        setFragmentVisibility(getString(R.string.tag_fragment_new_releases));
    }

    @Override
    public void inflateCreatorDetailFragment(UUID creatorID)
    {
        // If the fragment does not exist, create it and add it to the stack, otherwise re-add it
        if(creatorDetailFragment == null)
        {
            creatorDetailFragment = new CreatorDetailFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, creatorDetailFragment, getString(R.string.tag_fragment_creator_detail));
            transaction.commit();
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_creator_detail));
            fragments.add(new CreatedFragment(creatorDetailFragment, getString(R.string.tag_fragment_creator_detail)));
        }
        else
        {
            fragmentTagsOnStack.remove(getString(R.string.tag_fragment_creator_detail));
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_creator_detail));
        }
        setFragmentVisibility(getString(R.string.tag_fragment_creator_detail));
    }

    @Override
    public void inflateCreatorsListFragment(List<ComicCreator> creators)
    {
        // If the fragment does not exist, create it and add it to the stack, otherwise re-add it
        if(creatorsListFragment == null)
        {
            creatorsListFragment = new CreatorsListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, creatorsListFragment, getString(R.string.tag_fragment_creators_list));
            transaction.commit();
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_creators_list));
            fragments.add(new CreatedFragment(creatorsListFragment, getString(R.string.tag_fragment_creators_list)));
        }
        else
        {
            fragmentTagsOnStack.remove(getString(R.string.tag_fragment_creators_list));
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_creators_list));
        }
        setFragmentVisibility(getString(R.string.tag_fragment_creators_list));
    }

    @Override
    public void inflateReviewsFragment(UUID comicID)
    {
        // If the fragment does not exist, create it and add it to the stack, otherwise re-add it
        if(reviewsFragment == null)
        {
            reviewsFragment = new ReviewsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, reviewsFragment, getString(R.string.tag_fragment_reviews));
            transaction.commit();
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_reviews));
            fragments.add(new CreatedFragment(reviewsFragment, getString(R.string.tag_fragment_reviews)));
        }
        else
        {
            fragmentTagsOnStack.remove(getString(R.string.tag_fragment_reviews));
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_reviews));
        }
        setFragmentVisibility(getString(R.string.tag_fragment_reviews));
    }

    @Override
    public void inflateFullCoverFragment(String cover)
    {
        // If the fragment does not exist, create it and add it to the stack, otherwise re-add it
        if(fullCoverFragment == null)
        {
            fullCoverFragment = new FullCoverFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, fullCoverFragment, getString(R.string.tag_fragment_full_cover));
            transaction.commit();
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_full_cover));
            fragments.add(new CreatedFragment(fullCoverFragment, getString(R.string.tag_fragment_full_cover)));
        }
        else
        {
            fragmentTagsOnStack.remove(getString(R.string.tag_fragment_full_cover));
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_full_cover));
        }
        setFragmentVisibility(getString(R.string.tag_fragment_full_cover));
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
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_home));
            fragments.add(new CreatedFragment(homeFragment, getString(R.string.tag_fragment_home)));
        }
        else
        {
            fragmentTagsOnStack.remove(getString(R.string.tag_fragment_home));
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_home));
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
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_pull_list));
            fragments.add(new CreatedFragment(pullListFragment, getString(R.string.tag_fragment_pull_list)));
        }
        else
        {
            fragmentTagsOnStack.remove(getString(R.string.tag_fragment_pull_list));
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_pull_list));
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
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_collection));
            fragments.add(new CreatedFragment(collectionFragment, getString(R.string.tag_fragment_collection)));
        }
        else
        {
            fragmentTagsOnStack.remove(getString(R.string.tag_fragment_collection));
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_collection));
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
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_search));
            fragments.add(new CreatedFragment(searchFragment, getString(R.string.tag_fragment_search)));
        }
        else
        {
            fragmentTagsOnStack.remove(getString(R.string.tag_fragment_search));
            fragmentTagsOnStack.add(getString(R.string.tag_fragment_search));
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

    private boolean fragmentAlreadyExists(String fragmentTag)
    {
        for(CreatedFragment tag : fragments)
            if(tag.getTag().equals(fragmentTag))
                return true;
        return false;
    }
}
