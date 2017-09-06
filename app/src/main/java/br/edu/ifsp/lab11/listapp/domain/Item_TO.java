package br.edu.ifsp.lab11.listapp.domain;

/**
 * Created by r0xxFFFF-PC on 22/04/2017.
 *
 * An entity class that represent an item created by the user
 */
public class Item_TO extends Entity_TO{

	/**
	 * Constant that represents this class name
	 */
	public static final String TAG_NAME = "Item_TO";

	/**
	 * The name of this item
	 */
	private String name = "";

	/**
	 * A brief description of this item
	 */
	private String description = "";

	/**
	 * The price of this item
	 */
	private Double price = null;

	/**
	 * The amount of this item
	 */
	private Integer amount = null;

	/**
	 * The unit measurement of this item
	 */
	private String measurement = "";

	/**
	 * The category of this item
	 */
	private String category = "";

	/**
	 * An flag that indicates if this item is already bought
	 */
	private boolean bought = false;

	/**
	 * The ID of the list associated with this item
	 */
	private String listId = null;

	/**
	 *
	 */
	private String buyerId = null;

	/**
	 * Empty constructor
	 */
	public Item_TO() { super(); }
}
