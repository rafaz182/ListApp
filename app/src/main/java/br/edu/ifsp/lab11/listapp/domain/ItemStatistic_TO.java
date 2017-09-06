package br.edu.ifsp.lab11.listapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;

import java.util.Date;


/**
 * Created by r0xxFFFF-PC on 11/06/2017.
 */
@ParseClassName("ItemStatistic_TO")
public class ItemStatistic_TO extends ParseEntity_TO implements Parcelable {

    public static final String TAG = "ItemStatistic";

    private String item_id = null;
    public static final String ITEM_ID = "item_id";

    private String list_id = null;
    public static final String LIST_ID = "list_id";

    private int times_buyed = -1;
    public static final String TIMES_BUYED = "times_buyed";

    private int totalAmount = -1;
    private static final String AMOUNT = "totalAmount";

    public ItemStatistic_TO() { super(); }

    protected ItemStatistic_TO(Parcel in) {
        super();

        this.item_id = in.readString();
        setItem_id(item_id);

        this.list_id = in.readString();
        setList_id(list_id);

        this.times_buyed = in.readInt();
        setTimes_buyed(times_buyed);

        this.totalAmount = in.readInt();
        setTotalAmount(totalAmount);

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

    public String getItem_id() {
        return getString(ITEM_ID);
    }

    public void setItem_id(String item_id) {
        put(ITEM_ID, item_id);
    }

    public String getList_id() {
        return getString(LIST_ID);
    }

    public void setList_id(String list_id) {
        put(LIST_ID, list_id);
    }

    public int getTimes_buyed() {
        return getInt(TIMES_BUYED);
    }

    public void setTimes_buyed(int times_buyed) {
        put(TIMES_BUYED, times_buyed);
    }

    public int getTotalAmount() {
        return getInt(AMOUNT);
    }

    public void setTotalAmount(int totalAmount) {
        put(AMOUNT, totalAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemStatistic_TO that = (ItemStatistic_TO) o;

        if (getTimes_buyed() != that.getTimes_buyed()) return false;
        if (getTotalAmount() != that.getTotalAmount()) return false;
        if (getItem_id() != null ? !getItem_id().equals(that.getItem_id()) : that.getItem_id() != null) return false;
        return getList_id() != null ? getList_id().equals(that.getList_id()) : that.getList_id() == null;
    }

    @Override
    public int hashCode() {
        int result = getItem_id() != null ? getItem_id().hashCode() : 0;
        result = 31 * result + (getList_id() != null ? getList_id().hashCode() : 0);
        result = 31 * result + getTimes_buyed();
        result = 31 * result + getTimes_buyed();
        return result;
    }

    @Override
    public String toString() {
        return "ItemStatistic_TO{" +
                "item_id='" + getItem_id() + '\'' +
                ", list_id='" + getList_id() + '\'' +
                ", times_buyed=" + getTimes_buyed() +
                ", totalAmount=" + getTotalAmount() +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getItem_id());
        dest.writeString(getList_id());
        dest.writeInt(getTimes_buyed());
        dest.writeInt(getTotalAmount());

        dest.writeString(getObjectId());
        dest.writeLong(this.getCreatedAt() != null ? this.getCreatedAt().getTime() : -1);
        dest.writeLong(this.getUpdatedAt() != null ? this.getUpdatedAt().getTime() : -1);

    }

    public static final Creator<ItemStatistic_TO> CREATOR = new Creator<ItemStatistic_TO>() {
        @Override
        public ItemStatistic_TO createFromParcel(Parcel source) {
            return new ItemStatistic_TO(source);
        }

        @Override
        public ItemStatistic_TO[] newArray(int size) {
            return new ItemStatistic_TO[size];
        }
    };
}
