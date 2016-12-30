package com.mooveit.android.androidtemplateproject.common.model.repository;

import com.mooveit.android.androidtemplateproject.common.model.datasource.PetsDataSource;

public interface PetsRepository extends PetsDataSource {

    void invalidateCache();

}
