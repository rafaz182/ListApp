package br.edu.ifsp.lab11.listapp.repository;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import br.edu.ifsp.lab11.listapp.domain.Group_TO;

/**
 * Created by r0xxFFFF-PC on 11/05/2017.
 */

public class GroupRepository extends Repository<Group_TO> {

    private static GroupRepository mSingleton = null;

    public GroupRepository() { }

    public static GroupRepository getRepository(){

        if (mSingleton == null)
            mSingleton = new GroupRepository();

        return mSingleton;
    }

    public List<Group_TO> loadAllByUserID(String user_id) throws ParseException {
        //Local Variable
        List<Group_TO> groupToList = null;
        ParseQuery<Group_TO> query = null;

        query = ParseQuery.getQuery(Group_TO.class);
        query.whereEqualTo(Group_TO.USER_ID, user_id);

        groupToList = query.find();

        return groupToList;
    }

    public List<Group_TO> loadAllByListID(String list_id) throws ParseException {
        //Local Variable
        List<Group_TO> groupToList = null;
        ParseQuery<Group_TO> query = null;

        query = ParseQuery.getQuery(Group_TO.class);
        query.whereEqualTo(Group_TO.LIST_ID, list_id);

        groupToList = query.find();

        return groupToList;
    }
}
