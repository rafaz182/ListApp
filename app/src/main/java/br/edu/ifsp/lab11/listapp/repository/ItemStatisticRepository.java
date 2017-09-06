package br.edu.ifsp.lab11.listapp.repository;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import br.edu.ifsp.lab11.listapp.domain.ItemStatistic_TO;

/**
 * Created by r0xxFFFF-PC on 13/06/2017.
 */

public class ItemStatisticRepository extends Repository<ItemStatistic_TO> {

    private static ItemStatisticRepository mSingleton = null;

    public static ItemStatisticRepository getRepository(){

        if(mSingleton == null)
            mSingleton = new ItemStatisticRepository();

        return mSingleton;
    }

    public ItemStatistic_TO loadItemStatistic(String item_id, String list_id) throws ParseException {
        //Local Variables
        ParseQuery<ItemStatistic_TO> query;

        query = ParseQuery.getQuery(ItemStatistic_TO.class);
        query.whereEqualTo(ItemStatistic_TO.ITEM_ID, item_id);
        query.whereEqualTo(ItemStatistic_TO.LIST_ID, list_id);

        return query.getFirst();
    }

    public List<ItemStatistic_TO> loadByListId(String list_id) throws ParseException {
        //Local Variables
        ParseQuery<ItemStatistic_TO> query = ParseQuery.getQuery(ItemStatistic_TO.class);

        query.whereEqualTo(ItemStatistic_TO.LIST_ID, list_id);

        return query.find();
    }

}
