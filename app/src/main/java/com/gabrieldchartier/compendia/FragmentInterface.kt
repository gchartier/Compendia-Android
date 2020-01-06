package com.gabrieldchartier.compendia

import androidx.navigation.NavController

import com.gabrieldchartier.compendia.models.Comic
import com.gabrieldchartier.compendia.models.ComicBox
import java.util.UUID

interface FragmentInterface {
    fun inflateComicDetailFragment(comic: Comic)
    fun inflateOtherVersionsFragment(navController: NavController, actionID: Int, otherVersions: Array<String>, comicTitle: String)
    fun inflateSettingsFragment(navController: NavController)
    fun inflateBoxesFragment()
    fun inflateBoxDetailFragment(box: ComicBox)
    fun inflateNewReleasesFragment()
    fun inflateCreatorDetailFragment(navController: NavController, actionID: Int, creatorID: UUID)
    fun inflateReviewsFragment(navController: NavController, actionID: Int, comicID: UUID)
    fun inflateFullCoverFragment(navController: NavController, actionID: Int, cover: String)
    fun onBackPressed()
    fun displayBottomNav(display: Boolean)
}
