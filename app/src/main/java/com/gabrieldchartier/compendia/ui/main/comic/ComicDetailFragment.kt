package com.gabrieldchartier.compendia.ui.main.comic

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.models.Comic
import com.gabrieldchartier.compendia.models.ComicCreatorJoin
import com.gabrieldchartier.compendia.models.Creator
import com.gabrieldchartier.compendia.ui.main.MainActivity
import com.gabrieldchartier.compendia.ui.main.home.BaseHomeFragment
import com.gabrieldchartier.compendia.util.DateUtilities.Companion.convertLongToStringDate
import kotlinx.android.synthetic.main.fragment_comic_detail.*

class ComicDetailFragment : BaseHomeFragment()
{
    // todo possibly include this in di or viewmodel
    private var creators: MutableList<ComicCreatorJoin> = ArrayList()
    private var constraintLayout: ConstraintLayout? = null
    private var constraintSet: ConstraintSet? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_comic_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        subscribeObservers()
        (activity as MainActivity).apply {
            displayBottomNav(false)
            //todo setActionBar()
            // val toolbar = view.findViewById<Toolbar>(R.id.comicDetailToolbar)
            // var actionBar: ActionBar? = null
            // if (activity != null)
            // {
            //     (activity as AppCompatActivity).setSupportActionBar(toolbar)
            //     actionBar = (activity as AppCompatActivity).supportActionBar
            // }
            // if (actionBar != null)
            // {
            //     actionBar.setDisplayShowTitleEnabled(false)
            //     actionBar.setDisplayHomeAsUpEnabled(true)
            // }
            // else
            //     Log.e(TAG, "Support Action Bar was null")
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer{ dataState ->
            stateChangeListener.onDataStateChange(dataState)
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.comicDetailFields.comic?.let{ comic ->
                setComicData(comic)
            }
        })
    }

//todo remove unneeded views
//    // Views
//    private var customComicBoxes: List<ComicBox>? = null
//    private var readBox: ComicBox? = null
//    private var wantBox: ComicBox? = null
//    private var favoriteBox: ComicBox? = null
//    private var customComicBoxCheckboxes: MutableList<CheckBox>? = null
//    private var fragmentHeader: TextView? = null
//    private var cover: ImageView? = null
//    private var title: TextView? = null
//    private var publisher: TextView? = null
//    private var imprint: TextView? = null
//    private var publisherImprintSeparator: ImageView? = null
//    private var format: TextView? = null
//    private var coverDate: TextView? = null
//    private var coverPrice: TextView? = null
//    private var age: TextView? = null
//    private var ageRating: TextView? = null
//    private var otherVersionText: TextView? = null
//    private var otherVersionArrow: ImageView? = null
//    private var inCollectionIcon: ImageView? = null
//    private var hasReadIcon: ImageView? = null
//    private var userRating: SimpleRatingBar? = null
//    private var avgReviewText: TextView? = null
//    private var reviewsText: TextView? = null
//    private var description: TextView? = null
//    private var descriptionGradient: View? = null
//    private var dateCollected: TextView? = null
//    private var purchasePrice: TextView? = null
//    private var boughtAt: TextView? = null
//    private var condition: TextView? = null
//    private var grade: TextView? = null
//    private var quantity: TextView? = null
//    private var collectionDetailsGroup: Group? = null
//    private var collectionBoxesContentDivider: View? = null
//    private var creatorsBarrierBottom: Barrier? = null
//    private var creator1Group: Group? = null
//    private var creatorText1: TextView? = null
//    private var creatorType1: TextView? = null
//    private var creatorButton1: ImageView? = null
//    private var creator2Group: Group? = null
//    private var creatorText2: TextView? = null
//    private var creatorType2: TextView? = null
//    private var creatorButton2: ImageView? = null
//    private var creator3Group: Group? = null
//    private var creatorText3: TextView? = null
//    private var creatorType3: TextView? = null
//    private var creatorButton3: ImageView? = null
//    private var creator4Group: Group? = null
//    private var creatorText4: TextView? = null
//    private var creatorType4: TextView? = null
//    private var creatorButton4: ImageView? = null
//    private var seeAllCreatorsText: TextView? = null
//    private var seeAllCreatorsButton: ImageView? = null
//    private var noCreators: TextView? = null
//    private var boxRead: CompoundButton? = null
//    private var boxWant: CompoundButton? = null
//    private var boxFavorite: CompoundButton? = null
//    private var reviewsButton: ImageView? = null
//    private var editCollectionButton: ImageView? = null

    private fun initializeViews(view: View)
    {
        constraintSet = ConstraintSet()
        constraintLayout = view.findViewById(R.id.comicDetailConstraintLayout)
        constraintSet!!.clone(constraintLayout!!)
    }

    private fun setComicData(comic: Comic)
    {
        //todo fragmentHeader.text = comic.seriesName
        Glide.with(this)
                .load(comic.cover)
                .into(comicDetailCover)
        comicDetailTitle.text = comic.title
        //todo publisher.text = comic.publisherName
//  todo      if (comic.imprintName != "")
//            imprint.text = comic.imprintName
//        else {
//            imprint.visibility = View.GONE
//            publisherImprintSeparator.visibility = View.GONE
//        }
        comicDetailFormat.text = comic.formatType

        if(comic.releaseDate != null)
            comic.releaseDate?.let {
                comicDetailCoverDate.text = convertLongToStringDate(it)
            }
        else
            comicDetailCoverDate.text = R.string.comic_detail_no_release_date.toString()

        comicDetailCoverPrice.text = comic.coverPrice

        if (comic.isMature)
            comicDetailAgeRating.text = R.string.comic_detail_mature_rating.toString()
        else
            comicDetailAgeRating.text = R.string.comic_detail_not_rated.toString()

        if (comic.versions >= 1)
            comicDetailOtherVersionText.text = getString(R.string.comic_detail_other_versions_text, comic.versions.toString())
        else {
            comicDetailOtherVersionText.text = getString(R.string.comic_detail_other_versions_text, "No")
            comicDetailOtherVersionArrow.visibility = View.GONE
        }

        if (comic.isCollected)
            comicDetailInCollectionIcon.visibility = View.VISIBLE
        else
            comicDetailInCollectionIcon.visibility = View.GONE

// todo       if (collection!!.comicIsInBox(comic, ComicBox.READ_BOX_NAME))
//            hasReadIcon!!.visibility = View.VISIBLE

// todo figure out how to get the user rating, maybe store it on the comic? Or get it from the ratings table...
//      if (comic!!.userRating > 0.0f)
//            userRating!!.rating = comic!!.userRating
//        avgReviewText!!.text = getString(R.string.common_average_ratings, java.lang.Float.toString(comic!!.averageRating))

        if (comic.numberOfReviews >= 1)
            comicDetailReviewsText.text = getString(R.string.common_reviews, comic.numberOfReviews.toString())
        else
            comicDetailReviewsText.text = getString(R.string.comic_detail_no_reviews)

        if (comic.description.isNullOrEmpty())
            comicDetailDescription.text = getString(R.string.comic_detail_no_description)
        else
        {
            comicDetailDescription.text = comic.description
            descriptionGradient.post {
                if (comicDetailDescription.lineCount <= resources.getInteger(R.integer.DESCRIPTION_MAX_LINES))
                    descriptionGradient.visibility = View.GONE
                else
                    descriptionGradient.visibility = View.VISIBLE
            }
        }

        if (comic.isCollected)
        {
            if(comic.dateCollected != null)
                comic.dateCollected?.let {
                    comicDetailDateCollected.text = convertLongToStringDate(it)
                }
            else
                comicDetailDateCollected.text = R.string.comic_detail_no_collected_date.toString()

            comicDetailPurchasePrice.text = comic.purchasePrice
            comicDetailBoughtAt.text = comic.boughtAt
            comicDetailCondition.text = comic.condition
            comicDetailGrade.text = comic.grade
            comicDetailQuantity.text = comic.quantity.toString()
        }
        else {
//            comicDetailCollectionDetailsGroup.visibility = View.GONE
//            constraintSet = ConstraintSet()
//            constraintSet?.apply {
//                connect(comicDetailCollectionBoxesContentDivider.id, ConstraintSet.TOP,
//                        comicDetailCreatorsBarrierBottom.id, ConstraintSet.BOTTOM, 0)
//
//                applyTo(constraintLayout)
//            }
        }
    }

//todo add creators to view model
//    private fun inflateCreators()
//    {
//        // Initialize creators
//        val numberOfCreators = creators.size
//
//        // Set text and visibility of creators
//        if (numberOfCreators >= 1)
//        {
//            comicDetailCreator1Group.visibility = View.VISIBLE
//            comicDetailCreatorText1.text = creators[0]
//            comicDetailCreatorText1.tag = creators[0].pk
//            comicDetailCreatorType1.text = creators[0]
//            if (numberOfCreators >= 2)
//            {
//                creator2Group!!.visibility = View.VISIBLE
//                creatorText2!!.text = creators[1].name
//                creatorText2!!.tag = creators[1].id
//                creatorType2!!.text = creators[1].creatorTypes
//                if (numberOfCreators >= 3)
//                {
//                    creator3Group!!.visibility = View.VISIBLE
//                    creatorText3!!.text = creators[2].name
//                    creatorText3!!.tag = creators[2].id
//                    creatorType3!!.text = creators[2].creatorTypes
//                    if (numberOfCreators >= 4)
//                    {
//                        creator4Group!!.visibility = View.VISIBLE
//                        creatorText4!!.text = creators[3].name
//                        creatorText4!!.tag = creators[3].id
//                        creatorType4!!.text = creators[3].creatorTypes
//                        if (numberOfCreators > 4)
//                        {
//                            seeAllCreatorsText!!.visibility = View.VISIBLE
//                            seeAllCreatorsButton!!.visibility = View.VISIBLE
//                        }
//                    }
//                }
//            }
//        }
//        else
//            noCreators!!.visibility = View.VISIBLE
//    }

//todo add comic boxes to view model
//    private fun inflateComicBoxes()
//    {
//        // Initialize box checkboxes, and their layout params
//        customComicBoxes = collection!!.customComicBoxes
//        customComicBoxCheckboxes = ArrayList()
//        val layoutParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
//        var tempComicBox: CheckBox
//        layoutParams.setMargins(0, 8, 0, 0)
//        constraintSet = ConstraintSet()
//
//        // Build, and inflate the custom checkbox boxes
//        for (i in 0 until customComicBoxes!!.size - 1)
//        {
//            Log.d(TAG, "Comic Box " + (i + 1))
//            tempComicBox = CheckBox(activity)
//            //tempComicBox.setTag("box" + i);
//            tempComicBox.id = View.generateViewId()
//            tempComicBox.layoutParams = layoutParams
//            tempComicBox.text = customComicBoxes!![i].boxName
//            customComicBoxCheckboxes!!.add(tempComicBox)
//
//            // If the custom box being built is the first custom box, connect it to favorites box
//            if (i == 0)
//            {
//                constraintSet!!.clear(comicDetailBoxesEndContentDivider!!.id, ConstraintSet.TOP)
//                constraintSet!!.connect(customComicBoxCheckboxes!![i].id, ConstraintSet.START, comicDetailBoxFavorite!!.id, ConstraintSet.START)
//                constraintSet!!.connect(customComicBoxCheckboxes!![i].id, ConstraintSet.TOP, comicDetailBoxFavorite!!.id, ConstraintSet.BOTTOM)
//            }
//            else
//            {
//                constraintSet!!.connect(customComicBoxCheckboxes!![i].id, ConstraintSet.START, customComicBoxCheckboxes!![i].id, ConstraintSet.START)
//                constraintSet!!.connect(customComicBoxCheckboxes!![i].id, ConstraintSet.TOP, customComicBoxCheckboxes!![i].id, ConstraintSet.BOTTOM)
//            }// Else, connect it to the previous custom box that was built
//
//            // Constrain the last custom box to the top of the end content divider
//            if (i == customComicBoxes!!.size - 1)
//                constraintSet!!.connect(comicDetailBoxesEndContentDivider!!.id, ConstraintSet.TOP, customComicBoxCheckboxes!![i].id, ConstraintSet.BOTTOM)
//        }
//        constraintSet!!.applyTo(constraintLayout!!)
//
//        // Set the checked value of the box checkboxes
//        if (collection!!.comicIsInBox(comic, ComicBox.READ_BOX_NAME))
//            boxRead!!.isChecked = true
//        if (collection!!.comicIsInBox(comic, ComicBox.WANT_BOX_NAME))
//            boxWant!!.isChecked = true
//        if (collection!!.comicIsInBox(comic, ComicBox.FAVORITE_BOX_NAME))
//            boxFavorite!!.isChecked = true
//        for (box in customComicBoxCheckboxes!!)
//            if (collection!!.comicIsInBox(comic, box.text.toString()))
//                box.isChecked = true
//    }

//todo simplify this
//    private fun setViewListeners()
//    {
//        // Top detail listeners
//        if (comic!!.otherVersions.size >= 1)
//        {
//            otherVersionText!!.setOnClickListener(this)
//            otherVersionArrow!!.setOnClickListener(this)
//        }
//        reviewsText!!.setOnClickListener(this)
//        reviewsButton!!.setOnClickListener(this)
//        if (description!!.text != "")
//            description!!.setOnClickListener(this)
//        cover!!.setOnClickListener(this)
//
//        // Collection details listeners
//        editCollectionButton!!.setOnClickListener(this)
//
//        // Creator related listeners
//        creatorText1!!.setOnClickListener(this)
//        creatorButton1!!.setOnClickListener(this)
//        creatorText2!!.setOnClickListener(this)
//        creatorButton2!!.setOnClickListener(this)
//        creatorText3!!.setOnClickListener(this)
//        creatorButton3!!.setOnClickListener(this)
//        creatorText4!!.setOnClickListener(this)
//        creatorButton4!!.setOnClickListener(this)
//        seeAllCreatorsText!!.setOnClickListener(this)
//        seeAllCreatorsButton!!.setOnClickListener(this)
//
//        // Box checkbox listeners
//        boxRead!!.setOnCheckedChangeListener(this)
//        boxWant!!.setOnCheckedChangeListener(this)
//        boxFavorite!!.setOnCheckedChangeListener(this)
//        for (box in customComicBoxCheckboxes!!)
//            box.setOnCheckedChangeListener(this)
//    }

//todo refactor
//    override fun onClick(view: View)
//    {
//        if (view.id == R.id.comicDetailOtherVersionArrow || view.id == R.id.comicDetailOtherVersionText)
//        {
//            Log.d(TAG, "Comic detail other versions clicked")
//            activityFragmentInterface!!.inflateOtherVersionsFragment(Navigation.findNavController(view),
//                    R.id.action_comicDetailFragment_to_otherVersionsFragment,
//                    comic!!.otherVersionsAsStringArray, comic!!.title)
//        }
//        else if (view.id == R.id.comicDetailReviewsText || view.id == R.id.comicDetailReviewsButton)
//        {
//            Log.d(TAG, "Comic detail reviews clicked")
//            activityFragmentInterface!!.inflateReviewsFragment(Navigation.findNavController(view),
//                    R.id.action_comicDetailFragment_to_reviewsFragment, comic!!.comicID)
//        }
//        else if (view.id == R.id.comicDetailDescription)
//        {
//            Log.d(TAG, "Comic detail description gradient clicked")
//
//            descriptionGradient!!.post {
//                val animation: ObjectAnimator
//                if (descriptionGradient!!.visibility == View.VISIBLE)
//                {
//                    Log.d(TAG, "Comic detail description gradient is visible")
//                    animation = ObjectAnimator.ofInt(description, "maxLines", 300)
//                    animation.duration = 200
//                    animation.start()
//                    descriptionGradient!!.visibility = View.GONE
//                }
//                else if (descriptionGradient!!.visibility == View.GONE)
//                {
//                    Log.d(TAG, "Comic detail description gradient is gone")
//                    animation = ObjectAnimator.ofInt(description, "maxLines", resources.getInteger(R.integer.DESCRIPTION_MAX_LINES))
//                    animation.duration = 200
//                    animation.start()
//                    descriptionGradient!!.visibility = View.VISIBLE
//                }
//            }
//        }
//        else if (view.id == R.id.comicDetailEditCollectionButton)
//        {
//            Log.d(TAG, "Comic detail edit collection clicked")
//        }
//        else if (view.id == R.id.comicDetailCreatorText1 || view.id == R.id.comicDetailCreatorButton1)
//        {
//            Log.d(TAG, "Comic detail creator " + creatorText1!!.text + " clicked")
//            activityFragmentInterface!!.inflateCreatorDetailFragment(Navigation.findNavController(view),
//                    R.id.action_comicDetailFragment_to_creatorDetailFragment,
//                    UUID.fromString(creatorText1!!.tag.toString()))
//        }
//        else if (view.id == R.id.comicDetailCreatorText2 || view.id == R.id.comicDetailCreatorButton2)
//        {
//            Log.d(TAG, "Comic detail creator " + creatorText2!!.text + " clicked")
//            activityFragmentInterface!!.inflateCreatorDetailFragment(Navigation.findNavController(view),
//                    R.id.action_comicDetailFragment_to_creatorDetailFragment,
//                    UUID.fromString(creatorText2!!.tag.toString()))
//        }
//        else if (view.id == R.id.comicDetailCreatorText3 || view.id == R.id.comicDetailCreatorButton3)
//        {
//            Log.d(TAG, "Comic detail creator " + creatorText3!!.text + " clicked")
//            activityFragmentInterface!!.inflateCreatorDetailFragment(Navigation.findNavController(view),
//                    R.id.action_comicDetailFragment_to_creatorDetailFragment,
//                    UUID.fromString(creatorText3!!.tag.toString()))
//        }
//        else if (view.id == R.id.comicDetailCreatorText4 || view.id == R.id.comicDetailCreatorButton4)
//        {
//            Log.d(TAG, "Comic detail creator " + creatorText4!!.text + " clicked")
//            activityFragmentInterface!!.inflateCreatorDetailFragment(Navigation.findNavController(view),
//                    R.id.action_comicDetailFragment_to_creatorDetailFragment,
//                    UUID.fromString(creatorText4!!.tag.toString()))
//        }
//        else if (view.id == R.id.comicDetailSeeAllCreatorsText || view.id == R.id.comicDetailSeeAllCreatorsButton)
//        {
//            Log.d(TAG, "See all creators clicked")
//            activityFragmentInterface!!.inflateCreatorsListFragment(Navigation.findNavController(view),
//                    R.id.action_comicDetailFragment_to_creatorsListFragment, creators)
//        }
//        else if (view.id == R.id.comicDetailCover)
//        {
//            Log.d(TAG, "Comic cover clicked")
//            activityFragmentInterface!!.inflateFullCoverFragment(Navigation.findNavController(view),
//                    R.id.action_comicDetailFragment_to_fullCoverFragment, comic!!.cover)
//        }
//    }

//todo refactor
//    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean)
//    {
//        if (buttonView.id == R.id.comicDetailBoxRead)
//        {
//            Log.d(TAG, "setting check on read box to $isChecked")
//            if (isChecked && !readBox!!.containsComic(comic))
//                readBox!!.addComic(comic)
//            else if (!isChecked && readBox!!.containsComic(comic))
//                readBox!!.removeComic(comic)
//        }
//        else if (buttonView.id == R.id.comicDetailBoxWant)
//        {
//            Log.d(TAG, "setting check on want box to $isChecked")
//            if (isChecked && !wantBox!!.containsComic(comic))
//                wantBox!!.addComic(comic)
//            else if (!isChecked && wantBox!!.containsComic(comic))
//                wantBox!!.removeComic(comic)
//        }
//        else if (buttonView.id == R.id.comicDetailBoxFavorite)
//        {
//            Log.d(TAG, "setting check on favorite box to $isChecked")
//            if (isChecked && !favoriteBox!!.containsComic(comic))
//                favoriteBox!!.addComic(comic)
//            else if (!isChecked && favoriteBox!!.containsComic(comic))
//                favoriteBox!!.removeComic(comic)
//        }
//        else if (customComicBoxCheckboxes!!.contains(buttonView as CheckBox))
//            for (checkBox in customComicBoxCheckboxes!!)
//                if (checkBox.id == buttonView.getId())
//                {
//                    Log.d(TAG, "setting check on " + checkBox.text + " box to " + isChecked)
//                    val boxToBeToggled = collection!!.getComicBoxByName(checkBox.text.toString())
//                    if (boxToBeToggled != null)
//                        if (isChecked && !boxToBeToggled.containsComic(comic))
//                            boxToBeToggled.addComic(comic)
//                        else if (!isChecked && boxToBeToggled.containsComic(comic))
//                            boxToBeToggled.removeComic(comic)
//                }
//    }
}
