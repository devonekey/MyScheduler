package com.onekey.myscheduler.data.source;


import android.support.annotation.NonNull;

import com.onekey.myscheduler.data.Memo;
import com.onekey.myscheduler.data.MemoContent;

import java.util.List;

/**
 * Created by OneKey on 2017-09-26.
 */

public interface MemoDataSource {
    interface LoadMemosCallback {
        void onLoadSuccess(List<Memo> memos);
        void onLoading();
        void onLoadFailure();
    }

    interface LoadMemoCallback {
        void onLoadSuccess(Memo memo);
        void onLoading();
        void onLoadFailure();
    }

    interface SaveMemoCallback {
        void onSaveSuccess();
        void onSaving();
        void onSaveFailure();
    }

    void browseMemo(@NonNull String id,
                    @NonNull LoadMemoCallback loadMemoCallback);
    void readMemos(int offset,
                   int limit,
                   @NonNull LoadMemosCallback loadMemosCallback);
    void editMemo(@NonNull String id,
                  @NonNull MemoContent memo,
                  @NonNull SaveMemoCallback saveMemoCallback);
    void addMemo(@NonNull MemoContent memo,
                 @NonNull SaveMemoCallback saveMemoCallback);
    void clear();
}
