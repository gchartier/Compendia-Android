package com.gabrieldchartier.compendia.async

import android.os.AsyncTask
import android.util.Log
import com.gabrieldchartier.compendia.models.Comic
import com.gabrieldchartier.compendia.persistence.NewReleaseDao

// Call this to insert a new release which I probably won't ever do but this is an example for the future
class InsertAsyncTask(newReleaseDao : NewReleaseDao?) : AsyncTask<Comic, Void, Void>()
{
    companion object
    {
        val TAG : String = "InsertAsyncTask"
    }
    private var newReleaseDao : NewReleaseDao? = null
    init
    {
        this.newReleaseDao = newReleaseDao
        Log.d(TAG, "NewReleaseDao: " + this.newReleaseDao)
    }

    override fun doInBackground(vararg params: Comic?): Void?
    {
        Log.d(TAG, "Log1")
        Log.d(TAG, "doInBackground: thread " + Thread.currentThread().name)
        Log.d(TAG, "Log2")
        newReleaseDao?.insertNewReleases(*params)
        Log.d(TAG, "Log3")
        return null
    }

}