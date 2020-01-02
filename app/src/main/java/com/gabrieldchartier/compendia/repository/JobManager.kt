package com.gabrieldchartier.compendia.repository

import android.util.Log
import kotlinx.coroutines.Job

open class JobManager (private val className: String) {
    private val jobs: HashMap<String, Job> = HashMap()

    fun addJob(methodName: String, job: Job) {
        cancelJob(methodName)
        jobs[methodName] = job
    }

    fun cancelJob(methodName: String) {
        getJob(methodName)?.cancel()
    }

    fun getJob(methodName: String): Job? {
        if(jobs.containsKey(methodName))
            return jobs[methodName]?.let { return it }
        else
            return null
    }

    fun cancelActiveJobs() {
        for((methodName, job) in jobs)
            if (job.isActive) {
                Log.e("JobManager", "$className: cancelling job in $methodName")
                job.cancel()
            }
    }

}