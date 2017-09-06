package br.edu.ifsp.lab11.listapp.domain;

/**
 * Created by r0xxFFFF-PC on 11/06/2017.
 */

public class ItemStatistic_TO extends Entity_TO {

    public static final String TAG = "ItemStatistic";

    private String itemId = "";

    private String listId = "";

    private int timesBuyed = -1;

    private int totalAmount = -1;

    public ItemStatistic_TO() { super(); }

}
