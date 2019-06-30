package com.gabrieldchartier.compendia;

import com.gabrieldchartier.compendia.models.Comic;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;

public class OtherVersionRecyclerCategory extends ExpandableGroup<Comic>
{

    public OtherVersionRecyclerCategory(String otherVersionCategory, List<Comic> comics)
    {
        super(otherVersionCategory, comics);
    }
}