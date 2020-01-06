package com.gabrieldchartier.compendia.recycler_views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabrieldchartier.compendia.R;
import com.gabrieldchartier.compendia.models.Comic;
import com.gabrieldchartier.compendia.recycler_views.ComicChildViewHolder;
import com.gabrieldchartier.compendia.recycler_views.ExpandableCategoryViewHolder;
import com.gabrieldchartier.compendia.recycler_views.OtherVersionRecyclerCategory;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;

public class OtherVersionsExpandableRecyclerAdapter// extends ExpandableRecyclerViewAdapter<ExpandableCategoryViewHolder, ComicChildViewHolder>
{

    //todo rework this

//    private Context context;
//    private View.OnClickListener mClickListener;
//    public OtherVersionsExpandableRecyclerAdapter(List<? extends ExpandableGroup> groups, Context context)
//    {
//        super(groups);
//        this.context = context;
//    }
//
//    @Override
//    public ExpandableCategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType)
//    {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
//        return new ExpandableCategoryViewHolder(view);
//    }
//
//    @Override
//    public ComicChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType)
//    {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_list_item, parent, false);
//        ComicChildViewHolder holder = new ComicChildViewHolder(view);
//        holder.itemView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//                mClickListener.onClick(view);
//            }
//        });
//        return holder;
//    }
//
//    @Override
//    public void onBindChildViewHolder(ComicChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex)
//    {
//        final Comic comic = ((OtherVersionRecyclerCategory) group).getItems().get(childIndex);
//        holder.onBind(comic, context);
//    }
//
//    @Override
//    public void onBindGroupViewHolder(ExpandableCategoryViewHolder holder, int flatPosition, ExpandableGroup group)
//    {
//        holder.setCategoryTitle(group);
//    }
//
//    public void setClickListener(View.OnClickListener callback)
//    {
//        mClickListener = callback;
//    }
//
//    public void expandAll(List<? extends ExpandableGroup> groups)
//    {
//        for(ExpandableGroup group : groups)
//            if(!isGroupExpanded(group))
//                toggleGroup(group);
//    }
}
