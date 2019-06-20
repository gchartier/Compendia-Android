package com.gabrieldchartier.compendia;

import com.gabrieldchartier.compendia.models.Comic;

public interface FragmentInfoRelay
{
    void inflateComicDetailFragment(Comic comic);
    void onBackPressed();
}
