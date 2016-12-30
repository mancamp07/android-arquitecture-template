package com.mooveit.android.androidtemplateproject.common.model.datasource.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PetsDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "pets.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_PETS_TABLE =
            "CREATE TABLE " + PetsDatabaseContract.PetEntry.TABLE_NAME + " (" +
                    PetsDatabaseContract.PetEntry._ID + TEXT_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    PetsDatabaseContract.PetEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    PetsDatabaseContract.PetEntry.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP +
                    PetsDatabaseContract.PetEntry.COLUMN_NAME_EXTERNAL_ID + TEXT_TYPE + COMMA_SEP +
                    PetsDatabaseContract.PetEntry.COLUMN_NAME_UPDATED_AT + TEXT_TYPE +
                    " )";

    public PetsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
