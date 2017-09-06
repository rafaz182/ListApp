package br.edu.ifsp.lab11.listapp.activity.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.edu.ifsp.lab11.listapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by r0xxFFFF-PC on 20/05/2017.
 */

public class CreateListFragment extends DialogFragment {

    public static final String TAG_NAME = "CreateListFragment";

    @BindView(R.id.input_listName)
    TextView _inputListName = null;

    @BindView(R.id.action_negativeButton)
    Button _actionNegativeButton = null;

    @BindView(R.id.action_positiveButton)
    Button _actionPositiveButton = null;

    private CreateListDialogCallbacks mCallbacks = null;

    public CreateListFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallbacks = (CreateListDialogCallbacks) getActivity();
        } catch (ClassCastException e) {
            Log.e("ClassCastException", e.getMessage());
            throw new ClassCastException("Activity must implement CreateListDialogCallbacks.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Local Variable
        View view = inflater.inflate(R.layout.fragment_create_list, container, false);
        ButterKnife.bind(this, view);

        this.setEvents();

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        this._inputListName.setText("");
    }

    /**
     * Validate all fields in the form
     *
     * @return True if match the requirements
     */
    private boolean validate(){
        boolean valid = true;

        String listName = this._inputListName.getText().toString();

        if (listName.isEmpty()) {
            this._inputListName.setError(getString(R.string.error_field_required));
            valid = false;
        }
        else
            this._inputListName.setError(null);

        return valid;
    }

    private void setEvents(){

        this._actionNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        this._actionPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    mCallbacks.onCreateListConfirm(_inputListName.getText().toString());
                    _inputListName.setText("");
                    dismiss();
                }
            }
        });
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public interface CreateListDialogCallbacks{

        /**
         * Called when the confirm button is clicked.
         */
        void onCreateListConfirm(String listName);

    }
}
