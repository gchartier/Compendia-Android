package com.gabrieldchartier.compendia.ui.main.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import com.gabrieldchartier.compendia.FragmentInterface
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.models.Collection
import com.gabrieldchartier.compendia.models.ComicBox
import com.gabrieldchartier.compendia.models.User
import com.gabrieldchartier.compendia.recycler_views.ComicCoversAdapter
import com.gabrieldchartier.compendia.models.Comic
import com.gabrieldchartier.compendia.persistence.NewReleaseRepository
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HomeFragment : Fragment(), View.OnClickListener
{
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    companion object
    {
        //Constants
        private val TAG = "HomeFragment"
    }

    //Variables
    private var activityFragmentInterface: FragmentInterface? = null
    private var user: User? = null
    private var collection: Collection? = null
    private var thisWeeksNewReleases: ArrayList<Comic>? = null
    private var newReleasesLayoutManager: LinearLayoutManager? = null
    private var newReleasesAdapter: ComicCoversAdapter? = null
    private var comicBoxes: MutableList<ComicBox>? = null
    private var featuredBox: ComicBox? = null
    private var readBox: ComicBox? = null
    private var featuredBoxLayoutManager: LinearLayoutManager? = null
    private var featuredBoxAdapter: ComicCoversAdapter? = null
    private var newReleaseRepository : NewReleaseRepository? = null
    private var newReleases : LiveData<List<Comic>>? = null

    override fun onAttach(context: Context)
    {
        Log.d(TAG, "onAttach started")
        super.onAttach(context)
        activityFragmentInterface = activity as FragmentInterface?
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        Log.d(TAG, "onCreate started")
        super.onCreate(savedInstanceState)

        newReleaseRepository = NewReleaseRepository(context!!)

        //newReleases = NewReleases.getInstance()
        collection = Collection()
        user = User.getInstance()
        //TODO thisWeeksNewReleases = newReleases!!.thisWeek
        thisWeeksNewReleases = ArrayList()
        featuredBox = collection!!.getComicBoxByName(user!!.featuredBoxName)
        comicBoxes = collection!!.comicBoxes
        comicBoxes!!.remove(featuredBox)
        readBox = collection!!.getComicBoxByName(ComicBox.READ_BOX_NAME)
        //repository?.insertNewReleasesTask()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        Log.d(TAG, "onCreateView started")

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        setViewListeners()
        setViewData()
        retrieveNewReleases()
    }

    // Initialize the recycler views
    private fun initRecyclerViews()
    {
        Log.d(TAG, "Initializing Recycler Views")

        // Initialize New Releases Recycler View
        newReleasesLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        homeNewReleasesRecyclerView.layoutManager = newReleasesLayoutManager
        newReleasesAdapter = ComicCoversAdapter(activity, thisWeeksNewReleases)
        homeNewReleasesRecyclerView.adapter = newReleasesAdapter

        // Initialize Featured Box Recycler View
        featuredBoxLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        homeFeaturedBoxRecyclerView.layoutManager = featuredBoxLayoutManager
        if (featuredBox != null)
            featuredBoxAdapter = ComicCoversAdapter(activity, featuredBox!!.comicsInBox)
        homeFeaturedBoxRecyclerView.adapter = featuredBoxAdapter
    }

    // Set the data for the views on the screen
    private fun setViewData()
    {
        Log.d(TAG, "setting view data")

        // Set header information
        homeCollectedNum.text = collection!!.comics.size.toString()
        if (readBox != null)
            homeReadNum.text = readBox!!.comicsInBox.size.toString()
        else
            homeReadNum!!.text = 0.toString()
        homeReviewsNum!!.text = collection!!.reviews.size.toString()

        // Set box information
        homeFeaturedBoxHeader!!.text = user!!.featuredBoxName
        if (comicBoxes!!.size > 0)
            homeComicBoxText1!!.text = comicBoxes!![0].boxName
        if (comicBoxes!!.size > 1)
            homeComicBoxText2!!.text = comicBoxes!![1].boxName
        if (comicBoxes!!.size > 2)
            homeComicBoxText3!!.text = comicBoxes!![2].boxName
        else
        {
            homeComicBoxText3!!.visibility = View.GONE
            homeComicBoxButton3!!.visibility = View.GONE
        }
    }

    // Set the on click listeners for the views
    private fun setViewListeners()
    {
        Log.d(TAG, "setting view listeners")

        homeSettingsButton.setOnClickListener(this)
        homeSeeAllNewReleasesText.setOnClickListener(this)
        homeSeeAllNewReleasesButton.setOnClickListener(this)
        homeSeeAllBoxesText.setOnClickListener(this)
        homeSeeAllBoxesButton.setOnClickListener(this)
        homeFeaturedBoxHeader.setOnClickListener(this)
        homeFeaturedBoxButton.setOnClickListener(this)
        homeComicBoxText1.setOnClickListener(this)
        homeComicBoxButton1.setOnClickListener(this)
        homeComicBoxText2.setOnClickListener(this)
        homeComicBoxButton2.setOnClickListener(this)
        homeComicBoxText3.setOnClickListener(this)
        homeComicBoxButton3.setOnClickListener(this)
    }

    override fun onClick(v: View)
    {
        when(v.id)
        {
            R.id.homeSettingsButton -> {
                Log.d(TAG, "settings clicked")
                activityFragmentInterface?.inflateSettingsFragment(findNavController(v))
            }

            R.id.homeSeeAllNewReleasesText, R.id.homeSeeAllNewReleasesButton -> {
                Log.d(TAG, "see all new releases clicked")
                findNavController(v).navigate(R.id.action_homeFragment_to_newReleasesFragment)
            }

            R.id.homeSeeAllBoxesText, R.id.homeSeeAllBoxesButton -> {
                Log.d(TAG, "see all boxes clicked")
                findNavController(v).navigate(R.id.action_homeFragment_to_boxesFragment)
            }

            R.id.homeFeaturedBoxHeader, R.id.homeFeaturedBoxButton -> {
                Log.d(TAG, "comic box detail " + featuredBox!!.boxName + " clicked")
                findNavController(v).navigate(R.id.action_homeFragment_to_boxDetailFragment)
            }

            R.id.homeComicBoxText1, R.id.homeComicBoxButton1 -> {
                Log.d(TAG, "comic box detail " + comicBoxes!![0] + " clicked")
                findNavController(v).navigate(R.id.action_homeFragment_to_boxDetailFragment)
            }

            R.id.homeComicBoxText2, R.id.homeComicBoxButton2-> {
                Log.d(TAG, "comic box detail " + comicBoxes!![1] + " clicked")
                findNavController(v).navigate(R.id.action_homeFragment_to_boxDetailFragment)
            }

            R.id.homeComicBoxText3, R.id.homeComicBoxButton3 -> {
                Log.d(TAG, "comic box detail " + comicBoxes!![2] + " clicked")
                findNavController(v).navigate(R.id.action_homeFragment_to_boxDetailFragment)
            }
        }
    }

    private fun retrieveNewReleases()
    {
        Log.d(TAG, "Retrieving New Releases")

        // Create the observer which updates the UI.
        val newReleasesObserver = Observer<List<Comic>> { newReleases ->
            thisWeeksNewReleases?.clear()
            thisWeeksNewReleases?.addAll(newReleases)
            newReleasesAdapter?.notifyDataSetChanged()
        }

        val lifecycleOwner = this

        uiScope.launch {
            newReleases = newReleaseRepository?.retrieveNewReleasesTask()
            newReleases?.observe(lifecycleOwner, newReleasesObserver)
        }
    }
}