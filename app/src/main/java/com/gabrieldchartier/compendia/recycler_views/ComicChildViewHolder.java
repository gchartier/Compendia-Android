package com.gabrieldchartier.compendia.recycler_views;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.gabrieldchartier.compendia.R;
import com.gabrieldchartier.compendia.models.Comic;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ComicChildViewHolder extends ChildViewHolder
{
    private static final String TAG = "ComicChildViewHolder";

    private ImageView comicCover;
    private TextView comicTitle;
    private TextView comicID;
    private TextView comicPublisher;
    private ImageView comicPublisherImprintSeparator;
    private TextView comicImprint;
    private TextView comicFormat;
    private TextView comicCoverDate;
    private TextView comicCoverPrice;
    private SimpleRatingBar comicUserRating;
    private TextView comicAverageRatings;
    private TextView comicReviews;

    public ComicChildViewHolder(View itemView)
    {
        super(itemView);
        comicCover = itemView.findViewById(R.id.comicListItemCover);
        comicTitle = itemView.findViewById(R.id.comicListItemTitle);
        comicID = itemView.findViewById(R.id.comicListItemID);
        comicPublisher = itemView.findViewById(R.id.comicListItemPublisher);
        comicPublisherImprintSeparator = itemView.findViewById(R.id.comicListItemPublisherImprintSeparator);
        comicImprint = itemView.findViewById(R.id.comicListItemImprint);
        comicFormat = itemView.findViewById(R.id.comicListItemFormat);
        comicCoverDate = itemView.findViewById(R.id.comicListItemDate);
        comicCoverPrice = itemView.findViewById(R.id.comicListItemPrice);
        comicUserRating = itemView.findViewById(R.id.comicListItemUserRating);
        comicAverageRatings = itemView.findViewById(R.id.comicListItemAvgReviewText);
        comicReviews = itemView.findViewById(R.id.comicListItemReviewsText);
    }

    public void onBind(Comic comic, Context context)
    {
        Log.d(TAG, "OnBindViewHolder called with comic " + comic.getTitle());

        // Set the data for the list item
        Glide.with(context).asBitmap().load(comic.getCover()).into(comicCover);
        comicTitle.setText(comic.getTitle());
        comicID.setText(comic.getID().toString());
        comicPublisher.setText(comic.getPublisherName());
        if(comic.getImprintName() != null && !comic.getImprintName().equals(""))
            comicImprint.setText(comic.getImprintName());
        else
        {
            comicPublisherImprintSeparator.setVisibility(View.GONE);
            comicImprint.setVisibility(View.GONE);
        }
        comicFormat.setText(comic.getFormat());
        comicCoverDate.setText(comic.getReleaseDate());
        comicCoverPrice.setText(comic.getCoverPrice());
        if(comic.getUserRating() > 0.0f)
            comicUserRating.setRating(comic.getUserRating());
        comicAverageRatings.setText(context.getString(R.string.common_average_ratings, Float.toString(comic.getAverageRating())));
        if(comic.getTotalReviews() >= 1)
            comicReviews.setText(context.getString(R.string.common_reviews, Integer.toString(comic.getTotalReviews())));
        else
            comicReviews.setText(context.getString(R.string.comic_detail_no_reviews));
    }
}
