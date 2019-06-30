package com.gabrieldchartier.compendia;

import com.gabrieldchartier.compendia.models.Comic;

public interface FragmentInterface
{
    void inflateComicDetailFragment(Comic comic);
    void inflateOtherVersionsFragment(String[] otherVersions, String comicTitle);
    void onBackPressed();
}
