//public class LoginActivity {
//}

package com.example.echo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

//import com.example.echo.MainActivity;
//import com.example.echo.R;
//import com.parse.Parse;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick Login Button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the ParseUser
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                ParseUser user = new ParseUser();           //Creates a new user

                //We are setting a new username and password for the Parse user.
                user.setUsername(username);
                user.setPassword(password);

                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e != null) {        //If there is an exception, there is a problem with the sign up.
                            Toast.makeText(LoginActivity.this, "Issue with sign up!", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            loginUser(username, password);
                            Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user" +username);
        //TODO: Login to the main activity after the user has signed in properly.

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e!=null){
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, "Issue with login!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //TODO: navigate to the main activity if the user has signed in properly.
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
