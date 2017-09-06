package br.edu.ifsp.lab11.listapp.repository;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import br.edu.ifsp.lab11.listapp.domain.ListStatistic_TO;

/**
 * Created by r0xxFFFF-PC on 25/06/2017.
 */

public class ListStatisticRepository extends Repository<ListStatistic_TO> {

    private static ListStatisticRepository mSingleton = null;

    public static ListStatisticRepository getRepository(){
        if (mSingleton == null)
            mSingleton = new ListStatisticRepository();

        return mSingleton;
    }

    public List<ListStatistic_TO> loadByListId(String list_id) throws ParseException {
        //Local Variables
        List<ListStatistic_TO> list = null;
        ParseQuery<ListStatistic_TO> query = ParseQuery.getQuery(ListStatistic_TO.class);

        query.whereEqualTo(ListStatistic_TO.LIST_ID, list_id);

        list = query.find();

        return list;
    }
}
