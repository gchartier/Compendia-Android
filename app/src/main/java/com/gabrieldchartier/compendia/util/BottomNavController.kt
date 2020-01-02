package com.gabrieldchartier.compendia.util

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.util.BottomNavController.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.view.*

//todo understand this code and refactor
// Inspiration credit from stack overflow user Allan Veloso.
// https://stackoverflow.com/questions/50577356/android-jetpack-navigation-bottomnavigationview-with-youtube-or-instagram-like
class BottomNavController (
        val context: Context,
        @IdRes val containerId: Int,
        @IdRes val appStartDestinationId: Int,
        val graphChangeListener: OnNavigationGraphChanged?,
        val navGraphProvider: NavGraphProvider
) {
    private val navigationBackStack = BackStack.of(appStartDestinationId)
    lateinit var activity: Activity
    lateinit var fragmentManager: FragmentManager
    lateinit var navItemChangeListener: OnNavigationItemChanged

    init {
        if (context is Activity) {
            activity = context
            fragmentManager = (activity as FragmentActivity).supportFragmentManager
        }
    }

    fun onNavigationItemSelected(itemId: Int = navigationBackStack.last()): Boolean {

        Log.e("BottomNavController", "onNavigationItemSelected (line 42): NAVIGATIONBackSTACK.LAST() = ${navigationBackStack.last()}")

        // Replace fragment representing a navigation item
        val fragment = fragmentManager.findFragmentByTag(itemId.toString()) ?: NavHostFragment.create(navGraphProvider.getNavGraphId(itemId))
        Log.e("BottomNavController", "onNavigationItemSelected (line 46): ${fragment.tag}")
        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.fade_out
                )
                .replace(containerId, fragment, itemId.toString())
                .addToBackStack(null)
                .commit()

        // Add to back stack
        navigationBackStack.moveLast(itemId)

        // Update checked icon
        navItemChangeListener.onItemChanged(itemId)

        // Communicate with Activity
        graphChangeListener?.onGraphChange()

        return true
    }

    fun onBackPressed() {
        val childFragmentManager = fragmentManager.findFragmentById(containerId)!!
                .childFragmentManager

        Log.d("BottomNavController", "bnc: startId: ${appStartDestinationId}, last: ${navigationBackStack.last()}")
        for(id in navigationBackStack){
            Log.d("BottomNavController", "bnc list: id: $id")
        }

        when {
            // We should always try to go back on the child fragment manager stack before going to
            // the navigation stack. It's important to use the child fragment manager instead of the
            // NavController because if the user change tabs super fast commit of the
            // supportFragmentManager may mess up with the NavController child fragment manager back
            // stack

            childFragmentManager.popBackStackImmediate() -> { Log.d("BottomNavController", "BNC: popping child") }
            // Fragment back stack is empty so try to go back on the navigation stack
            navigationBackStack.size > 1 -> {
                Log.d("BottomNavController", "BNC: backstack size > 1")

                // Remove last item from back stack
                navigationBackStack.removeLast()

                // Update the container with new fragment
                onNavigationItemSelected()

            }
            // If the stack has only one and it's not the navigation home we should
            // ensure that the application always leave from startDestination
            navigationBackStack.last() != appStartDestinationId -> {
                Log.d("BottomNavController", "BNC: start != current")

                navigationBackStack.removeLast()
                navigationBackStack.add(0, appStartDestinationId)
                onNavigationItemSelected()
            }
            // Navigation stack is empty, so finish the activity
            else -> activity.finish()
        }
    }

    private class BackStack : ArrayList<Int>() {
        companion object {
            fun of(vararg elements: Int): BackStack {
                val b = BackStack()
                b.addAll(elements.toTypedArray())
                return b
            }
        }

        fun removeLast() = removeAt(size - 1)

        fun moveLast(item: Int) {
            remove(item) // if present, remove
            add(item) // add to end of list
        }
    }


    // For setting the checked icon in the bottom nav
    interface OnNavigationItemChanged {
        fun onItemChanged(itemId: Int)
    }

    // Get id of each graph
    // ex: R.navigation.nav_blog
    // ex: R.navigation.nav_create_blog
    interface NavGraphProvider {
        @NavigationRes
        fun getNavGraphId(itemId: Int): Int
    }

    // Execute when Navigation Graph changes.
    // ex: Select a new item on the bottom navigation
    // ex: Home -> Account
    interface OnNavigationGraphChanged {
        fun onGraphChange()
    }

    interface OnNavigationReselectedListener {

        fun onReselectNavItem(navController: NavController, fragment: Fragment)
    }

    interface OnDestinationChangedListener {

        fun onDestinationChanged(navController: NavController)
    }

    fun setOnItemNavigationChanged(listener: (itemId: Int) -> Unit) {
        this.navItemChangeListener = object : OnNavigationItemChanged {
            override fun onItemChanged(itemId: Int) {
                Log.d("BottomNavController", "onItemChanged (line 165): $itemId")
                listener.invoke(itemId)
            }
        }
    }

}

// Convenience extension to set up the navigation
fun BottomNavigationView.setUpNavigation(
        bottomNavController: BottomNavController,
        onReselectListener: OnNavigationReselectedListener
) {

    setOnNavigationItemSelectedListener {
        Log.e("Cannot invoke method length() on null object", "setUpNavigation (line 163): REEEEEEE $it")
        bottomNavController.onNavigationItemSelected(it.itemId)
    }

    setOnNavigationItemReselectedListener {
        bottomNavController
                .fragmentManager
                .findFragmentById(bottomNavController.containerId)!!
                .childFragmentManager
                .fragments[0]?.let { fragment ->

            onReselectListener.onReselectNavItem(
                    bottomNavController.activity.findNavController(bottomNavController.containerId),
                    fragment
            )
        }
    }

    bottomNavController.setOnItemNavigationChanged { itemId ->
        Log.e("Cannot invoke method length() on null object", "setUpNavigation (line 181): itemId = ${itemId}")
        menu.findItem(itemId).isChecked = true
    }
}