package com.mooveit.android.androidtemplateproject.common.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Locale;

public class Pet implements Parcelable {

    private long id;

    private String name;

    private String photoUrls;

    private List<Tag> tags;

    private String status;

    public Pet() {}

    protected Pet(Parcel in) {
        id = in.readLong();
        name = in.readString();
        photoUrls = in.readString();
        tags = in.createTypedArrayList(Tag.CREATOR);
        status = in.readString();
    }

    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };

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
        return String.format(Locale.getDefault(), "{id=%d, name=%s, photoUrls=%s, status=%s}",
                id, name, photoUrls, status);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof Pet) {
            Pet that = (Pet)o;
            return id == that.id;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(photoUrls);
        dest.writeTypedList(tags);
        dest.writeString(status);
    }
}
