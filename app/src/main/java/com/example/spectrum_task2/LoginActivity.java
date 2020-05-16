package com.example.spectrum_task2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    String email;
    String password;
    private FirebaseAuth mAuth;
    TextView warningLogin;
    ContentLoadingProgressBar progressBar;
    Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        submit = findViewById(R.id.activity_submit_button_login);
        getSupportActionBar().hide();
        progressBar = findViewById(R.id.loading_login);
        progressBar.hide();
    }

    public void clickLogin(View view) {
        submit.setClickable(false);
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        final EditText emailEditText = findViewById(R.id.email_editText_login);
        email = emailEditText.getText().toString();
        final EditText passwordEditText = findViewById(R.id.password_editText_login);
        password = passwordEditText.getText().toString();

        if(email.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "Password or email can't be empty", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d("success","Login Successful");
                        onLoginComplete();
                    } else {
                        Log.w("failed",task.getException().getMessage() , task.getException());
                        emailEditText.setError(task.getException().getMessage());
                        passwordEditText.setError(task.getException().getMessage());
                        progressBar.hide();
                    }
                }
            });
        }
        submit.setClickable(true);
    }

    private void onLoginComplete() {
        finish();
        startActivity(new Intent(this,WelcomeActivity.class));
    }

    public void toRegister(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    public void resetPassword(View view) {
        startActivity(new Intent(this,ResetPasswordActivity.class));

    }
}
