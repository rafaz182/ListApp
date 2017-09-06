package br.edu.ifsp.lab11.listapp.activity.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.parse.ParseException;

import br.edu.ifsp.lab11.listapp.R;
import br.edu.ifsp.lab11.listapp.domain.Item_TO;
import br.edu.ifsp.lab11.listapp.repository.ItemRepository;

/**
 * Created by Rafael on 07/06/2017.
 */

public class ExcludeItemFragment extends DialogFragment {

    public static final String TAG = "ExcludeItemFragment";

    /**
     *
     */
    private Item_TO mItem = null;

    public ExcludeItemFragment() {}

    public static ExcludeItemFragment newInstance(Item_TO item) {
        //Local Variables
        Bundle args = new Bundle();
        ExcludeItemFragment fragment = new ExcludeItemFragment();

        args.putParcelable(Item_TO.TAG_NAME, item);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.mItem = getArguments().getParcelable(Item_TO.TAG_NAME);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        //Local Variables
        //AlertDialog dialog = null;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.CreateListFragmentTheme);

        alertDialogBuilder.setTitle(getString(R.string.prompt_exclude_item_header));
        alertDialogBuilder.setMessage(String.format(getString(R.string.prompt_exclude_message), this.mItem.getName()));
        alertDialogBuilder.setPositiveButton(getString(R.string.action_ok),  new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    ItemRepository.getRepository().delete(mItem); // TODO Colocar asyncTask
                    mItem = null; //TODO Verificar se remove das listas
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.action_close), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (dialog != null ) {

                    dialog.dismiss();
                }
            }
        });

        return alertDialogBuilder.create();
    }
}
