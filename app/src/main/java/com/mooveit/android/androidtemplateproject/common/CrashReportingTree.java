package com.mooveit.android.androidtemplateproject.common;

import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public final class CrashReportingTree extends Timber.Tree {

    public CrashReportingTree(Context context) {
        Fabric.with(context, new Crashlytics());
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }

        Crashlytics.log(priority, tag, message);

        if (t != null && priority == Log.ERROR) {
            Crashlytics.logException(t);
        }
    }
}
