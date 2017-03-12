package com.rajat.android.aller.Util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

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

    public static Float parseRating(String string){
        Float value = Float.parseFloat(string);
        if(value>0){
            return value;
        }
        return Float.parseFloat("1");

    }



    public static String getTimeStamp() {
        Long tsLong = System.currentTimeMillis()/1000;
        String timestamp = tsLong.toString();
        return timestamp;
    }
}
