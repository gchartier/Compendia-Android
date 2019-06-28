package com.gabrieldchartier.compendia;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.gabrieldchartier.compendia.models.Collection;
import com.gabrieldchartier.compendia.models.Comic;
import com.gabrieldchartier.compendia.models.ComicCreator;
import com.gabrieldchartier.compendia.models.ComicList;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class ComicDetailFragment extends Fragment implements View.OnClickListener
{
    // Constants
    private static final String TAG = "ComicDetailFragment";

    // Widgets
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
    private TextView numberOfReviews;
    private ImageView reviewsButton;
    private View descriptionGradient;
    private TextView description;
    private TextView creator1Name;
    private TextView creator2Name;
    private TextView creator3Name;
    private TextView creator4Name;
    private TextView creator1Type;
    private TextView creator2Type;
    private TextView creator3Type;
    private TextView creator4Type;
    private Barrier creatorsBarrierBottom;
    private TextView noCreators;
    private TextView seeAllCreatorsText;
    private ImageView seeAllCreatorsButton;
    private Group creator1Group;
    private Group creator2Group;
    private Group creator3Group;
    private Group creator4Group;
    private Group collectionDetailsGroup;
    private ImageView editCollectionButton;
    private TextView collectedDate;
    private TextView purchasePrice;
    private TextView boughtAt;
    private TextView condition;
    private TextView grade;
    private TextView quantity;
    private View collectionDetailListsDivider;
    private CheckBox readList;
    private CheckBox wantList;
    private CheckBox favoriteList;
    private View endContentDivider;

    // Variables
    private Comic comic;
    private Collection collection;
    private boolean comicIsInCollection;
    private FragmentInfoRelay mInterface;
    private ConstraintLayout constraintLayout;
    private ConstraintSet constraintSet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Toolbar Menu
        setHasOptionsMenu(true);

        // Variables
        Bundle bundle = this.getArguments();
        collection = Collection.getInstance();

        // Retrieve bundle if it is not null
        if(bundle != null)
        {
            comic = bundle.getParcelable(getString(R.string.intent_comic));
            if(comic != null)
            {
                Log.d(TAG, "Retrieved comic bundle " + comic.getTitle());
                comicIsInCollection = collection.comicIsInCollection(comic);
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
            inflateCreators(view);
            inflateComicLists(view);
            setWidgetListeners();
        }
        else
        {
            view = null;
            new AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.alert_dialog_error_title))
                    .setMessage(getString(R.string.comic_detail_no_comic_error))

                    .setPositiveButton(getString(R.string.alert_dialog_go_back), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mInterface.onBackPressed();
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
                mInterface.onBackPressed();
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
        mInterface = (FragmentInfoRelay) getActivity();
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
        numberOfReviews = view.findViewById(R.id.comicDetailReviewsText);
        reviewsButton = view.findViewById(R.id.comicDetailReviewsArrow);
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
        collectionDetailListsDivider = view.findViewById(R.id.comicDetailCollectionListsContentDivider);
    }

    private void setViewData()
    {
        seriesTitle.setText(comic.getSeriesName());
        Glide.with(this)
                .load(Uri.parse(comic.getCover()))
                .into(cover);
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
        if(collection.comicIsRead(comic))
            hasReadIcon.setVisibility(View.VISIBLE);
        if(comic.getUserRating() > 0.0f)
            userRating.setRating(comic.getUserRating());
        averageRating.setText(getString(R.string.common_average_ratings, Float.toString(comic.getAverageRating())));
        if(comic.getTotalReviews() >= 1)
            numberOfReviews.setText(getString(R.string.common_reviews, Float.toString(comic.getTotalReviews())));
        else
            numberOfReviews.setText(getString(R.string.comic_detail_no_reviews));
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
            constraintSet.connect(collectionDetailListsDivider.getId(),ConstraintSet.TOP,
                    creatorsBarrierBottom.getId(), ConstraintSet.BOTTOM, 0);
            constraintSet.applyTo(constraintLayout);
        }
    }

    private void inflateCreators(View view)
    {
        // Set creator views
        creator1Name = view.findViewById(R.id.comicDetailCreator1);
        creator2Name = view.findViewById(R.id.comicDetailCreator2);
        creator3Name = view.findViewById(R.id.comicDetailCreator3);
        creator4Name = view.findViewById(R.id.comicDetailCreator4);
        creator1Type = view.findViewById(R.id.comicDetailCreatorType1);
        creator2Type = view.findViewById(R.id.comicDetailCreatorType2);
        creator3Type = view.findViewById(R.id.comicDetailCreatorType3);
        creator4Type = view.findViewById(R.id.comicDetailCreatorType4);
        creator1Group = view.findViewById(R.id.comicDetailCreatorGroup);
        creator2Group = view.findViewById(R.id.comicDetailCreator2Group);
        creator3Group = view.findViewById(R.id.comicDetailCreator3Group);
        creator4Group = view.findViewById(R.id.comicDetailCreator4Group);
        creatorsBarrierBottom = view.findViewById(R.id.comicDetailCreatorsBarrierBottom);
        noCreators = view.findViewById(R.id.comicDetailNoCreators);
        seeAllCreatorsText = view.findViewById(R.id.comicDetailSeeAllCreatorsText);
        seeAllCreatorsButton = view.findViewById(R.id.comicDetailSeeAllCreatorsButton);

        // Initialize creators
        List<ComicCreator> creators = comic.getCreators();
        int numberOfCreators = creators.size();

        // Set text and visibility of creators
        if(numberOfCreators >= 1)
        {
            creator1Group.setVisibility(View.VISIBLE);
            creator1Name.setText(creators.get(0).getName());
            creator1Type.setText(creators.get(0).getCreatorTypes());
            if(numberOfCreators >= 2)
            {
                creator2Group.setVisibility(View.VISIBLE);
                creator2Name.setText(creators.get(1).getName());
                creator2Type.setText(creators.get(1).getCreatorTypes());
                if(numberOfCreators >= 3)
                {
                    creator3Group.setVisibility(View.VISIBLE);
                    creator3Name.setText(creators.get(2).getName());
                    creator3Type.setText(creators.get(2).getCreatorTypes());
                    if(numberOfCreators >= 4)
                    {
                        creator4Group.setVisibility(View.VISIBLE);
                        creator4Name.setText(creators.get(3).getName());
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

    private void inflateComicLists(View view)
    {
        // Initialize list views
        readList = view.findViewById(R.id.comicDetailListRead);
        wantList = view.findViewById(R.id.comicDetailListWant);
        favoriteList = view.findViewById(R.id.comicDetailListFavorite);
        endContentDivider = view.findViewById(R.id.comicDetailListsEndContentDivider);

        // Initialize list checkboxes, and their layout params
        List<ComicList> customLists = collection.getCustomLists();
        ArrayList<CheckBox> customListCheckboxes = new ArrayList<>();
        ConstraintLayout.LayoutParams layoutParams;
        CheckBox newCustomList;
        layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,8,0,0);
        constraintSet = new ConstraintSet();

        // Build, and inflate the custom checkbox lists
        for(int i = 1; i <= customLists.size(); i++)
        {
            newCustomList = new CheckBox(getActivity());
            newCustomList.setTag("customList" + i);
            newCustomList.setId(View.generateViewId());
            newCustomList.setLayoutParams(layoutParams);
            newCustomList.setText(customLists.get(i).getListName());
            customListCheckboxes.add(newCustomList);

            // If the custom list being built is the first custom list, connect it to favorites list
            if(i == 1)
            {
                constraintSet.clear(endContentDivider.getId(), ConstraintSet.TOP);
                constraintSet.connect(customListCheckboxes.get(i).getId(), ConstraintSet.START, favoriteList.getId(), ConstraintSet.START);
                constraintSet.connect(customListCheckboxes.get(i).getId(), ConstraintSet.TOP, favoriteList.getId(), ConstraintSet.BOTTOM);
            }
            // Else, connect it to the previous custom list that was built
            else
            {
                constraintSet.connect(customListCheckboxes.get(i).getId(), ConstraintSet.START, customListCheckboxes.get(i - 1).getId(), ConstraintSet.START);
                constraintSet.connect(customListCheckboxes.get(i).getId(), ConstraintSet.TOP, customListCheckboxes.get(i - 1).getId(), ConstraintSet.BOTTOM);
            }

            // Constrain the last custom list to the top of the end content divider
            if(i == customLists.size())
                constraintSet.connect(endContentDivider.getId(), ConstraintSet.TOP, customListCheckboxes.get(i).getId(), ConstraintSet.BOTTOM);
        }
        constraintSet.applyTo(constraintLayout);

        // Set the checked value of the list checkboxes
        if(collection.comicIsRead(comic))
            readList.setChecked(true);
        if(collection.comicIsWanted(comic))
            wantList.setChecked(true);
        if(collection.comicIsFavorited(comic))
            favoriteList.setChecked(true);
        for(CheckBox list : customListCheckboxes)
            if(collection.comicIsInCustomList(comic, list.getText().toString()))
                list.setChecked(true);
    }

    private void setWidgetListeners()
    {
        // Top detail listeners
        if(comic.getOtherVersions().size() >= 1)
            otherVersionsText.setOnClickListener(this);
        otherVersionsButton.setOnClickListener(this);
        reviewsButton.setOnClickListener(this);
        description.setOnClickListener(this);

        // Collection details listeners
        editCollectionButton.setOnClickListener(this);

        // Creator related listeners
        creator1Group.setOnClickListener(this);
        creator2Group.setOnClickListener(this);
        creator3Group.setOnClickListener(this);
        creator4Group.setOnClickListener(this);
        seeAllCreatorsText.setOnClickListener(this);
        seeAllCreatorsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.comicDetailOtherVersionArrow || view.getId() == R.id.comicDetailOtherVersionText)
        {
            Log.d(TAG, "Comic detail other versions clicked");
            if(getActivity() != null)
                ((MainActivity)getActivity()).inflateOtherVersionsFragment(comic.getOtherVersions(), comic.getTitle());
        }
        else if(view.getId() == R.id.comicDetailReviewsArrow)
        {
            Log.d(TAG, "Comic detail reviews clicked");
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
    }
}
