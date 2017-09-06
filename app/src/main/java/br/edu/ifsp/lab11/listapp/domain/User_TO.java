package br.edu.ifsp.lab11.listapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by r0xxFFFF-PC on 22/04/2017.
 *
 * An entity class that represent the user of the application
 */
public class User_TO extends Entity_TO implements Parcelable {

    /**
     * Constant that represents this class name
     */
    public static final String TAG_NAME = "User_TO";

    /**
     * The full name of the user
     */
    private String userName = "";

    /**
     * User nick. Has to be unique in hole application.
     */
    private String userNick = "";

    /**
     * The email of the user
     */
    private String email = "";

    /**
     * The password of the user
     */
    private String password = "";

    /**
     * An flag that indicates if the email is verified
     */
    private boolean emailVerified = false;

    public User_TO() { super(); }

    protected User_TO(Parcel in) {
        this.userName = in.readString();
        this.userNick = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.emailVerified = in.readByte() != 0;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    @Override
    public String toString() {
        return "User_TO{" +
                "userName='" + userName + '\'' +
                ", userNick='" + userNick + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", emailVerified=" + emailVerified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User_TO user_to = (User_TO) o;

        if (emailVerified != user_to.emailVerified) return false;
        if (userName != null ? !userName.equals(user_to.userName) : user_to.userName != null)
            return false;
        if (userNick != null ? !userNick.equals(user_to.userNick) : user_to.userNick != null)
            return false;
        if (email != null ? !email.equals(user_to.email) : user_to.email != null) return false;
        return password != null ? password.equals(user_to.password) : user_to.password == null;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (userNick != null ? userNick.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (emailVerified ? 1 : 0);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userNick);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeByte(this.emailVerified ? (byte) 1 : (byte) 0);
    }

    public static final Parcelable.Creator<User_TO> CREATOR = new Parcelable.Creator<User_TO>() {
        @Override
        public User_TO createFromParcel(Parcel source) {
            return new User_TO(source);
        }

        @Override
        public User_TO[] newArray(int size) {
            return new User_TO[size];
        }
    };
}
