package br.edu.ifsp.lab11.listapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;

import java.util.Date;

/**
 * Created by r0xxFFFF-PC on 11/06/2017.
 */

@ParseClassName("UserStatistic_TO")
public class UserStatistic_TO extends ParseEntity_TO implements Parcelable {

    private String list_id = null;
    public  static final String LIST_ID = "list_id";

    private String user_id = null;
    public static final String USER_ID = "user_id";

    private int timesUserBought = 0;
    public static final String TIMES_USER_BOUGHT = "timesUserBought";

    private double totalUserSpent = 0.0;
    public static final String TOTAL_USER_SPENT = "totalUserSpent";

    public UserStatistic_TO() { super(); }

    protected UserStatistic_TO(Parcel in) {
        super();

        setList_id(in.readString());

        setUser_id(in.readString());

        setTimesUserBought(in.readInt());

        setTotalUserSpent(in.readDouble());

        setObjectId(in.readString());

        long tmpCreatedAt = in.readLong();
        if (tmpCreatedAt == -1)
            setCreatedAt(null);
        else
            setCreatedAt(new Date(tmpCreatedAt));

        long tmpUpdatedAt = in.readLong();
        if (tmpUpdatedAt == -1)
            this.setUpdatedAt(null);
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

    public String getUser_id() {
        return getString(USER_ID);
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
        put(USER_ID, user_id);
    }

    public int getTimesUserBought() {
        return getInt(TIMES_USER_BOUGHT);
    }

    public void setTimesUserBought(int timesUserBought) {
        this.timesUserBought = timesUserBought;
        put(TIMES_USER_BOUGHT, timesUserBought);
    }

    public double getTotalUserSpent() {
        return getDouble(TOTAL_USER_SPENT);
    }

    public void setTotalUserSpent(double totalUserSpent) {
        this.totalUserSpent = totalUserSpent;
        put(TOTAL_USER_SPENT, totalUserSpent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserStatistic_TO that = (UserStatistic_TO) o;

        if (getTimesUserBought() != that.getTimesUserBought()) return false;
        if (Double.compare(that.getTotalUserSpent(), getTotalUserSpent()) != 0) return false;
        return getList_id() != null ? getList_id().equals(that.getList_id()) : that.getList_id() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getList_id() != null ? getList_id().hashCode() : 0;
        result = 31 * result + getTimesUserBought();
        temp = Double.doubleToLongBits(getTotalUserSpent());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "UserStatistic_TO{" +
                "list_id='" + getList_id() + '\'' +
                ", timesUserBought=" + getTimesUserBought() +
                ", totalUserSpent=" + getTotalUserSpent() +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getList_id());
        dest.writeString(this.getUser_id());
        dest.writeInt(this.getTimesUserBought());
        dest.writeDouble(this.getTotalUserSpent());
        dest.writeString(this.getObjectId());
        dest.writeLong(this.getCreatedAt() != null ? this.getCreatedAt().getTime() : -1);
        dest.writeLong(this.getUpdatedAt() != null ? this.getUpdatedAt().getTime() : -1);
    }

    public static final Parcelable.Creator<UserStatistic_TO> CREATOR = new Parcelable.Creator<UserStatistic_TO>() {
        @Override
        public UserStatistic_TO createFromParcel(Parcel source) {
            return new UserStatistic_TO(source);
        }

        @Override
        public UserStatistic_TO[] newArray(int size) {
            return new UserStatistic_TO[size];
        }
    };
}
