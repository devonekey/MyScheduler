package com.onekey.myscheduler.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by OneKey on 2017-10-17.
 */

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter(AccessLevel.PUBLIC)
public class MemoContent extends Memo {
    public static final Long DEFAULT_ID = -1L;
    public static final int TYPE = 1;

    private String createdAt = null;
    private String updatedAt = null;
    @NonNull
    private final String title;
    private final String description;

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public String getDenotedDate() {
        return createdAt;
    }
}
