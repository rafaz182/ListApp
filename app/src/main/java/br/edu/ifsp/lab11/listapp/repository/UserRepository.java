package br.edu.ifsp.lab11.listapp.repository;

import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.reflect.Field;
import java.util.List;

import bolts.Task;
import br.edu.ifsp.lab11.listapp.domain.User_TO;

/**
 * Created by r0xxFFFF-PC on 22/04/2017.
 */
public class UserRepository extends Repository<User_TO> {

    private static UserRepository mSingleton = null;

    public UserRepository() { }

    public static UserRepository getRepository(){

        if (mSingleton == null){
            mSingleton = new UserRepository();
        }

        return mSingleton;
    }

    public User_TO login(String email, String password) throws ParseException {
        //Local Variables
        User_TO loggedUser = null;
        ParseQuery<User_TO> query = null;

        query = ParseQuery.getQuery(User_TO.class);
        query.whereEqualTo(User_TO.EMAIL, email);
        query.whereEqualTo(User_TO.PASSWORD, password);

        loggedUser = query.getFirst();

        return loggedUser;
    }

    public User_TO loadByEmail(String email) throws ParseException {
        //Local Variables
        User_TO user = null;
        ParseQuery<User_TO> query = null;

        query = ParseQuery.getQuery(User_TO.class);
        query.whereEqualTo(User_TO.EMAIL, email);

        user = query.getFirst();

        return user;
    }

    public List<User_TO> loadByArrayOfIDs(List<String> listObjectIDs) throws ParseException {
        //Local Variable
        List<User_TO> userTos = null;
        ParseQuery<User_TO> query = null;

        query = ParseQuery.getQuery(User_TO.class);
        query.whereContainedIn("objectId", listObjectIDs);

        userTos = query.find();

        return userTos;
    }
}
