package com.theah64.retrokit.database;

import android.content.ContentValues;

import com.theah64.retrokit.exceptions.CustomRuntimeException;

/**
 * Created by theapache64 on 16/9/17.
 */

public class AddBuilder {

    private final BaseTable baseTable;
    private final ContentValues cv = new ContentValues();

    public AddBuilder(BaseTable baseTable) {
        this.baseTable = baseTable;
    }


    public AddBuilder add(String column, String value) {
        cv.put(column, value);
        return this;
    }

    public AddBuilder add(String column, long value) {
        cv.put(column, value);
        return this;
    }

    public long done(boolean isThrowIfFailed) {
        final long insertId = baseTable.getWritableDatabase().insert(baseTable.getTableName(), null, cv);
        if (isThrowIfFailed && insertId == -1) {
            throw new CustomRuntimeException("Failed to insert new row");
        }
        return insertId;
    }

    public AddBuilder add(String column, boolean value) {
        cv.put(column, value);
        return this;
    }
}
