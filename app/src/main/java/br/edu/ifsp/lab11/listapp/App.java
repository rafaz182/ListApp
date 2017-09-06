package br.edu.ifsp.lab11.listapp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.parse.Parse;
import com.parse.ParseObject;

import br.edu.ifsp.lab11.listapp.domain.Group_TO;
import br.edu.ifsp.lab11.listapp.domain.ItemStatistic_TO;
import br.edu.ifsp.lab11.listapp.domain.Item_TO;
import br.edu.ifsp.lab11.listapp.domain.ListStatistic_TO;
import br.edu.ifsp.lab11.listapp.domain.List_TO;
import br.edu.ifsp.lab11.listapp.domain.UserStatistic_TO;
import br.edu.ifsp.lab11.listapp.domain.User_TO;

/**
 * Created by r0xxFFFF-PC on 22/04/2017.
 *
 * Application class
 */
public class App extends Application {

    public static final String APPLICATION_PREFERENCES = "AppPreferences";

    /**
     * Singleton pattern for this class
     */
    private static App mListAppAplication = null;

    /**
     * Singleton pattern for the current user of this session
     */
    private static User_TO mCurrentUser;

    private static SessionManager mCurrentSession = null;

    public App() {}

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(User_TO.class);
        ParseObject.registerSubclass(List_TO.class);
        ParseObject.registerSubclass(Group_TO.class);
        ParseObject.registerSubclass(Item_TO.class);
        ParseObject.registerSubclass(ItemStatistic_TO.class);
        ParseObject.registerSubclass(UserStatistic_TO.class);
        ParseObject.registerSubclass(ListStatistic_TO.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.parse_app_id))
                .server(getString(R.string.parse_server_url))
                //.clientKey(getString(R.string.parse_master_key))
                .build()
        );

        mCurrentSession = new SessionManager(getApplicationContext());
    }

    public static App getApplication(){

        if(mListAppAplication == null){
            mListAppAplication = new App();
        }

        return mListAppAplication;
    }

    public SessionManager getCurrentSession(){

        return mCurrentSession;
    }

    /**
     *
     * @param context
     * @return
     */
    public boolean isConnected(Context context){
        //Local Variables
        ConnectivityManager connectivityManager = null;
        NetworkInfo activeNetworkInfo = null;

        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void setCurrentSession(User_TO user){

        mCurrentSession.createLoginSession(user.getUser_name(), user.getEmail(), user.getObjectId());
    }

    /*public User_TO getCurrentUser(){

        if (mCurrentUser != null)
            return mCurrentUser;

        return null;
    }*/

   /* public void setCurrentUser(User_TO currentUser){
        mCurrentUser = currentUser;
    }*/
}
