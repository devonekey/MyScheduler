package com.onekey.myscheduler.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

/**
 * Created by OneKey on 2017-09-26.
 */

public class MemoDatabaseOpenHelper extends SQLiteOpenHelper implements MemoEntry {
    private static final String DB_NAME = "MyMemoDB.db";
    private static final int DB_VERSION = 1;

    public MemoDatabaseOpenHelper(@NonNull Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE `" + TABLE_NAME + "` (`"
                + COLUMN_NAME_ID + "` TEXT PRIMARY KEY, `"
                + COLUMN_NAME_CREATED_AT + "` TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, `"
                + COLUMN_NAME_UPDATED_AT + "` TEXT DEFAULT NULL, `"
                + COLUMN_NAME_TITLE + "` TEXT NOT NULL, `"
                + COLUMN_NAME_DESCRIPTION + "` TEXT DEFAULT NULL);"
                + "CREATE INDEX `" + MEMO_INDEX + "` ON `"
                + TABLE_NAME + "` (`"
                + COLUMN_NAME_CREATED_AT + "`, `"
                + COLUMN_NAME_UPDATED_AT + "`);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS `" + TABLE_NAME + "`";
        db.execSQL(sql);
    }
}
