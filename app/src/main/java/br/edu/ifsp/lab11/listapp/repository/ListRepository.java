package br.edu.ifsp.lab11.listapp.repository;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.lab11.listapp.domain.Item_TO;
import br.edu.ifsp.lab11.listapp.domain.List_TO;

/**
 * Created by r0xxFFFF-PC on 20/05/2017.
 */
public class ListRepository extends Repository<List_TO> {

    private static ListRepository mSingleton = null;

    public ListRepository() {  }

    public static ListRepository getRepository(){

        if (mSingleton == null)
            mSingleton = new ListRepository();

        return mSingleton;
    }

    /**
     * Fetch all List_TO which match the criteria
     *
     * @param owner_id - The value to match
     * @return - A list of results of the query
     */
    public List<List_TO> loadAllByOwnerID(String owner_id, boolean isLazyLoad) throws ParseException {
        //Local Variables
        List<List_TO> list_toList = null;
        ParseQuery<List_TO> query = null;

        query = ParseQuery.getQuery(List_TO.class);
        query.whereEqualTo(List_TO.OWNER_ID, owner_id);

        list_toList = query.find();

        if (!isLazyLoad){

            for (List_TO list : list_toList){

                if (list.getItem_list() != null) {

                    for (Item_TO item : list.getItem_list()) {

                        item = item.fetchIfNeeded();
                    }
                }
            }

            return list_toList;
        }

        return list_toList;
    }

    public List<List_TO> loadByArrayOfIDs(List<String> listObjectIDs, boolean isLazyLoad) throws ParseException {
        //Local Variable
        List<List_TO> list_toList = null;
        ParseQuery<List_TO> query = null;

        query = ParseQuery.getQuery(List_TO.class);
        query.whereContainedIn("objectId", listObjectIDs);
        /*for (String objectID : listObjectIDs)
            query.whereEqualTo("objectId", objectID);*/

        list_toList = query.find();

        if (!isLazyLoad){

            for (List_TO list : list_toList){

                if (list.getItem_list() != null) {

                    for (Item_TO item : list.getItem_list()) {

                        item = item.fetchIfNeeded();
                    }
                }
            }

            return list_toList;
        }

        return list_toList;
    }


}
