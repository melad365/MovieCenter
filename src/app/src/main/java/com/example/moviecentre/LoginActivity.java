package com.example.moviecentre;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    public static boolean someTest() {
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);


    }



    private void attemptLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.error_field_required));
            editTextEmail.requestFocus();
            return;
        }
        if(!isEmailValid(email)){
            editTextEmail.setError(getString(R.string.error_invalid_email));
        }
        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.error_field_required));
            editTextPassword.requestFocus();
            return;
        }
        if(!isPasswordValid(password) ){
            editTextPassword.setError(getString(R.string.error_invalid_password));
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public static boolean isEmailValid(String email){

        //System.out.println("email is ********* " + email);
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return false;
        else
            return true;
    }


    public static boolean isPasswordValid(String password) {
        return password.length() > 5;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewSignup:

                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.email_sign_in_button:
                attemptLogin();
                //startActivity(new Intent(this, HomeActivity.class));
        }
    }



}

