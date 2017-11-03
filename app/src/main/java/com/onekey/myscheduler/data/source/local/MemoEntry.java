package com.onekey.myscheduler.data.source.local;

/**
 * Created by OneKey on 2017-09-26.
 */

public interface MemoEntry {
    String TABLE_NAME = "memo";
    String COLUMN_NAME_ID = "id";
    String COLUMN_NAME_CREATED_AT = "created_at";
    String COLUMN_NAME_UPDATED_AT = "updated_at";
    String COLUMN_NAME_TITLE = "title";
    String COLUMN_NAME_DESCRIPTION = "description";
    String MEMO_INDEX = "memo_index";
}
