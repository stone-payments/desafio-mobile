package com.am.store.starwars.model.store.product;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Augusto on 14/01/2017.
 */

public class ProductImageEntity extends RealmObject {

    @PrimaryKey
    private String id;

    private byte[] bitmap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        this.bitmap = byteArray;
    }
}