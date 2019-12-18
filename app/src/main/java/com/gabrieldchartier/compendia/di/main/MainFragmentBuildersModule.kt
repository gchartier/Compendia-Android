package com.gabrieldchartier.compendia.di.main

import com.gabrieldchartier.compendia.ui.main.collection.BoxDetailFragment
import com.gabrieldchartier.compendia.ui.main.collection.BoxesFragment
import com.gabrieldchartier.compendia.ui.main.collection.CollectionFragment
import com.gabrieldchartier.compendia.ui.main.comic.*
import com.gabrieldchartier.compendia.ui.main.home.HomeFragment
import com.gabrieldchartier.compendia.ui.main.home.NewReleasesFragment
import com.gabrieldchartier.compendia.ui.main.home.SettingsFragment
import com.gabrieldchartier.compendia.ui.main.pull_list.PullListFragment
import com.gabrieldchartier.compendia.ui.main.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeBoxDetailFragment(): BoxDetailFragment

    @ContributesAndroidInjector()
    abstract fun contributeBoxesFragment(): BoxesFragment

    @ContributesAndroidInjector()
    abstract fun contributeCollectionFragment(): CollectionFragment

    @ContributesAndroidInjector()
    abstract fun contributeComicDetailFragment(): ComicDetailFragment

    @ContributesAndroidInjector()
    abstract fun contributeCreatorDetailFragment(): CreatorDetailFragment

    @ContributesAndroidInjector()
    abstract fun contributeCreatorsListFragment(): CreatorsListFragment

    @ContributesAndroidInjector()
    abstract fun contributeFullCoverFragment(): FullCoverFragment

    @ContributesAndroidInjector()
    abstract fun contributeOtherVersionsFragment(): OtherVersionsFragment

    @ContributesAndroidInjector()
    abstract fun contributeReviewsFragment(): ReviewsFragment

    @ContributesAndroidInjector()
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector()
    abstract fun contributeNewReleasesFragment(): NewReleasesFragment

    @ContributesAndroidInjector()
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector()
    abstract fun contributePullListFragment(): PullListFragment

    @ContributesAndroidInjector()
    abstract fun contributeSearchFragment(): SearchFragment

}