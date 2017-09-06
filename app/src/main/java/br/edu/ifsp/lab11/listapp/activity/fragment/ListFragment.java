package br.edu.ifsp.lab11.listapp.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.edu.ifsp.lab11.listapp.App;
import br.edu.ifsp.lab11.listapp.R;
import br.edu.ifsp.lab11.listapp.Utils;
import br.edu.ifsp.lab11.listapp.activity.adapter.CategoryItemAdapter;
import br.edu.ifsp.lab11.listapp.domain.ItemStatistic_TO;
import br.edu.ifsp.lab11.listapp.domain.Item_TO;
import br.edu.ifsp.lab11.listapp.domain.ListStatistic_TO;
import br.edu.ifsp.lab11.listapp.domain.List_TO;
import br.edu.ifsp.lab11.listapp.domain.UserStatistic_TO;
import br.edu.ifsp.lab11.listapp.domain.User_TO;
import br.edu.ifsp.lab11.listapp.repository.ItemStatisticRepository;
import br.edu.ifsp.lab11.listapp.repository.ListRepository;
import br.edu.ifsp.lab11.listapp.repository.ListStatisticRepository;
import br.edu.ifsp.lab11.listapp.repository.UserStatisticRepository;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by r0xxFFFF-PC on 11/05/2017.
 *
 * This class represents a list of itens created by the user
 */
public class ListFragment extends Fragment implements EditItemFragment.EditItemFragmentListener,
        CategoryItemAdapter.CategoryItemAdapterCallback{

    private String NO_CATEGORY;

    @BindView(R.id.input_item)
    AutoCompleteTextView _inputItem = null;

    @BindView(R.id.container_categorized_list)
    LinearLayout _containerCategorizedList = null;

    @BindView(R.id.action_restart_list)
    FloatingActionButton _actionRestartList = null;

    /**
     *
     */
    private AddGroupMemberFragment mAddGroupMemberFragment = null;

    private ListStatisticFragment mListStatisticFragment = null;

    /**
     *
     */
    private List_TO mCurrentList = null;

    private HashMap<String, CategoryItemAdapter> mAdapterDic = null;

    private HashMap<String, ListView> mListDic = null;

    private ArrayList<User_TO> mMembersList = null;

    //private ArrayAdapter<String> mHeaders = null;

    //private HashMap<String, ItemStatistic_TO> mItemStatisticDic = null;

    /**
     *
     */
    private String[] mDefaultItens = null;

    /**
     * An flag that indicates if this fragment was built
     */
    private boolean isBuilt = false;

    /**
     * Empty constructor
     */
    public ListFragment() { }

    public static ListFragment newInstance(List_TO list) {
        //Local Variables
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();

        args.putParcelable(List_TO.TAG_NAME, list);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle args = getArguments();

        NO_CATEGORY = getString(R.string.prompt_no_category);
        this.mCurrentList = args.getParcelable(List_TO.TAG_NAME);
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        this.mAdapterDic = new HashMap<>();
        this.mListDic = new HashMap<>();
        this.mAddGroupMemberFragment = AddGroupMemberFragment.newInstance(this.mCurrentList.getObjectId(), this.mMembersList);
        this.mListStatisticFragment = ListStatisticFragment.newInstance(this, this.mCurrentList.getObjectId());
        //this.mItemStatisticDic = new HashMap<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Local Variables
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);

        this.mDefaultItens = getResources().getStringArray(R.array.default_itens); // TODO colocar no servidor

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.setUI();
        this.setEvents();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.isBuilt = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //this.isBuilt = false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_list, menu);

        if (!App.getApplication().getCurrentSession().getUserID().equals(this.mCurrentList.getOwner_id())){
            menu.findItem(R.id.action_add_list_lember).setVisible(false);
            menu.findItem(R.id.action_delete_list).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        //Local Variables
        FragmentManager fragmentManager = getFragmentManager();

        switch (item.getItemId()){
            case R.id.action_refresh: // TODO colocar numa async
                if (App.getApplication().isConnected(getContext())) {

                    try {
                        if (this.isBuilt)
                            this.mCurrentList = ListRepository.getRepository().update(this.mCurrentList);
                        //this.mListDic = new HashMap<>();
                        //this.mAdapterDic = new HashMap<>();
                        //this.notifyDataSetChanged();
                        this.removeCategories();
                        this.loadCategories();
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e("ParseException", e.getMessage());
                    }

                    Toast.makeText(getContext(), R.string.prompt_list_update, Toast.LENGTH_LONG).show();
                    return true;
                }else {
                    Toast.makeText(getContext(), R.string.error_no_connection, Toast.LENGTH_LONG).show();
                    return false;
                }

            case R.id.action_add_list_lember:
                this.mAddGroupMemberFragment.setTargetFragment(this, 300);
                this.mAddGroupMemberFragment.show(fragmentManager, AddGroupMemberFragment.TAG_NAME);
                break;

            case R.id.action_show_statistic:
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.add(R.id.content_main, this.mListStatisticFragment, ListStatisticFragment.TAG);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(this);
                fragmentTransaction.commit();
                break;

        }

        return false;
    }

    @Override
    public void onOkClick(Item_TO item, String oldCategory) {
        // Local Variables
        CategoryItemAdapter adapter = null;

        if (oldCategory.equals(item.getCategory())) {
            if (oldCategory.isEmpty())
                oldCategory = NO_CATEGORY;

            adapter = this.mAdapterDic.get(oldCategory);
            adapter.notifyDataSetChanged();

            return;
        }

        //TODO quando a categoria estiver vazia, remove-la
        if (oldCategory == null || oldCategory.isEmpty()) {
            adapter = this.mAdapterDic.get(NO_CATEGORY);
            adapter.removeItem(item);
            adapter.notifyDataSetChanged();
            Utils.setDynamicHeight(this.mListDic.get(NO_CATEGORY));
        }else {
            adapter = this.mAdapterDic.get(oldCategory);
            adapter.removeItem(item);
            adapter.notifyDataSetChanged();
            Utils.setDynamicHeight(this.mListDic.get(oldCategory));
        }

        this.addItemToCategory(item);
        //this.mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBuyStateChange(Item_TO item) {
        //Local Variable

        item.setBought(true);
        if(item.getBuyer_id() == null || item.getBuyer_id().isEmpty())
            item.setBuyer_id(App.getApplication().getCurrentSession().getUserID());
    }

    public String getItemName(String item_id){

        if (mCurrentList == null)
            return "";

        for (Item_TO item : this.mCurrentList.getItem_list()){
            if (item.getObjectId().equals(item_id))
                return item.getName();
        }

        return "";
    }

    protected void setMembersList(ArrayList<User_TO> membersList){
        this.mMembersList = membersList;
    }

    /**
     * Configure view elements of this activity
     */
    private void setUI(){
        //Local Variables
        ArrayAdapter<String> defaultItens = null;

        defaultItens = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, this.mDefaultItens);

        this._inputItem.setAdapter(defaultItens);

        this.loadCategories();

        this.mAddGroupMemberFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CreateListFragmentTheme);

    }

    /**
     * Configure events to views
     */
    private void setEvents(){

        this._inputItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (!_inputItem.getText().toString().isEmpty() &&
                            event.getRawX() >= (_inputItem.getRight() - _inputItem.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){

                        Item_TO newItem = new Item_TO();

                        newItem.setName(_inputItem.getText().toString());
                        newItem.setAmount(1);
                        newItem.setPrice(0.0);
                        newItem.setBought(false);
                        newItem.setCategory("");
                        newItem.setDescription("");

                        mCurrentList.addItem(newItem);

                        addItemToCategory(newItem);
                        _inputItem.setText("");

                        return true;
                    }
                }

                return false;
            }
        });

        this._inputItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!_inputItem.getText().toString().isEmpty()){
                    _inputItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_menus_add_member, 0);
                } else {
                    _inputItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this._actionRestartList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (Item_TO item : mCurrentList.getItem_list()){
                    if (item.isBought()) {
                        generateStatistic();
                        notifyDataSetChanged();
                        break;
                    }

                }
            }
        });
    }

    private void generateStatistic(){
        //Local Variables
        ItemStatistic_TO itemStatistic = null;
        ListStatistic_TO listStatistic = null;
        List<UserStatistic_TO> userStatisticList = null;
        ArrayList<User_TO> members = null;

        if (App.getApplication().isConnected(getContext())){

            try {
                userStatisticList = UserStatisticRepository.getRepository().loadAllUserStatisticFromList(mCurrentList.getObjectId());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (userStatisticList != null && userStatisticList.size() > 0) {
                boolean found = false;

                for (Item_TO item : mCurrentList.getItem_list()) {
                    if (item.isBought()) {
                        for (UserStatistic_TO statistic : userStatisticList) {

                            if (!item.getBuyer_id().equals(statistic.getUser_id())) {
                                found = false;
                                continue;
                            }

                            statistic.setTimesUserBought(statistic.getTimesUserBought() + 1);
                            statistic.setTotalUserSpent(statistic.getTotalUserSpent() + item.getPrice());

                            found = true;
                            break;
                        }

                        if (!found) {
                            UserStatistic_TO statistic = new UserStatistic_TO();

                            statistic.setTimesUserBought(1);
                            statistic.setTotalUserSpent(item.getPrice());
                            statistic.setUser_id(item.getBuyer_id());
                            statistic.setList_id(mCurrentList.getObjectId());

                            userStatisticList.add(statistic);
                        }
                    }
                }

                try {
                    UserStatisticRepository.getRepository().updateArrayOfUserStatistic(userStatisticList);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else{
                userStatisticList = new ArrayList<>();
                boolean found = false;

                for (Item_TO item : mCurrentList.getItem_list()) {
                    if (item.isBought()) {
                        for (UserStatistic_TO statistic : userStatisticList) {

                            if (!item.getBuyer_id().equals(statistic.getUser_id())) {
                                found = false;
                                continue;
                            }

                            statistic.setTimesUserBought(statistic.getTimesUserBought() + 1);
                            statistic.setTotalUserSpent(statistic.getTotalUserSpent() + item.getPrice());

                            found = true;
                            break;
                        }

                        if (!found) {
                            UserStatistic_TO statistic = new UserStatistic_TO();

                            statistic.setTimesUserBought(1);
                            statistic.setTotalUserSpent(item.getPrice());
                            statistic.setUser_id(item.getBuyer_id());
                            statistic.setList_id(mCurrentList.getObjectId());

                            userStatisticList.add(statistic);
                        }
                    }
                }

                try {
                    UserStatisticRepository.getRepository().updateArrayOfUserStatistic(userStatisticList);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            for (Item_TO item : mCurrentList.getItem_list()){
                itemStatistic = null;
                listStatistic = null;
                if (item.isBought()){

                    try {
                        itemStatistic = ItemStatisticRepository.getRepository().loadItemStatistic(item.getObjectId(), mCurrentList.getObjectId());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (itemStatistic != null){
                        itemStatistic.setTimes_buyed(itemStatistic.getTimes_buyed()+1);
                        itemStatistic.setTotalAmount(itemStatistic.getTotalAmount() + item.getAmount());

                        try {
                            ItemStatisticRepository.getRepository().update(itemStatistic);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        itemStatistic = new ItemStatistic_TO();
                        itemStatistic.setTimes_buyed(1);
                        itemStatistic.setTotalAmount(item.getAmount());
                        itemStatistic.setItem_id(item.getObjectId());
                        itemStatistic.setList_id(mCurrentList.getObjectId());

                        try {
                            ItemStatisticRepository.getRepository().save(itemStatistic);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    listStatistic = new ListStatistic_TO();
                    listStatistic.setList_id(this.mCurrentList.getObjectId());
                    listStatistic.setItem_id(item.getObjectId());
                    listStatistic.setUser_id(App.getApplication().getCurrentSession().getUserID());
                    listStatistic.setAmount(item.getAmount());

                    try {
                        ListStatisticRepository.getRepository().save(listStatistic);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    item.setBought(false);
                    item.setBuyer_id("");
                }
            }

        }
    }

    /**
     * Iterate over all itens and separate by category
     */
    private void loadCategories(){
        //Local Variables
        ListView newCategory = null;
        CategoryItemAdapter adapter = null;
        TextView header = null;
        List<Item_TO> listItem = null;
        String category = null;

        listItem = this.mCurrentList.getItem_list();

        if(listItem != null && listItem.size() > 0) {
            for (Item_TO item : listItem) {
                category = item.getCategory();

                if (category == null || category.isEmpty()) {
                    category = NO_CATEGORY;
                }

                if (!this.mListDic.containsKey(category)){
                    newCategory = new ListView(getContext());
                    adapter = new CategoryItemAdapter(this, new ArrayList<Item_TO>());
                    header = (TextView) Utils.inflateFromLayoutId(getContext(), R.layout.separator_list);

                    this.mAdapterDic.put(category, adapter);
                    this.mListDic.put(category, newCategory);

                    header.setText(category);

                    newCategory.addHeaderView(header, null, false);

                    adapter.addItem(item);
                    newCategory.setAdapter(adapter);

                    //Utils.setDynamicHeight(newCategory);
                    this._containerCategorizedList.addView(newCategory);
                } else {
                    adapter = this.mAdapterDic.get(category);
                    adapter.addItem(item);
                    //Utils.setDynamicHeight(this.mListDic.get(category));
                }
            }

            this.notifyDataSetChanged();
        }
    }

    /**
     *
     */
    private void removeCategories(){

        this.mListDic = new HashMap<>();
        this.mAdapterDic = new HashMap<>();

        this._containerCategorizedList.removeAllViews();
    }

    private void addItemToCategory(Item_TO item){
        //Local Variables
        ListView newCategory = null;
        CategoryItemAdapter adapter = null;
        TextView header = null;
        List<Item_TO> listItem = null;
        String category = null;

        if (item.getCategory().isEmpty())
            category = NO_CATEGORY;
        else
            category = item.getCategory();

        if (!this.mListDic.containsKey(category)){
            newCategory = new ListView(getContext());
            adapter = new CategoryItemAdapter(this, new ArrayList<Item_TO>());
            header = (TextView) Utils.inflateFromLayoutId(getContext(), R.layout.separator_list);

            this.mAdapterDic.put(category, adapter);
            this.mListDic.put(category, newCategory);

            header.setText(category);

            newCategory.addHeaderView(header);

            adapter.addItem(item);
            newCategory.setAdapter(adapter);

            Utils.setDynamicHeight(newCategory);
            this._containerCategorizedList.addView(newCategory);
        } else {
            adapter = this.mAdapterDic.get(category);
            adapter.addItem(item);
            adapter.notifyDataSetChanged();
            Utils.setDynamicHeight(this.mListDic.get(category));
        }

        /*if (this.isBuilt) {
            try {
                ListRepository.getRepository().update(this.mCurrentList); // TODO task
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * Iterate over all categories and notify that List data was changed
     */
    private void notifyDataSetChanged(){

        for (String key : this.mAdapterDic.keySet()){

            this.mAdapterDic.get(key).notifyDataSetChanged();
            // set the height for each list after the notify
            Utils.setDynamicHeight(this.mListDic.get(key));
        }
    }
}
