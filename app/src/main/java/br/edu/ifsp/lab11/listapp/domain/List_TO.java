package br.edu.ifsp.lab11.listapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by r0xxFFFF-PC on 22/04/2017.
 *
 * An entity class that represent a list created by the user
 */
@ParseClassName("List_TO")
public class List_TO extends ParseEntity_TO implements Parcelable {

	/**
	 * Constant that represents this class name
	 */
	public static final String TAG_NAME = "List_TO";

	/**
	 * The name of the list
	 */
	private String list_name = null;
	public static final String LIST_NAME = "list_name";

	/**
	 * The ID of the user which create this list
	 */
	private String owner_id = null;
	public static final String OWNER_ID = "owner_id";

	/**
	 * All items inside this list entity
	 */
	private List<Item_TO> item_list = null;
	public static final String ITEM_LIST = "item_list";

	/**
	 * An flag that indicates if all members of this list can restart the bought items
	 */
	private boolean allCanRestart = false;
	public static final String ALL_CAN_RESTART = "allCanRestart";

	public List_TO() { super(); }

	protected List_TO(Parcel in) {
		super();

		setObjectId(in.readString());
		//getCreatedAt()

		this.list_name = in.readString();
		put(LIST_NAME, list_name);

		this.owner_id = in.readString();
		put(OWNER_ID, owner_id);

		this.item_list = new ArrayList<Item_TO>();
		in.readList(this.item_list, Item_TO.class.getClassLoader());
		put(ITEM_LIST, item_list);

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

	public String getList_name() {
		return getString(LIST_NAME);
	}

	public void setList_name(String list_name) {
		put(LIST_NAME, list_name);
	}

	public String getOwner_id() {
		return getString(OWNER_ID);
	}

	public void setOwner_id(String owner_id) {
		put(OWNER_ID, owner_id);
	}

	public List<Item_TO> getItem_list() {
		return getList(ITEM_LIST);
	}

	public void setItem_list(List<Item_TO> item_list) {
		put(ITEM_LIST, item_list);
	}

	public void addItem(Item_TO item){
		add(ITEM_LIST, item);
		item.setList_id(getObjectId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		List_TO list_to = (List_TO) o;

		if (getList_name() != null ? !getList_name().equals(list_to.getList_name()) : list_to.getList_name() != null)
			return false;
		if (getOwner_id() != null ? !getOwner_id().equals(list_to.getOwner_id()) : list_to.getOwner_id() != null)
			return false;
		return getItem_list() != null ? getItem_list().equals(list_to.getItem_list()) : list_to.getItem_list() == null;

	}

	@Override
	public int hashCode() {
		int result = getList_name() != null ? getList_name().hashCode() : 0;
		result = 31 * result + (getOwner_id() != null ? getOwner_id().hashCode() : 0);
		result = 31 * result + (getItem_list() != null ? getItem_list().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "List_TO{" +
				"list_name='" + getList_name() + '\'' +
				", owner_id='" + getOwner_id() + '\'' +
				", item_list=" + getItem_list().toString() +
				'}';
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.getObjectId());
		dest.writeString(this.getList_name());
		dest.writeString(this.getOwner_id());
		dest.writeList(this.getItem_list());
		dest.writeLong(this.getCreatedAt() != null ? this.getCreatedAt().getTime() : -1);
		dest.writeLong(this.getUpdatedAt() != null ? this.getUpdatedAt().getTime() : -1);
	}

	public static final Parcelable.Creator<List_TO> CREATOR = new Parcelable.Creator<List_TO>() {
		@Override
		public List_TO createFromParcel(Parcel source) {
			return new List_TO(source);
		}

		@Override
		public List_TO[] newArray(int size) {
			return new List_TO[size];
		}
	};
}
