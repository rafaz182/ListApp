package br.edu.ifsp.lab11.listapp.activity.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.edu.ifsp.lab11.listapp.R;
import br.edu.ifsp.lab11.listapp.activity.MainActivity;
import br.edu.ifsp.lab11.listapp.domain.ItemStatistic_TO;
import br.edu.ifsp.lab11.listapp.domain.Item_TO;
import br.edu.ifsp.lab11.listapp.domain.ListStatistic_TO;
import br.edu.ifsp.lab11.listapp.domain.List_TO;
import br.edu.ifsp.lab11.listapp.domain.ParseEntity_TO;
import br.edu.ifsp.lab11.listapp.domain.UserStatistic_TO;
import br.edu.ifsp.lab11.listapp.domain.User_TO;
import br.edu.ifsp.lab11.listapp.repository.ItemRepository;
import br.edu.ifsp.lab11.listapp.repository.ItemStatisticRepository;
import br.edu.ifsp.lab11.listapp.repository.ListStatisticRepository;
import br.edu.ifsp.lab11.listapp.repository.UserRepository;
import br.edu.ifsp.lab11.listapp.repository.UserStatisticRepository;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by r0xxFFFF-PC on 15/06/2017.
 */

public class ListStatisticFragment extends Fragment {

    public static final String TAG = "ListStatisticFragment";

    @BindView(R.id.prompt_top_selling)
    TextView _promptTopSelling = null;

    @BindView(R.id.prompt_top_buyer)
    TextView _promptTopBuyer = null;

    @BindView(R.id.prompt_total_buyed)
    TextView _promptTotalBuyed = null;

    @BindView(R.id.prompt_total_spent)
    TextView _promptTotalSpent = null;

    @BindView(R.id.graph_items_per_user)
    GraphView _graphItemsPerUser = null;

    @BindView(R.id.graph_items_time_bought)
    GraphView _graphItemsTimeBought = null;

    private String mListID = null;

    private String mTopBuyedItem = null;

    private String mTopBuyerUser = null;

    private Integer mTotalItensBuyed = null;

    private Double mTotalSpent = null;

    private BarGraphSeries<DataPoint> mTotalItensPerUser = null;

    private BarGraphSeries<DataPoint> mItemTimesBought = null;

    private List<ItemStatistic_TO> itemStatisticList = null;

    private List<UserStatistic_TO> userStatisticList = null;

    private List<ListStatistic_TO> listStatisticList = null;

    private ProgressDialog mProgressDialog = null;

    private Fragment mParent = null;

    public static ListStatisticFragment newInstance(Fragment parent, String list_id){
        //Local Variables
        ListStatisticFragment instance = new ListStatisticFragment();
        Bundle args = new Bundle();

        args.putString(List_TO.OBJECT_ID, list_id);
        instance.setArguments(args);
        instance.setParent(parent);

        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemStatisticList = new ArrayList<>();
        userStatisticList = new ArrayList<>();
        listStatisticList = new ArrayList<>();

        this.mProgressDialog = new ProgressDialog(getContext(), R.style.ListAppProgressDialogTheme);
        this.mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.mProgressDialog.setMessage(getString(R.string.prompt_loading_data)); //todo alterar a string
        this.mProgressDialog.setCancelable(false);

        this.mListID = getArguments().getString(List_TO.OBJECT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Local Variables
        View view = inflater.inflate(R.layout.fragment_list_statistic, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mProgressDialog.show();
        new LoadStatisticTask().execute(this.mListID);
    }

    public void setParent(Fragment parent){
        this.mParent = parent;
    }

    private void dimissProgressDialog(){
        this.mProgressDialog.dismiss();
    }

    private void setUI(){

        ((MainActivity)getActivity()).setToolbarTitle("Estatistica da lista"); // todo passar para resource

        /*this.mItemTimesBought.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });
        this.mTotalItensPerUser.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        //this.mItemTimesBought.setDataWidth(3);
        //this.mItemTimesBought.setTitle("awdawdaaaa");

        this.mItemTimesBought.setSpacing(5);
        this.mTotalItensPerUser.setSpacing(5);

        this.mItemTimesBought.setDrawValuesOnTop(true);
        this.mTotalItensPerUser.setDrawValuesOnTop(true);

        this.mItemTimesBought.setValuesOnTopColor(Color.RED);
        this.mTotalItensPerUser.setValuesOnTopColor(Color.RED);*/

        //this._graphItemsTimeBought.addSeries(this.mItemTimesBought);
        //this._graphItemsPerUser.addSeries(this.mTotalItensPerUser);

        this._graphItemsTimeBought.getViewport().setScrollable(true);
        this._graphItemsPerUser.getViewport().setScrollable(true);

        this._graphItemsTimeBought.getLegendRenderer().setVisible(true);
        this._graphItemsPerUser.getLegendRenderer().setVisible(true);

        this._graphItemsTimeBought.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        this._graphItemsPerUser.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        this._graphItemsTimeBought.getViewport().setMinX(0);
        this._graphItemsPerUser.getViewport().setMinX(0);

        this._graphItemsTimeBought.getViewport().setMinY(0);
        this._graphItemsPerUser.getViewport().setMinY(0);
    }

    private void setEvents(){

        this._graphItemsTimeBought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("uee", "click");
                if (_graphItemsTimeBought.getLegendRenderer().isVisible())
                    _graphItemsTimeBought.getLegendRenderer().setVisible(false);
                else
                    _graphItemsTimeBought.getLegendRenderer().setVisible(true);
            }
        });

        this._graphItemsPerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (_graphItemsPerUser.getLegendRenderer().isVisible())
                    _graphItemsPerUser.getLegendRenderer().setVisible(false);
                else
                    _graphItemsPerUser.getLegendRenderer().setVisible(true);
            }
        });
    }

    private void initialize() {
        //Local Variables
        Item_TO topBuyedItem = null;
        int topTimes = 0;
        String topItemID = null;
        int totalItensAmount = 0;
        int indexItemTimesBought = 1;
        //ArrayList<DataPoint> dpItemTimesBought = new ArrayList<>();
        ArrayList<BarGraphSeries<DataPoint>> seriesItemTimesBought = new ArrayList<>();

        User_TO topBuyerUser = null;
        int topTimesBought = 0;
        double totalSpent = 0;
        String topUserID = null;
        int indexItemPerUser = 0;
        //ArrayList<DataPoint> dpItemPerUser = new ArrayList<>();
        ArrayList<BarGraphSeries<DataPoint>> seriesItemPerUser = new ArrayList<>();

        if (itemStatisticList != null && itemStatisticList.size() > 0){

            for (ItemStatistic_TO itemStatistic : itemStatisticList){

                if (itemStatistic.getTimes_buyed() > topTimes){
                    topTimes = itemStatistic.getTimes_buyed();
                    topItemID = itemStatistic.getItem_id();
                }
                BarGraphSeries<DataPoint> barGraphSeries =
                        new BarGraphSeries<>(new DataPoint[]
                                {new DataPoint(indexItemTimesBought, itemStatistic.getTimes_buyed())});
                barGraphSeries.setSpacing(50);
                barGraphSeries.setDrawValuesOnTop(true);
                barGraphSeries.setValuesOnTopColor(Color.RED);
                barGraphSeries.setTitle(((ListFragment)mParent).getItemName(itemStatistic.getItem_id()));
                barGraphSeries.setDataWidth(2);
                barGraphSeries.setColor(Color.rgb(indexItemTimesBought*255/4, Math.abs(itemStatistic.getTimes_buyed()*255/6), 100));

                //dpItemTimesBought.add(new DataPoint(indexItemTimesBought, itemStatistic.getTimes_buyed()));
                seriesItemTimesBought.add(barGraphSeries);
                indexItemTimesBought++;
                totalItensAmount += itemStatistic.getTotalAmount();
            }
        }

        if (userStatisticList != null && userStatisticList.size() > 0){

            for (UserStatistic_TO userStatistic : userStatisticList){

                if (userStatistic.getTimesUserBought() > topTimesBought){
                    topTimesBought = userStatistic.getTimesUserBought();
                    topUserID = userStatistic.getUser_id();
                }
                BarGraphSeries<DataPoint> barGraphSeries =
                        new BarGraphSeries<>(new DataPoint[]
                                {new DataPoint(indexItemPerUser, userStatistic.getTimesUserBought())});
                //barGraphSeries.setSpacing(50);
                barGraphSeries.setDrawValuesOnTop(true);
                barGraphSeries.setValuesOnTopColor(Color.RED);
                try {
                    barGraphSeries.setTitle(UserRepository.getRepository().loadById(userStatistic.getUser_id()).getUser_name()); // todo change
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                barGraphSeries.setDataWidth(2);
                barGraphSeries.setColor(Color.rgb(indexItemPerUser*255/4, Math.abs(userStatistic.getTimesUserBought()*255/6), 100));

                //dpItemPerUser.add(new DataPoint(indexItemPerUser, userStatistic.getTimesUserBought()));
                seriesItemPerUser.add(barGraphSeries);
                indexItemPerUser++;
                totalSpent += userStatistic.getTotalUserSpent();
            }

        }

        if (listStatisticList != null && listStatisticList.size() > 0){

        }

        try {
            topBuyedItem = ItemRepository.getRepository().loadById(topItemID);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (topBuyedItem != null)
            this._promptTopSelling.setText(topBuyedItem.getName());
        this._promptTotalBuyed.setText(Integer.toString(totalItensAmount));

        try {
            topBuyerUser = UserRepository.getRepository().loadById(topUserID);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (topBuyerUser != null)
            this._promptTopBuyer.setText(topBuyerUser.getUser_name());
        this._promptTotalSpent.setText(Double.toString(totalSpent));


        for (BarGraphSeries series : seriesItemTimesBought)
            this._graphItemsTimeBought.addSeries(series);

        for (BarGraphSeries series : seriesItemPerUser)
            this._graphItemsPerUser.addSeries(series);

        setUI();
        setEvents();
        dimissProgressDialog();
    }

    private class LoadStatisticTask extends AsyncTask<String, Void, List<ParseEntity_TO>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<ParseEntity_TO> doInBackground(String... strings) {
            // Local Variable
            String list_id = strings[0];
            List<ParseEntity_TO> list = new ArrayList<>();

            try {
                list.addAll(ItemStatisticRepository.getRepository().loadByListId(list_id));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                list.addAll(UserStatisticRepository.getRepository().loadByListId(list_id));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                list.addAll(ListStatisticRepository.getRepository().loadByListId(list_id));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<ParseEntity_TO> parseEntityList) {
            super.onPostExecute(parseEntityList);

            for (ParseEntity_TO entity : parseEntityList){

                if (entity instanceof ItemStatistic_TO)
                    itemStatisticList.add((ItemStatistic_TO) entity);
                else if (entity instanceof UserStatistic_TO)
                    userStatisticList.add((UserStatistic_TO) entity);
                else
                    listStatisticList.add((ListStatistic_TO) entity);
            }

            initialize();
        }
    }
}
