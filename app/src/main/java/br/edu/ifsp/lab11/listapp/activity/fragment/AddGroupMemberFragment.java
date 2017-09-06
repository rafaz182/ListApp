package br.edu.ifsp.lab11.listapp.activity.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.lab11.listapp.App;
import br.edu.ifsp.lab11.listapp.R;
import br.edu.ifsp.lab11.listapp.activity.adapter.MemberGroupAdapter;
import br.edu.ifsp.lab11.listapp.domain.Group_TO;
import br.edu.ifsp.lab11.listapp.domain.Item_TO;
import br.edu.ifsp.lab11.listapp.domain.User_TO;
import br.edu.ifsp.lab11.listapp.repository.GroupRepository;
import br.edu.ifsp.lab11.listapp.repository.UserRepository;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by r0xxFFFF-PC on 29/05/2017.
 */

public class AddGroupMemberFragment extends DialogFragment {

    public static final String TAG_NAME = "AddGroupMemberFragment";

    private static final String MEMBERS_LIST = "membersList";

    @BindView(R.id.input_member_email)
    EditText _inputMemberEmail = null;

    @BindView(R.id.list_group_members)
    ListView _listGroupMembers = null;

    @BindView(R.id.action_close_button)
    Button _actionCloseButton = null;

    private ListFragment mParent = null;

    private ArrayList<User_TO> mMembersList = null;

    private MemberGroupAdapter mMemberGroupAdapter = null;

    private ProgressDialog mProgressDialog = null;

    private String mListID = null;

    public AddGroupMemberFragment() {}

    public static AddGroupMemberFragment newInstance(String list_id, ArrayList<User_TO> membersList){
        //Local Variables
        AddGroupMemberFragment newInstance = new AddGroupMemberFragment();
        Bundle args = new Bundle();

        args.putString(Group_TO.LIST_ID, list_id);
        args.putParcelableArrayList(MEMBERS_LIST, membersList);
        newInstance.setArguments(args);

        return newInstance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.mListID = getArguments().getString(Group_TO.LIST_ID);
        this.mMembersList = getArguments().getParcelableArrayList(MEMBERS_LIST);
        this.mProgressDialog = new ProgressDialog(getContext(), R.style.ListAppProgressDialogTheme);
        this.mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.mProgressDialog.setMessage(getString(R.string.prompt_loading_members));
        this.mProgressDialog.setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Local Variable
        View view = inflater.inflate(R.layout.fragment_add_group_memeber, container, false);
        ButterKnife.bind(this, view);

        if (this.mMembersList != null && this.mMembersList.size() > 0){
            mMemberGroupAdapter = new MemberGroupAdapter(getContext(), mMembersList);

            setUI();
            setEvents();
        } else {
            mMembersList = new ArrayList<>();
            mProgressDialog.show();
            new LoadMembersTask().execute(this.mListID);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        this._inputMemberEmail.setText("");
    }

    /**
     * Validate all fields in the form
     *
     * @return True if match the requirements
     */
    private boolean validate() {
        boolean valid = true;

        String email = this._inputMemberEmail.getText().toString();

        if (email.isEmpty()) {
            this._inputMemberEmail.setError(getString(R.string.error_field_required));
            valid = false;
        } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this._inputMemberEmail.setError(getString(R.string.error_invalid_email));
            valid = false;
        } else if (email.equals(App.getApplication().getCurrentSession().getUserEmail())){
            this._inputMemberEmail.setError(getString(R.string.error_cannot_add_yourself));
            valid = false;
        }else {
            this._inputMemberEmail.setError(null);
        }

        return valid;
    }

    /**
     * Configure view elements of this activity
     */
    private void setUI(){

        this._listGroupMembers.setAdapter(this.mMemberGroupAdapter);
    }

    /**
     * Configure events to views
     */
    private void setEvents(){

        this._inputMemberEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!_inputMemberEmail.getText().toString().isEmpty()){
                    _inputMemberEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_menu_done, 0);
                } else {
                    _inputMemberEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this._inputMemberEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (!_inputMemberEmail.getText().toString().isEmpty() &&
                            event.getRawX() >= (_inputMemberEmail.getRight() - _inputMemberEmail.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){

                        if (validate()) {
                            mProgressDialog.setMessage(getString(R.string.prompt_adding_member_list));
                            mProgressDialog.show();
                            new AddMemberTask().execute(_inputMemberEmail.getText().toString());
                            return true;
                        }
                    }
                }

                return false;
            }
        });

        this._actionCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * Callback method for dismiss the dialog.
     */
    private void dismissProgressDialog(){

        this.mProgressDialog.dismiss();
    }

    /**
     * Load all members from list's group
     */
    private class LoadMembersTask extends AsyncTask<String, Void, List<User_TO>>{

        public LoadMembersTask() {
        }

        @Override
        protected List<User_TO> doInBackground(String... params) {
            //Local Variable
            String list_id = params[0];
            List<Group_TO> groupTos = null;
            List<User_TO> userTos = null;

            try {
                groupTos = GroupRepository.getRepository().loadAllByListID(list_id);

                if (groupTos != null && groupTos.size() > 0){
                    ArrayList<String> membersID = new ArrayList<>();

                    for (Group_TO groupTo : groupTos){
                        membersID.add(groupTo.getUser_id());
                    }

                    userTos = UserRepository.getRepository().loadByArrayOfIDs(membersID); //TODO isso deveria ficar acessivel para outras classes/fragments

                    return userTos;
                }
            } catch (ParseException e) {
                Log.e("ParseException", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<User_TO> user_tos) {
            super.onPostExecute(user_tos);

            if (user_tos != null && user_tos.size() > 0){
                mMembersList.addAll(user_tos);
                mMemberGroupAdapter = new MemberGroupAdapter(getContext(), mMembersList);
            }else {
                mMemberGroupAdapter = new MemberGroupAdapter(getContext(), new ArrayList<User_TO>());
            }

            setUI();
            setEvents();
            dismissProgressDialog();

            if (getTargetFragment() instanceof ListFragment)
                ((ListFragment)getTargetFragment()).setMembersList(mMembersList);
        }
    }

    /**
     * Add new member for this list group
     */
    private class AddMemberTask extends AsyncTask<String, Void, Boolean>{

        private User_TO userExist = null;

        public AddMemberTask() {
        }

        @Override
        protected Boolean doInBackground(String... params) {
            //Local Variable
            String userEmail = params[0];
            // TODO verificar se membro ja está na lista
            try {
                this.userExist = UserRepository.getRepository().loadByEmail(userEmail);
            } catch (ParseException e) {
                Log.e("ParseException", e.getMessage());
            }

            if (this.userExist == null)
                return false;
            else
                return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean){
                Group_TO newGroupMember = new Group_TO();
                newGroupMember.setUser_id(this.userExist.getObjectId());
                newGroupMember.setList_id(mListID);

                try {
                    GroupRepository.getRepository().save(newGroupMember);
                } catch (ParseException e) {
                    Log.e("ParseException", e.getMessage());
                }

                mMemberGroupAdapter.addItem(this.userExist);
                mMembersList.add(this.userExist);
                mMemberGroupAdapter.notifyDataSetChanged();
                _inputMemberEmail.setText("");

                if (getTargetFragment() instanceof ListFragment)
                    ((ListFragment)getTargetFragment()).setMembersList(mMembersList);
                dismissProgressDialog();
            }else {
                dismissProgressDialog();
                Toast.makeText(getContext(), R.string.error_user_not_found, Toast.LENGTH_LONG).show();
            }
        }
    }
}
