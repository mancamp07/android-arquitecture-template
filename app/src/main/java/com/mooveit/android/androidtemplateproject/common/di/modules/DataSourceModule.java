package com.mooveit.android.androidtemplateproject.common.di.modules;

import android.content.Context;

import com.mooveit.android.androidtemplateproject.common.model.datasource.PetsDataSource;
import com.mooveit.android.androidtemplateproject.common.model.datasource.local.LocalPetsDataSource;
import com.mooveit.android.androidtemplateproject.common.model.datasource.local.PetsDatabaseHelper;
import com.mooveit.android.androidtemplateproject.common.model.datasource.remote.RemotePetsDataSource;
import com.mooveit.android.androidtemplateproject.common.network.PetStoreService;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

@Module(
        includes = NetworkModule.class
)
public class DataSourceModule {

    @Provides
    @Singleton
    SqlBrite provideSqlBrite() {
        return new SqlBrite.Builder()
                .build();
    }

    @Provides
    @Singleton
    PetsDatabaseHelper providePetsDatabaseHelper(Context context) {
        return new PetsDatabaseHelper(context);
    }

    @Provides
    @Singleton
    BriteDatabase provideDatabase(SqlBrite sqlBrite, PetsDatabaseHelper helper) {
        return sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
    }


    @Provides
    @Singleton
    @Named("local")
    PetsDataSource provideLocalPetsDataSource(BriteDatabase database) {
        return new LocalPetsDataSource(database);
    }

    @Provides
    @Singleton
    @Named("remote")
    PetsDataSource provideRemotePetsDataSource(PetStoreService petStoreService) {
        return new RemotePetsDataSource(petStoreService);
    }

}
