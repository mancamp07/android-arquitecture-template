package com.mooveit.android.androidtemplateproject.common;

import com.mooveit.android.androidtemplateproject.common.rx.ImmediateSchedulerProvider;
import com.mooveit.android.androidtemplateproject.common.rx.SchedulerProvider;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseTest {

    protected SchedulerProvider mSchedulerProvider = new ImmediateSchedulerProvider();
}
