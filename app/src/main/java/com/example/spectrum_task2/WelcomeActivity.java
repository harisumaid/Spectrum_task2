package com.example.spectrum_task2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        TextView pageTitle = findViewById(R.id.page_title_welcome);
        pageTitle.setText("WELCOME");
        getSupportActionBar().hide();
    }

    public void messageClicked(View view) {

        Intent messageIntent = new Intent(Intent.ACTION_MAIN);
        messageIntent.addCategory(Intent.CATEGORY_APP_MESSAGING);
        startActivity(messageIntent);

    }

    public void phoneClicked(View view) {
        Uri number = Uri.parse("tel:7008683249");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void browserClicked(View view) {
        Uri webpage = Uri.parse("https://www.spectrumcet.com");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(webIntent,
                PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
        Log.d("intent",String.valueOf(isIntentSafe)+activities.size() );
        if(isIntentSafe) {
            startActivity(webIntent);
        }
    }

    public void facebookClicked(View view) {

            Uri facebookUri = Uri.parse("https://m.facebook.com/");
            Intent facebookIntent = new Intent("android.intent.category.LAUNCHER",facebookUri);
            facebookIntent.setClassName("com.facebook.katana", "com.facebook.katana.LoginActivity");

            try {
                startActivity(facebookIntent);
            } catch (ActivityNotFoundException e){
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://m.facebook.com")));
            }


    }

    public void instagramClicked(View view) {
        Uri instagramUri = Uri.parse("https://www.instagram.com/");
        Intent instagramIntent = new Intent(Intent.CATEGORY_LAUNCHER,instagramUri);
        instagramIntent.setClassName("com.instagram.android","com.instagram.android.activity.MainTabActivity");
        startActivity(instagramIntent);
    }

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        super.onBackPressed();
        finish();
        startActivity(new Intent(this,LoginActivity.class));
    }
}
