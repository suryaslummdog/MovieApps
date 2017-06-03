package com.ikadeksurya.movieapps.fragment;

import com.ikadeksurya.movieapps.R;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by IKadekSurya on 28/05/2017.
 */

public class SettingFragment extends PreferenceFragmentCompat{

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_movie);
    }
}
