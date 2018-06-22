package com.ttcnpm.group28.weatherapp.quochoang;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ttcnpm.group28.weatherapp.R;

public class ActivityAboutApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        getSupportActionBar().hide();
    }

    public void BrowserFacebook(View view) {
        Intent browserFacebook = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/1810876459205063/"));
        startActivity(browserFacebook);
    }

    public void BrowserGitlab(View view) {
        Intent browserGitlab = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gitlab.com/thuanle/ttcnpm-172-28"));
        startActivity(browserGitlab);
    }

    public void BrowserGooglePlus(View view) {
        Intent browserGooglePlus = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.vn/"));
        startActivity(browserGooglePlus);
    }
    public void BrowserGoogleMail(View view) {
        Intent browserGoogleMail = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/"));
        startActivity(browserGoogleMail);
    }

    public void backToSettingButton(View view) {
        finish();
    }
}
