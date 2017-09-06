package br.edu.ifsp.lab11.listapp.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.lab11.listapp.App;
import br.edu.ifsp.lab11.listapp.R;
import br.edu.ifsp.lab11.listapp.activity.fragment.CreateListFragment;
import br.edu.ifsp.lab11.listapp.activity.fragment.ListFragment;
import br.edu.ifsp.lab11.listapp.activity.fragment.NavigationDrawerFragment;
import br.edu.ifsp.lab11.listapp.domain.List_TO;
import br.edu.ifsp.lab11.listapp.repository.ListRepository;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by r0xxFFFF-PC on 07/05/2017.
 *
 * The application main screen
 */
public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        CreateListFragment.CreateListDialogCallbacks {

    @BindView(R.id.drawer_layout)
    DrawerLayout _drawerLayout;

    @BindView(R.id.toolbar_main)
    Toolbar _toolbarMain;

    @BindView(R.id.content_main)
    FrameLayout _contentMain;

    @BindView(R.id.prompt_user_name)
    TextView _promptUserName = null;

    @BindView(R.id.prompt_user_email)
    TextView _promptUserEmail = null;

    @BindView(R.id.action_createList)
    FloatingActionButton _actionCreateList = null;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    public NavigationDrawerFragment mNavigationDrawerFragment = null; // TODO testar com private

    /**
     * Adapter between the user's list and the ListView of the Drawer
     */
    private ArrayAdapter<String> mDrawerListAdapter = null;

    /**
     * Instance of screen used to create new list
     */
    private CreateListFragment mCreateListFragment = null;

    /**
     * An array of all list from this user
     */
    private ArrayList<List_TO> mAllUsersList = null;

    /**
     * The index of the current list in exhibition
     */
    private int mCurrentListIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        this.mAllUsersList = intent.getParcelableArrayListExtra(List_TO.TAG_NAME);
        this.mCreateListFragment = new CreateListFragment();
        this.mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        this.createDrawerListAdapter();
        this.setUI();
        this.setEvents();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        //Local Variables
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (position == -1)
            return;

        this.mCurrentListIndex = position;

        fragmentManager.beginTransaction()
                .replace(R.id.content_main, ListFragment.newInstance(this.mAllUsersList.get(this.mCurrentListIndex)))
                .commit();

        getSupportActionBar().setTitle(this.mDrawerListAdapter.getItem(position));
    }

    @Override
    public void onCreateListConfirm(String listName) {
        //Local Variable
        List_TO newList = new List_TO();

        newList.setList_name(listName);
        newList.setOwner_id(App.getApplication().getCurrentSession().getUserID());

        try {
            newList = ListRepository.getRepository().save(newList); // TODO colocar em uma task
        } catch (ParseException e) {
            Log.e("ParseException", e.getMessage());
        }
        this.mAllUsersList.add(newList);
        this.mDrawerListAdapter.add(listName);
        this.mDrawerListAdapter.notifyDataSetChanged();

        this.onNavigationDrawerItemSelected(mAllUsersList.size()-1);

        this._drawerLayout.closeDrawers();
    }

    public void setToolbarTitle(String title){

        this._toolbarMain.setTitle(title);
    }

    /**
     * Create an adapter between the user's list fetched from database and the ListView in the
     * Drawer
     */
    private void createDrawerListAdapter(){
        //Local Variable
        ArrayList<String> listNames = new ArrayList<>();
        ArrayAdapter<String> adapter = null;

        for (List_TO list : this.mAllUsersList)
            listNames.add(list.getList_name());

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                listNames
                );

        this.mDrawerListAdapter = adapter;
    }

    /**
     * Configure view elements of this activity
     */
    private void setUI(){

        this.setSupportActionBar(this._toolbarMain);

        this.mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                this._drawerLayout,
                this._toolbarMain,
                this.mDrawerListAdapter
        );

        this._promptUserName.setText(App.getApplication().getCurrentSession().getUserName());
        this._promptUserEmail.setText(App.getApplication()
                .getCurrentSession().getUserEmail());

        this.mCreateListFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CreateListFragmentTheme);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    /**
     * Configure events to views
     */
    private void setEvents(){

        this._actionCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCreateListFragment.show(getSupportFragmentManager(), CreateListFragment.TAG_NAME);
            }
        });
    }
}
