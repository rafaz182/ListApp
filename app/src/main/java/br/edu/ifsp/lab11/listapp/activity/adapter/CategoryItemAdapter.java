package br.edu.ifsp.lab11.listapp.activity.adapter;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.edu.ifsp.lab11.listapp.R;
import br.edu.ifsp.lab11.listapp.Utils;
import br.edu.ifsp.lab11.listapp.activity.fragment.EditItemFragment;
import br.edu.ifsp.lab11.listapp.activity.fragment.ListFragment;
import br.edu.ifsp.lab11.listapp.domain.ItemStatistic_TO;
import br.edu.ifsp.lab11.listapp.domain.Item_TO;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by r0xxFFFF-PC on 15/05/2017.
 *
 *
 */
public class CategoryItemAdapter extends BaseAdapter {

    private Fragment mParent = null;

    private ArrayList<Item_TO> mItemList = null;

    private EditItemFragment mEditItemFragment = null;

    public CategoryItemAdapter(Fragment parent, ArrayList<Item_TO> itens) {

        this.mParent = parent;
        this.mItemList = itens;
    }

    @Override
    public int getCount() {
        return this.mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Local Variables
        View view = null;
        CategoryItemHolder holder = null;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        //DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
        //NumberFormat numberFormat = NumberFormat.getInstance();

        if (convertView == null || convertView.getTag() == null) {
            view = Utils.inflateFromLayoutId(this.mParent.getContext(), R.layout.item_layout);
            holder = new CategoryItemHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (CategoryItemHolder) view.getTag(); // getting null
        }

        Item_TO item = (Item_TO) getItem(position);
        holder.mCurrentPosition = position;
        holder._inputBuyState.setChecked(item.isBought());
        holder._promptProductName.setText(item.getName());
        holder._promptItemAmount.setText("("+Integer.toString(item.getAmount())+")");
        holder._promptTotalPrice.setText(decimalFormat.format(item.getPrice()).replace('.', ','));
        holder._promptItemDescription.setText(item.getDescription());

        if (holder._promptItemDescription.getText().toString().isEmpty())
            holder._promptItemSeparator2.setVisibility(View.INVISIBLE);
        else
            holder._promptItemSeparator2.setVisibility(View.VISIBLE);

        return view;
    }

    public void addItem(Item_TO item){

        if (item != null)
            this.mItemList.add(item);
        else {
            Log.e("NullPointerException", "Cannot add an empty or null item to list.");
            throw new NullPointerException("Cannot add an empty or null item to list.");
        }
    }

    public void removeItem(Item_TO item){
        if (item != null) {
            this.mItemList.remove(item);
            //notifyDataSetChanged();
        }else {
            Log.e("NullPointerException", "Cannot remove an empty or null item to list.");
            throw new NullPointerException("Cannot remove an empty or null item to list.");
        }
    }

    /**
     * Viewholder pattern class for this personalized component
     */
    public class CategoryItemHolder {

        @BindView(R.id.input_buyState)
        CheckBox _inputBuyState = null;

        @BindView(R.id.prompt_productName)
        TextView _promptProductName = null;

        @BindView(R.id.prompt_itemAmount)
        TextView _promptItemAmount = null;

        @BindView(R.id.prompt_itemDescription)
        TextView _promptItemDescription = null;

        @BindView(R.id.prompt_totalPrice)
        TextView _promptTotalPrice = null;

        @BindView(R.id.prompt_itemSeparator2)
        TextView _promptItemSeparator2 = null;

        @BindView(R.id.action_editItem)
        ImageView _actionEditItem = null;

        int mCurrentPosition = -1;

        public CategoryItemHolder(View view) {
            ButterKnife.bind(this, view);

            this.setEvents();
        }

        /**
         * Set the events of component
         */
        private void setEvents(){

            this._inputBuyState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //Local Variable
                    Item_TO item = null;

                    if ( isChecked ) {

                        item = mItemList.get(mCurrentPosition);
                        _inputBuyState.setEnabled(false);

                        try {
                            ((CategoryItemAdapterCallback) mParent).onBuyStateChange(item);
                        }catch (ClassCastException e){
                            Log.e("ClassCastException", e.getMessage());
                            throw new ClassCastException("Parent must implement CategoryItemAdapterCallback.");
                        }

                    } else {
                        _inputBuyState.setEnabled(true);
                    }
                }
            });

            this._actionEditItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //TODO deixar coesa (remover daqui, passar para um callback no parent)
                    mEditItemFragment = EditItemFragment.newInstance(mItemList.get(mCurrentPosition));
                    mEditItemFragment.setTargetFragment(mParent, 300);
                    mEditItemFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CreateListFragmentTheme);
                    mEditItemFragment.show(mParent.getChildFragmentManager(), EditItemFragment.TAG_NAME);
                }
            });
        }
    }

    /**
     *
     */
    public interface CategoryItemAdapterCallback{

        void onBuyStateChange(Item_TO item);
    }
}
