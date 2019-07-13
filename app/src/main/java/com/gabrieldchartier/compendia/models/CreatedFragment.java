package com.gabrieldchartier.compendia.models;

import android.support.v4.app.Fragment;

public class CreatedFragment
{
    private Fragment fragment;
    private String tag;

    public CreatedFragment(Fragment fragment, String tag)
    {
        this.fragment = fragment;
        this.tag = tag;
    }

    public Fragment getFragment()
    {
        return fragment;
    }

    public void setFragment(Fragment fragment)
    {
        this.fragment = fragment;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }
}
