package com.gabrieldchartier.compendia.recycler_views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gabrieldchartier.compendia.FragmentInterface;
import com.gabrieldchartier.compendia.R;
import com.gabrieldchartier.compendia.models.Comic;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;

public class ComicRecordRecyclerAdapter //extends RecyclerView.Adapter<ComicRecordRecyclerAdapter.ViewHolder>
{

    //todo rework this

//    // Constants
//    private static final String TAG = "ComicRecordAdapter";
//
//    // Variables
//    private ArrayList<Comic> otherVersionComics;
//    private Context context;
//    private FragmentInterface fragmentRelay;
//    private View.OnClickListener mClickListener;
//
//    public ComicRecordRecyclerAdapter(Context context, ArrayList<Comic> otherVersionComics)
//    {
//        this.otherVersionComics = otherVersionComics;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
//    {
//        Log.d(TAG, "onCreateViewHolder called");
//
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comic_list_item, viewGroup, false);
//
//        ComicRecordRecyclerAdapter.ViewHolder holder = new ViewHolder(view);
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(context.getString(R.string.intent_comic), otherVersionComics.get(holder.getAdapterPosition()));
//        //TODO holder.itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_otherVersionsFragment_to_comicDetailFragment, bundle));
////        holder.itemView.setOnClickListener(new View.OnClickListener()
////        {
////            @Override
////            public void onClick(View view) {
////                mClickListener.onClick(view);
////            }
////        });
//
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i)
//    {
//        Log.d(TAG, "OnBindViewHolder called");
//
//        Log.d(TAG, otherVersionComics.toString());
//
//        Comic comic = otherVersionComics.get(viewHolder.getAdapterPosition());
//
//        // Set the data for the list item
//        Glide.with(context).asBitmap().load(comic.getCover()).into(viewHolder.comicCover);
//        viewHolder.comicTitle.setText(comic.getTitle());
//        viewHolder.comicPublisher.setText(comic.getPublisherName());
//        if(comic.getImprintName() != null && !comic.getImprintName().equals(""))
//            viewHolder.comicImprint.setText(comic.getImprintName());
//        else
//        {
//            viewHolder.comicPublisherImprintSeparator.setVisibility(View.GONE);
//            viewHolder.comicImprint.setVisibility(View.GONE);
//        }
//        viewHolder.comicFormat.setText(otherVersionComics.get(viewHolder.getAdapterPosition()).getFormat());
//        viewHolder.comicCoverDate.setText(otherVersionComics.get(viewHolder.getAdapterPosition()).getReleaseDate());
//        viewHolder.comicCoverPrice.setText(otherVersionComics.get(viewHolder.getAdapterPosition()).getCoverPrice());
//        if(otherVersionComics.get(viewHolder.getAdapterPosition()).getUserRating() > 0.0f)
//            viewHolder.comicUserRating.setRating(comic.getUserRating());
//        viewHolder.comicAverageRatings.setText(context.getString(R.string.common_average_ratings, Float.toString(comic.getAverageRating())));
//        if(comic.getTotalReviews() >= 1)
//            viewHolder.comicReviews.setText(context.getString(R.string.common_reviews, Integer.toString(comic.getTotalReviews())));
//        else
//            viewHolder.comicReviews.setText(context.getString(R.string.comic_detail_no_reviews));
//    }
//
//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView)
//    {
//        super.onAttachedToRecyclerView(recyclerView);
//        fragmentRelay = (FragmentInterface) context;
//    }
//
//    @Override
//    public int getItemCount()
//    {
//        return otherVersionComics.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder
//    {
//        ImageView comicCover;
//        TextView comicTitle;
//        TextView comicPublisher;
//        ImageView comicPublisherImprintSeparator;
//        TextView comicImprint;
//        TextView comicFormat;
//        TextView comicCoverDate;
//        TextView comicCoverPrice;
//        SimpleRatingBar comicUserRating;
//        TextView comicAverageRatings;
//        TextView comicReviews;
//
//        public ViewHolder(View itemView)
//        {
//            super(itemView);
//            comicCover = itemView.findViewById(R.id.comicListItemCover);
//            comicTitle = itemView.findViewById(R.id.comicListItemTitle);
//            comicPublisher = itemView.findViewById(R.id.comicListItemPublisher);
//            comicPublisherImprintSeparator = itemView.findViewById(R.id.comicListItemPublisherImprintSeparator);
//            comicImprint = itemView.findViewById(R.id.comicListItemImprint);
//            comicFormat = itemView.findViewById(R.id.comicListItemFormat);
//            comicCoverDate = itemView.findViewById(R.id.comicListItemDate);
//            comicCoverPrice = itemView.findViewById(R.id.comicListItemPrice);
//            comicUserRating = itemView.findViewById(R.id.comicListItemUserRating);
//            comicAverageRatings = itemView.findViewById(R.id.comicListItemAvgReviewText);
//            comicReviews = itemView.findViewById(R.id.comicListItemReviewsText);
//        }
//    }
//
//    public void setClickListener(View.OnClickListener callback) {
//        mClickListener = callback;
//    }
}
