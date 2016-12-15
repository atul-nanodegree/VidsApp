package com.vidsapp.util;

import android.content.Context;
import android.content.res.Configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by somendra on 11-Nov-16.
 */
public class VidsApplUtil {

    public static String TYPE_VIDEO = "video";
    public static String TYPE_CHANNEL = "channel";
    public static String TYPE_PLAYLIST = "playlist";
    public static String FAV_FILE_NAME = "FavFile";

    public static String formatVidsList(String[] vids) {
        StringBuffer formatedVids = new StringBuffer();
        if (vids != null) {
            for (int i = 0; i < vids.length; i++) {
                formatedVids.append(vids[i]);
                formatedVids.append(",");
            }
            formatedVids.deleteCharAt(formatedVids.length() - 1);
        }
        return formatedVids.toString();
    }

    public static void writeDataInFile(Context ctx, String file, String data) {
        try {
            FileOutputStream fOut = ctx.openFileOutput(file,ctx.MODE_PRIVATE);
            fOut.write(data.getBytes());
            fOut.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readDataFromFile(Context ctx, String file) {
        String data = "";
        try {
            FileInputStream fin = ctx.openFileInput(file);
            int c;
            while( (c = fin.read()) != -1){
                data = data + Character.toString((char)c);
            }
        }
        catch(Exception e){
        }
        return data;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
