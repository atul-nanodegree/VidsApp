package com.vidsapp;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.vidsapp.util.VidsApplUtil;

public class VidsActivity extends AppCompatActivity {

    private Spinner categorySpinner, subCategorySpinner;
    private  CoordinatorLayout mMainCoordinatorLayout;
    private Fragment mVideoFragment;
    private Fragment mPlayListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vids);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setLogo(R.drawable.icon);
        mMainCoordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatelayout);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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
                            } else if (selectedCategory.equalsIgnoreCase("Bollywood movies")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.movies_list));
                            } else if (selectedCategory.equalsIgnoreCase("News channels")) {
                                initializeVidsSubCategory(getResources().getStringArray(R.array.live_news_channels));
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
                                        "PLEpfh9jiEpYSYIUMQjeCO1lUmpYZv3Qtc";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Sad melodies")) {
                                formatedVidsList = "PLEEDF17AE177F527B,PL509690A35754E24F,PL448273752D6A392B,PL4BC9A2F7F2B75D83," +
                                        "PL63EEC96AFCC324A4&index=4,PL901339D6BC9E5D73,PLACAFFF15158CE67E" +
                                        "PL814FA5D5BEC48E7F,PL8E2CBE8655E5154F";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
                            } else if (selectedSubCategory.equalsIgnoreCase("Soft melodies")) {
                                formatedVidsList = "PLYkopulgW-Z2cFmcf4d15Tao6Rak8WZYb,PL-tiXFkBA6IyMEVAno7PxyQ3s2yeMykjo," +
                                        "PLkClf2ueYJXhyyRSOAA0A3qjTMdcbgTNe,PL2540C704C182FA98,PL05CAD8640438D536," +
                                        "PLiAttA3ZvGfk1vuF8Xq7j24sBsg4QuH17,PLeoPwI_un2DHGq6EZ2u6tWagq4FmXLpw9,PLpPW6R_JX0Uv34fU4ifzQjbC1TFSCZ3od";
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
                                        "PLwOScGCjb93DDU64Ai0E-GbnbH3aOh3R2," +
                                        "PL9850DBE604B4D416,PLIoF1EmGOU3D8wjbiuTPSIPddPev1cj5h,PLRmdV1u90qKkT7l10w6c4OOxbbvNZxWdE," +
                                        "PLP15S8bV9o5bPHmsXzVvcG-WMa_7HDkyH,PLHdrJ3cHREuY_IpdqzaGMrZ0er03dE8SU," +
                                        "PL3492286914EC878C,PLhDMUiHBg1erHxiWeP8OkSeZ0i0CDxshQ,PL8E2DE298DB5DCCA8," +
                                        "PL9AFFF4ABB022B7AE,PL98E962440015C6A1,PL0355E07646C0C291,PLcW36x0vC9XK5kIxUGCgcCRTB8kb3ZDZn," +
                                        "PLA826A6E6DED51189,PLCF6C3CDD70DCB26A,PLCwTa6m59uGXwRF_6-3Udi5L2W-9DAm6I," +
                                        "PLgh_EjxPZ7fqwFgMvixpGOq1ndnz3tgJs,PLARm22A77n4N9sMd_AeI9AOCOY3rCkWKP," +
                                        "PLD6DB477019E5F012,PLShHEGqasFSzx81JoKnDUtfFUokISYA3F";
                                videoType = VidsApplUtil.TYPE_PLAYLIST;
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
            Snackbar.make(mMainCoordinatorLayout, "No internet connection.Check your connection and try again", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
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
        if (id == R.id.action_settings) {
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
