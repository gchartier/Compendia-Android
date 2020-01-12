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
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.models.*
import com.gabrieldchartier.compendia.recycler_views.HorizontalComicCoverListAdapter
import com.gabrieldchartier.compendia.ui.main.MainActivity
import com.gabrieldchartier.compendia.ui.main.home.state.HomeStateEvent
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseHomeFragment(), HorizontalComicCoverListAdapter.Interaction
{
//    private val viewModelJob = Job()
//    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
//
//    private var activityFragmentInterface: FragmentInterface? = null
//    private var user: User? = null
//    private var collection: Collection? = null
//    private var thisWeeksNewReleases: ArrayList<Comic>? = null
//    private var newReleasesLayoutManager: LinearLayoutManager? = null
//    private var newReleasesAdapter: ComicCoversAdapter? = null
//    private var comicBoxes: MutableList<ComicBox>? = null
//    private var featuredBox: ComicBox? = null
//    private var readBox: ComicBox? = null
//    private var featuredBoxLayoutManager: LinearLayoutManager? = null
//    private var featuredBoxAdapter: ComicCoversAdapter? = null
//    private var newReleaseRepository : NewReleaseRepository? = null
//    private var newReleases : LiveData<List<Comic>>? = null

    @Inject
    lateinit var requestManager: RequestManager

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
        setOnClickListeners()
        getNewReleases()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeNewReleasesRecyclerView.adapter = null
    }

    private fun getNewReleases() {
        viewModel.setStateEvent(HomeStateEvent.GetNewReleasesEvent())
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
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {  viewState ->
            viewState.homeFields.newReleases?.let {
                newReleaseCoversAdapter.submitList(it)
                //Log.d("HomeFragment", "subscribeObservers (line 101): ${it.get(0)}")
                Log.d("HomeFragment", "subscribeObservers (line 91): ${newReleaseCoversAdapter.itemCount}")
            }
        })
    }

    private fun setOnClickListeners() {
        //todo
    }

    // Initialize the recycler views
    private fun initRecyclerViews()
    {
        homeNewReleasesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
            newReleaseCoversAdapter = HorizontalComicCoverListAdapter(requestManager = requestManager, interaction = this@HomeFragment)
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

    override fun onItemSelected(position: Int, item: Comic) {
        Log.d("HomeFragment", "onItemSelected (line 261): clicked on ${item.title} at position $position")
        viewModel.setComicInfoForDetail(item)
        if(findNavController().currentDestination?.id == R.id.homeFragment)
            Log.e("HomeFragment", "HOME FRAGMENT IS CURRENT DESTINATION")
        findNavController().navigate(R.id.action_homeFragment_to_comic_nav_graph)
    }
//
//    // Set the data for the views on the screen
//    private fun setViewData()
//    {
//        // Set header information
//        homeCollectedNum.text = collection!!.comics.size.toString()
//        if (readBox != null)
//            homeReadNum.text = readBox!!.comicsInBox.size.toString()
//        else
//            homeReadNum!!.text = 0.toString()
//        homeReviewsNum!!.text = collection!!.reviews.size.toString()
//
//        // Set box information
//        homeFeaturedBoxHeader!!.text = user!!.featuredBoxName
//        if (comicBoxes!!.size > 0)
//            homeComicBoxText1!!.text = comicBoxes!![0].boxName
//        if (comicBoxes!!.size > 1)
//            homeComicBoxText2!!.text = comicBoxes!![1].boxName
//        if (comicBoxes!!.size > 2)
//            homeComicBoxText3!!.text = comicBoxes!![2].boxName
//        else
//        {
//            homeComicBoxText3!!.visibility = View.GONE
//            homeComicBoxButton3!!.visibility = View.GONE
//        }
//    }
//
//    // Set the on click listeners for the views
//    private fun setViewListeners()
//    {
//        homeSettingsButton.setOnClickListener(this)
//        homeSeeAllNewReleasesText.setOnClickListener(this)
//        homeSeeAllNewReleasesButton.setOnClickListener(this)
//        homeSeeAllBoxesText.setOnClickListener(this)
//        homeSeeAllBoxesButton.setOnClickListener(this)
//        homeFeaturedBoxHeader.setOnClickListener(this)
//        homeFeaturedBoxButton.setOnClickListener(this)
//        homeComicBoxText1.setOnClickListener(this)
//        homeComicBoxButton1.setOnClickListener(this)
//        homeComicBoxText2.setOnClickListener(this)
//        homeComicBoxButton2.setOnClickListener(this)
//        homeComicBoxText3.setOnClickListener(this)
//        homeComicBoxButton3.setOnClickListener(this)
//    }
//
//    override fun onClick(v: View)
//    {
//        when(v.id)
//        {
//            R.id.homeSettingsButton -> {
//                Log.e("HomeFragment", "onClick (line 155): ${findNavController().currentDestination}")
//                findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
//            }
//
//            R.id.homeSeeAllNewReleasesText, R.id.homeSeeAllNewReleasesButton -> {
//                findNavController(v).navigate(R.id.action_homeFragment_to_newReleasesFragment)
//            }
//
//            R.id.homeSeeAllBoxesText, R.id.homeSeeAllBoxesButton -> {
//                findNavController(v).navigate(R.id.action_homeFragment_to_boxesFragment)
//            }
//
//            R.id.homeFeaturedBoxHeader, R.id.homeFeaturedBoxButton -> {
//                findNavController(v).navigate(R.id.action_homeFragment_to_boxDetailFragment)
//            }
//
//            R.id.homeComicBoxText1, R.id.homeComicBoxButton1 -> {
//                findNavController(v).navigate(R.id.action_homeFragment_to_boxDetailFragment)
//            }
//
//            R.id.homeComicBoxText2, R.id.homeComicBoxButton2-> {
//                findNavController(v).navigate(R.id.action_homeFragment_to_boxDetailFragment)
//            }
//
//            R.id.homeComicBoxText3, R.id.homeComicBoxButton3 -> {
//                findNavController(v).navigate(R.id.action_homeFragment_to_boxDetailFragment)
//            }
//        }
//    }
}