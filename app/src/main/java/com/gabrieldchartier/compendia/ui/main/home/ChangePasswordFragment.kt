package com.gabrieldchartier.compendia.ui.main.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.ui.main.MainActivity
import com.gabrieldchartier.compendia.ui.main.home.state.ChangePasswordFields
import com.gabrieldchartier.compendia.ui.main.home.state.HomeStateEvent
import com.gabrieldchartier.compendia.util.SuccessHandling.Companion.RESPONSE_PASSWORD_UPDATE_SUCCESS
import kotlinx.android.synthetic.main.detail_toolbar.*
import kotlinx.android.synthetic.main.fragment_change_password.*

class ChangePasswordFragment : BaseHomeFragment() {

    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is Activity)
            mainActivity = context as MainActivity
        else
            Log.e("SettingsFragment", "onAttach (line 33): context was not an instance of activity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        initializeFragmentToolbar()

        change_password_button.setOnClickListener {
            viewModel.setStateEvent(HomeStateEvent.ChangePasswordEvent(
                    current_password.text.toString(),
                    new_password.text.toString(),
                    new_password_confirmation.text.toString()
            ))
        }
        (activity as AppCompatActivity).supportActionBar!!.show()
        mainActivity.displayBottomNav(false)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer {
            it.data?.let { data ->
                data.response?.let { viewStateEvent ->
                    if(viewStateEvent.peekContent().message.equals(RESPONSE_PASSWORD_UPDATE_SUCCESS))
                        findNavController().popBackStack()
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {  viewState ->
            viewState.changePasswordFields?.let {
                setChangePasswordFields(it)
            }
        })
    }

    private fun setChangePasswordFields(changePasswordFields: ChangePasswordFields) {
        current_password.setText(changePasswordFields.currentPassword)
        new_password.setText(changePasswordFields.newPassword)
        new_password_confirmation.setText(changePasswordFields.newPasswordConfirmation)
    }

    private fun initializeFragmentToolbar()
    {
        (activity as MainActivity).setSupportActionBar(change_password_toolbar as Toolbar)
        val actionBar: ActionBar? = (activity as MainActivity).supportActionBar

        if (actionBar != null)
        {
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(true)
            fragmentHeader.text = getString(R.string.tag_fragment_change_password)
        }
        else
            Log.e("ChangePaswordFragment", "initializeFragmentToolbar (line 88)")
    }
}