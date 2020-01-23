package com.gabrieldchartier.compendia.ui.main.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.models.*
import com.gabrieldchartier.compendia.recycler_views.HorizontalComicCoverListAdapter
import com.gabrieldchartier.compendia.ui.main.MainActivity
import com.gabrieldchartier.compendia.ui.main.home.state.HomeStateEvent
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseHomeFragment(), HorizontalComicCoverListAdapter.Interaction, View.OnClickListener
{
    @Inject
    lateinit var requestManager: RequestManager

    @Inject
    lateinit var requestOptions: RequestOptions

    private lateinit var newReleaseCoversAdapter: HorizontalComicCoverListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).displayBottomNav(true)
        initRecyclerViews()
        subscribeObservers()
        //getComicBoxes()
        getNewReleases()
        //getCollectionDetails()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeNewReleasesRecyclerView.adapter = null
    }

    override fun onStart() {
        super.onStart()
        homeSettingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }
    }

    private fun getComicBoxes() {
        viewModel.setStateEvent(HomeStateEvent.GetComicBoxesEvent())
    }

    private fun getNewReleases() {
        viewModel.setStateEvent(HomeStateEvent.GetNewReleasesEvent())
    }

    private fun getCollectionDetails() {
        viewModel.setStateEvent(HomeStateEvent.GetCollectionDetailsEvent())
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer {
            stateChangeListener.onDataStateChange(it)
            it?.let {dataState ->
                dataState.data?.let { data ->
                    data.data?.let { event ->
                        event.getContentIfNotHandled()?.let { viewState ->
                            viewState.homeFields.newReleases?.let { newReleases ->
                                viewModel.setNewReleases(newReleases)
                            }

                            viewState.homeFields.comicBoxes?.let { comicBoxes ->
                                viewModel.setComicBoxes(comicBoxes)
                            }

                            viewState.homeFields.collectionDetails?.let { collectionDetails ->
                                viewModel.setCollectionDetails(collectionDetails)
                            }
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {  viewState ->
            viewState.homeFields.newReleases?.let { newReleases ->
                if(newReleases.isEmpty()) {
                    Log.d("HomeFragment", "subscribeObservers (line 81): new releases list was empty...")
                    homeNoNewReleasesText.visibility = View.VISIBLE
                    homeSeeAllNewReleasesText.visibility = View.GONE
                    homeSeeAllNewReleasesButton.visibility = View.GONE
                    homeNewReleasesRecyclerView.visibility = View.GONE
                }
                else {
                    newReleaseCoversAdapter.submitList(newReleases)
                    homeSeeAllNewReleasesText.visibility = View.VISIBLE
                    homeSeeAllNewReleasesButton.visibility = View.VISIBLE
                    homeNewReleasesRecyclerView.visibility = View.VISIBLE
                    homeNoNewReleasesText.visibility = View.GONE
                }
                Log.d("HomeFragment", "subscribeObservers (line 91): ${newReleaseCoversAdapter.itemCount}")
            }

            viewState.homeFields.comicBoxes?.let { comicBoxes ->
                Log.d("HomeFragment", "subscribeObservers (line 102): entered view state listener for comicboxes")
                if(comicBoxes.size >= 3) {
                    homeComicBoxText1.text = comicBoxes[0].name
                    homeComicBoxText2.text = comicBoxes[1].name
                    homeComicBoxText3.text = comicBoxes[2].name

                    if(comicBoxes.size > 3) {
                        homeComicBoxText4.visibility = View.VISIBLE
                        homeComicBoxButton4.visibility = View.VISIBLE
                        homeComicBoxText4.text = comicBoxes[3].name
                    }
                    else {
                        homeComicBoxText4.visibility = View.GONE
                        homeComicBoxButton4.visibility = View.GONE
                    }
                }
            }

            viewState.homeFields.collectionDetails?.let { collectionDetails ->
                homeCollectedNum.text = collectionDetails.numberOfCollectedComics.toString()
                homeReadNum.text = collectionDetails.numberOfReadComics.toString()
                homeReviewsNum.text = collectionDetails.numberOfReviews.toString()
            }
        })
    }

    // Initialize the recycler views
    private fun initRecyclerViews()
    {
        homeNewReleasesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
            newReleaseCoversAdapter = HorizontalComicCoverListAdapter(requestManager = requestManager, requestOptions = requestOptions, interaction = this@HomeFragment)
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if(lastPosition == newReleaseCoversAdapter.itemCount.minus(1)) {
                        Log.d("HomeFragment", "onScrollStateChanged (line 150): Loading next page of results.....")
                    }
                }
            })
            adapter = newReleaseCoversAdapter
        }
    }

    override fun onItemSelected(position: Int, item: ComicWithData) {
        Log.d("HomeFragment", "onItemSelected (line 261): clicked on ${item.comic.title} at position $position")
        viewModel.setComicInfoForDetail(item)
        if(findNavController().currentDestination?.id == R.id.homeFragment)
            Log.e("HomeFragment", "HOME FRAGMENT IS CURRENT DESTINATION")
        findNavController().navigate(R.id.action_homeFragment_to_comic_nav_graph)
    }

    override fun onClick(v: View)
    {
        when(v.id)
        {
            R.id.homeSeeAllNewReleasesText, R.id.homeSeeAllNewReleasesButton -> {
                findNavController().navigate(R.id.action_homeFragment_to_newReleasesFragment)
            }

            R.id.homeSeeAllBoxesText, R.id.homeSeeAllBoxesButton -> {
                findNavController().navigate(R.id.action_homeFragment_to_boxesFragment)
            }
        }
    }
}