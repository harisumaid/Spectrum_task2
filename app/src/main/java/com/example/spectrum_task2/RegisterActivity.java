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
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    String email;
    String password;
    private FirebaseAuth mAuth;
    ContentLoadingProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        progressBar=findViewById(R.id.loading_register);
    }

    public void clickRegister(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        
        final EditText emailEditText = findViewById(R.id.email_editText_register);
        email = emailEditText.getText().toString();
        final EditText passwordEditText = findViewById(R.id.password_editText_register);
        password = passwordEditText.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(getApplicationContext(), "Password or email can't be empty", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d("success","successfully created a user");
                        onRegisterationComplete();
                    } else {
                        Log.w("failed","task failed",task.getException());
                        emailEditText.setError(task.getException().getMessage());
                        passwordEditText.setError(task.getException().getMessage());
                        progressBar.hide();
                    }
                }
            });
        }


    }

    private void onRegisterationComplete() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
