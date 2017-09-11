package com.theah64.retrokit.models;

/**
 * Created by theapache64 on 24/8/17.
 */

public class SimpleModel {
    private final String id, title, subtitle;

    public SimpleModel(String id, String title, String subtitle) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
