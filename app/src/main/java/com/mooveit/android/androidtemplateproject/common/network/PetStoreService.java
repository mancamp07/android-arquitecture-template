package com.mooveit.android.androidtemplateproject.common.network;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface PetStoreService {

    @GET("pet/findByStatus?status=available")
    Observable<List<Pet>> getPets();

    @POST("pet")
    Observable<Pet> createPet(@Body Pet pet);

    @PUT("pet")
    Observable<Pet> updatePet(@Body Pet pet);

    @DELETE("pet/{petId}")
    Observable<Void> deletePet(@Path("petId") long petId);
}
