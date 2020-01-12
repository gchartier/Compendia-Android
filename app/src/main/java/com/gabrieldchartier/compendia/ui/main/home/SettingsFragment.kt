package com.gabrieldchartier.compendia.ui.main.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.models.AccountProperties
import com.gabrieldchartier.compendia.session.SessionManager
import com.gabrieldchartier.compendia.ui.main.MainActivity
import com.gabrieldchartier.compendia.ui.main.home.state.HomeStateEvent
import kotlinx.android.synthetic.main.detail_toolbar.*
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseHomeFragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).displayBottomNav(false)
        initializeFragmentToolbar()
        setOnClickListeners()
        subscribeObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            android.R.id.home -> {
                (activity as MainActivity).onBackPressed()
                return true
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.setStateEvent(HomeStateEvent.GetAccountPropertiesEvent())
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer {
            stateChangeListener.onDataStateChange(it)
            it?.let {dataState ->
                dataState.data?.let { data ->
                    data.data?.let { event ->
                        event.getContentIfNotHandled()?.let { viewState ->
                            viewState.accountProperties?.let { accountProperties ->
                                viewModel.setAccountPropertiesData(accountProperties)
                            }
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {  viewState ->
            viewState.accountProperties?.let {
                setAccountPropertiesFields(it)
            }
        })
    }

    private fun initializeFragmentToolbar()
    {
        (activity as MainActivity).setSupportActionBar(settings_toolbar as Toolbar)
        val actionBar: ActionBar? = (activity as MainActivity).supportActionBar

        if (actionBar != null)
        {
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(true)
            fragmentHeader.text = getString(R.string.tag_fragment_settings)
        }
        else
            Log.e("SettingsFragment", "initializeFragmentToolbar (line 56)")
    }

    private fun setOnClickListeners() {
        log_out_button.setOnClickListener { viewModel.logout() }

        change_password_button.setOnClickListener { findNavController().navigate(R.id.action_settingsFragment_to_changePasswordFragment) }
    }

    private fun setAccountPropertiesFields(accountProperties: AccountProperties) {
        account_username_field?.text = accountProperties.username
    }
}
