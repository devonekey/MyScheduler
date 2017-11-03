package com.onekey.myscheduler.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Created by OneKey on 2017-10-17.
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class MemoDate extends Memo {
    public static final int TYPE = 2;

    @NonNull
    private String denotedDate;

    @Override
    public int getItemType() {
        return TYPE;
    }
}
