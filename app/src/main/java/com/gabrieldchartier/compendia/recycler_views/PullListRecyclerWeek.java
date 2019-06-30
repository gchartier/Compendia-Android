package com.gabrieldchartier.compendia.recycler_views;

import com.gabrieldchartier.compendia.models.Comic;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;

public class PullListRecyclerWeek extends ExpandableGroup<Comic>
{
    public PullListRecyclerWeek(String pullListWeek, List<Comic> comics)
    {
        super(pullListWeek, comics);
    }
}
