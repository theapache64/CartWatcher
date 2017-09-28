package com.theah64.retrokit.utils;

import android.util.Log;

import java.util.List;

/**
 * Created by theapache64 on 28/9/17.
 */

public abstract class ListItemReplacer<T> {
    private static final String X = ListItemReplacer.class.getSimpleName();
    private final List<T> list;
    private String findProperty;

    public ListItemReplacer(List<T> list) {
        this.list = list;
    }

    public abstract String getReplaceProperty(T t);

    public ListItemReplacer<T> replace(String findProperty) {
        this.findProperty = findProperty;
        return this;
    }

    public int with(T t) {
        //First getting the index
        int pos = -1;
        for (int i = 0; i < list.size(); i++) {
            final T t1 = list.get(i);
            if (getReplaceProperty(t1).equals(findProperty)) {
                pos = i;
                break;
            }
        }

        if (pos != -1) {
            list.remove(pos);
            list.add(pos, t);
            Log.i(X, "Match found");
        } else {
            Log.e(X, "No match found");
        }

        return pos;
    }
}
