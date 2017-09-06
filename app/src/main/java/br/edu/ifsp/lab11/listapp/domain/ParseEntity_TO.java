package br.edu.ifsp.lab11.listapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseObject;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by r0xxFFFF-PC on 29/04/2017.
 */
public abstract class ParseEntity_TO extends ParseObject{

    private String objectId = null;
    public static final String OBJECT_ID = "objectId";

    private Date createdAt = null;
    public static final String CREATED_AT = "createdAt";

    private Date updatedAt = null;
    public static final String UPDATED_AT = "updatedAt";

    public ParseEntity_TO() {
        this.objectId = super.getObjectId();
        this.createdAt = super.getCreatedAt();
        this.updatedAt = super.getUpdatedAt();
    }

    public String getObjectId(){
        if (super.getObjectId() != null)
            return super.getObjectId();
        else
            return this.objectId;
    }

    public void setObjectId(String objectId){
        this.objectId = objectId;
        super.setObjectId(objectId);
    }

    public Date getCreatedAt(){
        if (super.getCreatedAt() != null)
            return super.getCreatedAt();
        else
            return this.createdAt;
    }

    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt(){
        if (super.getUpdatedAt() != null)
            return super.getUpdatedAt();
        else
            return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt){
        this.updatedAt = updatedAt;
    }
}
