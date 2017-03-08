package com.rajat.android.aller.Util;

import android.net.Uri;

/**
 * Created by rajat on 3/8/2017.
 */

public class Utilities {
    public static String convertToString(CharSequence sequence){
        if(sequence != null){
            return sequence.toString();
        }
        return null;
    }
    public static String convertToString(Uri sequence){
        if(sequence != null){
            return sequence.toString();
        }
        return null;
    }
    public static String convertToString(Float sequence){
        if(sequence != null){
            return sequence.toString();
        }
        return null;
    }
}
