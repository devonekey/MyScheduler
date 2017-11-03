package com.onekey.myscheduler.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.onekey.myscheduler.data.Memo;
import com.onekey.myscheduler.data.MemoContent;
import com.onekey.myscheduler.data.source.MemoDataSource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by OneKey on 2017-09-26.
 */

public class MemoLocalDataSource implements MemoDataSource, MemoEntry {
    private static MemoLocalDataSource memoLocalDataSource;
    private MemoDatabaseOpenHelper memoDatabaseOpenHelper;

    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_LIMIT = 1;

    private MemoLocalDataSource(@NonNull Context context) {
        memoDatabaseOpenHelper = new MemoDatabaseOpenHelper(context);
    }

    public static MemoLocalDataSource getInstance(@NonNull Context context) {
        if (memoLocalDataSource == null) {
            memoLocalDataSource = new MemoLocalDataSource(context);
        }

        return memoLocalDataSource;
    }

    @Override
    public void browseMemo(@NonNull String id,
                           @NonNull LoadMemoCallback loadMemoCallback) {
        loadMemoCallback.onLoading();

        SQLiteDatabase db = memoDatabaseOpenHelper.getReadableDatabase();

        String[] columns = {
                COLUMN_NAME_ID,
                "DATETIME(`" + COLUMN_NAME_CREATED_AT + "`, 'localtime') " +
                        "as `" + COLUMN_NAME_CREATED_AT + "` ",
                "(CASE WHEN(`" + COLUMN_NAME_UPDATED_AT + "` IS NULL) " +
                        "THEN `" + COLUMN_NAME_UPDATED_AT + "` " +
                        "ELSE DATETIME(`" + COLUMN_NAME_UPDATED_AT + "`) " +
                        "END) " +
                        "AS `" + COLUMN_NAME_UPDATED_AT + "`",
                COLUMN_NAME_TITLE,
                COLUMN_NAME_DESCRIPTION
        };
        String orderBy = COLUMN_NAME_CREATED_AT + ", " + COLUMN_NAME_UPDATED_AT + " DESC";
        String limitWithOffset = DEFAULT_OFFSET + ", " + DEFAULT_LIMIT;

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, orderBy, limitWithOffset);

        Memo memo = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                String createdAt = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_CREATED_AT));
                String updatedAt = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_UPDATED_AT));
                String title = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_TITLE));
                String description = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_DESCRIPTION));
                memo = new MemoContent(createdAt, updatedAt, title, description);
                memo.setId(id);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (memo == null) {
            loadMemoCallback.onLoadFailure();
        } else {
            loadMemoCallback.onLoadSuccess(memo);
        }
    }

    @Override
    public void readMemos(int offset,
                          int limit,
                          @NonNull LoadMemosCallback loadMemosCallback) {
        loadMemosCallback.onLoading();

        SQLiteDatabase db = memoDatabaseOpenHelper.getReadableDatabase();

        String[] columns = {
                COLUMN_NAME_ID,
                "DATETIME(`" + COLUMN_NAME_CREATED_AT + "`, 'localtime') " +
                        "as `" + COLUMN_NAME_CREATED_AT + "` ",
                "(CASE WHEN(`" + COLUMN_NAME_UPDATED_AT + "` IS NULL) " +
                        "THEN `" + COLUMN_NAME_UPDATED_AT + "` " +
                        "ELSE DATETIME(`" + COLUMN_NAME_UPDATED_AT + "`) " +
                        "END) " +
                        "AS `" + COLUMN_NAME_UPDATED_AT + "`",
                COLUMN_NAME_TITLE,
                COLUMN_NAME_DESCRIPTION
        };
        String orderBy = "DATETIME(`" + COLUMN_NAME_CREATED_AT + "`, 'localtime') DESC";
        String limitWithOffset = offset + ", " + limit;

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, orderBy, limitWithOffset);

        List<Memo> memos = new ArrayList<>();

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                String id = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_ID));
                String createdAt = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_CREATED_AT));
                String updatedAt = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_UPDATED_AT));
                String title = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_TITLE));
                String description = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_DESCRIPTION));
                Memo memo = new MemoContent(createdAt, updatedAt, title, description);
                memo.setId(id);
                memos.add(memo);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (memos.isEmpty()) {
            loadMemosCallback.onLoadFailure();
        } else {
            loadMemosCallback.onLoadSuccess(memos);
        }
    }

    @Override
    public void editMemo(@NonNull String id,
                         @NonNull MemoContent memo,
                         @NonNull SaveMemoCallback saveMemoCallback) {
        saveMemoCallback.onSaving();

        SQLiteDatabase db = memoDatabaseOpenHelper.getWritableDatabase();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String updatedAt = dateFormat.format(Calendar.getInstance().getTime());

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_UPDATED_AT, updatedAt);
        values.put(COLUMN_NAME_TITLE, memo.getTitle());
        values.put(COLUMN_NAME_DESCRIPTION, memo.getDescription());
        String whereClause = COLUMN_NAME_ID + "=?";
        String[] whereArgs = new String[]{id};

        int row = db.update(TABLE_NAME, values, whereClause, whereArgs);

        db.close();

        if (row == 0) {
            saveMemoCallback.onSaveFailure();
        } else {
            saveMemoCallback.onSaveSuccess();
        }
    }

    @Override
    public void addMemo(@NonNull MemoContent memo,
                        @NonNull SaveMemoCallback saveMemoCallback) {
        saveMemoCallback.onSaving();

        SQLiteDatabase db = memoDatabaseOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, memo.getId());
        values.put(COLUMN_NAME_TITLE, memo.getTitle());
        values.put(COLUMN_NAME_DESCRIPTION, memo.getDescription());

        Long rowId = db.insert(TABLE_NAME, null, values);

        db.close();

        if (rowId == -1L) {
            saveMemoCallback.onSaveFailure();
        } else {
            saveMemoCallback.onSaveSuccess();
        }
    }

    @Override
    public void clear() {
        // ignore this block
    }
}
