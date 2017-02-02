package com.mooveit.android.androidtemplateproject.common;

import com.mooveit.android.androidtemplateproject.common.di.components.ApplicationComponent;
import com.mooveit.android.androidtemplateproject.common.di.modules.ApplicationModule;
import com.mooveit.android.androidtemplateproject.common.mock.di.components.DaggerMockApplicationComponent;
import com.mooveit.android.androidtemplateproject.common.mock.di.components.MockApplicationComponent;

public class TestApplication extends AndroidTemplateApplication {

    private MockApplicationComponent mMockApplicationComponent;

    @Override
    public ApplicationComponent getApplicationComponent() {

        if (mMockApplicationComponent == null) {
            mMockApplicationComponent = DaggerMockApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }

        return mMockApplicationComponent;
    }
}
