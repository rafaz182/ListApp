package br.edu.ifsp.lab11.listapp.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;

import br.edu.ifsp.lab11.listapp.App;
import br.edu.ifsp.lab11.listapp.R;
import br.edu.ifsp.lab11.listapp.domain.User_TO;
import br.edu.ifsp.lab11.listapp.repository.UserRepository;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by r0xxFFFF-PC on 30/04/2017.
 *
 * A screen for user registration
 */
public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.input_name)
    EditText _inputName;

    @BindView(R.id.input_email)
    EditText _inputEmail;

    @BindView(R.id.input_password)
    EditText _inputPassword;

    @BindView(R.id.input_confirmPassword)
    EditText _inputConfirmPassowrd;

    @BindView(R.id.btn_register)
    Button _btnRegister;

    private ProgressDialog mProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        this.setUI();
        this.setEvents();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //super.onSaveInstanceState(outState, outPersistentState); // BUG Android API
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    /**
     * Validate all fields in the form
     *
     * @return True if match the requirements
     */
    private boolean validate(){
        //Local Variables
        boolean valid = true;
        String name = this._inputName.getText().toString();
        String email = this._inputEmail.getText().toString();
        String password = this._inputPassword.getText().toString();
        String confirmPassword = this._inputConfirmPassowrd.getText().toString();

        if(name.isEmpty()){
            this._inputName.setError(getString(R.string.error_field_required));
            valid = false;
        }else {
            this._inputName.setError(null);
        }

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

        if (confirmPassword.isEmpty()){
            this._inputConfirmPassowrd.setError(getString(R.string.error_field_required));
            valid = false;
        } else if (!confirmPassword.equals(password)){
            this._inputConfirmPassowrd.setError(getString(R.string.error_doesnt_match));
            valid = false;
        }else {
            this._inputConfirmPassowrd.setError(null);
        }

        return valid;
    }

    /**
     * Gets the values of the fields and create a user instance
     *
     * @return - A instance of user
     */
    private User_TO createUser(){
        //Local Variables
        User_TO userTO = new User_TO();
        String name = this._inputName.getText().toString();
        String password = this._inputPassword.getText().toString();
        String email = this._inputEmail.getText().toString();

        userTO.setUser_name(name);
        userTO.setEmail(email);
        userTO.setPassword(password);
        userTO.setEmailVerified(false);

        return userTO;
    }

    /**
     * Configure view elements of this activity
     */
    private void setUI(){
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle(R.string.prompt_createAccount);

        this.mProgressDialog = new ProgressDialog(this, R.style.ListAppProgressDialogTheme);
        this.mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.mProgressDialog.setMessage(getString(R.string.prompt_creating_account));
        this.mProgressDialog.setCancelable(false);
        //this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.amber, null)));
    }

    /**
     * Configure events to views
     */
    private void setEvents(){

        this._btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Local Variables
                User_TO userTO = null;

                if (validate()){
                    userTO = createUser();

                    mProgressDialog.show();
                    new RegisterUserTask(RegisterActivity.this).execute(userTO);

                    if (App.getApplication().isConnected(getApplicationContext())) {
                        mProgressDialog.show();
                        new RegisterUserTask(RegisterActivity.this).execute(userTO);
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.error_no_connection, Toast.LENGTH_LONG).show();
                    }
                }
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
     * Asynchronous task which verify if already have a user with this email, if doesn't, create a
     * new User
     */
    private class RegisterUserTask extends AsyncTask<User_TO, Void, Boolean>{

        private User_TO mNewUser = null;

        private RegisterActivity mParent = null;

        public RegisterUserTask(RegisterActivity mParent) {
            this.mParent = mParent;
        }

        @Override
        protected Boolean doInBackground(User_TO... params) {
            //Local Variables
            User_TO userExist = null;

            this.mNewUser = params[0];

            try {
                userExist = UserRepository.getRepository().loadByEmail(this.mNewUser.getEmail());
            } catch (ParseException e) {
                Log.e("ParseException", e.getMessage());
            }

            if (userExist == null){
                return true;
            } else{
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean){
                this.mParent.dismissProgressDialog();
                try {
                    UserRepository.getRepository().save(this.mNewUser);
                } catch (ParseException e) {
                    Log.e("ParseException", e.getMessage());
                }
                Toast.makeText(getApplicationContext(), R.string.prompt_account_created, Toast.LENGTH_LONG).show();
                this.mParent.dismissProgressDialog();
                this.mParent.onBackPressed();
            }else {
                this.mParent.dismissProgressDialog();
                _inputEmail.setError(getString(R.string.error_email_aready_exist));
            }
        }

    }

}
