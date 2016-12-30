package com.mooveit.android.androidtemplateproject.common.model.datasource.local;

import android.provider.BaseColumns;

public final class PetsDatabaseContract {

    private PetsDatabaseContract() {}

    public static abstract class PetEntry implements BaseColumns {
        public static final String TABLE_NAME = "pets";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_EXTERNAL_ID = "external_id";
        public static final String COLUMN_NAME_UPDATED_AT = "updated_at";
    }
}
