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
public class List_TO extends Entity_TO {

	/**
	 * Constant that represents this class name
	 */
	public static final String TAG_NAME = "List_TO";

	/**
	 * The name of the list
	 */
	private String listName = "";

	/**
	 * The ID of the user which create this list
	 */
	private String ownerId = "";

	/**
	 * All items inside this list entity
	 */
	private List<Item_TO> itemList = null;

	/**
	 * An flag that indicates if all members of this list can restart the bought items
	 */
	private boolean allCanRestart = false;

	public List_TO() { super(); }

}
