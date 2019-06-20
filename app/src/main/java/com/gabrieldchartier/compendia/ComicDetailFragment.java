package com.gabrieldchartier.compendia;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.gabrieldchartier.compendia.models.Collection;
import com.gabrieldchartier.compendia.models.Comic;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

public class ComicDetailFragment extends Fragment implements View.OnClickListener
{
    //Constants
    private static final String TAG = "ComicDetailFragment";

    //Widgets
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
    private TextView userRatingText;
    private SimpleRatingBar userRating;
    private ImageView hasReadIcon;
    private ImageView inCollectionIcon;
    private TextView averageRating;
    private TextView numberOfReviews;
    private TextView description;
    private TextView[] creatorNames;
    private TextView[] creatorTypes;
    private TextView[] creatorButtons;
    private Group collectionDetailsGroup;
    private ImageView editCollectionButton;
    private TextView collectedDate;
    private TextView purchasePrice;
    private TextView boughtAt;
    private TextView condition;
    private TextView grade;
    private TextView quantity;

    // Constraint
    ConstraintLayout constraintLayout;
    ConstraintSet constraintSet;

    //Variables
    private Comic comic;
    private Collection collection;
    private FragmentInfoRelay mInterface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        //TODO make this a singleton maybe
        collection = new Collection();
        if(bundle != null)
        {
            comic = bundle.getParcelable(getString(R.string.intent_comic));
            if(comic != null)
                Log.d(TAG, "Retrieved comic bundle " + comic.getTitle());
            else
                Log.d(TAG, "Retrieved comic bundle is null");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView: started");

        // Inflate the view and instantiate the widgets
        View view = inflater.inflate(R.layout.fragment_comic_detail, container, false);
        constraintLayout = view.findViewById(R.id.comicDetailConstraintLayout);
        constraintSet = new ConstraintSet();
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
        userRatingText = view.findViewById(R.id.comicDetailReviewHeader);
        hasReadIcon = view.findViewById(R.id.comicDetailHasReadIcon);
        inCollectionIcon = view.findViewById(R.id.comicDetailInCollectionIcon);
        averageRating = view.findViewById(R.id.comicDetailAvgReviewNum);
        numberOfReviews = view.findViewById(R.id.comicDetailReviewsNum);
        description = view.findViewById(R.id.comicDetailDescription);
        creatorNames = new TextView[]{};
        creatorTypes = new TextView[]{};
        creatorButtons = new TextView[]{};
        collectionDetailsGroup = view.findViewById(R.id.comicDetailCollectionDetailsGroup);
        editCollectionButton = view.findViewById(R.id.comicDetailEditCollectionButton);
        collectedDate = view.findViewById(R.id.comicDetailDateCollected);
        purchasePrice = view.findViewById(R.id.comicDetailPurchasePrice);
        boughtAt = view.findViewById(R.id.comicDetailBoughtAt);
        condition = view.findViewById(R.id.comicDetailCondition);
        grade = view.findViewById(R.id.comicDetailGrade);
        quantity = view.findViewById(R.id.comicDetailQuantity);

        // Set the widgets to the comic data
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
            //TODO check this and ensure it works
            numberOfOtherVersions.setVisibility(View.GONE);
            otherVersionsText.setVisibility(View.GONE);
            otherVersionsButton.setVisibility(View.GONE);
            constraintSet.connect(userRatingText.getId(),ConstraintSet.TOP, age.getId(), ConstraintSet.BOTTOM,0);
            constraintSet.applyTo(constraintLayout);
        }
        if(collection.comicIsInCollection(comic))
            inCollectionIcon.setVisibility(View.VISIBLE);
        //TODO make this loop for every list...
        if(collection.comicIsInList(comic, "Read"))
            hasReadIcon.setVisibility(View.VISIBLE);
        if(comic.getUserRating() > 0.0f)
            userRating.setRating(comic.getUserRating());
        averageRating.setText(comic.getAverageRatingToString());
        numberOfReviews.setText(comic.getTotalReviews());
        description.setText(comic.getDescription());
        //TODO inflateCreators();
        if(collection.comicIsInCollection(comic))
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
            constraintSet.connect(userRatingText.getId(),ConstraintSet.TOP, age.getId(), ConstraintSet.BOTTOM,0);
            constraintSet.applyTo(constraintLayout);
        }

        // Set on click listeners
        backArrow.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mInterface = (FragmentInfoRelay) getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called.");
    }

    //TODO Set the toolbar info
    private void initToolbar()
    {

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
}
