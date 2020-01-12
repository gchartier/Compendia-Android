package com.gabrieldchartier.compendia.ui.main.comic.state

import com.gabrieldchartier.compendia.models.Comic

data class ComicViewState (var comicDetailFields: ComicDetailFields = ComicDetailFields()) {
    data class ComicDetailFields(var comic: Comic? = null)
}