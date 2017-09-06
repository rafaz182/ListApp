package br.edu.ifsp.lab11.listapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by r0xxFFFF-PC on 11/06/2017.
 */
@ParseClassName("ListStatistic_TO")
public class ListStatistic_TO extends ParseEntity_TO implements Parcelable {

    private String list_id = null;
    public static final String LIST_ID = "list_id";

    private String item_id = null;
    public static final String ITEM_ID = "item_id";

    private String user_id = null;
    private static final String USER_ID = "user_id";

    private int amount = 0;
    public static final String AMOUNT = "amount";

    public ListStatistic_TO() { super(); }

    protected ListStatistic_TO(Parcel in) {
        super();

        setList_id(in.readString());

        setItem_id(in.readString());

        setUser_id(in.readString());

        setAmount(in.readInt());

        setObjectId(in.readString());

        long tmpCreatedAt = in.readLong();
        if (tmpCreatedAt == -1)
            setCreatedAt(null);
        else
            setCreatedAt(new Date(tmpCreatedAt));

        long tmpUpdatedAt = in.readLong();
        if (tmpUpdatedAt == -1)
            setUpdatedAt(null);
        else
            setUpdatedAt(new Date(tmpUpdatedAt));

    }

    public String getList_id() {
        return getString(LIST_ID);
    }

    public void setList_id(String list_id) {
        this.list_id = list_id;
        put(LIST_ID, list_id);
    }

    public String getItem_id() {
        return getString(ITEM_ID);
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
        put(ITEM_ID, item_id);
    }

    public String getUser_id() {
        return getString(USER_ID);
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
        put(USER_ID, user_id);
    }

    public int getAmount() {
        return getInt(AMOUNT);
    }

    public void setAmount(int amount) {
        this.amount = amount;
        put(AMOUNT, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListStatistic_TO that = (ListStatistic_TO) o;

        if (getAmount() != that.getAmount()) return false;
        if (getList_id() != null ? !getList_id().equals(that.getList_id()) : that.getList_id() != null) return false;
        if (getItem_id() != null ? !getItem_id().equals(that.getItem_id()) : that.getItem_id() != null) return false;
        return getUser_id() != null ? getUser_id().equals(that.getUser_id()) : that.getUser_id() == null;
    }

    @Override
    public int hashCode() {
        int result = getList_id() != null ? getList_id().hashCode() : 0;
        result = 31 * result + (getItem_id() != null ? getItem_id().hashCode() : 0);
        result = 31 * result + (getUser_id() != null ? getUser_id().hashCode() : 0);
        result = 31 * result + getAmount();
        return result;
    }

    @Override
    public String toString() {
        return "ListStatistic_TO{" +
                "list_id='" + getList_id() + '\'' +
                ", item_id='" + getItem_id() + '\'' +
                ", user_id='" + getUser_id() + '\'' +
                ", amount=" + getAmount() +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getList_id());
        dest.writeString(getItem_id());
        dest.writeString(getUser_id());
        dest.writeInt(getAmount());

        dest.writeString(getObjectId());
        dest.writeLong(this.getCreatedAt() != null ? this.getCreatedAt().getTime() : -1);
        dest.writeLong(this.getUpdatedAt() != null ? this.getUpdatedAt().getTime() : -1);

    }

    public static final Parcelable.Creator<ListStatistic_TO> CREATOR = new Parcelable.Creator<ListStatistic_TO>() {
        @Override
        public ListStatistic_TO createFromParcel(Parcel source) {
            return new ListStatistic_TO(source);
        }

        @Override
        public ListStatistic_TO[] newArray(int size) {
            return new ListStatistic_TO[size];
        }
    };
}
