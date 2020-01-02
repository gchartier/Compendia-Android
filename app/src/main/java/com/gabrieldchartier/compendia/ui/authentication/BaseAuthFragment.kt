package com.gabrieldchartier.compendia.ui.authentication

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.gabrieldchartier.compendia.view_models.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseAuthFragment: DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        cancelActiveJobs()
    }

    fun cancelActiveJobs() {
        viewModel.cancelActiveJobs()
    }
}