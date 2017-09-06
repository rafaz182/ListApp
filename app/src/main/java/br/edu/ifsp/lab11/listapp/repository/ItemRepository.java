package br.edu.ifsp.lab11.listapp.repository;

import com.parse.ParseException;
import com.parse.ParseQuery;

import br.edu.ifsp.lab11.listapp.domain.Item_TO;

/**
 * Created by r0xxFFFF-PC on 20/05/2017.
 */

public class ItemRepository extends Repository<Item_TO> {

    private static ItemRepository mSingleton = null;

    public ItemRepository() {    }

    public static ItemRepository getRepository(){

        if (mSingleton == null)
            mSingleton = new ItemRepository();

        return mSingleton;
    }

    public void deleteByID(String objectID) throws ParseException {
        //Local Variables
        ParseQuery<Item_TO> query;

        query = ParseQuery.getQuery(Item_TO.class);
        query.whereEqualTo(ParseEntity_TO.OBJECT_ID, objectID);
        query.getFirst().delete();
    }
}
