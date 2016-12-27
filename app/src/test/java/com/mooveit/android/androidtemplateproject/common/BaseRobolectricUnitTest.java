package com.mooveit.android.androidtemplateproject.common;

import android.os.Build;

import com.mooveit.android.androidtemplateproject.BuildConfig;
import com.mooveit.android.androidtemplateproject.common.robolectric.runner.CustomRobolectricGradleTestRunner;

import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(CustomRobolectricGradleTestRunner.class)
@Config(
        application = TestApplication.class,
        constants = BuildConfig.class,
        sdk = Build.VERSION_CODES.M)
public abstract class BaseRobolectricUnitTest {

    protected TestApplication getApplication() {
        return (TestApplication) RuntimeEnvironment.application;
    }


    protected String getNextStartedActivityClassName() {
        ShadowApplication application = shadowOf(RuntimeEnvironment.application);
        return application.getNextStartedActivity().getComponent().getClassName();
    }
}
