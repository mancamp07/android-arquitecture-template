package com.mooveit.android.androidtemplateproject.network;

import com.mooveit.android.androidtemplateproject.model.entities.Pet;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Single;

public interface PetStoreService {

    @GET("pet/findByTags?tags=mooveit")
    Single<List<Pet>> getPets();

    @POST("pet")
    Single<Pet> createPet(@Body Pet pet);

    @PUT("pet")
    Single<Pet> updatePet(@Body Pet pet);

    @DELETE("pet/{petId}")
    Single<Void> deletePet(@Path("petId") long petId);
}
