package br.edu.ifsp.lab11.listapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by r0xxFFFF-PC on 22/04/2017.
 *
 * An entity class that represent an item created by the user
 */
@ParseClassName("Item_TO")
public class Item_TO extends ParseEntity_TO implements Parcelable {

	/**
	 * Constant that represents this class name
	 */
	public static final String TAG_NAME = "Item_TO";

	/**
	 * The name of this item
	 */
	private String name = null;
	public static final String NAME = "name";

	/**
	 * A brief description of this item
	 */
	private String description = null;
	public static final String DESCRIPTION = "description";

	/**
	 * The price of this item
	 */
	private Double price = null;
	public static final String PRICE = "price";

	/**
	 * The amount of this item
	 */
	private Integer amount = null;
	public static final String AMOUNT = "amount";

	/**
	 * The unit measurement of this item
	 */
	private String measurement = null;
	public static final String MEASUREMENT = "measurement";

	/**
	 * The category of this item
	 */
	private String category = null;
	public static final String CATEGORY = "category";

	/**
	 * An flag that indicates if this item is already bought
	 */
	private boolean bought = false;
	public static final String BOUGHT = "bought";

	/**
	 * The ID of the list associated with this item
	 */
	private String list_id = null;
	public static final String LIST_ID = "list_id";

	/**
	 *
	 */
	private String buyer_id = null;
	public static final String BUYER_ID = "buyer_id";

	/**
	 * Empty constructor
	 */
	public Item_TO() { super(); }

	protected Item_TO(Parcel in) {
		super();

		this.name = in.readString();
		if (this.name == null)
			put(NAME, "");
		else
			put(NAME, name);

		this.description = in.readString();
		if (this.description == null)
			put(DESCRIPTION, "");
		else
			put(DESCRIPTION, description);

		this.price = (Double) in.readValue(Double.class.getClassLoader());
		put(PRICE, price);

		this.amount = (Integer) in.readValue(Integer.class.getClassLoader());
		put(AMOUNT, amount);

		this.measurement = in.readString();
		if (this.measurement == null)
			put(MEASUREMENT, "");
		else
			put(MEASUREMENT, measurement);

		this.category = in.readString();
		if (this.category == null)
			put(CATEGORY, "");
		else
			put(CATEGORY, category);

		this.bought = in.readByte() != 0;
		put(BOUGHT, bought);

		this.list_id = in.readString();
		put(LIST_ID, list_id);

		setObjectId(in.readString());

		long tmpCreatedAt = in.readLong();
		setCreatedAt(tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt));

		long tmpUpdatedAt = in.readLong();
		setUpdatedAt(tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt));

		this.buyer_id = in.readString();
		if (buyer_id == null)
			setBuyer_id("");
		else
			setBuyer_id(buyer_id);
	}

	public String getName() {
		//return this.name;
		return getString(NAME);
	}

	public void setName(String name) {
		this.name = name;
		put(NAME, name);
	}

	public String getDescription() {
		//return this.description;
		return getString(DESCRIPTION);
	}

	public void setDescription(String description) {
		this.description = description;
		put(DESCRIPTION, description);
	}

	public Double getPrice() {
		//return this.price;
		return getDouble(PRICE);
	}

	public void setPrice(Double price) {
		this.price = price;
		put(PRICE, price);
	}

	public Integer getAmount() {
		//return this.amount;
		return getInt(AMOUNT);
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
		put(AMOUNT, amount);
	}

	public String getMeasurement() {
		//return this.measurement;
		return getString(MEASUREMENT);
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
		put(MEASUREMENT, measurement);
	}

	public String getCategory(){
		//return this.category;
		return getString(CATEGORY);
	}

	public void setCategory(String category){
		this.category = category;
		put(CATEGORY, category);
	}

	public boolean isBought() {
		//return this.bought;
		return getBoolean(BOUGHT);
	}

	public void setBought(boolean bought) {
		this.bought = bought;
		put(BOUGHT, bought);
	}

	public String getList_id() {
		//return this.list_id;
		return getString(LIST_ID);
	}

	public void setList_id(String list_id) {
		this.list_id = list_id;
		put(LIST_ID, list_id);
	}

	public String getBuyer_id() {
		return getString(BUYER_ID);
	}

	public void setBuyer_id(String buyer_id) {
		put(BUYER_ID, buyer_id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Item_TO item_to = (Item_TO) o;

		if (isBought() != item_to.isBought()) return false;
		if (getName() != null ? !getName().equals(item_to.getName()) : item_to.getName() != null) return false;
		if (getDescription() != null ? !getDescription().equals(item_to.getDescription()) : item_to.getDescription() != null)
			return false;
		if (getPrice() != null ? !getPrice().equals(item_to.price) : item_to.getPrice() != null) return false;
		if (getAmount() != null ? !getAmount().equals(item_to.getAmount()) : item_to.getAmount() != null) return false;
		if (getMeasurement() != null ? !getMeasurement().equals(item_to.getMeasurement()) : item_to.getMeasurement() != null)
			return false;
		return getList_id() != null ? getList_id().equals(item_to.getList_id()) : item_to.getList_id() == null;

	}

	@Override
	public int hashCode() {
		int result = getName() != null ? getName().hashCode() : 0;
		result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
		result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
		result = 31 * result + (getAmount() != null ? getAmount().hashCode() : 0);
		result = 31 * result + (getMeasurement() != null ? getMeasurement().hashCode() : 0);
		result = 31 * result + (isBought() ? 1 : 0);
		result = 31 * result + (getList_id() != null ? getList_id().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Item_TO{" +
				"name='" + getName() + '\'' +
				", description='" + getDescription() + '\'' +
				", price=" + getPrice() +
				", amount=" + getAmount() +
				", measurement='" + getMeasurement() + '\'' +
				", category='" + getCategory() + '\'' +
				", bought=" + isBought() +
				", list_id='" + getList_id() + '\'' +
				'}';
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getName());
		dest.writeString(getDescription());
		dest.writeValue(getPrice());
		dest.writeValue(getAmount());
		dest.writeString(getMeasurement());
		dest.writeString(getCategory());
		dest.writeByte(isBought() ? (byte) 1 : (byte) 0);
		dest.writeString(getList_id());
		dest.writeString(getObjectId());
		dest.writeLong(getCreatedAt() != null ? getCreatedAt().getTime() : -1);
		dest.writeLong(getCreatedAt() != null ? getCreatedAt().getTime() : -1);
		dest.writeString(getBuyer_id());
	}

	public static final Creator<Item_TO> CREATOR = new Creator<Item_TO>() {
		@Override
		public Item_TO createFromParcel(Parcel source) {
			return new Item_TO(source);
		}

		@Override
		public Item_TO[] newArray(int size) {
			return new Item_TO[size];
		}
	};
}
