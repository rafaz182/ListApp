package br.edu.ifsp.lab11.listapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by r0xxFFFF-PC on 10/06/2017.
 */

public class Utils {

    public static void setDynamicHeight(ListView mListView) {
        // Local Variables
        int height = 0;
        int desiredWidth = 0;
        ListAdapter mListAdapter = mListView.getAdapter();
        ViewGroup.LayoutParams params = null;
        View listItem = null;

        if (mListAdapter == null) {

            return;
        }

        desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);

        for (int i = 0; i < mListAdapter.getCount(); i++) {
            listItem = mListAdapter.getView(i, null, mListView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }

        params = mListView.getLayoutParams();

        if(params == null)
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);

        params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));

        mListView.setLayoutParams(params);
        mListView.requestLayout();
    }

    public static View inflateFromLayoutId(Context context, int layoutId){
        //Local Variables
        LayoutInflater inflater = null;
        View view = null;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(layoutId, null);

        return view;
    }
}

