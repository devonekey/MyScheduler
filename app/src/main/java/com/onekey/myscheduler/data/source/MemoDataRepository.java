package com.onekey.myscheduler.data.source;

import android.support.annotation.NonNull;

import com.onekey.myscheduler.data.Memo;
import com.onekey.myscheduler.data.MemoContent;
import com.onekey.myscheduler.data.MemoDate;
import com.onekey.myscheduler.etc.ConstantsEntry;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by OneKey on 2017-09-26.
 */

public class MemoDataRepository implements MemoDataSource, ConstantsEntry {
    private static MemoDataRepository memoDataRepository;
    private final List<Memo> cache;
    private final MemoDataSource memoLocalDataSource;
    private int lastLoadedPosition = DEFAULT_POSITION;
    private int offset = DEFAULT_OFFSET;

    private static final int DEFAULT_OFFSET = 0;
    private static final int LIMIT = 5;

    private MemoDataRepository(MemoDataSource memoLocalDataSource) {
        cache = new ArrayList<>();
        this.memoLocalDataSource = memoLocalDataSource;
    }

    public static MemoDataRepository getInstance(MemoDataSource memoLocalDataSource) {
        if (memoDataRepository == null) {
            memoDataRepository = new MemoDataRepository(memoLocalDataSource);
        }
        return memoDataRepository;
    }

    @Override
    public void browseMemo(@NonNull String id,
                           @NonNull LoadMemoCallback loadMemoCallback) {
        loadMemoCallback.onLoading();

        for (Memo memo : cache) {
            if (memo.getId().equals(id)) {
                loadMemoCallback.onLoadSuccess(memo);
                return;
            }
        }
        memoLocalDataSource.browseMemo(id, loadMemoCallback);
    }

    @Override
    public void readMemos(int offset,
                          int limit,
                          @NonNull LoadMemosCallback loadMemosCallback) {
        memoLocalDataSource.readMemos(offset, limit, new LoadMemosCallback() {
            @Override
            public void onLoadSuccess(List<Memo> memos) {
                memoDataRepository.offset += memos.size();

                for (Memo memo : memos) {
                    if (lastLoadedPosition != DEFAULT_POSITION) {
                        int compRes = 0;

                        try {
                            compRes = compareDenotedDate(memo, cache.get(lastLoadedPosition));
                        } catch (ParseException e) {}

                        if (compRes < 0) {
                            cache.add(new MemoDate(memo.getDenotedDate()));
                            cache.add(memo);
                            lastLoadedPosition += 2;
                        } else if (compRes == 0) {
                            cache.add(memo);
                            lastLoadedPosition++;
                        }
                    } else {
                        cache.add(new MemoDate(memo.getDenotedDate()));
                        cache.add(memo);
                        lastLoadedPosition += 2;
                    }
                }

                loadMemosCallback.onLoadSuccess(memos);
            }

            @Override
            public void onLoading() {
                loadMemosCallback.onLoading();
            }

            @Override
            public void onLoadFailure() {
                loadMemosCallback.onLoadFailure();
            }
        });
    }

    @Override
    public void editMemo(@NonNull String id,
                         @NonNull MemoContent newMemo,
                         @NonNull SaveMemoCallback saveMemoCallback) {
        saveMemoCallback.onSaving();

        for (Memo oldMemo : cache) {
            if (oldMemo.getId().equals(id)) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String updatedAt = dateFormat.format(Calendar.getInstance().getTime());

                newMemo.setId(oldMemo.getId());
                newMemo.setCreatedAt(((MemoContent) oldMemo).getCreatedAt());
                newMemo.setUpdatedAt(updatedAt);

                cache.add(cache.indexOf(oldMemo), newMemo);
                cache.remove(oldMemo);
                break;
            }
        }
        memoLocalDataSource.editMemo(id, newMemo, saveMemoCallback);
    }

    @Override
    public void addMemo(@NonNull MemoContent memo,
                        @NonNull SaveMemoCallback saveMemoCallback) {
        saveMemoCallback.onSaving();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String createdAt = dateFormat.format(Calendar.getInstance().getTime());

        memo.setCreatedAt(createdAt);

        if (!cache.isEmpty()
                && cache.get(FIRST_POSITION) instanceof MemoDate) {
            int compRes;

            try {
                compRes = compareDenotedDate(memo, cache.get(FIRST_POSITION));
            } catch (ParseException e) {
                saveMemoCallback.onSaveFailure();
                return;
            }

            if (compRes < 0) {
                saveMemoCallback.onSaveFailure();
                return;
            } else if (compRes == 0) {
                cache.remove(FIRST_POSITION);

                lastLoadedPosition--;
            }
        }

        cache.add(FIRST_POSITION, memo);
        cache.add(FIRST_POSITION, new MemoDate(createdAt));

        lastLoadedPosition += 2;

        memoLocalDataSource.addMemo(memo, saveMemoCallback);
        offset++;
    }

    @Override
    public void clear() {
        cache.clear();
        lastLoadedPosition = DEFAULT_OFFSET;
        offset = DEFAULT_OFFSET;
    }

    public void getMemos(@NonNull LoadMemosCallback loadMemosCallback) {
        loadMemosCallback.onLoading();

        readMemos(offset, LIMIT, loadMemosCallback);
    }

    public void getMemo(@NonNull Integer position,
                        @NonNull LoadMemoCallback loadMemoCallback) {
        loadMemoCallback.onLoading();

        if (cache.isEmpty()
                || position < FIRST_POSITION
                || position > cache.size() - 1) {
            loadMemoCallback.onLoadFailure();
            return;
        }

        try {
            Memo memo = cache.get(position);

            loadMemoCallback.onLoadSuccess(memo);
        } catch (IndexOutOfBoundsException e) {
            loadMemoCallback.onLoadFailure();
        }
    }

    public void putMemo(int position,
                        @NonNull MemoContent memo,
                        @NonNull SaveMemoCallback saveMemoCallback) {
        saveMemoCallback.onSaving();

        if (position == DEFAULT_POSITION) {
            addMemo(memo, saveMemoCallback);
        } else {
            getMemo(position, new LoadMemoCallback() {
                @Override
                public void onLoadSuccess(Memo oldMemo) {
                    editMemo(oldMemo.getId(), memo, saveMemoCallback);
                }

                @Override
                public void onLoading() {
                    saveMemoCallback.onSaving();
                }

                @Override
                public void onLoadFailure() {
                    saveMemoCallback.onSaveFailure();
                }
            });

        }
    }

    public int getItemCount() {
        return cache.isEmpty() ? 0 : cache.size();
    }

    public int getItemType(@NonNull Integer position) {
        Memo memo = cache.get(position);

        return memo.getItemType();
    }

    private int compareDenotedDate(Memo currentMemo, Memo compareMemo) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar currentCalendar = Calendar.getInstance();
        Calendar compareCalendar = Calendar.getInstance();

        currentCalendar.setTime(dateFormat.parse(currentMemo.getDenotedDate()));
        compareCalendar.setTime(dateFormat.parse(compareMemo.getDenotedDate()));

        return currentCalendar.compareTo(compareCalendar);
    }
}
