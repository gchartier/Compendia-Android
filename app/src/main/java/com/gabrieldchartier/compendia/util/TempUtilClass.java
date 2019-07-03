package com.gabrieldchartier.compendia.util;

import android.content.Context;

public class TempUtilClass
{
    public static int getImage(Context context, String imageName)
    {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}
