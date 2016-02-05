package com.mooveit.android.androidtemplateproject.model;

import java.util.List;

public class Pet {

    private long id;

    private String name;

    private String photoUrls;

    private List<Tag> tags;

    private String status;

    public Pet() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("{id=%d, name=%s, photoUrls=%s, status=%s}",
                id, name, photoUrls, status);
    }
}
