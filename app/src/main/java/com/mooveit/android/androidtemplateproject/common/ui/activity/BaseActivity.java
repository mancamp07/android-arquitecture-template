package com.mooveit.android.androidtemplateproject.common.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

public class BaseActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
