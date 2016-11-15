package vidsapp.wowapps.com.vidsapp.util;

import java.util.ArrayList;

/**
 * Created by somendra on 11-Nov-16.
 */
public class VideoModelUtil {

    private static VideoModelUtil ourInstance = null;

    // Cat, Sub-cat and vids list reference
    private ArrayList<String> vCatList = null;

    private ArrayList<String> vHomeRemedyList = null;
    private ArrayList<String> vHomHighBPList = null;
    private ArrayList<String> vHomDiabetesList = null;
    private ArrayList<String> vHomObesityList = null;
    private ArrayList<String> vHomCholesterolList = null;
    private ArrayList<String> vHomHeartProbsList = null;
    private ArrayList<String> vHomHeadacheList = null;
    private ArrayList<String> vHomLiverProbsList = null;
    private ArrayList<String> vHomBellyFatList = null;
    private ArrayList<String> vHomCoughColdList = null;
    private ArrayList<String> vHomSinusitisList = null;
    private ArrayList<String> vHomGastricList = null;
    private ArrayList<String> vHomFeverList = null;
    private ArrayList<String> vHomGiddinessList = null;
    private ArrayList<String> vHomLooseMotionList = null;
    private ArrayList<String> vHomVomitingList = null;
    private ArrayList<String> vHomConstipationList = null;
    private ArrayList<String> vHomDandruffList = null;

    private ArrayList<String> vBeautyTipsList = null;

    private ArrayList<String> vMotivationalList = null;
    private ArrayList<String> vMotSandeepMList = null;
    private ArrayList<String> vMotHeemeshMList = null;
    private ArrayList<String> vMotTanejaList = null;
    private ArrayList<String> vMotUjjwalPList = null;
    private ArrayList<String> vMotOthersList = null;

    private ArrayList<String> vMythologicalList = null;
    private ArrayList<String> vMytChalisaList = null;
    private ArrayList<String> vMytAartiList = null;
    private ArrayList<String> vMytMantraList = null;
    private ArrayList<String> vMytBhajansList = null;
    private ArrayList<String> vMytOthersList = null;

    private ArrayList<String> vBWSongsList = null;
    private ArrayList<String> vSongSadList = null;
    private ArrayList<String> vSongRomanticList = null;
    private ArrayList<String> vSongNaughtyList = null;
    private ArrayList<String> vSongDanceList = null;
    private ArrayList<String> vSongSlowList = null;
    private ArrayList<String> vSongOldMelodyList = null;
    private ArrayList<String> vSong90sHitList = null;
    private ArrayList<String> vSongQawwaliList = null;

    private ArrayList<String> vGhazalsList = null;
    private ArrayList<String> vGhazJagjitList = null;
    private ArrayList<String> vGhazPankajList = null;
    private ArrayList<String> vGhazHariharanList = null;
    private ArrayList<String> vGhazGhulamALiList = null;
    private ArrayList<String> vGhazMehdiHasanList = null;
    private ArrayList<String> vGhazTalatAzizList = null;
    private ArrayList<String> vGhazAnupJalotaList = null;
    private ArrayList<String> vGhazNusratList = null;
    private ArrayList<String> vGhazOtherList = null;



    public static VideoModelUtil getInstance() {
        if (ourInstance == null) {
            ourInstance = new VideoModelUtil();
        }
        return ourInstance;
    }

    private VideoModelUtil() {
    }

    private void initializeVidsCategory() {
        vCatList = new ArrayList<String>();
        vCatList.add("Home remedies");
        vCatList.add("Beauty tips");
        vCatList.add("Motivational");
        vCatList.add("Mythological");
        vCatList.add("Bollywood songs");
        vCatList.add("Gazals");
        vCatList.add("Shayari");
        vCatList.add("Kavi sammelan");
        vCatList.add("Bollywood movies");
        vCatList.add("Kids");
        vCatList.add("Parenting");
        vCatList.add("Food recipes");
    }

    private void initializeVidsSubCategory() {

    }
}
