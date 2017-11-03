package com.onekey.myscheduler.data;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by OneKey on 2017-09-26.
 */

@Getter
@Setter(AccessLevel.PUBLIC)
public abstract class Memo {
    private String id = UUID.randomUUID().toString();

    public abstract int getItemType();
    public abstract String getDenotedDate();
}
