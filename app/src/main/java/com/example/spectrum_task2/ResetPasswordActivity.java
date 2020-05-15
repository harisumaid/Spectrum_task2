package com.example.spectrum_task2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
    }

    public void resetPassword(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        final EditText emailEditText = findViewById(R.id.reset_email_editText);
        final String email = emailEditText.getText().toString();
        if(!email.isEmpty()) {
            progressBar = findViewById(R.id.loading_reset);
            progressBar.show();
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressBar.hide();
                                Log.d("reset", "Email sent.");
                                Toast.makeText(ResetPasswordActivity.this, "Password reset link sent to your email id \n Please check your email", Toast.LENGTH_SHORT).show();
                                getBackToLogin();
                            } else {
                                progressBar.hide();
                                emailEditText.setError(task.getException().getMessage());
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Email Id can't be empty \n Please enter a valid email id", Toast.LENGTH_SHORT).show();
        }



    }

    private void getBackToLogin() {
        finish();
    }
}
