package com.mooveit.android.androidtemplateproject.common;

import com.mooveit.android.androidtemplateproject.common.rx.SchedulerProvider;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Scheduler;
import rx.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseTest {

    protected SchedulerProvider mSchedulerProvider = new SchedulerProvider() {
        @Override
        public Scheduler io() {
            return Schedulers.immediate();
        }

        @Override
        public Scheduler computation() {
            return Schedulers.immediate();
        }

        @Override
        public Scheduler main() {
            return Schedulers.immediate();
        }
    };
}
