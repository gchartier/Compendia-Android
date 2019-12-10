package com.gabrieldchartier.compendia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.navigation.ui.setupWithNavController
import com.gabrieldchartier.compendia.models.Comic
import com.gabrieldchartier.compendia.models.ComicBox
import com.gabrieldchartier.compendia.models.ComicCreator
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import kotlinx.android.synthetic.main.activity_main.*
import java.util.UUID

class MainActivity : AppCompatActivity(), FragmentInterface, BottomNavigationView.OnNavigationItemSelectedListener
{
    companion object
    {
        // Constants
        private const val BOTTOM_NAV_HOME = 0
        private const val BOTTOM_NAV_PULL_LIST = 1
        private const val BOTTOM_NAV_COLLECTION = 2
        private const val BOTTOM_NAV_SEARCH = 3
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // TODO isFirstLogin()
        initBottomNav(Navigation.findNavController(this, R.id.nav_host_fragment))
    }

    // Initialize the bottom navigation view
    private fun initBottomNav(navController: NavController)
    {
        Log.d(TAG, "Building bottom nav view")
        bottom_nav?.setupWithNavController(navController)
        bottom_nav?.enableAnimation(false)
        bottom_nav?.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottom_nav?.isItemHorizontalTranslationEnabled = false
    }

    // Hide the bottom navigation view
    override fun hideBottomNav()
    {
        bottom_nav.visibility = View.GONE
    }

    // Show the bottom navigation view
    override fun showBottomNav()
    {
        bottom_nav.visibility = View.VISIBLE
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId)
                {
                    R.id.bottom_nav_home -> {
                        Log.d(TAG, "Home bottom nav icon clicked")
                        showBottomNav()
                    }
                    R.id.bottom_nav_pull_list -> {
                        Log.d(TAG, "Home bottom nav icon clicked")
                        showBottomNav()
                    }
                    R.id.bottom_nav_collection -> {
                        Log.d(TAG, "Home bottom nav icon clicked")
                        showBottomNav()
                    }
                    R.id.bottom_nav_search -> {
                        Log.d(TAG, "Home bottom nav icon clicked")
                        showBottomNav()
                    }

                }
        return false
    }

    override fun onBackPressed()
    {
        Log.d(TAG, "ENTRY: " + supportFragmentManager.backStackEntryCount)
        super.onBackPressed()

        val bottomMenu = bottom_nav!!.menu
        when(supportFragmentManager.primaryNavigationFragment?.id)
        {
            R.id.nav_host_fragment -> {
                showBottomNav()
                bottomMenu.getItem(BOTTOM_NAV_HOME).isChecked = true
            }
            R.id.bottom_nav_collection -> {
                showBottomNav()
                bottomMenu.getItem(BOTTOM_NAV_COLLECTION).isChecked = true
            }
            R.id.bottom_nav_pull_list -> {
                showBottomNav()
                bottomMenu.getItem(BOTTOM_NAV_PULL_LIST).isChecked = true
            }
            R.id.bottom_nav_search -> {
                showBottomNav()
                bottomMenu.getItem(BOTTOM_NAV_SEARCH).isChecked = true
            }
        }

        //TODO app exit toast
//        if(supportFragmentManager.backStackEntryCount == 0)
//        {
//            appExitAttempts++
//            Toast.makeText(this, getString(R.string.exit_toast), Toast.LENGTH_SHORT).show()
//        }
//        if(appExitAttempts >= 2)
//        {
//            Log.d(TAG, "Calling exit")
//            super.onBackPressed()
//            appExitAttempts = 0
//        }

    }

    // Inflate the comic detail fragment and pass in the comic data
    override fun inflateComicDetailFragment(comic: Comic)
    {
        hideBottomNav()
    }

    // Inflate the other versions fragment passing in the list of other comic versions
    override fun inflateOtherVersionsFragment(navController: NavController, actionID: Int, otherVersions: Array<String>, comicTitle: String)
    {
        hideBottomNav()
        val bundle = Bundle()
        bundle.putStringArray(getString(R.string.intent_other_versions), otherVersions)
        bundle.putString(getString(R.string.intent_comic_title), comicTitle)
        navController.navigate(actionID, bundle)
    }

    override fun inflateSettingsFragment(navController: NavController)
    {
        hideBottomNav()
        navController.navigate(R.id.action_homeFragment_to_settingsFragment)
    }

    override fun inflateBoxesFragment()
    {
        hideBottomNav()
    }

    override fun inflateBoxDetailFragment(box: ComicBox)
    {
        hideBottomNav()
    }

    override fun inflateNewReleasesFragment()
    {
        hideBottomNav()
    }

    override fun inflateCreatorDetailFragment(navController: NavController, actionID: Int, creatorID: UUID)
    {
        hideBottomNav()
        val bundle = Bundle()
        bundle.putSerializable(getString(R.string.intent_creator_id), creatorID)
        navController.navigate(actionID, bundle)
    }

    override fun inflateCreatorsListFragment(navController: NavController, actionID: Int, creators: MutableList<ComicCreator>)
    {
        hideBottomNav()
        val bundle = Bundle()
        bundle.putSerializable(getString(R.string.intent_creators), ArrayList(creators))
        navController.navigate(actionID, bundle)
    }

    override fun inflateReviewsFragment(navController: NavController, actionID: Int, comicID: UUID)
    {
        hideBottomNav()
        val bundle = Bundle()
        bundle.putSerializable(getString(R.string.intent_comic_id), comicID)
        navController.navigate(actionID, bundle)
    }

    override fun inflateFullCoverFragment(navController: NavController, actionID: Int, cover: String)
    {
        hideBottomNav()
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
