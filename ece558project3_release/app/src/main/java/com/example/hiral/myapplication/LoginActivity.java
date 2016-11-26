package com.example.hiral.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Created by Hiral on 7/28/2016.
 */
/**
 * A login screen that offers login via email/password.
 * Provides Functionality to Signup for new account
 */
public class LoginActivity extends AppCompatActivity {

    // UI references
    private EditText mEmail;
    private EditText mPasswordView;
    Button mSignInButton;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Creates object for LoginDataBaseAdapter to gain access to database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        mEmail = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        //OnClick Listener for SignInButton
        Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button mSignUpButton = (Button) findViewById(R.id.email_sign_up_button);


        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                // Code below gets the User name and Password
                String userName = mEmail.getText().toString();
                String password = mPasswordView.getText().toString();

                /**TODO: Code here to fetch the Password from database for respective user name
                  *TODO: Code here to check if the password entered matches with the database entry
                  *TODO: if password matches navigate to LocationUpdate Activity using intent
                  *TODO: if password does not match show a toast "username or password does not match"
                  */
                String correctPwd = loginDataBaseAdapter.getSingleEntry(userName);

                if(password.equals(correctPwd)){
                    //Intent i  = new Intent(LoginActivity.this, LocationUpdate.class);
                    Intent i = LocationUpdate.newIntent(LoginActivity.this, userName);
                    startActivity(i);
                }
                else{
                    Toast.makeText(LoginActivity.this,
                            "username or password does not match",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSignUpButton.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view){
                Intent i = new Intent(LoginActivity.this, SignUPActivity.class);
                startActivity(i);
            }

        });
        /** TODO: Code here to create onclick listener for SignUp Button
         *  TODO: OnClick event for SignUp Button Navigates to SignUpActivity
         */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }
}