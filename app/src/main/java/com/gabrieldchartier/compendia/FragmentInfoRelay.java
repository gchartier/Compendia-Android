package com.gabrieldchartier.compendia;

import com.gabrieldchartier.compendia.models.Comic;

import java.util.List;
import java.util.UUID;

public interface FragmentInfoRelay
{
    void inflateComicDetailFragment(Comic comic);
    void inflateOtherVersionsFragment(List<UUID> otherVersions, String comicTitle);
    void onBackPressed();
}
