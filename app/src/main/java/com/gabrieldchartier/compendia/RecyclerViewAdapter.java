package com.gabrieldchartier.compendia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gabrieldchartier.compendia.models.Comic;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    // Constants
    private static final String TAG = "RecyclerViewAdapter";

    // Variables
    private ArrayList<Comic> mComics;
    private Context mContext;
    private FragmentInfoRelay fragmentRelay;

    public RecyclerViewAdapter(Context mContext, ArrayList<Comic> mComics)
    {
        this.mComics = mComics;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        Log.d(TAG, "onCreateViewHolder called");

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i)
    {
        Log.d(TAG, "OnBindViewHolder called");
        Glide.with(mContext).asBitmap().load(mComics.get(viewHolder.getAdapterPosition()).getCover()).into(viewHolder.image);
        viewHolder.image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "onClick: clicked on a comic " + mComics.get(viewHolder.getAdapterPosition()).getTitle());
                fragmentRelay.inflateComicDetailFragment(mComics.get(viewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        fragmentRelay = (FragmentInfoRelay) mContext;
    }

    @Override
    public int getItemCount()
    {
        return mComics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        public ViewHolder(View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.otherVersionsListItemCover);

        }
    }
}
