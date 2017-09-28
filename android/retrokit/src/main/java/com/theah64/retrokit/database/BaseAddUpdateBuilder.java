package com.theah64.retrokit.database;

import android.content.ContentValues;

/**
 * Created by theapache64 on 28/9/17.
 */

public abstract class BaseAddUpdateBuilder {


    private final BaseTable baseTable;
    private final ContentValues cv = new ContentValues();

    public BaseTable getBaseTable() {
        return baseTable;
    }

    public ContentValues getCv() {
        return cv;
    }

    public BaseAddUpdateBuilder(BaseTable baseTable) {
        this.baseTable = baseTable;
    }


    public BaseAddUpdateBuilder add(String column, String value) {
        cv.put(column, value);
        return this;
    }

    public BaseAddUpdateBuilder add(String column, long value) {
        cv.put(column, value);
        return this;
    }

    public BaseAddUpdateBuilder add(String column, boolean value) {
        getCv().put(column, value);
        return this;
    }


    public abstract long done(boolean isThrowIfFailed);
}
