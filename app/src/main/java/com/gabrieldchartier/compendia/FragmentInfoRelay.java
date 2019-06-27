package com.gabrieldchartier.compendia;

import com.gabrieldchartier.compendia.models.Comic;

import java.util.UUID;

public interface FragmentInfoRelay
{
    void inflateComicDetailFragment(Comic comic);
    void inflateOtherVersionsFragment(UUID[] otherVersions, String comicTitle);
    void initializeFragmentToolbar(int toolbarID, String TAG);
    void onBackPressed();
}
