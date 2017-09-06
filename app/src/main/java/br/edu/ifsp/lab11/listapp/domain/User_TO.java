package br.edu.ifsp.lab11.listapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by r0xxFFFF-PC on 22/04/2017.
 *
 * An entity class that represent the user of the application
 */
@ParseClassName("User_TO")
public class User_TO extends ParseEntity_TO implements Parcelable {

    /**
     * Constant that represents this class name
     */
    public static final String TAG_NAME = "User_TO";

    /**
     * The full name of the user
     */
    private String user_name = null;
    public static final String USER_NAME = "user_name";

    /**
     * The email of the user
     */
    private String email = null;
    public static final String EMAIL = "email";

    /**
     * The password of the user
     */
    private String password = null;
    public static final String PASSWORD = "password";

    /**
     * An flag that indicates if the email is verified
     */
    private boolean emailVerified = false;
    public static final String EMAIL_VERIFIED = "emailVerified";

    /**
     *
     */
    private Object authData = null;
    public static final String AUTH_DATA = "authData";

    public User_TO() { super(); }

    protected User_TO(Parcel in) {
        super();

        this.user_name = in.readString();
        setUser_name(user_name);

        this.email = in.readString();
        setEmail(email);

        this.password = in.readString();
        setPassword(password);

        this.emailVerified = in.readByte() != 0;
        setEmailVerified(emailVerified);
        //this.authData = in.readParcelable(Object.class.getClassLoader());
    }

    public String getUser_name() {
        return getString(USER_NAME);
    }

    public void setUser_name(String user_name) {
        put(USER_NAME, user_name);
    }

    public String getEmail() {
        return getString(EMAIL);
    }

    public void setEmail(String email) {
        put(EMAIL, email);
    }

    public String getPassword() {
        return getString(PASSWORD);
    }

    public void setPassword(String password) {
        put(PASSWORD, password);
    }

    public boolean isEmailVerified() {
        return getBoolean(EMAIL_VERIFIED);
    }

    public void setEmailVerified(boolean emailVerified) {
        put(EMAIL_VERIFIED, emailVerified);
    }

    public Object getAuthData() {
        return get(AUTH_DATA);
    }

    public void setAuthData(Object authData) {
        put(AUTH_DATA, authData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User_TO user_to = (User_TO) o;

        if (emailVerified != user_to.emailVerified) return false;
        if (user_name != null ? !user_name.equals(user_to.user_name) : user_to.user_name != null)
            return false;
        if (email != null ? !email.equals(user_to.email) : user_to.email != null) return false;
        if (password != null ? !password.equals(user_to.password) : user_to.password != null)
            return false;
        return authData != null ? authData.equals(user_to.authData) : user_to.authData == null;

    }

    @Override
    public int hashCode() {
        int result = user_name != null ? user_name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (emailVerified ? 1 : 0);
        result = 31 * result + (authData != null ? authData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User_TO{" +
                "user_name='" + getUser_name() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword()+ '\'' +
                ", emailVerified=" + isEmailVerified() +
                ", authData=" + this.authData +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_name);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeByte(this.emailVerified ? (byte) 1 : (byte) 0);
        //dest.writeParcelable(this.authData, flags);
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
