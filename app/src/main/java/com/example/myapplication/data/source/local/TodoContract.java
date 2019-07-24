package com.example.myapplication.data.source.local;

import android.provider.BaseColumns;

/**
 * 1
 */
public final class TodoContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private TodoContract() {}

    /* Inner class that defines the table contents */
    public static class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes_table";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
} 