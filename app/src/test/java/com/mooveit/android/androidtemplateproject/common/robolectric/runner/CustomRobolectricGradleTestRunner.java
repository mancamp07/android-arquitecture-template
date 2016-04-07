package com.mooveit.android.androidtemplateproject.common.robolectric.runner;

import android.support.design.widget.Snackbar;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.internal.bytecode.InstrumentationConfiguration;

public class CustomRobolectricGradleTestRunner extends RobolectricGradleTestRunner {

    public CustomRobolectricGradleTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public InstrumentationConfiguration createClassLoaderConfig() {
        InstrumentationConfiguration.Builder builder = InstrumentationConfiguration.newBuilder();
        builder.addInstrumentedClass(Snackbar.class.getName());

        return builder.build();
    }
}
