package com.mooveit.android.androidtemplateproject.common;

import android.support.annotation.CallSuper;
import android.support.test.InstrumentationRegistry;

import com.mooveit.android.androidtemplateproject.common.util.SystemAnimations;

import org.junit.After;
import org.junit.Before;

public abstract class BaseAndroidTest {

    private SystemAnimations systemAnimations;


    @CallSuper
    @Before
    public void setup() {
        systemAnimations = new SystemAnimations(InstrumentationRegistry.getContext());
        systemAnimations.disableAll();
    }

    @CallSuper
    @After
    public void tearDown() {
        systemAnimations.enableAll();
    }
}
