package com.gabrieldchartier.compendia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabrieldchartier.compendia.models.Comic;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;

public class OtherVersionsExpandableRecyclerAdapter extends ExpandableRecyclerViewAdapter<OtherVersionCategoryViewHolder, ComicChildViewHolder>
{
    private Context context;
    private View.OnClickListener mClickListener;
    public OtherVersionsExpandableRecyclerAdapter(List<? extends ExpandableGroup> groups, Context context)
    {
        super(groups);
        this.context = context;
    }

    @Override
    public OtherVersionCategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_version_category_list_item, parent, false);
        return new OtherVersionCategoryViewHolder(view);
    }

    @Override
    public ComicChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_versions_list_item, parent, false);
        ComicChildViewHolder holder = new ComicChildViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(view);
            }
        });
        return holder;
    }

    @Override
    public void onBindChildViewHolder(ComicChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex)
    {
        final Comic comic = ((OtherVersionRecyclerCategory) group).getItems().get(childIndex);
        holder.onBind(comic, context);
    }

    @Override
    public void onBindGroupViewHolder(OtherVersionCategoryViewHolder holder, int flatPosition, ExpandableGroup group)
    {
        holder.setOtherVersionCategory(group);
    }

    public void setClickListener(View.OnClickListener callback)
    {
        mClickListener = callback;
    }
}
