package com.gabrieldchartier.compendia.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gabrieldchartier.compendia.BaseActivity
import com.gabrieldchartier.compendia.FragmentInterface
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.models.Comic
import com.gabrieldchartier.compendia.models.ComicBox
import com.gabrieldchartier.compendia.ui.authentication.AuthActivity
import com.gabrieldchartier.compendia.ui.authentication.BaseAuthFragment
import com.gabrieldchartier.compendia.ui.main.collection.BoxDetailFragment
import com.gabrieldchartier.compendia.ui.main.collection.BoxesFragment
import com.gabrieldchartier.compendia.ui.main.comic.*
import com.gabrieldchartier.compendia.ui.main.home.BaseHomeFragment
import com.gabrieldchartier.compendia.ui.main.home.NewReleasesFragment
import com.gabrieldchartier.compendia.ui.main.home.SettingsFragment
import com.gabrieldchartier.compendia.util.BottomNavController
import com.gabrieldchartier.compendia.util.BottomNavController.*
import com.gabrieldchartier.compendia.util.setUpNavigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.UUID

class MainActivity : BaseActivity(), FragmentInterface, NavGraphProvider, OnNavigationGraphChanged, OnNavigationReselectedListener
{
    private lateinit var bottomNavigationView: BottomNavigationView
    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
        BottomNavController(
                this,
                R.id.main_nav_host_fragment,
                R.id.bottom_nav_home,
                this,
                this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // TODO isFirstLogin()
        //setupActionBar()
        bottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView.setUpNavigation(bottomNavController, this)
        if(savedInstanceState == null)
            bottomNavController.onNavigationItemSelected()
        subscribeObservers()
        displayBottomNav(true)
    }

//    private fun setupActionBar() {
//        setSupportActionBar(tool_bar)
//    }

    override fun onBackPressed() = bottomNavController.onBackPressed()

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun subscribeObservers() {
        sessionManager.cachedToken.observe(this, Observer {
            if(it == null || it.account_pk == -1 || it.token == null) {
                navAuthActivity()
                finish()
            }
        })
    }

    private fun navAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }


    // BottomNavController interface method implementations

    override fun getNavGraphId(itemId: Int) = when(itemId) {
        R.id.bottom_nav_home -> R.navigation.home_nav_graph

        R.id.bottom_nav_collection -> R.navigation.collection_nav_graph

        R.id.bottom_nav_pull_list -> R.navigation.pull_list_nav_graph

        R.id.bottom_nav_search -> R.navigation.search_nav_graph

        else -> R.navigation.home_nav_graph
    }

    override fun onGraphChange() {
        cancelActiveJobs()
    }

    private fun cancelActiveJobs() {
        val fragments = bottomNavController.fragmentManager.findFragmentById(bottomNavController.containerId)?.childFragmentManager?.fragments
        if(fragments != null) {
            for(fragment in fragments) {
                when(fragment) {
                    is BaseAuthFragment -> fragment.cancelActiveJobs()
                    is BaseHomeFragment -> fragment.cancelActiveJobs()
                }
            }
        }
        displayProgressBar(false)
    }

    override fun onReselectNavItem(navController: NavController, fragment: Fragment) {

        when(bottomNavigationView.selectedItemId) {

            R.id.bottom_nav_home -> {
                when(fragment) {

                    is BoxDetailFragment ->
                        navController.navigate(R.id.action_boxDetailFragment_to_homeFragment)

                    is BoxesFragment ->
                        navController.navigate(R.id.action_boxesFragment_to_homeFragment)

                    is ComicDetailFragment ->
                        navController.navigate(R.id.action_nested_comic_comicDetailFragment_to_homeFragment)

                    is CreatorDetailFragment ->
                        navController.navigate(R.id.action_nested_comic_creatorDetailFragment_to_homeFragment)

                    is CreatorsListFragment ->
                        navController.navigate(R.id.action_nested_comic_creatorsListFragment_to_homeFragment)

                    is FullCoverFragment ->
                        navController.navigate(R.id.action_nested_comic_fullCoverFragment_to_homeFragment)

                    is OtherVersionsFragment ->
                        navController.navigate(R.id.action_nested_comic_otherVersionsFragment_to_homeFragment)

                    is ReviewsFragment ->
                        navController.navigate(R.id.action_nested_comic_reviewsFragment_to_homeFragment)

                    is NewReleasesFragment ->
                        navController.navigate(R.id.action_newReleasesFragment_to_homeFragment)

                    is SettingsFragment ->
                        navController.navigate(R.id.action_settingsFragment_to_homeFragment)

                    else -> {
                        Log.e("MainActivity", "onReselectNavItem (line 101): Unused fragment passed in")
                    }
                }
            }

            R.id.bottom_nav_pull_list -> {
                when(fragment) {

                    is ComicDetailFragment ->
                        navController.navigate(R.id.action_nested_comic_comicDetailFragment_to_pullListFragment)

                    is CreatorDetailFragment ->
                        navController.navigate(R.id.action_nested_comic_creatorDetailFragment_to_pullListFragment)

                    is CreatorsListFragment ->
                        navController.navigate(R.id.action_nested_comic_creatorsListFragment_to_pullListFragment)

                    is FullCoverFragment ->
                        navController.navigate(R.id.action_nested_comic_fullCoverFragment_to_pullListFragment)

                    is OtherVersionsFragment ->
                        navController.navigate(R.id.action_nested_comic_otherVersionsFragment_to_pullListFragment)

                    is ReviewsFragment ->
                        navController.navigate(R.id.action_nested_comic_reviewsFragment_to_pullListFragment)

                    else -> {
                        Log.e("MainActivity", "onReselectNavItem (line 101): Unused fragment passed in")
                    }
                }
            }

            R.id.bottom_nav_collection -> {
                when(fragment) {

                    is ComicDetailFragment ->
                        navController.navigate(R.id.action_nested_comic_comicDetailFragment_to_collectionFragment)

                    is CreatorDetailFragment ->
                        navController.navigate(R.id.action_nested_comic_creatorDetailFragment_to_collectionFragment)

                    is CreatorsListFragment ->
                        navController.navigate(R.id.action_nested_comic_creatorsListFragment_to_collectionFragment)

                    is FullCoverFragment ->
                        navController.navigate(R.id.action_nested_comic_fullCoverFragment_to_collectionFragment)

                    is OtherVersionsFragment ->
                        navController.navigate(R.id.action_nested_comic_otherVersionsFragment_to_collectionFragment)

                    is ReviewsFragment ->
                        navController.navigate(R.id.action_nested_comic_reviewsFragment_to_collectionFragment)

                    else -> {
                        Log.e("MainActivity", "onReselectNavItem (line 101): Unused fragment passed in")
                    }
                }
            }

            R.id.bottom_nav_search -> {
                when(fragment) {

                    is ComicDetailFragment ->
                        navController.navigate(R.id.action_nested_comic_comicDetailFragment_to_searchFragment)

                    is CreatorDetailFragment ->
                        navController.navigate(R.id.action_nested_comic_creatorDetailFragment_to_searchFragment)

                    is CreatorsListFragment ->
                        navController.navigate(R.id.action_nested_comic_creatorsListFragment_to_searchFragment)

                    is FullCoverFragment ->
                        navController.navigate(R.id.action_nested_comic_fullCoverFragment_to_searchFragment)

                    is OtherVersionsFragment ->
                        navController.navigate(R.id.action_nested_comic_otherVersionsFragment_to_searchFragment)

                    is ReviewsFragment ->
                        navController.navigate(R.id.action_nested_comic_reviewsFragment_to_searchFragment)

                    else -> {
                        Log.e("MainActivity", "onReselectNavItem (line 101): Unused fragment passed in")
                    }
                }
            }
        }
    }

    override fun displayBottomNav(display: Boolean)
    {
        if (display) bottom_nav.visibility = View.VISIBLE else bottom_nav.visibility = View.GONE
    }

    override fun displayProgressBar(bool: Boolean) {
        if(bool)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.GONE
    }

    // Inflate the comic detail fragment and pass in the comic data
    override fun inflateComicDetailFragment(comic: Comic)
    {
        displayBottomNav(false)
    }

    // Inflate the other versions fragment passing in the list of other comic versions
    override fun inflateOtherVersionsFragment(navController: NavController, actionID: Int, otherVersions: Array<String>, comicTitle: String)
    {
        displayBottomNav(false)
        val bundle = Bundle()
        bundle.putStringArray(getString(R.string.intent_other_versions), otherVersions)
        bundle.putString(getString(R.string.intent_comic_title), comicTitle)
        navController.navigate(actionID, bundle)
    }

    override fun inflateSettingsFragment(navController: NavController)
    {
        displayBottomNav(false)
        navController.navigate(R.id.action_homeFragment_to_settingsFragment)
    }

    override fun inflateBoxesFragment()
    {
        displayBottomNav(false)
    }

    override fun inflateBoxDetailFragment(box: ComicBox)
    {
        displayBottomNav(false)
    }

    override fun inflateNewReleasesFragment()
    {
        displayBottomNav(false)
    }

    override fun inflateCreatorDetailFragment(navController: NavController, actionID: Int, creatorID: UUID)
    {
        displayBottomNav(false)
        val bundle = Bundle()
        bundle.putSerializable(getString(R.string.intent_creator_id), creatorID)
        navController.navigate(actionID, bundle)
    }

    override fun inflateReviewsFragment(navController: NavController, actionID: Int, comicID: UUID)
    {
        displayBottomNav(false)
        val bundle = Bundle()
        bundle.putSerializable(getString(R.string.intent_comic_id), comicID)
        navController.navigate(actionID, bundle)
    }

    override fun inflateFullCoverFragment(navController: NavController, actionID: Int, cover: String)
    {
        displayBottomNav(false)
        val bundle = Bundle()
        bundle.putString(getString(R.string.intent_comic_cover), cover)
        navController.navigate(actionID, bundle)
    }

//TODO Run first login tutorial and welcome information
//    private fun isFirstLogin() {
//        Log.d(TAG, "isFirstLogin: called")
//        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
//        val isFirstLogin = preferences.getBoolean(PreferenceKeys.FIRST_TIME_LOGIN, true)
//
//        if (isFirstLogin) {
//            Log.d(TAG, "isFirstLogin if: called")
//
//            val alertDialogBuilder = AlertDialog.Builder(this)
//            alertDialogBuilder.setMessage(getString(R.string.first_time_user_message))
//            alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
//                Log.d(TAG, "Closing opening dialog")
//                val editor = preferences.edit()
//                editor.putBoolean(PreferenceKeys.FIRST_TIME_LOGIN, false)
//                editor.apply()
//                dialog.dismiss()
//            }
//            alertDialogBuilder.setIcon(R.mipmap.ic_compendia_icon)
//            alertDialogBuilder.setTitle(" ")
//            val alertDialog = alertDialogBuilder.create()
//            alertDialog.show()
//        }
//    }
}