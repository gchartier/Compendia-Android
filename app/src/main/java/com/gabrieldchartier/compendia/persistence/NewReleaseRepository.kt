package com.gabrieldchartier.compendia.persistence

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class NewReleaseRepository(context : Context)
{
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    companion object
    {
        val TAG : String = "NewReleaseRepository"
    }

//    private var comicDatabase : ComicDatabase? = null
//
//    init
//    {
//        comicDatabase = ComicDatabase.getInstance(context)
//    }

    // You would call this from the fragment to insert a comic async
//    fun insertNewReleasesTask(vararg newReleases : Comic?)
//    {
//
//    }

//    fun updateNewRelease(newRelease : Comic)
//    {
//
//    }

//    suspend fun retrieveNewReleasesTask() : LiveData<List<Comic>>?
//    { // TODO fix cahceing and remove next line when done so
//        thread{comicDatabase?.getNewReleaseDao()?.nukeTable()}
//        val newReleases : LiveData<List<Comic>>? = slowFetch()
//        Log.d(TAG, "OYYY $newReleases.value")
//        if(newReleases?.value.isNullOrEmpty()) //TODO Add cache timeout condition here as well
//        {
//            //TODO call API web service to load the data and store cache
//            Log.d(TAG, "Cache was empty or old, refreshing with new data: " + newReleases?.value.toString())
//            InsertAsyncTask(comicDatabase?.getNewReleaseDao()).execute(*TempCollection().comics.toTypedArray())
//        }
//        else
//        {
//            Log.d(TAG, "Cache was present, loading from cache")
//            Log.d(TAG, "Cache present: " + newReleases?.value.toString())
//        }
//        return newReleases
//    }
//
//    suspend fun slowFetch(): LiveData<List<Comic>>?
//    {
//        return comicDatabase?.getNewReleaseDao()?.getNewReleases()
//    }
}