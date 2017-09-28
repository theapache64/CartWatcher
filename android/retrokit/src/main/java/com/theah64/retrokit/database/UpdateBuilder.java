package com.theah64.retrokit.database;

import com.theah64.retrokit.exceptions.CustomRuntimeException;

/**
 * Created by theapache64 on 28/9/17.
 */

public class UpdateBuilder extends BaseAddUpdateBuilder {

    private String whereClause;
    private String[] whereArgs;

    public UpdateBuilder(BaseTable baseTable) {
        super(baseTable);
    }


    public UpdateBuilder where(final String whereClause, final String[] whereArgs) {
        this.whereClause = whereClause;
        this.whereArgs = whereArgs;
        return this;
    }

    public UpdateBuilder where(final String whereClause, final String whereArg) {
        this.whereClause = whereClause;
        this.whereArgs = new String[]{whereArg};
        return this;
    }

    @Override
    public long done(boolean isThrowIfFailed) {
        final long insertId = getBaseTable().getWritableDatabase().update(getBaseTable().getTableName(), getCv(), whereClause, whereArgs);
        if (isThrowIfFailed && insertId == -1) {
            throw new CustomRuntimeException("Failed to insert new row");
        }

        return insertId;
    }
}
