package com.gabrieldchartier.compendia;

import com.gabrieldchartier.compendia.models.Comic;

public interface FragmentInfoRelay
{
    void inflateComicDetailFragment(Comic comic);
    void inflateOtherVersionsFragment(String[] otherVersions, String comicTitle);
    void onBackPressed();
}
