package br.edu.ifsp.lab11.listapp.activity.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.ifsp.lab11.listapp.R;
import br.edu.ifsp.lab11.listapp.domain.Item_TO;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by r0xxFFFF-PC on 30/05/2017.
 */

public class EditItemFragment extends DialogFragment {

    public static final String TAG_NAME = "EditItemFragmentListener";

    private static final String LISTENER = "EditItemFragmentListener";

    @BindView(R.id.input_item_name)
    TextView _inputItemName = null;

    @BindView(R.id.input_item_description)
    TextView _inputItemDescription = null;

    @BindView(R.id.input_item_category)
    AutoCompleteTextView _inputItemCategory = null;

    @BindView(R.id.input_item_price)
    TextView _inputItemPrice = null;

    @BindView(R.id.input_item_amount)
    TextView _inputItemAmount = null;

    @BindView(R.id.action_close_button)
    Button _actionCloseButton = null;

    @BindView(R.id.action_ok_button)
    Button _actionOkButton = null;

    @BindView(R.id.action_delete_item)
    ImageView _actionDeleteItem = null;

    private Item_TO mCurrentItem = null;

    private String[] mDefaultCategories= null;

    private EditItemFragmentListener mListener = null;

    private ExcludeItemFragment mExcludeItemFragment = null;

    public EditItemFragment() {}

    public static EditItemFragment newInstance(Item_TO itemTo){
        //Local Variables
        EditItemFragment newInstance = new EditItemFragment();
        Bundle args = new Bundle();

        args.putParcelable(Item_TO.TAG_NAME, itemTo);
        newInstance.setArguments(args);

        return newInstance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.mCurrentItem = getArguments().getParcelable(Item_TO.TAG_NAME);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.mListener = (EditItemFragmentListener) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e("ClassCastException", e.getMessage());
            throw new ClassCastException("Fragment must implement EditItemFragmentListener.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Local Variables
        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);
        ButterKnife.bind(this, view);

        this.mDefaultCategories = getResources().getStringArray(R.array.default_category);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.setUI();
        this.setEvents();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        this._inputItemName.setText("");
        this._inputItemDescription.setText("");
        this._inputItemCategory.setText("");
        this._inputItemPrice.setText("");
        this._inputItemAmount.setText("");
    }

    /**
     * Configure view elements of this activity
     */
    private void setUI(){
        //Local Variables
        ArrayAdapter<String> defaultCategories = null;

        defaultCategories = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, this.mDefaultCategories);

        this._inputItemCategory.setAdapter(defaultCategories);

        this._inputItemName.setText(this.mCurrentItem.getName());
        this._inputItemDescription.setText(this.mCurrentItem.getDescription());
        this._inputItemCategory.setText(this.mCurrentItem.getCategory());
        this._inputItemPrice.setText(String.valueOf(this.mCurrentItem.getPrice()));
        this._inputItemAmount.setText(String.valueOf(this.mCurrentItem.getAmount()));
    }

    /**
     * Configure events to views
     */
    private void setEvents(){

        this._actionOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Local Variables
                String oldCategory = mCurrentItem.getCategory();

                mCurrentItem.setName(_inputItemName.getText().toString());
                mCurrentItem.setDescription(_inputItemDescription.getText().toString());
                mCurrentItem.setCategory(_inputItemCategory.getText().toString());
                mCurrentItem.setPrice(Double.valueOf(_inputItemPrice.getText().toString()));
                mCurrentItem.setAmount(Integer.valueOf(_inputItemAmount.getText().toString()));

                mListener.onOkClick(mCurrentItem, oldCategory);

                dismiss();
            }
        });

        this._actionCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        this._actionDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mExcludeItemFragment = ExcludeItemFragment.newInstance(mCurrentItem);
                mExcludeItemFragment.show(getChildFragmentManager(), EditItemFragment.TAG_NAME);
            }
        });
    }

    /**
     *
     */
    public interface EditItemFragmentListener {

        void onOkClick(Item_TO item, String oldCategory);
    }
}
