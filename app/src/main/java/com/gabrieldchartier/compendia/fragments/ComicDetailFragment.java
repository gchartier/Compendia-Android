package com.gabrieldchartier.compendia.fragments;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Barrier;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.gabrieldchartier.compendia.FragmentInterface;
import com.gabrieldchartier.compendia.R;
import com.gabrieldchartier.compendia.models.Collection;
import com.gabrieldchartier.compendia.models.Comic;
import com.gabrieldchartier.compendia.models.ComicCreator;
import com.gabrieldchartier.compendia.models.ComicBox;
import com.gabrieldchartier.compendia.util.TempUtilClass;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("FieldCanBeLocal")
public class ComicDetailFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{
    // Constants
    private static final String TAG = "ComicDetailFragment";

    // Views
    private TextView title;
    private TextView seriesTitle;
    private ImageView cover;
    private TextView publisher;
    private ImageView detailSeparator1;
    private TextView imprint;
    private TextView format;
    private TextView coverDate;
    private TextView coverPrice;
    private TextView age;
    private TextView ageRating;
    private TextView otherVersionsText;
    private ImageView otherVersionsButton;
    private SimpleRatingBar userRating;
    private ImageView hasReadIcon;
    private ImageView inCollectionIcon;
    private TextView averageRating;
    private TextView reviewsText;
    private ImageView reviewsButton;
    private View descriptionGradient;
    private TextView description;
    private TextView seeAllCreatorsText;
    private ImageView seeAllCreatorsButton;
    private TextView creator1Name;
    private ImageView creator1Button;
    private TextView creator1Type;
    private Group creator1Group;
    private TextView creator2Name;
    private ImageView creator2Button;
    private TextView creator2Type;
    private Group creator2Group;
    private TextView creator3Name;
    private ImageView creator3Button;
    private TextView creator3Type;
    private Group creator3Group;
    private TextView creator4Name;
    private ImageView creator4Button;
    private TextView creator4Type;
    private Group creator4Group;
    private Barrier creatorsBarrierBottom;
    private TextView noCreators;
    private Group collectionDetailsGroup;
    private ImageView editCollectionButton;
    private TextView collectedDate;
    private TextView purchasePrice;
    private TextView boughtAt;
    private TextView condition;
    private TextView grade;
    private TextView quantity;
    private View collectionDetailBoxesDivider;
    private CheckBox readBoxCheckbox;
    private CheckBox wantBoxCheckbox;
    private CheckBox favoriteBoxCheckbox;
    private List<CheckBox> customComicBoxCheckboxes;
    private View endContentDivider;

    // Variables
    private Comic comic;
    private Collection collection;
    private boolean comicIsInCollection;
    private FragmentInterface activityFragmentInterface;
    private ConstraintLayout constraintLayout;
    private ConstraintSet constraintSet;
    private List<ComicCreator> creators;
    private List<ComicBox> customComicBoxes;
    private ComicBox readBox;
    private ComicBox wantBox;
    private ComicBox favoriteBox;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        Log.d(TAG, "Calling onCreate");
        super.onCreate(savedInstanceState);

        // Toolbar Menu
        setHasOptionsMenu(true);

        // Variables
        Bundle bundle = this.getArguments();
        collection = new Collection();

        Log.d(TAG, "collection: " + collection);
        List<ComicBox> boxes = collection.getComicBoxes();
        for(ComicBox c : boxes)
            Log.d(TAG, "Boxes: " + c.getBoxName());

        Log.d(TAG, "Bundle == " + bundle);

        // Retrieve bundle if it is not null
        if(bundle != null)
        {
            comic = bundle.getParcelable(getString(R.string.intent_comic));
            if(comic != null)
            {
                Log.d(TAG, "Retrieved comic bundle " + comic.getTitle());
                comicIsInCollection = collection.comicIsInCollection(comic);
                creators = comic.getCreators();
                readBox = collection.getComicBoxByName(ComicBox.READ_BOX_NAME);
                Log.d(TAG, "Read Box " + readBox);
                wantBox = collection.getComicBoxByName(ComicBox.WANT_BOX_NAME);
                favoriteBox = collection.getComicBoxByName(ComicBox.FAVORITE_BOX_NAME);
            }
            else
            {
                Log.d(TAG, "Comic retrieved from bundle was null");
                comicIsInCollection = false;
            }
        }
        else
            Log.d(TAG, "Bundle was null");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView: started");

        View view;

        // If the retrieved comic is not null, build the view
        //TODO maybe there's a better way to do this so that it won't bomb. Also, test this
        if(comic != null)
        {
            // Inflate the view, then initialize the toolbar and widgets
            view = inflater.inflate(R.layout.fragment_comic_detail, container, false);
            initializeFragmentToolbar(view);
            initializeViews(view);

            // Set the widgets to the comic data
            setViewData();
            inflateCreators();
            inflateComicBoxes(view);
            setViewListeners();
        }
        else
        {
            view = null;
            new AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.alert_dialog_error_title))
                    .setMessage(getString(R.string.comic_detail_no_comic_error))

                    .setPositiveButton(getString(R.string.alert_dialog_go_back), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            activityFragmentInterface.onBackPressed();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(getString(R.string.alert_dialog_report_error), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Open Report Error Dialog
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        return view;
    }

    // Initialize the fragment toolbar at the top of the screen
    private void initializeFragmentToolbar(View view)
    {
        Toolbar toolbar = view.findViewById(R.id.comicDetailToolbar);
        ActionBar actionBar = null;
        if(getActivity() != null)
        {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        }
        if(actionBar != null)
        {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        else
            Log.e(TAG, "Support Action Bar was null");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.detail_tool_bar_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                activityFragmentInterface.onBackPressed();
                return true;
            case R.id.subMenuSubmitReport:
                //TODO submit report
                return true;
            case R.id.subMenuShare:
                //TODO share
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        activityFragmentInterface = (FragmentInterface) getActivity();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called.");
    }

    private void initializeViews(View view)
    {
        constraintSet = new ConstraintSet();
        constraintLayout = view.findViewById(R.id.comicDetailConstraintLayout);
        constraintSet.clone(constraintLayout);
        seriesTitle = view.findViewById(R.id.comicDetailFragmentHeader);
        cover = view.findViewById(R.id.comicDetailCover);
        title = view.findViewById(R.id.comicDetailTitle);
        publisher = view.findViewById(R.id.comicDetailPublisher);
        detailSeparator1 = view.findViewById(R.id.comicDetailPublisherImprintSeparator);
        imprint = view.findViewById(R.id.comicDetailImprint);
        format = view.findViewById(R.id.comicDetailFormat);
        coverDate = view.findViewById(R.id.comicDetailCoverDate);
        coverPrice = view.findViewById(R.id.comicDetailCoverPrice);
        age = view.findViewById(R.id.comicDetailAge);
        ageRating = view.findViewById(R.id.comicDetailAgeRating);
        otherVersionsText = view.findViewById(R.id.comicDetailOtherVersionText);
        otherVersionsButton = view.findViewById(R.id.comicDetailOtherVersionArrow);
        userRating = view.findViewById(R.id.comicDetailUserRating);
        hasReadIcon = view.findViewById(R.id.comicDetailHasReadIcon);
        inCollectionIcon = view.findViewById(R.id.comicDetailInCollectionIcon);
        averageRating = view.findViewById(R.id.comicDetailAvgReviewText);
        reviewsText = view.findViewById(R.id.comicDetailReviewsText);
        reviewsButton = view.findViewById(R.id.comicDetailReviewsButton);
        descriptionGradient = view.findViewById(R.id.descriptionGradient);
        description = view.findViewById(R.id.comicDetailDescription);
        collectionDetailsGroup = view.findViewById(R.id.comicDetailCollectionDetailsGroup);
        editCollectionButton = view.findViewById(R.id.comicDetailEditCollectionButton);
        collectedDate = view.findViewById(R.id.comicDetailDateCollected);
        purchasePrice = view.findViewById(R.id.comicDetailPurchasePrice);
        boughtAt = view.findViewById(R.id.comicDetailBoughtAt);
        condition = view.findViewById(R.id.comicDetailCondition);
        grade = view.findViewById(R.id.comicDetailGrade);
        quantity = view.findViewById(R.id.comicDetailQuantity);
        collectionDetailBoxesDivider = view.findViewById(R.id.comicDetailCollectionBoxesContentDivider);

        // Creator-related views
        creator1Name = view.findViewById(R.id.comicDetailCreatorText1);
        creator1Button = view.findViewById(R.id.comicDetailCreatorButton1);
        creator1Type = view.findViewById(R.id.comicDetailCreatorType1);
        creator1Group = view.findViewById(R.id.comicDetailCreator1Group);
        creator2Name = view.findViewById(R.id.comicDetailCreatorText2);
        creator2Button = view.findViewById(R.id.comicDetailCreatorButton2);
        creator2Type = view.findViewById(R.id.comicDetailCreatorType2);
        creator2Group = view.findViewById(R.id.comicDetailCreator2Group);
        creator3Name = view.findViewById(R.id.comicDetailCreatorText3);
        creator3Button = view.findViewById(R.id.comicDetailCreatorButton3);
        creator3Type = view.findViewById(R.id.comicDetailCreatorType3);
        creator3Group = view.findViewById(R.id.comicDetailCreator3Group);
        creator4Name = view.findViewById(R.id.comicDetailCreatorText4);
        creator4Button = view.findViewById(R.id.comicDetailCreatorButton4);
        creator4Type = view.findViewById(R.id.comicDetailCreatorType4);
        creator4Group = view.findViewById(R.id.comicDetailCreator4Group);
        creatorsBarrierBottom = view.findViewById(R.id.comicDetailCreatorsBarrierBottom);
        noCreators = view.findViewById(R.id.comicDetailNoCreators);
        seeAllCreatorsText = view.findViewById(R.id.comicDetailSeeAllCreatorsText);
        seeAllCreatorsButton = view.findViewById(R.id.comicDetailSeeAllCreatorsButton);
    }

    private void setViewData()
    {
        seriesTitle.setText(comic.getSeriesName());
        //TODO replace the glide load parameter with: Uri.parse(comic.getCover()) and remove the if/else about context
        if(getActivity() != null)
        Glide.with(this)
                .load(TempUtilClass.getImage(getActivity(), comic.getCover()))
                .into(cover);
        else
            Log.e(TAG, "Activity context is null for some reason");
        title.setText(comic.getTitle());
        publisher.setText(comic.getPublisherName());
        if(!comic.getImprintName().equals(""))
            imprint.setText(comic.getImprintName());
        else
        {
            imprint.setVisibility(View.GONE);
            detailSeparator1.setVisibility(View.GONE);
        }
        format.setText(comic.getFormat());
        coverDate.setText(comic.getReleaseDate());
        coverPrice.setText(comic.getCoverPrice());
        age.setText(comic.getAge());
        ageRating.setText(comic.getAgeRating());
        if(comic.getOtherVersions().size() >= 1)
            otherVersionsText.setText(getString(R.string.comic_detail_other_versions_text, Integer.toString(comic.getOtherVersions().size())));
        else
        {
            otherVersionsText.setText(getString(R.string.comic_detail_other_versions_text, "No"));
            otherVersionsButton.setVisibility(View.GONE);
        }
        if(comicIsInCollection)
            inCollectionIcon.setVisibility(View.VISIBLE);
        if(collection.comicIsInBox(comic, ComicBox.READ_BOX_NAME))
            hasReadIcon.setVisibility(View.VISIBLE);
        if(comic.getUserRating() > 0.0f)
            userRating.setRating(comic.getUserRating());
        averageRating.setText(getString(R.string.common_average_ratings, Float.toString(comic.getAverageRating())));
        if(comic.getTotalReviews() >= 1)
            reviewsText.setText(getString(R.string.common_reviews, Integer.toString(comic.getTotalReviews())));
        else
            reviewsText.setText(getString(R.string.comic_detail_no_reviews));
        if(comic.getDescription().equals(""))
            description.setText(getString(R.string.comic_detail_no_description));
        else
            description.setText(comic.getDescription());
        descriptionGradient.post(new Runnable()
        {
            @Override
            public void run()
            {
                if(description.getLineCount() <= getResources().getInteger(R.integer.DESCRIPTION_MAX_LINES))
                    descriptionGradient.setVisibility(View.GONE);
                else
                    descriptionGradient.setVisibility(View.VISIBLE);
            }
        });
        if(comicIsInCollection)
        {
            collectedDate.setText(comic.getDateCollected());
            purchasePrice.setText(comic.getPurchasePrice());
            boughtAt.setText(comic.getBoughtAt());
            condition.setText(comic.getCondition());
            grade.setText(comic.getGradeAndAgencyString());
            quantity.setText(String.valueOf(comic.getQuantity()));
        }
        else
        {
            collectionDetailsGroup.setVisibility(View.GONE);
            constraintSet = new ConstraintSet();
            constraintSet.connect(collectionDetailBoxesDivider.getId(),ConstraintSet.TOP,
                    creatorsBarrierBottom.getId(), ConstraintSet.BOTTOM, 0);
            constraintSet.applyTo(constraintLayout);
        }
    }

    private void inflateCreators()
    {
        // Initialize creators
        int numberOfCreators = creators.size();

        // Set text and visibility of creators
        if(numberOfCreators >= 1)
        {
            creator1Group.setVisibility(View.VISIBLE);
            creator1Name.setText(creators.get(0).getName());
            creator1Name.setTag(creators.get(0).getID());
            creator1Type.setText(creators.get(0).getCreatorTypes());
            if(numberOfCreators >= 2)
            {
                creator2Group.setVisibility(View.VISIBLE);
                creator2Name.setText(creators.get(1).getName());
                creator2Name.setTag(creators.get(1).getID());
                creator2Type.setText(creators.get(1).getCreatorTypes());
                if(numberOfCreators >= 3)
                {
                    creator3Group.setVisibility(View.VISIBLE);
                    creator3Name.setText(creators.get(2).getName());
                    creator3Name.setTag(creators.get(2).getID());
                    creator3Type.setText(creators.get(2).getCreatorTypes());
                    if(numberOfCreators >= 4)
                    {
                        creator4Group.setVisibility(View.VISIBLE);
                        creator4Name.setText(creators.get(3).getName());
                        creator4Name.setTag(creators.get(3).getID());
                        creator4Type.setText(creators.get(3).getCreatorTypes());
                        if(numberOfCreators > 4)
                        {
                            seeAllCreatorsText.setVisibility(View.VISIBLE);
                            seeAllCreatorsButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }
        else
        {
            noCreators.setVisibility(View.VISIBLE);
        }
    }

    private void inflateComicBoxes(View view)
    {
        // Initialize box views
        readBoxCheckbox = view.findViewById(R.id.comicDetailBoxRead);
        wantBoxCheckbox = view.findViewById(R.id.comicDetailBoxWant);
        favoriteBoxCheckbox = view.findViewById(R.id.comicDetailBoxFavorite);
        endContentDivider = view.findViewById(R.id.comicDetailBoxesEndContentDivider);

        // Initialize box checkboxes, and their layout params
        customComicBoxes = collection.getCustomComicBoxes();
        customComicBoxCheckboxes = new ArrayList<>();
        ConstraintLayout.LayoutParams layoutParams;
        CheckBox tempComicBox;
        layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,8,0,0);
        constraintSet = new ConstraintSet();

        // Build, and inflate the custom checkbox boxes
        for(int i = 0; i < customComicBoxes.size() - 1; i++)
        {
            Log.d(TAG, "Comic Box " + (i+1));
            tempComicBox = new CheckBox(getActivity());
            //tempComicBox.setTag("box" + i);
            tempComicBox.setId(View.generateViewId());
            tempComicBox.setLayoutParams(layoutParams);
            tempComicBox.setText(customComicBoxes.get(i).getBoxName());
            customComicBoxCheckboxes.add(tempComicBox);

            // If the custom box being built is the first custom box, connect it to favorites box
            if(i == 0)
            {
                constraintSet.clear(endContentDivider.getId(), ConstraintSet.TOP);
                constraintSet.connect(customComicBoxCheckboxes.get(i).getId(), ConstraintSet.START, favoriteBoxCheckbox.getId(), ConstraintSet.START);
                constraintSet.connect(customComicBoxCheckboxes.get(i).getId(), ConstraintSet.TOP, favoriteBoxCheckbox.getId(), ConstraintSet.BOTTOM);
            }
            // Else, connect it to the previous custom box that was built
            else
            {
                constraintSet.connect(customComicBoxCheckboxes.get(i).getId(), ConstraintSet.START, customComicBoxCheckboxes.get(i).getId(), ConstraintSet.START);
                constraintSet.connect(customComicBoxCheckboxes.get(i).getId(), ConstraintSet.TOP, customComicBoxCheckboxes.get(i).getId(), ConstraintSet.BOTTOM);
            }

            // Constrain the last custom box to the top of the end content divider
            if(i == customComicBoxes.size() - 1)
                constraintSet.connect(endContentDivider.getId(), ConstraintSet.TOP, customComicBoxCheckboxes.get(i).getId(), ConstraintSet.BOTTOM);
        }
        constraintSet.applyTo(constraintLayout);

        // Set the checked value of the box checkboxes
        if(collection.comicIsInBox(comic, ComicBox.READ_BOX_NAME))
            readBoxCheckbox.setChecked(true);
        if(collection.comicIsInBox(comic, ComicBox.WANT_BOX_NAME))
            wantBoxCheckbox.setChecked(true);
        if(collection.comicIsInBox(comic, ComicBox.FAVORITE_BOX_NAME))
            favoriteBoxCheckbox.setChecked(true);
        for(CheckBox box : customComicBoxCheckboxes)
            if(collection.comicIsInBox(comic, box.getText().toString()))
                box.setChecked(true);
    }

    private void setViewListeners()
    {
        // Top detail listeners
        if(comic.getOtherVersions().size() >= 1)
        {
            otherVersionsText.setOnClickListener(this);
            otherVersionsButton.setOnClickListener(this);
        }
        reviewsText.setOnClickListener(this);
        reviewsButton.setOnClickListener(this);
        if(!description.getText().equals(""))
            description.setOnClickListener(this);
        cover.setOnClickListener(this);

        // Collection details listeners
        editCollectionButton.setOnClickListener(this);

        // Creator related listeners
        creator1Name.setOnClickListener(this);
        creator1Button.setOnClickListener(this);
        creator2Name.setOnClickListener(this);
        creator2Button.setOnClickListener(this);
        creator3Name.setOnClickListener(this);
        creator3Button.setOnClickListener(this);
        creator4Name.setOnClickListener(this);
        creator4Button.setOnClickListener(this);
        seeAllCreatorsText.setOnClickListener(this);
        seeAllCreatorsButton.setOnClickListener(this);

        // Box checkbox listeners
        readBoxCheckbox.setOnCheckedChangeListener(this);
        wantBoxCheckbox.setOnCheckedChangeListener(this);
        favoriteBoxCheckbox.setOnCheckedChangeListener(this);
        for(CheckBox box : customComicBoxCheckboxes)
        {
            box.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.comicDetailOtherVersionArrow || view.getId() == R.id.comicDetailOtherVersionText)
        {
            Log.d(TAG, "Comic detail other versions clicked");
            activityFragmentInterface.inflateOtherVersionsFragment(comic.getOtherVersionsAsStringArray(), comic.getTitle());
        }
        else if(view.getId() == R.id.comicDetailReviewsText || view.getId() == R.id.comicDetailReviewsButton)
        {
            Log.d(TAG, "Comic detail reviews clicked");
            activityFragmentInterface.inflateReviewsFragment(comic.getID());
        }
        else if(view.getId() == R.id.comicDetailDescription)
        {
            Log.d(TAG, "Comic detail description gradient clicked");

            descriptionGradient.post(new Runnable()
            {
                @Override
                public void run()
                {
                    ObjectAnimator animation;
                    if(descriptionGradient.getVisibility() == View.VISIBLE)
                    {
                        Log.d(TAG, "Comic detail description gradient is visible");
                        animation = ObjectAnimator.ofInt(description, "maxLines", 300);
                        animation.setDuration(200);
                        animation.start();
                        descriptionGradient.setVisibility(View.GONE);
                    }
                    else if(descriptionGradient.getVisibility() == View.GONE)
                    {
                        Log.d(TAG, "Comic detail description gradient is gone");
                        animation = ObjectAnimator.ofInt(description, "maxLines", getResources().getInteger(R.integer.DESCRIPTION_MAX_LINES));
                        animation.setDuration(200);
                        animation.start();
                        descriptionGradient.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        else if(view.getId() == R.id.comicDetailEditCollectionButton)
        {
            Log.d(TAG, "Comic detail edit collection clicked");
        }
        else if(view.getId() == R.id.comicDetailCreatorText1 || view.getId() == R.id.comicDetailCreatorButton1)
        {
            Log.d(TAG, "Comic detail creator " + creator1Name.getText() + " clicked");
            activityFragmentInterface.inflateCreatorDetailFragment(UUID.fromString(creator1Name.getTag().toString()));
        }
        else if(view.getId() == R.id.comicDetailCreatorText2 || view.getId() == R.id.comicDetailCreatorButton2)
        {
            Log.d(TAG, "Comic detail creator " + creator2Name.getText() + " clicked");
            activityFragmentInterface.inflateCreatorDetailFragment(UUID.fromString(creator2Name.getTag().toString()));
        }
        else if(view.getId() == R.id.comicDetailCreatorText3 || view.getId() == R.id.comicDetailCreatorButton3)
        {
            Log.d(TAG, "Comic detail creator " + creator3Name.getText() + " clicked");
            activityFragmentInterface.inflateCreatorDetailFragment(UUID.fromString(creator3Name.getTag().toString()));
        }
        else if(view.getId() == R.id.comicDetailCreatorText4 || view.getId() == R.id.comicDetailCreatorButton4)
        {
            Log.d(TAG, "Comic detail creator " + creator4Name.getText() + " clicked");
            activityFragmentInterface.inflateCreatorDetailFragment(UUID.fromString(creator4Name.getTag().toString()));
        }
        else if(view.getId() == R.id.comicDetailSeeAllCreatorsText || view.getId() == R.id.comicDetailSeeAllCreatorsButton)
        {
            Log.d(TAG, "See all creators clicked");
            activityFragmentInterface.inflateCreatorsListFragment(creators);
        }
        else if(view.getId() == R.id.comicDetailCover)
        {
            Log.d(TAG, "Comic cover clicked");
            activityFragmentInterface.inflateFullCoverFragment(comic.getCover());
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if(buttonView.getId() == R.id.comicDetailBoxRead)
        {
            Log.d(TAG, "setting check on read box to " + isChecked);
            if(isChecked && !readBox.containsComic(comic))
                readBox.addComic(comic);
            else if(!isChecked && readBox.containsComic(comic))
                readBox.removeComic(comic);
        }
        else if(buttonView.getId() == R.id.comicDetailBoxWant)
        {
            Log.d(TAG, "setting check on want box to " + isChecked);
            if(isChecked && !wantBox.containsComic(comic))
                wantBox.addComic(comic);
            else if(!isChecked && wantBox.containsComic(comic))
                wantBox.removeComic(comic);
        }
        else if(buttonView.getId() == R.id.comicDetailBoxFavorite)
        {
            Log.d(TAG, "setting check on favorite box to " + isChecked);
            if(isChecked && !favoriteBox.containsComic(comic))
                favoriteBox.addComic(comic);
            else if(!isChecked && favoriteBox.containsComic(comic))
                favoriteBox.removeComic(comic);
        }
        else if(customComicBoxCheckboxes.contains((CheckBox)buttonView))
        {
            for(CheckBox checkBox : customComicBoxCheckboxes)
            {
                if(checkBox.getId() == buttonView.getId())
                {
                    Log.d(TAG, "setting check on " + checkBox.getText() + " box to " + isChecked);
                    ComicBox boxToBeToggled = collection.getComicBoxByName(checkBox.getText().toString());
                    if(boxToBeToggled != null)
                        if(isChecked && !boxToBeToggled.containsComic(comic))
                            boxToBeToggled.addComic(comic);
                        else if(!isChecked && boxToBeToggled.containsComic(comic))
                            boxToBeToggled.removeComic(comic);
                }
            }
        }
    }
}
