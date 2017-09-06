package br.edu.ifsp.lab11.listapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by r0xxFFFF-PC on 22/04/2017.
 */
@ParseClassName("Group_TO")
public class Group_TO extends ParseObject implements Parcelable {

	/**
	 * Constant that represents this class name
	 */
	public static final String TAG_NAME = "Group_TO";

/*	private String group_name = null;
	public static final String GROUP_NAME = "group_name";*/

	private String list_id = null;
	public static final String LIST_ID = "list_id";

    private String user_id = null;
	public static final String USER_ID = "user_id";

	public Group_TO() {}

	protected Group_TO(Parcel in) {
		/*this.group_name = in.readString();*/
		this.list_id = in.readString();
		this.user_id = in.readString();
	}

	/*public String getGroup_name() {
		return getString(GROUP_NAME);
	}

	public void setGroup_name(String group_name) {
		put(GROUP_NAME, group_name);
	}*/

	public String getList_id() {
		return getString(LIST_ID);
	}

	public void setList_id(String list_id) {
		put(LIST_ID, list_id);
	}

	public String getUser_id() {
		return getString(USER_ID);
	}

	public void setUser_id(String user_id) {
		put(USER_ID, user_id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Group_TO group_to = (Group_TO) o;

		/*if (getGroup_name() != null ? !getGroup_name().equals(group_to.getGroup_name()) : group_to.getGroup_name() != null)
			return false;*/
		if (getList_id() != null ? !getList_id().equals(group_to.getList_id()) : group_to.getList_id() != null)
			return false;
		return getUser_id() != null ? getUser_id().equals(group_to.getUser_id()) : group_to.getUser_id() == null;

	}

	@Override
	public int hashCode() {
		int result = getList_id() != null ? getList_id().hashCode() : 0;
		result = 31 * result + (getUser_id() != null ? getUser_id().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Group_TO{" +
				/*"group_name='" + getGroup_name() + '\'' +*/
				", list_id='" + getList_id() + '\'' +
				", user_id='" + getUser_id() + '\'' +
				'}';
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		/*dest.writeString(getGroup_name());*/
		dest.writeString(getList_id());
		dest.writeString(getUser_id());
	}

	public static final Parcelable.Creator<Group_TO> CREATOR = new Parcelable.Creator<Group_TO>() {
		@Override
		public Group_TO createFromParcel(Parcel source) {
			return new Group_TO(source);
		}

		@Override
		public Group_TO[] newArray(int size) {
			return new Group_TO[size];
		}
	};
}
