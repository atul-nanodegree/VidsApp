package com.vidsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.vidsapp.util.VidsApplUtil;

public class VidsActivity extends AppCompatActivity {

    private Spinner categorySpinner, subCategorySpinner;
    private  CoordinatorLayout mMainCoordinatorLayout;
    private VideosListFragment mVideoFragment;
    private Fragment mPlayListFragment;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vids);


        //Displaying banner ads at bottom of screen

      //  mAdView = (AdView) findViewById(R.id.adView);

        mAdView=new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(getResources().getString(R.string.banner_home_footer_video));

        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM;
        // layoutParams.gravity = isTopPosition ? Gravity.TOP : Gravity.BOTTOM;

        this.addContentView(mAdView, layoutParams);

        AdRequest adRequest = new AdRequest.Builder()
                // Add a test device to show Test Ads
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("501CA82BA12681A250E422CC4BF70A13") //Random Text
                .build();
        mAdView.loadAd(adRequest);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setLogo(R.drawable.icon);
        mMainCoordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatelayout);

        initializeVidsCategory();
        mVideoFragment = new VideosListFragment();
        mPlayListFragment = new PlayListFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_frame, mPlayListFragment, mPlayListFragment.getClass().getSimpleName()).commit();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, mVideoFragment, mVideoFragment.getClass().getSimpleName()).commit();

    }

    private void initializeVidsCategory() {
        categorySpinner = (Spinner) findViewById(R.id.category);
        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text,
                getResources().getStringArray(R.array.vids_category));
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(catAdapter);
        categorySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        String selectedCategory = parent.getItemAtPosition(pos).toString();
                        if (selectedCategory != null) {
                            if (selectedCategory.equalsIgnoreCase("Home remedies")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.home_remedies_list));
                            } else if (selectedCategory.equalsIgnoreCase("Motivational")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.motivational_list));
                            } else if (selectedCategory.equalsIgnoreCase("Beauty tips")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.beauty_tips_list));
                            } else if (selectedCategory.equalsIgnoreCase("Bollywood songs")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.songs_list));
                            } else if (selectedCategory.equalsIgnoreCase("Shayari/Poetry")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.shayari_poetry_list));
                            }
                            else if (selectedCategory.equalsIgnoreCase("Tamil special")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.tamil_list));
                            }
                            else if (selectedCategory.equalsIgnoreCase("Telugu special")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.telugu_list));
                            }else if (selectedCategory.equalsIgnoreCase("Bollywood movies")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.movies_list));
                            } else if (selectedCategory.equalsIgnoreCase("News channels")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.live_news_channels));
                            }
                            else if (selectedCategory.equalsIgnoreCase("Food recipes")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.food_recepies));
                            }
                            else if (selectedCategory.equalsIgnoreCase("Kids")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.kids_section));
                            } else if (selectedCategory.equalsIgnoreCase("Mythological")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.mythological_list));
                            }
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }

    private void initializeVidsSubCategory(String[] subCategory) {
        subCategorySpinner = (Spinner) findViewById(R.id.sub_category);
        ArrayAdapter<String> subCatAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_text, subCategory);
        subCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subCategorySpinner.setAdapter(subCatAdapter);
        subCategorySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        String selectedSubCategory = parent.getItemAtPosition(pos).toString();
                        if (selectedSubCategory != null) {
                            if(!NetworkUtil.isConnected(VidsActivity.this)) {
                                Snackbar.make(mMainCoordinatorLayout, "No internet connection.Check your connection and try again", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                return;
                            }
                            String formatedVidsList = null;
                            String videoType = null;
                            if (selectedSubCategory.equalsIgnoreCase("High blood pressure")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.high_bp_vids));
                                videoType = VidsApplUtil.TYPE_VIDEO;
                            } else if (selectedSubCategory.equalsIgnoreCase("Diabetes")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.diabetes_vids));
                                videoType = VidsApplUtil.TYPE_VIDEO;
                            } else if (selectedSubCategory.equalsIgnoreCase("Obesity")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.obesity_vids));
                                videoType = VidsApplUtil.TYPE_VIDEO;
                            } else if (selectedSubCategory.equalsIgnoreCase("High cholesterol")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.cholesterol_vids));
                                videoType = VidsApplUtil.TYPE_VIDEO;
                            } else if (selectedSubCategory.equalsIgnoreCase("Heart diseases")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.heart_vids));
                                videoType = VidsApplUtil.TYPE_VIDEO;
                            } else if (selectedSubCategory.equalsIgnoreCase("Dengue / Fever")) {
                                formatedVidsList = "PL3VNq9vJiHYiDQKw7y9INSjUF5fesm7ww," +
                                        "PLx_SXrzz9En9k7PxkhxHNzgbQIhGUMflh," +
                                        "PLx_SXrzz9En9ig7toZLXuIYXiB9yKi4bw," +
                                        "PLIEH2_hww8lzKOgB4v5CgXA_kpmJJzZdQ," +
                                        "PLQtlDeMxHkRi00saV53BiREAspJTYZSFu," +
                                        "PL2pHEdOz05rUzcL4nlpMR_iEocBuHUAkz";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Dandruff")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.dandruff_vids));
                                videoType = VidsApplUtil.TYPE_VIDEO;
                            } else if (selectedSubCategory.equalsIgnoreCase("Sandeep Maheshwari")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.motiv_sandeep_m_vids));
                                videoType = VidsApplUtil.TYPE_VIDEO;
                            } else if (selectedSubCategory.equalsIgnoreCase("Him-eesh Madaan")) {
                                formatedVidsList = "UCZQDF0x18Xe6RZayvod99zA";
                                videoType = VidsApplUtil.TYPE_CHANNEL;
                            } else if (selectedSubCategory.equalsIgnoreCase("Ujjwal Patni")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.motiv_ujjwal_patni_vids));
                                videoType = VidsApplUtil.TYPE_VIDEO;
                            } else if (selectedSubCategory.equalsIgnoreCase("TS Madaan")) {
                                formatedVidsList = "UCKjJXmZxk1SiTUQUB28KHpw";
                                videoType = VidsApplUtil.TYPE_CHANNEL;
                            } else if (selectedSubCategory.equalsIgnoreCase("Other motivational")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.motiv_others_vids));
                                videoType = VidsApplUtil.TYPE_VIDEO;
                            } else if (selectedSubCategory.equalsIgnoreCase("Arushi Jain")) {
                                formatedVidsList = "UClg1BK5TSUqbepZH4PU_coQ";
                                videoType = VidsApplUtil.TYPE_CHANNEL;
                            } else if (selectedSubCategory.equalsIgnoreCase("Sneha")) {
                                formatedVidsList = "UChl2JWlia8biiCBPxmWlhzA";
                                videoType = VidsApplUtil.TYPE_CHANNEL;
                            } else if (selectedSubCategory.equalsIgnoreCase("Rani")) {
                                formatedVidsList = "UClexepNPmyQUCNapwOwS0cQ";
                                videoType = VidsApplUtil.TYPE_CHANNEL;
                            } else if (selectedSubCategory.equalsIgnoreCase("Trisha")) {
                                formatedVidsList = "UC2QBCIyo_FbTlm0Rfh_6wkw";
                                videoType = VidsApplUtil.TYPE_CHANNEL;
                            } else if (selectedSubCategory.equalsIgnoreCase("Herbal beauty tips")) {
                                formatedVidsList = "UCbEVwbYCJpmJ0Kb69WOVD0w";
                                videoType = VidsApplUtil.TYPE_CHANNEL;
                            } else if (selectedSubCategory.equalsIgnoreCase("Romantic melodies")) {
                                formatedVidsList = "PL9833A9778A6BB457,PLCD0BBCF0821C9084,PL5dxFpVs9q2mZxqWNNLsNdQEEhO9ilR6J," +
                                        "PLMzHS1Fh7fElUHzikx0v71_qOtgt-h1wJ,PLL30uj4mBoYP-8-CZ-9fqW7KmuU2M2vy0,PL30D1D396F4CDFBB0," +
                                        "PL33748924FF32D510,PL7AD198111F85DB9C,PLE58787A354301DFE,PLHsv_F3G7XLXOQIZ60Bj7P2KkbFcZ5ph_," +
                                        "PLdSBm5cs_QX-H_LAu_6SZ2L9rUD__k2zR,PL4BD003AD44BA8658,PL_m7vkBtlHcBtQt9bWEMCpA8RB2crrdtY," +
                                        "PLEpfh9jiEpYSYIUMQjeCO1lUmpYZv3Qtc,PL6yJ_wRlltkn2PASVNv9hl5FA_PF2aje1," +
                                        "PL9RkVPgrltm6Tz4bXEpjaKX2cfW1UV5o5,PLC8F558ADFCC36381,PLjity7Lwv-zoY0kEMflSdq4jfSN123Zt8" +
                                        "PL7J3pqkCagEn_E_9gXYFLHkDtJcacRoxm,PLfXBPjrIbg2ccBaZO0yNCcymQzLPY-tyG," +
                                        "PLPCDrz-Og2T-u0oh_A-O0rwNekXyPWWbr,PLC12343C91911624A,PLEpfh9jiEpYTbAx1qZRhVUXnl8UHuTHIY," +
                                        "PLg1BjcWzCFSYBOBmeR0NajiHXG0_LRQgw,PLVWtdc2bihyxeAe9fXdknxwuTPyrSdR57," +
                                        "PLP4sPcNdogm31VTqOM50FTVG8JRe77O4B,PL4uUU2x5ZgR1JOlcY9SZB94MW6fBE8ovU";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Sad melodies")) {
                                formatedVidsList = "PLEEDF17AE177F527B,PL509690A35754E24F,PL448273752D6A392B,PL4BC9A2F7F2B75D83," +
                                        "PL63EEC96AFCC324A4&index=4,PL901339D6BC9E5D73,PLACAFFF15158CE67E" +
                                        "PL814FA5D5BEC48E7F,PL8E2CBE8655E5154F,PLA6yIV3qMD7j2ohZs4HAI94yMwrxHtqa_";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Soft melodies")) {
                                formatedVidsList = "PLYkopulgW-Z2cFmcf4d15Tao6Rak8WZYb,PL-tiXFkBA6IyMEVAno7PxyQ3s2yeMykjo," +
                                        "PLkClf2ueYJXhyyRSOAA0A3qjTMdcbgTNe,PL2540C704C182FA98,PL05CAD8640438D536," +
                                        "PLiAttA3ZvGfk1vuF8Xq7j24sBsg4QuH17,PLeoPwI_un2DHGq6EZ2u6tWagq4FmXLpw9,PLpPW6R_JX0Uv34fU4ifzQjbC1TFSCZ3od," +
                                        "PLAE90B1604A57DB28";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Wedding special")) {
                                formatedVidsList = "PLq7Fj9d8nmvNYtqRNdszwjloSUup_4NmG,PLaJZU9rQ6wK9KEUV1s0OUZqQ5q_9ueqo6," +
                                        "PLLy1_srkKwEXnPNuLy7zgXwxMqz5RoX66,PLRH3rh7wq6OBfLpttBCc0WSQWlzj9KynI";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Choice of Punjabi")) {
                                formatedVidsList = "PLvgBVbOFNRybywHIFTAKZaoKuiScRRDTT,PLRFkvWpQ2NJy1Z6khi24LCj4fKevPyG2M&index=7," +
                                        "PLFFyMei_d85U5RQdXjRQ5F012qr4vSmSa,PL-zNzV4g9SYmlUoQZpnnHTi5XC88YYldC," +
                                        "PLFFyMei_d85UsOmq3EfRfEcwILgugeuUU,PLYbDx92VRr8Y2HfwZFbMZxKBdjDI0A4OO," +
                                        "PLAd87v8kxZ9JLo1DvgYcpOv4IBILRxzqU,PLEK4199_zBwAOSel6zxVBmP1eSH_Tg9kl";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Singers collection")) {
                                formatedVidsList = "PLcW36x0vC9XLd-z-Gu55FtTuQgxknRhd5,PLGMW_7AY_F42LG1ZtE5LnLT4bK42_sIDi," +
                                        "PL_ojMrMyUd15UV2hNQdFzyuYzENI68y6Y,PLqkoKqLHcWCyuG3bxMkOmH1Z18yFzny5i," +
                                        "PLwOScGCjb93DDU64Ai0E-GbnbH3aOh3R2,PLebd87M1RwWVnywOe04Y5GlZfa4fHF4nY," +
                                        "PL9850DBE604B4D416,PLIoF1EmGOU3D8wjbiuTPSIPddPev1cj5h,PLRmdV1u90qKkT7l10w6c4OOxbbvNZxWdE," +
                                        "PLP15S8bV9o5bPHmsXzVvcG-WMa_7HDkyH,PLHdrJ3cHREuY_IpdqzaGMrZ0er03dE8SU," +
                                        "PL3492286914EC878C,PLhDMUiHBg1erHxiWeP8OkSeZ0i0CDxshQ,PL8E2DE298DB5DCCA8," +
                                        "PL9AFFF4ABB022B7AE,PL98E962440015C6A1,PL0355E07646C0C291,PLcW36x0vC9XK5kIxUGCgcCRTB8kb3ZDZn," +
                                        "PLA826A6E6DED51189,PLCF6C3CDD70DCB26A,PLCwTa6m59uGXwRF_6-3Udi5L2W-9DAm6I," +
                                        "PLgh_EjxPZ7fqwFgMvixpGOq1ndnz3tgJs,PLARm22A77n4N9sMd_AeI9AOCOY3rCkWKP," +
                                        "PLD6DB477019E5F012,PLShHEGqasFSzx81JoKnDUtfFUokISYA3F";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } if (selectedSubCategory.equalsIgnoreCase("Rock the dance floor")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.dance_florr_playlist));
                                videoType = VidsApplUtil.TYPE_VIDEO;
                            } else if (selectedSubCategory.equalsIgnoreCase("Latest jukebox")) {
                                formatedVidsList = "UCFdfwBIBO0t8u06PvApgnPg";
                                videoType = VidsApplUtil.TYPE_CHANNEL;
                            } else if (selectedSubCategory.equalsIgnoreCase("Remix jukebox")) {
                                formatedVidsList = "UCbMtyOUNOQKWOGyoCAlNicw";
                                videoType = VidsApplUtil.TYPE_CHANNEL;
                            } else if (selectedSubCategory.equalsIgnoreCase("Filmi melody")) {
                                formatedVidsList = "UCP6uH_XlsxrXwZQ4DlqbqPg";
                                videoType = VidsApplUtil.TYPE_CHANNEL;
                            } else if (selectedSubCategory.equalsIgnoreCase("Pop Chartbusters")) {
                                formatedVidsList = "UCzL6rJhkoXkIt0fCv9T9_uA";
                                videoType = VidsApplUtil.TYPE_CHANNEL;
                            } else if (selectedSubCategory.equalsIgnoreCase("Sune ansune gaane")) {
                                formatedVidsList = "UCtW7qWjpCZ8zps-Cf2NF26w";
                                videoType = VidsApplUtil.TYPE_CHANNEL;
                            } else if (selectedSubCategory.equalsIgnoreCase("Hits of the week")) {
                                formatedVidsList = "UCzBeabhpibZNOecCvw3nUKA";
                                videoType = VidsApplUtil.TYPE_CHANNEL;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Hindi Hot Item Songs")) {
                                formatedVidsList = "PLHEnjnT8-WbLm_kxHT4Pbm5XGFCkUvnL_,PLAerbFI-NKthxQsIN8SnpCezDg57sB1X0,PL0Wy1yLrrfpb0-fUbzbFX8NT1tvwG_Jd_," +
                                        "PLAerbFI-NKthU8pkpUnrgGZykeJJVy-Fc,PLg94EwI1kNbkUcCdSLFsa9l2P5wXShxK3";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("A. R. Rahman Hit Tamil Songs")) {
                                formatedVidsList = "PLo-rZP7UP-Fmhaxktws5YXUoVPXImE6rK,PL4QNnZJr8sROExTFqbu-4OJIWS4XHZbng,";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Ilayaraja Hit Tamil Songs")) {
                                formatedVidsList = "PL8yOO2xYRcZtV9MerodXVdl2YIx60F87Y,PLNAG_jXQnh0J6WMVQTikNQ4ll53w0SPka";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("RajniKant Special")) {
                                formatedVidsList = "PLjity7Lwv-zpveZTZw7bcYKOwR1btkgl2,PLl97Be7_Hy_OQkeWTqdlt_7QbT4KlrK_u,PLjity7Lwv-zoZvasd7XbXzGWNcdwJnf6c";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Kamal Hasan Special")) {
                                formatedVidsList = "PL38A1F533861A4528,PLGgcJv7ZxqrU5iHgpORIYVoAjrqC3pBqk";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Tamil Movies")) {
                                formatedVidsList = "PLFAwfSsckJkQkhYY_4CNsHvKk2gSb9zKB,PLthizvY3wuX8KHZaynRSG61pbNY4JO_0m," +
                                        "PLpsfNoniec7yl7AGshjrr_lUXsMAxZE0C," +
                                        "PLV6iX0NmV9IhSupxYo_mYdBYGbiJAkvYM,PLx8w5arTJ_r-G28ItZVbUJcj05iRIGkPG,PLobdrPwSOEBzVMjnmxvruKS8T50qVb0xE," +
                                        "PLvd-Pw0LMeSVyBQCyciYRSv4GdCXUfNB2,PLUFNI2g6uaW6qLO1A7qiBwQu5E7hwRr3X";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }

                            else if (selectedSubCategory.equalsIgnoreCase("Tamil Hot Item Songs")) {
                                formatedVidsList = "PLjity7Lwv-zo-9PYexEaeJWFRkeZhKiJd,PL_eVpGrJ2tRZwFtsTuPMoVkBmJZTHnHZC";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("S. P. Balasubrahmanyam Songs")) {
                                formatedVidsList = "PLnKSZ48XMs3vafEfcBWHAH4HihSxlagtA,PLpZ-VVUqFU70GvGczMGNonpPQbfEXfPQ5,PLDA50F45B27E2BA2B,PL6A2EC60A87F71FCD";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Telugu Songs")) {
                                formatedVidsList = "PLamAR1afoIYHbwn9h4jJziV_EOa86yhmV,PLWG3kwozwBkdlonVOSMInAO-D9cwQB1jL,PLhJ7IoDMF96meL-muoQ1bBUTogM2e6Owu,PL9D80C36288186633,PL1F8DE5C3B4588783,PLzTiNL8wJzyD_82WrvvtcPc5xgcRwIhmc,PL0__z7c1lf8vLX7A7ourdKLpBmJXqqqvu,PLoBDkJXxnuE4MYAMNKjryc5yvD8L2zXyR," +
                                        "PL0ZpYcTg19EF1niHfDu6oyPj40BHPr5gh,PL0ZpYcTg19EFYP_oDBwRsUGPYJoipkJpA,PLZ6CIf9zpADTic6OZ1zTaw5Sg3ySgeRHS,PLE1HL7ydDnKP4MF3ejvOcT5sCtm2tWkIi";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Telugu Movies")) {
                                formatedVidsList = "PLoBDkJXxnuE66Hi8GBRCF9KNBWNo_TlDW,PLXBHKlMRm_yw3uUcxtmFuYPWOORr37oxK,PLMHbK5ZCEE8UI5TTGV46h2KjN7Y71xLmG,PLzTiNL8wJzyBdNAE4F8nE7Mf3kK7TwAh-,PLXBHKlMRm_yzK8djuuIJB-lAAA2WmXImb";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Telugu Hot Item Songs")) {
                                formatedVidsList = "PLRSX77yV8Wsbr5u8Cld7tyi4dw2lCA3oc,PLj2XdQjbrSEGel_04JVuxjLrElvMo5jnn";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Telugu Dance Special Songs")) {
                                formatedVidsList = "PLoP3X4JX-s1S0f81XfnsBrlnv4ru9rH1d,PLHwuF0yy3RxWdDERguZ91QgwCeqLAOA4a,PLf5Bhjwoj6B-bpkD-xK1EyVewMs8ifr46";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Romantic")) {
                                formatedVidsList = "PLURHF8ZY2Y56kupEqz3TVLD__Zj-tlPSd,PLAE95D5B634195317,PLURHF8ZY2Y54V-lQ4zE5ToS4gyALjjTJx," +
                                        "PLeo6eDZGP-ml04Y1IH-vrOq7KkB8yIBxn,PLFFyMei_d85UcDVoD2H98jCc9Mbem7hGZ";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } if (selectedSubCategory.equalsIgnoreCase("Action")) {
                                formatedVidsList = "PLURHF8ZY2Y556Mnr_-LqpySgTVmVBvjaQ,PLd-JGjCltpgBhO1b3il2bmQeeaz-H8Ed5," +
                                        "PL661Qfv7ujynpiEq6dWx50K6Cf9qr_vnp,PLntSKD8vRcxarqz_ouzsPIcLvOmAvVSEE,PL2D9E09B8E804DC1F";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } if (selectedSubCategory.equalsIgnoreCase("Thriller / suspense")) {
                                formatedVidsList = "PLURHF8ZY2Y54gw4qX3uleIgUCUmjlG08g,PLG5YDXfHgllYslpZCJAyllnz4PwmdKxlv," +
                                        "PLIbm8PMCgsLRgZlAWMd_AIxz4k5FP2fuZ,PLjXbLTS058MrrUNfJieKoyxCAUxNWjDgV," +
                                        "PLF2sIvUHDxJ5f8iUkRz46pdZ_5OpFrpFx";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } if (selectedSubCategory.equalsIgnoreCase("Horror")) {
                                formatedVidsList = "PLOFbEV8DRegRvGpbfRsnZJYPKLGNa-Q5S,PL4_uBMZhIS8I3OSl8mHQ7WMWOrutfIQCa," +
                                        "PLv2b4SmNyK3snoB1oIvvmQYogs458Pkaq,PLnOjHXskqOEaz-Vr3di8Kk31O1oYEw75p";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } if (selectedSubCategory.equalsIgnoreCase("Comedy")) {
                                formatedVidsList = "PL0iajqfMHtYmTO2VunNO1fy5ErPtzkhii,PLURHF8ZY2Y55xZTpilq5fPTMlWMorkGBP," +
                                        "PL0FE8gBGxbQmTmgYcACJDNF_7mq4as9vZ,PL24DPWlf6HgmXN0I6eKSEXhiZu5pMLKTC," +
                                        "PL0CaUqi81mPmybMiWGxqh8mkfMbgpPaOc,PL4ABBC887A886A9AC,PLvdrEOxqEIXx3zjbNLdPh5L_trN53PelA," +
                                        "PL0yy74BDBbR2JXE1lvx0ESxl2RwnBYxmE,PLX5lVjC5eKN0-X16IqsjoJAv6sJ_Z_3CF," +
                                        "PL0CaUqi81mPmJx-X-2p17hf-7eaG-Cd-H,PLv1E6xGMdApdcVRdsrjQqs6nkGUiHpOqL," +
                                        "PLzRTv50jotmkaWb5agvR2y6RF8jnHDvsg,PLQXb3Gb48Rmh2VC1wQqNgQVTyrZcOtxDh";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Ghazals")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.ghazals_vids));
                                videoType = VidsApplUtil.TYPE_VIDEO;
                            } else if (selectedSubCategory.equalsIgnoreCase("Sharaabi songs")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.sharaabi_vids));
                                videoType = VidsApplUtil.TYPE_VIDEO;
                            } else if (selectedSubCategory.equalsIgnoreCase("Live News")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.news_vids));
                                videoType = VidsApplUtil.TYPE_VIDEO;
                            }

                            else if (selectedSubCategory.equalsIgnoreCase("FoodFood")) {
                                formatedVidsList = "PLI-l1OOUnVtOcrO0MP0Qn67bvaGYS5-QS,PLI-l1OOUnVtMhpmj1HzNXg1qd0ByTrihB," +
                                        "PLI-l1OOUnVtM5p90oI4Rw7D09znrQRoZN," + "PLI-l1OOUnVtNOY4uWnfa2nHIAbC8elqId,PLI-l1OOUnVtP0Gf2y4Ugc-EycGxO0rOZx," +
                                        "PLI-l1OOUnVtNhMBIQlX839aTOMH4mV6Gs," +
                                        "PLI-l1OOUnVtMTN8AA9RIJYxXuh1WFfwtu,PLI-l1OOUnVtPQstzp1gNQYEjZzZxliYW6," +
                                        "PLI-l1OOUnVtNDOcpleIxf9tzvoA5kMhny,PLI-l1OOUnVtP-0jnNXlwnVAPNC9ozhBzE";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }

                            else if (selectedSubCategory.equalsIgnoreCase("Sanjeev Kapoor Khazana")) {
                                formatedVidsList = "PLQlI5m713h7v42G_1FIWN5rQGatYA-O4G,PLQlI5m713h7uihKlOYmowOUZ2hlg4c7gw," +
                                        "PLQlI5m713h7t_2jt--yqIA5wFav6VX0dY,PLQlI5m713h7unbL24Qq4KZrV1E3E9WLzb,PLB4C07FAAE6FA5428," +
                                        "PL4267B4165D10C828,PLQlI5m713h7tkaNw-3zJIz-jo1Vfs9bFZ,PLQlI5m713h7spQLH0gfDxKJPHeoSNZTgv," +
                                        "PLQlI5m713h7vuTgnYz7AzZsv6taL_Fby5,PLQlI5m713h7vxfMnikMOzCA8RG5gcXdl2," +
                                        "PLQlI5m713h7tSb1Gxh7eRDDGLasGKzIO2,PLAFFE3D587F96E152,PLQlI5m713h7tZ0vjlSHkXwVKlEoxVI_Rh," +
                                        "PLF100B1D028BC0CC6,PLjxv5PjVc17Amno81j0DrntRvN-36TCfU,PLQlI5m713h7u1lTAMT07P38zO1ObJj6Jn," +
                                        "PLRcarg7b0_VkLGC9R0QucmtozBY8Z_09t,PLKXMBEaAiocUjidbYMGqAH0xule2h__Vu,PL0E62D9550D7860A2," +
                                        "PL2A2B90A383DD1BC3,PLyb4WJKDdS7geTyUOCTCKd0hcIymsPSw-,PLXtOj-oKyAlaOTnW374KxO1RHBW94OQ3S," +
                                        "PLfzzrAmALcU561WCzXePXixVP2KZybN2N,PL8DCCA92008AD0BBC,PLVDBP1b0X_lflsRdWyqvvcrX3fswHs_Ll," +
                                        "PLQlI5m713h7sT0VkGEH9Kjc6PnQxnzNKl,PLWmhiFviaXYi4LsS6NKpetIbFdlvZggNR," +
                                        "PLVK7OUj1rCtOTkC_qxC8gWmEmCJU-pnyJ";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }

                            else if (selectedSubCategory.equalsIgnoreCase("Nisha Madhulika")) {
                                formatedVidsList = "PLodB7SudGtZrkkBnSEYO_F-r51aIdb88E," +
                                        "PLodB7SudGtZrFhdcEfZf5cpBZ5CFzjlzn,PLHKsrffpkToERfAjXU9uqLk3zvRQSp_wh," +
                                        "PLHKsrffpkToE2qwQp8iaZfynoZ5PFSk5L,PLodB7SudGtZoRtq25D2FciPeQqY3vI1Xe," +
                                        "PLHKsrffpkToFG9DD0XY1n-wrAUrKaOYOr,PLFP3fWTk1lmoFLCjFLha2oT8oBR2Q5pwm," +
                                        "PLHKsrffpkToFTpjtfqvkoU_kwbJdBoNsR,PLiX-65dsgFOR7SzTlIHs2reHyNfPjG_-9," +
                                        "PLHKsrffpkToEHVKyeBZnibdKBft0CI6O7,PLFgI0Ia8_4kmUQ3CDg0eD9ajVF-Jlwcdi," +
                                        "PLHKsrffpkToGzSbxv97ZcOPUg96g1hRRJ,PLAD87A1390DC6EF7E,PL63F962E25F65088C," +
                                        "PLXtOj-oKyAlbUuCiLbQ2cW0gQUCp7kKCH,PLa5XztyR6BvhakojGU3bsoNmrsCyi58S4," +
                                        "PLHKsrffpkToFhiCl340gbXL1ZL0LWVdxO,PLCVz3z4ki8dwnUcq-RIK1Dv0fBk5tPxlj," +
                                        "PLHKsrffpkToHF7RXvBOhOmx5X2Xq3NMrO,PLHKsrffpkToF4cmMZESewIYXWHhq9KCLF," +
                                        "PLHKsrffpkToHwEaxZ9Uuj180E7U844IPv,PLHKsrffpkToGvaFgcTPhFem9oHL4iYsoO,PL93E44A69F0425AD5," +
                                        "PLHKsrffpkToGPsJB5XBttx-rUXuSEDgfM,PL2E13244613DE182C,PLHKsrffpkToF5OTVNvbq6bqVUf3Limj9w," +
                                        "PLHKsrffpkToFSdirHAn_N9Lsc1w_FASIe,PLHKsrffpkToGDOTNdcQ4gxy7kkOB7Xn2y," +
                                        "PLHKsrffpkToFGVWEqfgMSBG2EioEJ-5jY,PLHKsrffpkToHu4iPt8Q-0BTT8At7oy0Vu," +
                                        "PLHKsrffpkToEEce3BAjl_1CJrZuO3zoi0,PLHKsrffpkToFzgv73hoJX4_zfthilPp8o," +
                                        "PLHKsrffpkToGa5nwIo9_PkmpuroLRIrWD,PLHKsrffpkToFNULXimjYMblb3ftY8Qsr2," +
                                        "PLrFg_WvCkeGVwPEkarczjg1dXSXrO4zJg,PLHKsrffpkToG75nrwM84uXsQ1ZKgM_oaC," +
                                        "PLHKsrffpkToGVHHH-qrQSAPeyfCyNu2jJ,PLHKsrffpkToH4S-kaHGh9ZXxMPPuL0H90," +
                                        "PLHKsrffpkToG4Nww72W9LgEZFWUmU1z8X,PLHKsrffpkToH0vib0gnxEQBI9dMdiVrO2," +
                                        "PLHKsrffpkToFiRphzIz8jFyMuXBeFJKH6,PLHKsrffpkToHzR0f6j8k5fEB8QYcFyKfA," +
                                        "PLHKsrffpkToErB14NWx9ZQvLvWV4somEU,PLHKsrffpkToHlLNSHsmV6H5TtvvK5xFNA," +
                                        "PLHKsrffpkToHgnCaV0VTK3W6Or453kuFH,PLHKsrffpkToFGVWEqfgMSBG2EioEJ-5jY," +
                                        "PLHKsrffpkToFp3fDZfS8cNmBcql_J-ner,PLHKsrffpkToEJXUX7X042GxZi6a0fBT9w," +
                                        "PLHKsrffpkToFORIQ1heaTFYMh7uWG9OmM,PLHKsrffpkToHLR2QIzyQTq9jo19h4mqNr";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Kabita Kitchen")) {
                                formatedVidsList = "\n" +
                                        "PLKorCs_pzToiYxdYOq0A59K-NwqRwfAWj,PLKorCs_pzTogEIKigT7hsMYU6FdgCjplb,PLKorCs_pzToiNFfviaPDQ5ZQG_SZdQ55H," +
                                        "PLKorCs_pzTog0F8XzwmX4nOPvPsD3dr_y," +
                                        "PLKorCs_pzTogNw_TRpiexO6XLgNsNUKgs,PLKorCs_pzToggrCCIyJWQCG9m9P4B0WMC,PLKorCs_pzTohjMFu99KKC1ROUmnc_QF12," +
                                        "PLKorCs_pzTogUibudBDhVEMQumB-RVwE_,PLKorCs_pzTojzS9CP7AoKOGPoupqB_Q73,PLKorCs_pzTojhoh4x8-lBH9o3Y3juPdkQ," +
                                        "PLKorCs_pzToj74rfZRcDwgQXWnBAubrkE,PLKorCs_pzTojhGmrvou9_ybmUMwyhwNz4,PLKorCs_pzTojLCYtE2fOfHDE1saETKqgN," +
                                        "PLKorCs_pzTohvySkcASDa_1CNhU2o3aSE,PLKorCs_pzToivlM3S7tcYvMDrL7c90n-t,PLKorCs_pzTogyTFVntOfupXtQJdR5aRiE," +
                                        "PLKorCs_pzTojpmlLqrby4javSg8xCTjuY,PLKorCs_pzTojXvd_0LhGApJMSUJwRjKK2,PLKorCs_pzTohIH3eV70dV1g21T3TWWEgQ," +
                                        "PLKorCs_pzTogbQfMGRDNr_Km7I2H6tdqy,PLKorCs_pzTojMRS37n5oh9dlv6Obb5eBb";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Tarla Dalal")) {
                                formatedVidsList = "PLjMg8wIdiWavDO2JCk7iSEdxkwRJcUiPF,PLE63A4B659365002B," +
                                        "PLjMg8wIdiWatUUedWR0j6xkE2qW0Adgsg,PLEAF0B48D2FF9CE50,PLjMg8wIdiWasxToaXOIlQyjzUfTsgULl1," +
                                        "PLjMg8wIdiWavnRT_WWOFkD43-qgXAjo1T,PLjMg8wIdiWauSGMdaok8FRUkRBIpMH4Jv";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }  else if (selectedSubCategory.equalsIgnoreCase("Rajshri Food")) {
                                formatedVidsList = "\n" +
                                        "PLhQ0_mMdHj7_g7pIB1J6SOfcNzY0wQ8JT,PLhQ0_mMdHj7_sjHI0cuuF-vJdaXxaGSwf,PLhQ0_mMdHj7_nqT6m-Yk-LvfgzHjvbW8i," +
                                        "PLhQ0_mMdHj7-R9xN9q4BVo7nbr6o-tFPj,PLhQ0_mMdHj793PXy_9kmuwi4oJU7-YeIF," +
                                        "PLhQ0_mMdHj7--9HXaJRuinVxNbP4M-lvv,PLhQ0_mMdHj7_uXuLb0rZTm8jyldYgA8Hz," +
                                        "LLdegm7Y2AePJhkkmWCyYEwg,PLhQ0_mMdHj7-qjKAGjGSq9c6dsebwGrd4,PLhQ0_mMdHj78vgaQqjqQm56dw5-z8_5Ld," +
                                        "PLhQ0_mMdHj7_wYuccgJfvh-YVtuk-HY00,PLhQ0_mMdHj7-ue-sPbDi9wIN_-EmowQfE";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Vikas Khanna")) {
                                formatedVidsList = "\n" +
                                        "PLqHo0TDtnNkQMJ2Iq6rvLz33MMbmQTxW8,PLqHo0TDtnNkSJxwTRqOKtEobR4ywa5MP5,PLqHo0TDtnNkT93memHq5a66xtfP35HXbr," +
                                        "PLqHo0TDtnNkQsAJf6eF3nM4YAmXNA8Hth,PLqHo0TDtnNkSLN1j8mOy2nLV3-99z8M0r,PLqHo0TDtnNkTV13MjsZPK8JlMjgd8_Fj3";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Pankaj Bhadouria")) {
                                formatedVidsList = "\n" +
                                        "LLA34Z3lq8FozSQzDHsSLcmQ,PLbF9OWJ9PNmbx3Mzqd7re6hdaEWonx3wI";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Vahchef")) {
                                formatedVidsList = "\n" +
                                        "PLecDmWZ6vbWS0bMyK6qzNfhki3cnfp2Wk,PLecDmWZ6vbWSQpU2uwB-nmJ5EHVfusJ5s," +
                                        "PLecDmWZ6vbWTjFPqyBd8w1APtlqLNofXh,PLecDmWZ6vbWQALPIfG-Ek9_J68OJY6KyD,PLecDmWZ6vbWSjlSWjt-QeT8e_o_vSTXxx," +
                                        "PLecDmWZ6vbWQhWwMu1sdFqptVama7sO5R,PLecDmWZ6vbWQghczJIOhwG27vC6TZKN8A," +
                                        "PLecDmWZ6vbWRyKbgCX3KybX1-cs-LxdvY,PLecDmWZ6vbWRGbF6e6giqFPC1teswBZbT,PLecDmWZ6vbWRfP52lP4B1QjHad9xbdBJ7";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("ChuChu TV")) {
                                formatedVidsList = "PLV-cxl3VSwWHa83pPHIealm1vg0Cdp3aZ,PLV-cxl3VSwWEZPb6yEvQaf68_yY8chu8U,PLV-cxl3VSwWFB3u4D6Vq5LsmiP3H-wGOS," +
                                        "PLV-cxl3VSwWHZz0NarV7B7fhYUkT0rAGX,PLV-cxl3VSwWFcpAjZsnDFgezlLOIzSCYo,PLV-cxl3VSwWHBvsggTtJOvg81wKFICLX_," +
                                        "PLV-cxl3VSwWHy3JvK-E9e944E_ci8j_g4,PLV-cxl3VSwWEiktlr-EJ9PhyvEkNL1gPD,PLV-cxl3VSwWHjyMYLoxmHWkzFenDzxZsV," +
                                        "PLV-cxl3VSwWFsBZwigZ71s0gxInr5Z1jT,PLV-cxl3VSwWG_opeAaq442N-6HrMyRyUC";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Animation Movies")) {
                                formatedVidsList = "PLVeGq8yvmEoZMwMu0ySPU5Lt3ltRRrNYd,PLkLWhpNfRRnFfUMTMxedLG-jCtbGQoGRE," +
                                        "PLra5p7VeoCdVIJHlaYTEs7h0gmvD-bifC";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Tom and Jerry")) {
                                formatedVidsList = "\n" +
                                        "PLOLEQVkmI9eugSKUsrQBH0rxMN-qBLUyV,PLwp8m9anCLK3jmItMyLTgfNpM-526z8Cl," +
                                        "PLTpHF7zUZcc0h3BLxEJuSEcG-GTsrtA85";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Doraemon")) {
                                formatedVidsList = "\n" +
                                        "PL7AowHd5QvCq2dOzdHchSO7d4dR3oiOJP,PL7AowHd5QvCo2uz9xyU2Lof3_uz-LPGMG," +
                                        "PL7AowHd5QvCqYOdUZ2dPEfSeKxOdNwMvG,PLUJRY7F6BIGrUgrYuv_e0895iW97jnC-F";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Disney Junior")) {
                                formatedVidsList = "\n" +
                                        "PLI-nQtOHVKRu0erW8Khquk1pRvEHDpR7i,PLrLu47k41a8QTEAgmldgX08HTiloFx8wC";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Chhota Bheem")) {
                                formatedVidsList = "\n" +
                                        "PL3A3Us6jIaWQkU6NAnAuGPyXJFZkIoqRs,PLFf9vSAD6FtpIXLY83wIS4CpRWxlAJZMO," +
                                        "PL0rMr_qVm_FLR09J5s3pqLFJf87bhRUKq,PLhPu2jwebYCmtyoS1KTQDANKcvPjqYRtg," +
                                        "PLmcXlFHrXrBCYHTXyeK4U9r96VuiAk_8d";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Very Funny Cartoons")) {
                                formatedVidsList = "PL1TtObLAkXskULDTZPY3C6E4RTAGs_AQv,PLC8DECF60FEEC2780";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Motu Patlu")) {
                                formatedVidsList = "PLkffMCxtJZZRkHFE_Wp4GQiIPqgud3EZu,PL0Jedq5WL3qG_nPthRIzkBZSVLq0xAN72," +
                                        "PLFf9vSAD6FtrtZvesJqGQmHIxiw169Efp";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Oggy And The Cockroaches")) {
                                formatedVidsList = "PLkffMCxtJZZRKcKvMMhGZWp4ZwntQKSPt";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }

                            else if (selectedSubCategory.equalsIgnoreCase("Baby TV")) {
                                formatedVidsList = "PLakLrQJOovvm3w7JXI2hImYvfJKjGNiOa,PLakLrQJOovvm1p46BcwlpE23V5uym9fWr," +
                                        "PLakLrQJOovvmeMTnQsiugVGY4LI4VUoEB,PLakLrQJOovvlSC-gfT2UvCFU9qxLOXiN-," +
                                        "PLakLrQJOovvlpJzAuQuWN8yOszoycRTzz,PLakLrQJOovvl5VLQvckznxKOIWr5bJ-m5," +
                                        "PLakLrQJOovvmJocamFAquztsY3JLZbWWI,PLakLrQJOovvneB91lg4XW4L09T4fVTpVV";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Bob The Builder English")) {
                                formatedVidsList = "PLiluoe0QNL4EXc5qYPJWoqZ2iyBPk3ybk";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("Bob The Builder Hindi")) {
                                formatedVidsList = "PLliauKz65-NiPx0PlS4PfdTVZM_fHQelx";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("NODDY in Hindi")) {
                                formatedVidsList = "PL4per263uEmD7aXLvUP8YpApycdAmVvP9";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            else if (selectedSubCategory.equalsIgnoreCase("NODDY in English")) {
                                formatedVidsList = "PLiluoe0QNL4EXc5qYPJWoqZ2iyBPk3ybk";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Parenting tips/guidelines")) {
                                formatedVidsList = "PL9sA8HKjDkC_TWnYbLPBo79XPtkcTZO8V," +
                                        "PLa0TAGDLF77orWYXhoEn2E3IeLQLQz8R5," +
                                        "PLqbvA-TUu6H6g-G5f71-1u2dOjK34bFtd," +
                                        "PLrRNujJ2zctynmFGHrIQ0IAks1-6G-0q_," +
                                        "PLWv6Z9ksk7OMsFqb5MRJv-gbPkqGE_VKJ";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Bhajans")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.bhajans_playlist));
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("TV serials")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.serials_playlist));
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Kavi sammelan")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.kavi_sammelan_vids));
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Hindi-urdu shayari")) {
                                formatedVidsList = VidsApplUtil.formatVidsList(
                                        getResources().getStringArray(R.array.shayari_vids));
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            }
                            if(VidsApplUtil.TYPE_VIDEO.equals(videoType)){
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_frame, mVideoFragment, mVideoFragment.getClass().getSimpleName()).commit();
                                if (listener != null) {
                                    listener.onFetchVideo(videoType, formatedVidsList);
                                }
                            }
                            else if(VidsApplUtil.TYPE_CHANNEL.equals(videoType) || VidsApplUtil.TYPE_PLAYLIST.equals(videoType)){
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_frame, mPlayListFragment, mPlayListFragment.getClass().getSimpleName()).commit();
                                if (listenerPl != null) {
                                    listenerPl.onFetchPl(videoType, formatedVidsList);
                                }
                            }

                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!NetworkUtil.isConnected(this)) {
            Snackbar.make(mMainCoordinatorLayout, "Uh oh! No internet connection. Try again later!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        YoutubeNewTOldVideosListAdapter adapter = mVideoFragment.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vids, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            Intent i = new Intent(VidsActivity.this, VidsFavoriteActivity.class);
            String favVidsIds = VidsApplUtil.readDataFromFile(this, VidsApplUtil.FAV_FILE_NAME);
            String formatedIds = "";
            if (favVidsIds != null && favVidsIds.startsWith(",")) {
                formatedIds = favVidsIds.substring(1, favVidsIds.length());
            }
            i.putExtra(VidsApplUtil.TYPE_VIDEO, formatedIds);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private FetchVidsListener listener;
    private FetchVidsListener listenerPl;

    public void setFetchVideoListener(FetchVidsListener fvListener) {
        this.listener = fvListener;
    }
    public void setFetchPlListener(FetchVidsListener fvListener) {
        this.listenerPl = fvListener;
    }

    public interface FetchVidsListener {
        public void onFetchVideo(String vidsType, String vidsIds);
        public void onFetchPl(String vidsType, String plIds);

    }

}
