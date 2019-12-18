package com.gabrieldchartier.compendia.ui.main.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.session.SessionManager
import com.gabrieldchartier.compendia.ui.main.MainActivity
import kotlinx.android.synthetic.main.detail_toolbar.*
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseHomeFragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is Activity)
            mainActivity = context as MainActivity
        else
            Log.e("SettingsFragment", "onAttach (line 33): context was not an instance of activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.displayBottomNav(false)
        initializeFragmentToolbar()
        setOnClickListeners()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            android.R.id.home -> {
                mainActivity.onBackPressed()
                return true
            }
        }
        return true
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
    }
}
