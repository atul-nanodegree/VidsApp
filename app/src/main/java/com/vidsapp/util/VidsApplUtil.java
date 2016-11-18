package com.vidsapp.util;

import java.util.ArrayList;

/**
 * Created by somendra on 11-Nov-16.
 */
public class VidsApplUtil {

    public static String TYPE_VIDEO = "video";
    public static String TYPE_CHANNEL = "channel";

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
}
