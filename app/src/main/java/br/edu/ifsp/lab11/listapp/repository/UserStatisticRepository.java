package br.edu.ifsp.lab11.listapp.repository;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import br.edu.ifsp.lab11.listapp.domain.UserStatistic_TO;

/**
 * Created by r0xxFFFF-PC on 24/06/2017.
 */

public class UserStatisticRepository extends Repository<UserStatistic_TO> {

    private static UserStatisticRepository mSingleton;

    public static UserStatisticRepository getRepository(){
        if (mSingleton == null)
            mSingleton = new UserStatisticRepository();

        return mSingleton;
    }

    public UserStatistic_TO loadUserStatistic(String user_id, String list_id) throws ParseException {
        //Local Variable
        ParseQuery<UserStatistic_TO> query = null;

        query = ParseQuery.getQuery(UserStatistic_TO.class);
        query.whereEqualTo(UserStatistic_TO.USER_ID, user_id);
        query.whereEqualTo(UserStatistic_TO.LIST_ID, list_id);

        return query.getFirst();
    }

    public List<UserStatistic_TO> loadAllUserStatisticFromList(String list_id) throws ParseException {
        //Local Variable
        ParseQuery<UserStatistic_TO> query = null;

        query = ParseQuery.getQuery(UserStatistic_TO.class);
        query.whereEqualTo(UserStatistic_TO.LIST_ID, list_id);

        return query.find();
    }

    public void updateArrayOfUserStatistic(List<UserStatistic_TO> userStatisticList) throws ParseException {
        //Local Variable
        for (UserStatistic_TO statistic : userStatisticList){

            statistic = this.update(statistic);
        }
    }

    public List<UserStatistic_TO> loadByListId(String list_id) throws ParseException {
        //Local Variables
        ParseQuery<UserStatistic_TO> query = ParseQuery.getQuery(UserStatistic_TO.class);

        query.whereEqualTo(UserStatistic_TO.LIST_ID, list_id);

        return query.find();
    }
}
