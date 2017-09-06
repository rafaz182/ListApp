package br.edu.ifsp.lab11.listapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by r0xxFFFF-PC on 22/04/2017.
 */
public class Group_TO extends Entity_TO{

	/**
	 * Constant that represents this class name
	 */
	public static final String TAG_NAME = "Group_TO";

	private String groupName = "";

	private String listId = "";

    private String userId = "";

	public Group_TO() { super(); }
}
