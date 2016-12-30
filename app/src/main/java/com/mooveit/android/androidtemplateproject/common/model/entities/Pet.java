package com.mooveit.android.androidtemplateproject.common.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Locale;

public class Pet implements Parcelable {

    private transient long databaseId;

    private String name;

    private String photoUrls;

    private List<Tag> tags;

    private String status;

    private transient long updatedAt;

    @SerializedName("id")
    private long externalId;

    protected Pet(Parcel in) {
        databaseId = in.readLong();
        name = in.readString();
        photoUrls = in.readString();
        tags = in.createTypedArrayList(Tag.CREATOR);
        status = in.readString();
        externalId = in.readLong();
        updatedAt = in.readLong();
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

    public Pet() {
    }

    public Pet(long petId, String petName, String petStatus, long externalId, long updatedAt) {
        this.databaseId = petId;
        this.name = petName;
        this.status = petStatus;
        this.externalId = externalId;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return databaseId;
    }

    public void setId(long id) {
        this.databaseId = id;
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

    public long getExternalId() {
        return externalId;
    }

    public void setExternalId(long externalId) {
        this.externalId = externalId;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(),
                "{databaseId=%d, name=%s, photoUrls=%s, status=%s, externalId=%s, updatedAt=%s}",
                databaseId, name, photoUrls, status, externalId, updatedAt);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof Pet) {
            Pet that = (Pet) o;
            return databaseId == that.databaseId;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(databaseId);
        dest.writeString(name);
        dest.writeString(photoUrls);
        dest.writeTypedList(tags);
        dest.writeString(status);
        dest.writeLong(externalId);
        dest.writeLong(updatedAt);
    }
}
