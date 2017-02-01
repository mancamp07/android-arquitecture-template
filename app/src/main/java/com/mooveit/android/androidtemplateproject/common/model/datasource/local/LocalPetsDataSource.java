package com.mooveit.android.androidtemplateproject.common.model.datasource.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mooveit.android.androidtemplateproject.common.model.datasource.PetsDataSource;
import com.mooveit.android.androidtemplateproject.common.model.datasource.local.PetsDatabaseContract.PetEntry;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.Calendar;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class LocalPetsDataSource implements PetsDataSource {

    private static final String TAG = LocalPetsDataSource.class.getSimpleName();

    private final BriteDatabase mDatabase;

    private Func1<Cursor, Pet> mMapper = cursor -> {
        long petId = cursor.getLong(cursor.getColumnIndexOrThrow(PetEntry._ID));
        String petName = cursor.getString(cursor.getColumnIndexOrThrow(PetEntry.COLUMN_NAME_NAME));
        String petStatus =
                cursor.getString(cursor.getColumnIndexOrThrow(PetEntry.COLUMN_NAME_STATUS));
        long externalId = cursor.getLong(cursor.getColumnIndexOrThrow(PetEntry.COLUMN_NAME_EXTERNAL_ID));
        long updatedAt = cursor.getLong(cursor.getColumnIndexOrThrow(PetEntry.COLUMN_NAME_UPDATED_AT));
        return new Pet(petId, petName, petStatus, externalId, updatedAt);
    };

    public LocalPetsDataSource(BriteDatabase database) {
        this.mDatabase = database;
    }

    @Override
    public Observable<List<Pet>> getPets() {
        String sql = "SELECT * FROM " + PetEntry.TABLE_NAME
                + " ORDER BY " + PetEntry.COLUMN_NAME_UPDATED_AT + " DESC";
        return mDatabase.createQuery(PetEntry.TABLE_NAME, sql)
                .mapToList(mMapper);
    }


    @Override
    public Observable<Pet> createPet(Pet pet) {
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_NAME_NAME, pet.getName());
        values.put(PetEntry.COLUMN_NAME_STATUS, pet.getStatus());
        values.put(PetEntry.COLUMN_NAME_EXTERNAL_ID, pet.getExternalId());
        values.put(PetEntry.COLUMN_NAME_UPDATED_AT, Calendar.getInstance().getTimeInMillis());
        long id = mDatabase.insert(PetEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
        pet.setId(id);
        return Observable.just(pet);
    }

    @Override
    public Observable<Pet> updatePet(Pet pet) {
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_NAME_NAME, pet.getName());
        values.put(PetEntry.COLUMN_NAME_STATUS, pet.getStatus());
        values.put(PetEntry.COLUMN_NAME_EXTERNAL_ID, pet.getExternalId());
        values.put(PetEntry.COLUMN_NAME_UPDATED_AT, Calendar.getInstance().getTimeInMillis());
        String selection = PetEntry.COLUMN_NAME_EXTERNAL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(pet.getExternalId())};
        int updatedRows = mDatabase.update(PetEntry.TABLE_NAME, values, selection, selectionArgs);
        Log.d(TAG, "Updated rows = " + updatedRows);
        return Observable.just(pet);
    }

    @Override
    public Observable<Void> deletePet(Pet pet) {
        String selection = PetEntry.COLUMN_NAME_EXTERNAL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(pet.getExternalId())};
        int deletedRows = mDatabase.delete(PetEntry.TABLE_NAME, selection, selectionArgs);
        Log.d(TAG, "Deleted rows = " + deletedRows);
        return Observable.just(null);
    }

    @Override
    public Observable<Void> deleteAll() {
        int deletedRows = mDatabase.delete(PetEntry.TABLE_NAME, null);
        Log.d(TAG, "Deleted rows = " + deletedRows);
        return Observable.just(null);
    }

    @Override
    public Observable<Pet> getPet(long petId) {
        String sql = "SELECT * FROM " + PetEntry.TABLE_NAME
                + " WHERE " + PetEntry.COLUMN_NAME_EXTERNAL_ID + " = ?";
        return mDatabase.createQuery(PetEntry.TABLE_NAME, sql, String.valueOf(petId))
                .mapToOne(mMapper);
    }
}
