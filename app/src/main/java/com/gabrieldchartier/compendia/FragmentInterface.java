package com.gabrieldchartier.compendia;

import com.gabrieldchartier.compendia.models.Comic;
import com.gabrieldchartier.compendia.models.ComicBox;
import com.gabrieldchartier.compendia.models.ComicCreator;

import java.util.List;
import java.util.UUID;

public interface FragmentInterface
{
    void inflateComicDetailFragment(Comic comic);
    void inflateOtherVersionsFragment(String[] otherVersions, String comicTitle);
    void inflateSettingsFragment();
    void inflateBoxesFragment();
    void inflateBoxDetailFragment(ComicBox box);
    void inflateNewReleasesFragment();
    void inflateCreatorDetailFragment(UUID creatorID);
    void inflateCreatorsListFragment(List<ComicCreator> creators);
    void inflateReviewsFragment(UUID comicID);
    void inflateFullCoverFragment(String cover);
    void onBackPressed();
}
