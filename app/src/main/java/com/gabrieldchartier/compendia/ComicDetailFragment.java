package com.gabrieldchartier.compendia;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.gabrieldchartier.compendia.models.Collection;
import com.gabrieldchartier.compendia.models.Comic;
import com.gabrieldchartier.compendia.models.ComicList;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import java.util.ArrayList;
import java.util.List;

public class ComicDetailFragment extends Fragment implements View.OnClickListener
{
    // Constants
    private static final String TAG = "ComicDetailFragment";

    // Widgets
    private ImageView backArrow;
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
    private TextView numberOfOtherVersions;
    private TextView otherVersionsText;
    private ImageView otherVersionsButton;
    private SimpleRatingBar userRating;
    private ImageView hasReadIcon;
    private ImageView inCollectionIcon;
    private TextView averageRating;
    private TextView numberOfReviews;
    private TextView description;
    private TextView[] creatorNames;
    private TextView[] creatorTypes;
    private TextView[] creatorButtons;
    private Barrier creatorsBarrier;
    private Group collectionDetailsGroup;
    private ImageView editCollectionButton;
    private TextView collectedDate;
    private TextView purchasePrice;
    private TextView boughtAt;
    private TextView condition;
    private TextView grade;
    private TextView quantity;
    private View contentDivider3;
    private ArrayList<CheckBox> customListCheckboxes;
    private CheckBox readList;
    private CheckBox wantList;
    private CheckBox favoriteList;

    // Variables
    private Comic comic;
    private Collection collection;
    private List<ComicList> customLists;
    private boolean comicIsInCollection;
    private FragmentInfoRelay mInterface;
    private ConstraintLayout constraintLayout;
    private ConstraintSet constraintSet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();

        collection = Collection.getInstance();
        customLists = collection.getCustomLists();
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

        if(comic != null)
        {
            // Inflate the view and instantiate the widgets
            view = inflater.inflate(R.layout.fragment_comic_detail, container, false);
            setViewWidgets(view);

            // Set the widgets to the comic data
            setViewData();
            inflateCreators();
            inflateComicLists();

            // Set OnClick listeners
            backArrow.setOnClickListener(this);
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
                            // TODO Report Error Logic
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        return view;
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

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.comicDetailBackArrow)
        {
            Log.d(TAG, "Comic detail back button clicked");
            mInterface.onBackPressed();
        }
    }

    private void setViewWidgets(View view)
    {
        constraintSet = new ConstraintSet();
        constraintLayout = view.findViewById(R.id.comicDetailConstraintLayout);
        constraintSet.clone(constraintLayout);
        backArrow = view.findViewById(R.id.comicDetailBackArrow);
        seriesTitle = view.findViewById(R.id.comicDetailFragmentHeader);
        cover = view.findViewById(R.id.comicDetailCover);
        title = view.findViewById(R.id.comicDetailTitle);
        publisher = view.findViewById(R.id.comicDetailPublisher);
        detailSeparator1 = view.findViewById(R.id.comicDetailSeparator1);
        imprint = view.findViewById(R.id.comicDetailImprint);
        format = view.findViewById(R.id.comicDetailFormat);
        coverDate = view.findViewById(R.id.comicDetailCoverDate);
        coverPrice = view.findViewById(R.id.comicDetailCoverPrice);
        age = view.findViewById(R.id.comicDetailAge);
        ageRating = view.findViewById(R.id.comicDetailAgeRating);
        numberOfOtherVersions = view.findViewById(R.id.comicDetailOtherVersionNum);
        otherVersionsText = view.findViewById(R.id.comicDetailOtherVersionText);
        otherVersionsButton = view.findViewById(R.id.comicDetailOtherVersionArrow);
        userRating = view.findViewById(R.id.comicDetailUserRating);
        hasReadIcon = view.findViewById(R.id.comicDetailHasReadIcon);
        inCollectionIcon = view.findViewById(R.id.comicDetailInCollectionIcon);
        averageRating = view.findViewById(R.id.comicDetailAvgReviewNum);
        numberOfReviews = view.findViewById(R.id.comicDetailReviewsNum);
        description = view.findViewById(R.id.comicDetailDescription);
        creatorNames = new TextView[comic.getCreators().size()];
        creatorTypes = new TextView[comic.getCreators().size()];
        creatorButtons = new TextView[comic.getCreators().size()];
        creatorsBarrier = view.findViewById(R.id.comicDetailCreatorsBarrier);
        collectionDetailsGroup = view.findViewById(R.id.comicDetailCollectionDetailsGroup);
        editCollectionButton = view.findViewById(R.id.comicDetailEditCollectionButton);
        collectedDate = view.findViewById(R.id.comicDetailDateCollected);
        purchasePrice = view.findViewById(R.id.comicDetailPurchasePrice);
        boughtAt = view.findViewById(R.id.comicDetailBoughtAt);
        condition = view.findViewById(R.id.comicDetailCondition);
        grade = view.findViewById(R.id.comicDetailGrade);
        quantity = view.findViewById(R.id.comicDetailQuantity);
        contentDivider3 = view.findViewById(R.id.comicDetailContentDivider3);
        readList = view.findViewById(R.id.comicDetailListRead);
        wantList = view.findViewById(R.id.comicDetailListWant);
        favoriteList = view.findViewById(R.id.comicDetailListFavorite);
    }

    private void setViewData()
    {
        seriesTitle.setText(comic.getSeriesName());
        Glide.with(this)
                .load(Uri.parse(comic.getCover()))
                .into(cover);
        title.setText(comic.getTitle());
        publisher.setText(comic.getPublisherName());
        if(comic.getImprintName() != null && !comic.getImprintName().equals(""))
            imprint.setText(comic.getImprintName());
        else
        {
            imprint.setVisibility(View.GONE);
            detailSeparator1.setVisibility(View.GONE);
        }
        format.setText(comic.getFormat());
        coverDate.setText(comic.getReleaseDate().toString());
        coverPrice.setText(comic.getCoverPrice().toString());
        age.setText(comic.getAge());
        ageRating.setText(comic.getAgeRating());
        if(comic.getOtherVersions().length >= 1)
            numberOfOtherVersions.setText(comic.getOtherVersions().length);
        else
        {
            numberOfOtherVersions.setVisibility(View.INVISIBLE);
            otherVersionsText.setText(getString(R.string.comic_detail_no_other_versions));
            otherVersionsButton.setVisibility(View.GONE);
        }
        if(comicIsInCollection)
            inCollectionIcon.setVisibility(View.VISIBLE);
        if(collection.comicIsRead(comic))
            hasReadIcon.setVisibility(View.VISIBLE);
        if(comic.getUserRating() > 0.0f)
            userRating.setRating(comic.getUserRating());
        averageRating.setText(comic.getAverageRatingToString());
        if(comic.getTotalReviews() >= 1)
            numberOfReviews.setText(comic.getTotalReviews());
        else
            numberOfReviews.setText(getString(R.string.comic_detail_no_reviews));
        description.setText(comic.getDescription());
        if(comicIsInCollection)
        {
            collectedDate.setText(comic.getDateCollected().toString());
            purchasePrice.setText(comic.getPurchasePrice().toString());
            boughtAt.setText(comic.getBoughtAt());
            condition.setText(comic.getCondition());
            grade.setText(comic.getGradeAndAgencyString());
            quantity.setText(comic.getQuantity());
        }
        else
        {
            //TODO check this and ensure it works
            collectionDetailsGroup.setVisibility(View.GONE);
            constraintSet = new ConstraintSet();
            constraintSet.connect(contentDivider3.getId(),ConstraintSet.TOP, creatorsBarrier.getId(), ConstraintSet.BOTTOM,0);
            constraintSet.applyTo(constraintLayout);
        }
    }

    //TODO
    private void inflateCreators()
    {

    }

    private void inflateComicLists()
    {
        // Initialize list checkboxes, and their layout params
        customListCheckboxes = new ArrayList<>();
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

            // If the custom list being built is the first custom list, connect it to favorites
            // Else, connect it to the previous custom list
            if(i == 1)
            {
                constraintSet.connect(customListCheckboxes.get(i).getId(), ConstraintSet.START, favoriteList.getId(), ConstraintSet.START);
                constraintSet.connect(customListCheckboxes.get(i).getId(), ConstraintSet.TOP, favoriteList.getId(), ConstraintSet.BOTTOM);
            }
            else
            {
                constraintSet.connect(customListCheckboxes.get(i).getId(), ConstraintSet.START, customListCheckboxes.get(i - 1).getId(), ConstraintSet.START);
                constraintSet.connect(customListCheckboxes.get(i).getId(), ConstraintSet.TOP, customListCheckboxes.get(i - 1).getId(), ConstraintSet.BOTTOM);
            }
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
}
