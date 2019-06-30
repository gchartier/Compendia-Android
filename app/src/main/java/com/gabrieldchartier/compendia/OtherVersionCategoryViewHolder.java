package com.gabrieldchartier.compendia;

import android.view.View;
import android.widget.TextView;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class OtherVersionCategoryViewHolder extends GroupViewHolder
{

        private TextView otherVersionCategory;

        public OtherVersionCategoryViewHolder(View itemView)
        {
            super(itemView);
            otherVersionCategory = itemView.findViewById(R.id.otherVersionListItemCategory);
        }

        public void setOtherVersionCategory(ExpandableGroup group)
        {
            otherVersionCategory.setText(group.getTitle());
        }
}
