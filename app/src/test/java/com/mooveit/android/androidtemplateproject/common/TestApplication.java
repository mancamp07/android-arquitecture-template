package com.mooveit.android.androidtemplateproject.common;

import com.mooveit.android.androidtemplateproject.common.di.components.ApplicationComponent;
import com.mooveit.android.androidtemplateproject.common.di.modules.ApplicationModule;
import com.mooveit.android.androidtemplateproject.common.mock.di.components.DaggerMockApplicationComponent;
import com.mooveit.android.androidtemplateproject.common.mock.di.components.MockApplicationComponent;
import com.mooveit.android.androidtemplateproject.common.mock.di.modules.MockNetworkModule;
import com.mooveit.android.androidtemplateproject.common.mock.di.modules.MockRepositoriesModule;

public class TestApplication extends AndroidTemplateApplication {

    private MockApplicationComponent mMockApplicationComponent;

    private MockRepositoriesModule mMockRepositoriesModule;

    @Override
    public ApplicationComponent getApplicationComponent() {

        if (mMockApplicationComponent == null) {
            mMockApplicationComponent = DaggerMockApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .mockNetworkModule(new MockNetworkModule())
                    .mockRepositoriesModule(getRepositoriesModule())
                    .build();
        }

        return mMockApplicationComponent;
    }


    @Override
    public MockRepositoriesModule getRepositoriesModule() {
        if (mMockRepositoriesModule == null) {
            mMockRepositoriesModule = new MockRepositoriesModule();
        }
        return mMockRepositoriesModule;
    }
}
