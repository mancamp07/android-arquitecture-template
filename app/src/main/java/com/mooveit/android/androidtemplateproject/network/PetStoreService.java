package com.mooveit.android.androidtemplateproject.network;

import com.mooveit.android.androidtemplateproject.model.entities.Pet;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Single;

public interface PetStoreService {

    @GET("pet/findByTags?tags=mooveit")
    Single<List<Pet>> getPets();

    @POST("pet")
    Single<Pet> createPet(@Body Pet pet);
}
