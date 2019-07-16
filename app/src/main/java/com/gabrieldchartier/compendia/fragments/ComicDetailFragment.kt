package com.gabrieldchartier.compendia.fragments

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.media.Image
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Barrier
import androidx.constraintlayout.widget.Group
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.gabrieldchartier.compendia.FragmentInterface
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.models.Collection
import com.gabrieldchartier.compendia.models.Comic
import com.gabrieldchartier.compendia.models.ComicCreator
import com.gabrieldchartier.compendia.models.ComicBox
import com.gabrieldchartier.compendia.util.TempUtilClass
import com.iarcuschin.simpleratingbar.SimpleRatingBar
import kotlinx.android.synthetic.main.detail_toolbar.*
import kotlinx.android.synthetic.main.fragment_comic_detail.*
import kotlin.collections.ArrayList
import java.util.UUID

class ComicDetailFragment : Fragment(), View.OnClickListener, CompoundButton.OnCheckedChangeListener
{
    companion object
    {
        // Constants
        private const val TAG = "ComicDetailFragment"
    }

    // Variables
    private var comic: Comic? = null
    private var collection: Collection? = null
    private var comicIsInCollection: Boolean = false
    private var activityFragmentInterface: FragmentInterface? = null
    private var constraintLayout: ConstraintLayout? = null
    private var constraintSet: ConstraintSet? = null
    private var creators: MutableList<ComicCreator> = ArrayList()

    // Views
    private var customComicBoxes: List<ComicBox>? = null
    private var readBox: ComicBox? = null
    private var wantBox: ComicBox? = null
    private var favoriteBox: ComicBox? = null
    private var customComicBoxCheckboxes: MutableList<CheckBox>? = null
    private var fragmentHeader: TextView? = null
    private var cover: ImageView? = null
    private var title: TextView? = null
    private var publisher: TextView? = null
    private var imprint: TextView? = null
    private var publisherImprintSeparator: ImageView? = null
    private var format: TextView? = null
    private var coverDate: TextView? = null
    private var coverPrice: TextView? = null
    private var age: TextView? = null
    private var ageRating: TextView? = null
    private var otherVersionText: TextView? = null
    private var otherVersionArrow: ImageView? = null
    private var inCollectionIcon: ImageView? = null
    private var hasReadIcon: ImageView? = null
    private var userRating: SimpleRatingBar? = null
    private var avgReviewText: TextView? = null
    private var reviewsText: TextView? = null
    private var description: TextView? = null
    private var descriptionGradient: View? = null
    private var dateCollected: TextView? = null
    private var purchasePrice: TextView? = null
    private var boughtAt: TextView? = null
    private var condition: TextView? = null
    private var grade: TextView? = null
    private var quantity: TextView? = null
    private var collectionDetailsGroup: Group? = null
    private var collectionBoxesContentDivider: View? = null
    private var creatorsBarrierBottom: Barrier? = null
    private var creator1Group: Group? = null
    private var creatorText1: TextView? = null
    private var creatorType1: TextView? = null
    private var creatorButton1: ImageView? = null
    private var creator2Group: Group? = null
    private var creatorText2: TextView? = null
    private var creatorType2: TextView? = null
    private var creatorButton2: ImageView? = null
    private var creator3Group: Group? = null
    private var creatorText3: TextView? = null
    private var creatorType3: TextView? = null
    private var creatorButton3: ImageView? = null
    private var creator4Group: Group? = null
    private var creatorText4: TextView? = null
    private var creatorType4: TextView? = null
    private var creatorButton4: ImageView? = null
    private var seeAllCreatorsText: TextView? = null
    private var seeAllCreatorsButton: ImageView? = null
    private var noCreators: TextView? = null
    private var boxRead: CompoundButton? = null
    private var boxWant: CompoundButton? = null
    private var boxFavorite: CompoundButton? = null
    private var reviewsButton: ImageView? = null
    private var editCollectionButton: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        Log.d(TAG, "Calling onCreate")
        super.onCreate(savedInstanceState)

        // Toolbar Menu
        setHasOptionsMenu(true)

        // Variables
        val bundle = this.arguments
        collection = Collection()

        Log.d(TAG, "collection: " + collection!!)
        val boxes = collection!!.comicBoxes
        for (c in boxes)
            Log.d(TAG, "Boxes: " + c.boxName)

        Log.d(TAG, "Bundle == $bundle")

        // Retrieve bundle and set comic
        comic = bundle!!.getParcelable(getString(R.string.intent_comic))
        Log.d(TAG, "Retrieved comic bundle " + comic!!.title)
        comicIsInCollection = collection!!.comicIsInCollection(comic)
        creators = comic!!.creators
        readBox = collection!!.getComicBoxByName(ComicBox.READ_BOX_NAME)
        Log.d(TAG, "Read Box " + readBox!!)
        wantBox = collection!!.getComicBoxByName(ComicBox.WANT_BOX_NAME)
        favoriteBox = collection!!.getComicBoxByName(ComicBox.FAVORITE_BOX_NAME)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: started")

        val view: View?

        // If the retrieved comic is not null, build the view
        //TODO maybe there's a better way to do this so that it won't bomb. Also, test this
        if (comic != null)
        {
            // Inflate the view, then initialize the toolbar and widgets
            view = inflater.inflate(R.layout.fragment_comic_detail, container, false)
            initializeFragmentToolbar(view!!)
            initializeViews(view)

            // Set the widgets to the comic data
            setViewData()
            inflateCreators()
            inflateComicBoxes()
            setViewListeners()
            activityFragmentInterface!!.hideBottomNav()
        }
        else
        {
            view = null
            AlertDialog.Builder(activity)
                    .setTitle(getString(R.string.alert_dialog_error_title))
                    .setMessage(getString(R.string.comic_detail_no_comic_error))

                    .setPositiveButton(getString(R.string.alert_dialog_go_back)) { _, _ -> activityFragmentInterface!!.onBackPressed() }

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(getString(R.string.alert_dialog_report_error)) { _, _ ->
                        // TODO Open Report Error Dialog
                    }
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
        }
        return view
    }

    // Initialize the fragment toolbar at the top of the screen
    private fun initializeFragmentToolbar(view: View)
    {
        val toolbar = view.findViewById<Toolbar>(R.id.comicDetailToolbar)
        var actionBar: ActionBar? = null
        if (activity != null)
        {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            actionBar = (activity as AppCompatActivity).supportActionBar
        }
        if (actionBar != null)
        {
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        else
            Log.e(TAG, "Support Action Bar was null")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        inflater.inflate(R.menu.detail_tool_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    { // TODO figure out the returns here
        when (item.itemId)
        {
            android.R.id.home -> {
                activityFragmentInterface!!.onBackPressed()
                return true
            }
            R.id.subMenuSubmitReport -> {
                return true
            }
            R.id.subMenuShare -> {
                return true
            }
        }
        return true
    }

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        activityFragmentInterface = activity as FragmentInterface?
    }

    private fun initializeViews(view: View)
    {
        constraintSet = ConstraintSet()
        constraintLayout = view.findViewById(R.id.comicDetailConstraintLayout)
        constraintSet!!.clone(constraintLayout!!)
        fragmentHeader = view.findViewById(R.id.comicDetailFragmentHeader)
        cover = view.findViewById(R.id.comicDetailCover)
        title = view.findViewById(R.id.comicDetailTitle)
        publisher = view.findViewById(R.id.comicDetailPublisher)
        publisherImprintSeparator = view.findViewById(R.id.comicDetailPublisherImprintSeparator)
        imprint = view.findViewById(R.id.comicDetailImprint)
        format = view.findViewById(R.id.comicDetailFormat)
        coverDate = view.findViewById(R.id.comicDetailCoverDate)
        coverPrice = view.findViewById(R.id.comicDetailCoverPrice)
        age = view.findViewById(R.id.comicDetailAge)
        ageRating = view.findViewById(R.id.comicDetailAgeRating)
        otherVersionText = view.findViewById(R.id.comicDetailOtherVersionText)
        otherVersionArrow = view.findViewById(R.id.comicDetailOtherVersionArrow)
        userRating = view.findViewById(R.id.comicDetailUserRating)
        hasReadIcon = view.findViewById(R.id.comicDetailHasReadIcon)
        inCollectionIcon = view.findViewById(R.id.comicDetailInCollectionIcon)
        avgReviewText = view.findViewById(R.id.comicDetailAvgReviewText)
        reviewsText = view.findViewById(R.id.comicDetailReviewsText)
        reviewsButton = view.findViewById(R.id.comicDetailReviewsButton)
        descriptionGradient = view.findViewById(R.id.descriptionGradient)
        description = view.findViewById(R.id.comicDetailDescription)
        collectionDetailsGroup = view.findViewById(R.id.comicDetailCollectionDetailsGroup)
        editCollectionButton = view.findViewById(R.id.comicDetailEditCollectionButton)
        dateCollected = view.findViewById(R.id.comicDetailDateCollected)
        purchasePrice = view.findViewById(R.id.comicDetailPurchasePrice)
        boughtAt = view.findViewById(R.id.comicDetailBoughtAt)
        condition = view.findViewById(R.id.comicDetailCondition)
        grade = view.findViewById(R.id.comicDetailGrade)
        quantity = view.findViewById(R.id.comicDetailQuantity)
        collectionBoxesContentDivider = view.findViewById(R.id.comicDetailCollectionBoxesContentDivider)
        boxRead = view.findViewById(R.id.comicDetailBoxRead)
        boxWant = view.findViewById(R.id.comicDetailBoxWant)
        boxFavorite = view.findViewById(R.id.comicDetailBoxFavorite)

        // Creator-related views
        creatorText1 = view.findViewById(R.id.comicDetailCreatorText1)
        creatorButton1 = view.findViewById(R.id.comicDetailCreatorButton1)
        creatorType1 = view.findViewById(R.id.comicDetailCreatorType1)
        creator1Group = view.findViewById(R.id.comicDetailCreator1Group)
        creatorText2 = view.findViewById(R.id.comicDetailCreatorText2)
        creatorButton2 = view.findViewById(R.id.comicDetailCreatorButton2)
        creatorType2 = view.findViewById(R.id.comicDetailCreatorType2)
        creator2Group = view.findViewById(R.id.comicDetailCreator2Group)
        creatorText3 = view.findViewById(R.id.comicDetailCreatorText3)
        creatorButton3 = view.findViewById(R.id.comicDetailCreatorButton3)
        creatorType3 = view.findViewById(R.id.comicDetailCreatorType3)
        creator3Group = view.findViewById(R.id.comicDetailCreator3Group)
        creatorText4 = view.findViewById(R.id.comicDetailCreatorText4)
        creatorButton4 = view.findViewById(R.id.comicDetailCreatorButton4)
        creatorType4 = view.findViewById(R.id.comicDetailCreatorType4)
        creator4Group = view.findViewById(R.id.comicDetailCreator4Group)
        creatorsBarrierBottom = view.findViewById(R.id.comicDetailCreatorsBarrierBottom)
        noCreators = view.findViewById(R.id.comicDetailNoCreators)
        seeAllCreatorsText = view.findViewById(R.id.comicDetailSeeAllCreatorsText)
        seeAllCreatorsButton = view.findViewById(R.id.comicDetailSeeAllCreatorsButton)
    }

    private fun setViewData()
    {
        fragmentHeader!!.text = comic!!.seriesName
        //TODO replace the glide load parameter with: Uri.parse(comic.getCover()) and remove the if/else about context
        if (activity != null)
            Glide.with(this)
                    .load(TempUtilClass.getImage(activity!!, comic!!.cover))
                    .into(cover!!)
        else
            Log.e(TAG, "Activity context is null for some reason")
        title!!.text = comic!!.title
        publisher!!.text = comic!!.publisherName
        if (comic!!.imprintName != "")
            imprint!!.text = comic!!.imprintName
        else {
            imprint!!.visibility = View.GONE
            publisherImprintSeparator!!.visibility = View.GONE
        }
        format!!.text = comic!!.format
        coverDate!!.text = comic!!.releaseDate
        coverPrice!!.text = comic!!.coverPrice
        age!!.text = comic!!.age
        ageRating!!.text = comic!!.ageRating
        if (comic!!.otherVersions.size >= 1)
            otherVersionText!!.text = getString(R.string.comic_detail_other_versions_text, Integer.toString(comic!!.otherVersions.size))
        else {
            otherVersionText!!.text = getString(R.string.comic_detail_other_versions_text, "No")
            otherVersionArrow!!.visibility = View.GONE
        }
        if (comicIsInCollection)
            inCollectionIcon!!.visibility = View.VISIBLE
        if (collection!!.comicIsInBox(comic, ComicBox.READ_BOX_NAME))
            hasReadIcon!!.visibility = View.VISIBLE
        if (comic!!.userRating > 0.0f)
            userRating!!.rating = comic!!.userRating
        avgReviewText!!.text = getString(R.string.common_average_ratings, java.lang.Float.toString(comic!!.averageRating))
        if (comic!!.totalReviews >= 1)
            reviewsText!!.text = getString(R.string.common_reviews, Integer.toString(comic!!.totalReviews))
        else
            reviewsText!!.text = getString(R.string.comic_detail_no_reviews)
        if (comic!!.description == "")
            description!!.text = getString(R.string.comic_detail_no_description)
        else
            description!!.text = comic!!.description
        descriptionGradient!!.post {
            if (description!!.lineCount <= resources.getInteger(R.integer.DESCRIPTION_MAX_LINES))
                descriptionGradient!!.visibility = View.GONE
            else
                descriptionGradient!!.visibility = View.VISIBLE
        }
        if (comicIsInCollection)
        {
            dateCollected!!.text = comic!!.dateCollected
            purchasePrice!!.text = comic!!.purchasePrice
            boughtAt!!.text = comic!!.boughtAt
            condition!!.text = comic!!.condition
            grade!!.text = comic!!.gradeAndAgencyString
            quantity!!.text = comic!!.quantity.toString()
        } else {
            collectionDetailsGroup!!.visibility = View.GONE
            constraintSet = ConstraintSet()
            constraintSet!!.connect(collectionBoxesContentDivider!!.id, ConstraintSet.TOP,
                    creatorsBarrierBottom!!.id, ConstraintSet.BOTTOM, 0)
            constraintSet!!.applyTo(constraintLayout!!)
        }
    }

    private fun inflateCreators()
    {
        // Initialize creators
        val numberOfCreators = creators.size

        // Set text and visibility of creators
        if (numberOfCreators >= 1)
        {
            creator1Group!!.visibility = View.VISIBLE
            creatorText1!!.text = creators[0].name
            creatorText1!!.tag = creators[0].id
            creatorType1!!.text = creators[0].creatorTypes
            if (numberOfCreators >= 2)
            {
                creator2Group!!.visibility = View.VISIBLE
                creatorText2!!.text = creators[1].name
                creatorText2!!.tag = creators[1].id
                creatorType2!!.text = creators[1].creatorTypes
                if (numberOfCreators >= 3)
                {
                    creator3Group!!.visibility = View.VISIBLE
                    creatorText3!!.text = creators[2].name
                    creatorText3!!.tag = creators[2].id
                    creatorType3!!.text = creators[2].creatorTypes
                    if (numberOfCreators >= 4)
                    {
                        creator4Group!!.visibility = View.VISIBLE
                        creatorText4!!.text = creators[3].name
                        creatorText4!!.tag = creators[3].id
                        creatorType4!!.text = creators[3].creatorTypes
                        if (numberOfCreators > 4)
                        {
                            seeAllCreatorsText!!.visibility = View.VISIBLE
                            seeAllCreatorsButton!!.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        else
            noCreators!!.visibility = View.VISIBLE
    }

    private fun inflateComicBoxes()
    {
        // Initialize box checkboxes, and their layout params
        customComicBoxes = collection!!.customComicBoxes
        customComicBoxCheckboxes = ArrayList()
        val layoutParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        var tempComicBox: CheckBox
        layoutParams.setMargins(0, 8, 0, 0)
        constraintSet = ConstraintSet()

        // Build, and inflate the custom checkbox boxes
        for (i in 0 until customComicBoxes!!.size - 1)
        {
            Log.d(TAG, "Comic Box " + (i + 1))
            tempComicBox = CheckBox(activity)
            //tempComicBox.setTag("box" + i);
            tempComicBox.id = View.generateViewId()
            tempComicBox.layoutParams = layoutParams
            tempComicBox.text = customComicBoxes!![i].boxName
            customComicBoxCheckboxes!!.add(tempComicBox)

            // If the custom box being built is the first custom box, connect it to favorites box
            if (i == 0)
            {
                constraintSet!!.clear(comicDetailBoxesEndContentDivider!!.id, ConstraintSet.TOP)
                constraintSet!!.connect(customComicBoxCheckboxes!![i].id, ConstraintSet.START, comicDetailBoxFavorite!!.id, ConstraintSet.START)
                constraintSet!!.connect(customComicBoxCheckboxes!![i].id, ConstraintSet.TOP, comicDetailBoxFavorite!!.id, ConstraintSet.BOTTOM)
            }
            else
            {
                constraintSet!!.connect(customComicBoxCheckboxes!![i].id, ConstraintSet.START, customComicBoxCheckboxes!![i].id, ConstraintSet.START)
                constraintSet!!.connect(customComicBoxCheckboxes!![i].id, ConstraintSet.TOP, customComicBoxCheckboxes!![i].id, ConstraintSet.BOTTOM)
            }// Else, connect it to the previous custom box that was built

            // Constrain the last custom box to the top of the end content divider
            if (i == customComicBoxes!!.size - 1)
                constraintSet!!.connect(comicDetailBoxesEndContentDivider!!.id, ConstraintSet.TOP, customComicBoxCheckboxes!![i].id, ConstraintSet.BOTTOM)
        }
        constraintSet!!.applyTo(constraintLayout!!)

        // Set the checked value of the box checkboxes
        if (collection!!.comicIsInBox(comic, ComicBox.READ_BOX_NAME))
            boxRead!!.isChecked = true
        if (collection!!.comicIsInBox(comic, ComicBox.WANT_BOX_NAME))
            boxWant!!.isChecked = true
        if (collection!!.comicIsInBox(comic, ComicBox.FAVORITE_BOX_NAME))
            boxFavorite!!.isChecked = true
        for (box in customComicBoxCheckboxes!!)
            if (collection!!.comicIsInBox(comic, box.text.toString()))
                box.isChecked = true
    }

    private fun setViewListeners()
    {
        // Top detail listeners
        if (comic!!.otherVersions.size >= 1)
        {
            otherVersionText!!.setOnClickListener(this)
            otherVersionArrow!!.setOnClickListener(this)
        }
        reviewsText!!.setOnClickListener(this)
        reviewsButton!!.setOnClickListener(this)
        if (description!!.text != "")
            description!!.setOnClickListener(this)
        cover!!.setOnClickListener(this)

        // Collection details listeners
        editCollectionButton!!.setOnClickListener(this)

        // Creator related listeners
        creatorText1!!.setOnClickListener(this)
        creatorButton1!!.setOnClickListener(this)
        creatorText2!!.setOnClickListener(this)
        creatorButton2!!.setOnClickListener(this)
        creatorText3!!.setOnClickListener(this)
        creatorButton3!!.setOnClickListener(this)
        creatorText4!!.setOnClickListener(this)
        creatorButton4!!.setOnClickListener(this)
        seeAllCreatorsText!!.setOnClickListener(this)
        seeAllCreatorsButton!!.setOnClickListener(this)

        // Box checkbox listeners
        boxRead!!.setOnCheckedChangeListener(this)
        boxWant!!.setOnCheckedChangeListener(this)
        boxFavorite!!.setOnCheckedChangeListener(this)
        for (box in customComicBoxCheckboxes!!)
            box.setOnCheckedChangeListener(this)
    }

    override fun onClick(view: View)
    {
        if (view.id == R.id.comicDetailOtherVersionArrow || view.id == R.id.comicDetailOtherVersionText)
        {
            Log.d(TAG, "Comic detail other versions clicked")
            activityFragmentInterface!!.inflateOtherVersionsFragment(Navigation.findNavController(view),
                    R.id.action_comicDetailFragment_to_otherVersionsFragment,
                    comic!!.otherVersionsAsStringArray, comic!!.title)
        }
        else if (view.id == R.id.comicDetailReviewsText || view.id == R.id.comicDetailReviewsButton)
        {
            Log.d(TAG, "Comic detail reviews clicked")
            activityFragmentInterface!!.inflateReviewsFragment(Navigation.findNavController(view),
                    R.id.action_comicDetailFragment_to_reviewsFragment, comic!!.id)
        }
        else if (view.id == R.id.comicDetailDescription)
        {
            Log.d(TAG, "Comic detail description gradient clicked")

            descriptionGradient!!.post {
                val animation: ObjectAnimator
                if (descriptionGradient!!.visibility == View.VISIBLE)
                {
                    Log.d(TAG, "Comic detail description gradient is visible")
                    animation = ObjectAnimator.ofInt(description, "maxLines", 300)
                    animation.duration = 200
                    animation.start()
                    descriptionGradient!!.visibility = View.GONE
                }
                else if (descriptionGradient!!.visibility == View.GONE)
                {
                    Log.d(TAG, "Comic detail description gradient is gone")
                    animation = ObjectAnimator.ofInt(description, "maxLines", resources.getInteger(R.integer.DESCRIPTION_MAX_LINES))
                    animation.duration = 200
                    animation.start()
                    descriptionGradient!!.visibility = View.VISIBLE
                }
            }
        }
        else if (view.id == R.id.comicDetailEditCollectionButton)
        {
            Log.d(TAG, "Comic detail edit collection clicked")
        }
        else if (view.id == R.id.comicDetailCreatorText1 || view.id == R.id.comicDetailCreatorButton1)
        {
            Log.d(TAG, "Comic detail creator " + creatorText1!!.text + " clicked")
            activityFragmentInterface!!.inflateCreatorDetailFragment(Navigation.findNavController(view),
                    R.id.action_comicDetailFragment_to_creatorDetailFragment,
                    UUID.fromString(creatorText1!!.tag.toString()))
        }
        else if (view.id == R.id.comicDetailCreatorText2 || view.id == R.id.comicDetailCreatorButton2)
        {
            Log.d(TAG, "Comic detail creator " + creatorText2!!.text + " clicked")
            activityFragmentInterface!!.inflateCreatorDetailFragment(Navigation.findNavController(view),
                    R.id.action_comicDetailFragment_to_creatorDetailFragment,
                    UUID.fromString(creatorText2!!.tag.toString()))
        }
        else if (view.id == R.id.comicDetailCreatorText3 || view.id == R.id.comicDetailCreatorButton3)
        {
            Log.d(TAG, "Comic detail creator " + creatorText3!!.text + " clicked")
            activityFragmentInterface!!.inflateCreatorDetailFragment(Navigation.findNavController(view),
                    R.id.action_comicDetailFragment_to_creatorDetailFragment,
                    UUID.fromString(creatorText3!!.tag.toString()))
        }
        else if (view.id == R.id.comicDetailCreatorText4 || view.id == R.id.comicDetailCreatorButton4)
        {
            Log.d(TAG, "Comic detail creator " + creatorText4!!.text + " clicked")
            activityFragmentInterface!!.inflateCreatorDetailFragment(Navigation.findNavController(view),
                    R.id.action_comicDetailFragment_to_creatorDetailFragment,
                    UUID.fromString(creatorText4!!.tag.toString()))
        }
        else if (view.id == R.id.comicDetailSeeAllCreatorsText || view.id == R.id.comicDetailSeeAllCreatorsButton)
        {
            Log.d(TAG, "See all creators clicked")
            activityFragmentInterface!!.inflateCreatorsListFragment(Navigation.findNavController(view),
                    R.id.action_comicDetailFragment_to_creatorsListFragment, creators)
        }
        else if (view.id == R.id.comicDetailCover)
        {
            Log.d(TAG, "Comic cover clicked")
            activityFragmentInterface!!.inflateFullCoverFragment(Navigation.findNavController(view),
                    R.id.action_comicDetailFragment_to_fullCoverFragment, comic!!.cover)
        }
    }


    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean)
    {
        if (buttonView.id == R.id.comicDetailBoxRead)
        {
            Log.d(TAG, "setting check on read box to $isChecked")
            if (isChecked && !readBox!!.containsComic(comic))
                readBox!!.addComic(comic)
            else if (!isChecked && readBox!!.containsComic(comic))
                readBox!!.removeComic(comic)
        }
        else if (buttonView.id == R.id.comicDetailBoxWant)
        {
            Log.d(TAG, "setting check on want box to $isChecked")
            if (isChecked && !wantBox!!.containsComic(comic))
                wantBox!!.addComic(comic)
            else if (!isChecked && wantBox!!.containsComic(comic))
                wantBox!!.removeComic(comic)
        }
        else if (buttonView.id == R.id.comicDetailBoxFavorite)
        {
            Log.d(TAG, "setting check on favorite box to $isChecked")
            if (isChecked && !favoriteBox!!.containsComic(comic))
                favoriteBox!!.addComic(comic)
            else if (!isChecked && favoriteBox!!.containsComic(comic))
                favoriteBox!!.removeComic(comic)
        }
        else if (customComicBoxCheckboxes!!.contains(buttonView as CheckBox))
            for (checkBox in customComicBoxCheckboxes!!)
                if (checkBox.id == buttonView.getId())
                {
                    Log.d(TAG, "setting check on " + checkBox.text + " box to " + isChecked)
                    val boxToBeToggled = collection!!.getComicBoxByName(checkBox.text.toString())
                    if (boxToBeToggled != null)
                        if (isChecked && !boxToBeToggled.containsComic(comic))
                            boxToBeToggled.addComic(comic)
                        else if (!isChecked && boxToBeToggled.containsComic(comic))
                            boxToBeToggled.removeComic(comic)
                }
    }
}
