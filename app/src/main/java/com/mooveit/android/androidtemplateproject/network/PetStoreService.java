package com.mooveit.android.androidtemplateproject.network;

import com.mooveit.android.androidtemplateproject.model.Pet;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface PetStoreService {

    @GET("pet/findByTags?tags=mooveit")
    Observable<List<Pet>> getPets();
}
