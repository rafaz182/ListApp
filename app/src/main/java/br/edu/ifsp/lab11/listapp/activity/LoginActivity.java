package br.edu.ifsp.lab11.listapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.lab11.listapp.App;
import br.edu.ifsp.lab11.listapp.R;
import br.edu.ifsp.lab11.listapp.domain.Group_TO;
import br.edu.ifsp.lab11.listapp.domain.List_TO;
import br.edu.ifsp.lab11.listapp.domain.User_TO;
import br.edu.ifsp.lab11.listapp.repository.GroupRepository;
import br.edu.ifsp.lab11.listapp.repository.ListRepository;
import br.edu.ifsp.lab11.listapp.repository.UserRepository;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.championswimmer.libsocialbuttons.fabs.FABFacebook;
import in.championswimmer.libsocialbuttons.fabs.FABGoogleplus;

/**
 * Created by r0xxFFFF-PC on 14/04/2017.
 *
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.input_email)
    EditText _inputEmail = null;

    @BindView(R.id.input_password)
    EditText _inputPassword = null;

    @BindView(R.id.link_signup)
    TextView _linkSingUp = null;

    @BindView(R.id.action_login)
    Button _actionLogin = null;

    @BindView(R.id.action_loginFacebook)
    FABFacebook _actionLoginFacebook = null;

    @BindView(R.id.action_loginGoogleplus)
    FABGoogleplus _actionLoginGooglePlus;

    private ProgressDialog mProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //Log.d("uee", App.getApplication().getCurrentSession().getUserName());
        //Log.d("uee", App.getApplication().getCurrentSession().getUserEmail());

        this.setUI();
        this.setEvents();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    /**
     * Validate all fields in the form
     *
     * @return True if match the requirements
     */
    private boolean validate() {
        boolean valid = true;

        String email = this._inputEmail.getText().toString();
        String password = this._inputPassword.getText().toString();

        if (email.isEmpty()) {
            this._inputEmail.setError(getString(R.string.error_field_required));
            valid = false;
        } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this._inputEmail.setError(getString(R.string.error_invalid_email));
            valid = false;
        }else {
            this._inputEmail.setError(null);
        }

        if (password.isEmpty()) {
            this._inputPassword.setError(getString(R.string.error_field_required));
            valid = false;
        } else {
            this._inputPassword.setError(null);
        }

        return valid;
    }

    /**
     * Configure view elements of this activity
     */
    private void setUI(){
        this.getSupportActionBar().hide();

        this.mProgressDialog = new ProgressDialog(this, R.style.ListAppProgressDialogTheme);
        this.mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.mProgressDialog.setMessage(getString(R.string.prompt_loading_data));
        this.mProgressDialog.setCancelable(false);
    }

    /**
     * Configure events to views
     */
    private void setEvents(){
        //Local Variables
        final Intent argsRegisterActivity = new Intent(this, RegisterActivity.class);

        this._actionLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()) {
                    String email = _inputEmail.getText().toString();
                    String password = _inputPassword.getText().toString();
                    if (App.getApplication().isConnected(getApplicationContext())) {
                        mProgressDialog.show();
                        new UserLoginTask(LoginActivity.this).execute(email, password);
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.error_no_connection, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        this._linkSingUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(argsRegisterActivity);
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
     * Async task who fetch data from the sever matching the params
     */
    private class UserLoginTask extends AsyncTask<String, Void, Bundle> {

        /**
         * Parent of this task
         */
        private LoginActivity mParent = null;

        public UserLoginTask(LoginActivity mParent) {
            this.mParent = mParent;
        }

        @Override
        protected Bundle doInBackground(String... params) {
            //Local Variables
            Bundle args = new Bundle();
            User_TO loggedUser = null;
            ArrayList<List_TO> usersListList = new ArrayList<>();
            ArrayList<Group_TO> usersGroupList = new ArrayList<>();

            try {
                loggedUser = UserRepository.getRepository().login(params[0], params[1]);

                if (loggedUser != null && !loggedUser.getObjectId().isEmpty()){
                    App.getApplication().setCurrentSession(loggedUser);
                    String objectID = loggedUser.getObjectId();

                    usersListList.addAll(ListRepository.getRepository().loadAllByOwnerID(objectID, false));
                    usersGroupList.addAll(GroupRepository.getRepository().loadAllByUserID(objectID));

                    if(!usersGroupList.isEmpty()){
                        List<String> listIDs = new ArrayList<>();

                        for (Group_TO group : usersGroupList)
                            listIDs.add(group.getList_id());

                        usersListList.addAll(ListRepository.getRepository().loadByArrayOfIDs(listIDs, false)); // TODO verificar se tras uma lista de listas
                    }
                } else return null;
            } catch (ParseException e) {
                Log.e("ParseException", e.getMessage());
            }

            args.putParcelableArrayList(List_TO.TAG_NAME, usersListList);
            //args.putSerializable(Group_TO.TAG_NAME, usersGroupList);

            return args;
        }

        @Override
        protected void onPostExecute(Bundle args) {
            super.onPostExecute(args);

            if (App.getApplication().getCurrentSession().isLoggedIn()){
                mParent.dismissProgressDialog();
                this.mParent.startActivity(
                        new Intent(this.mParent, MainActivity.class)
                                .putExtras(args)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                );
                finish();
            } else {
                Toast.makeText(mParent, R.string.error_user_not_found, Toast.LENGTH_LONG).show();
                mParent.dismissProgressDialog();
            }
        }
    }

}

