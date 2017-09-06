package br.edu.ifsp.lab11.listapp.activity.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import br.edu.ifsp.lab11.listapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by r0xxFFFF-PC on 07/05/2017.
 *
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 */
public class NavigationDrawerFragment extends Fragment  {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    @BindView(R.id.drawer_listView)
    ListView _drawerListView = null;

    /**
     * The reference of the parent of this fragment;
     */
    private NavigationDrawerCallbacks mCallbacks = null;

    private View mFragmentContainerView = null;

    private DrawerLayout mDrawerLayout = null;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle = null;


    private ArrayAdapter<String> mDrawerListAdapter = null;

    /**
     *
     */
    private int mCurrentSelectedPosition = -1;

    /**
     *
     */
    private boolean mFromSavedInstanceState = false;

    /**
     *
     */
    private boolean mUserLearnedDrawer = false;

    public NavigationDrawerFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallbacks = (NavigationDrawerCallbacks) getActivity();
        } catch (ClassCastException e) {
            Log.e("ClassCastException", e.getMessage());
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Local Variables
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        mUserLearnedDrawer = sharedPreferences.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Local Variables
        View view = inflater.inflate(R.layout.drawer_main, container);
        ButterKnife.bind(this, view);

        return view;
    }

/*    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Indicate that this fragment would like to influence the set of actions in the action bar.
        //setHasOptionsMenu(true);
    }*/

    @Override
    public void onDetach() {
        super.onDetach();

        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

/*    @Override
    public void onCreateOptionsMenu(Menu fragment_list_menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.fragment_list_menu.global, fragment_list_menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(fragment_list_menu, inflater);
    }*/

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.action_example) {
            Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar, ArrayAdapter<String> drawerListView) {
        this.mFragmentContainerView = getActivity().findViewById(fragmentId);
        this.mDrawerLayout = drawerLayout;
        this.mDrawerListAdapter = drawerListView;

        this.mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        this.mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!isAdded()) {
                    return;
                }

                if (mUserLearnedDrawer) {

                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        if (!this.mUserLearnedDrawer && !this.mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        this.setUI();
        this.setEvents();
    }

    public boolean isDrawerOpen() {
        return false;//mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    private void selectItem(int position) {
        //Local Variables

        mCurrentSelectedPosition = position;
        if (_drawerListView != null) {
            _drawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(Gravity.START);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        //ActionBar actionBar = getActionBar();
        //this.mParentToolbar.setDisplayShowTitleEnabled(true);
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        //this.mParentToolbar.setTitle(R.string.app_name);
    }

    /**
     * Get the parent Action bar
     *
     * @return - The current parent action bar
     */
/*    private ActionBar getActionBar() {
        return ((AppCompatActivity)getActivity()).getSupportActionBar();
    }*/

    /**
     * Configure view elements of this fragment
     */
    private void setUI(){

        this._drawerListView.setAdapter(this.mDrawerListAdapter);

        if (this.mCurrentSelectedPosition != -1)
            this._drawerListView.setItemChecked(mCurrentSelectedPosition, true);
    }

    /**
     * Configure events to views
     */
    private void setEvents(){

        this._drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
}
