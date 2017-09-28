package com.theah64.retrokit.database;

import com.theah64.retrokit.exceptions.CustomRuntimeException;

/**
 * Created by theapache64 on 16/9/17.
 */

public class AddBuilder extends BaseAddUpdateBuilder {

    public AddBuilder(BaseTable baseTable) {
        super(baseTable);
    }

    @Override
    public long done(boolean isThrowIfFailed) {

        final long insertId = getBaseTable().getWritableDatabase().insert(getBaseTable().getTableName(), null, getCv());
        if (isThrowIfFailed && insertId == -1) {
            throw new CustomRuntimeException("Failed to insert new row");
        }

        return insertId;
    }


}
