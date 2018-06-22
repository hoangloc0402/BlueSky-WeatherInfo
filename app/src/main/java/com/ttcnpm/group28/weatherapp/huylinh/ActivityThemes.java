package com.ttcnpm.group28.weatherapp.huylinh;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.ttcnpm.group28.weatherapp.R;

public class ActivityThemes extends AppCompatActivity {

    private SharedPreferences shared;
    private ImageButton imageButton;
    public int checkedCard, currentCheck;
    public int newTheme;
    private Activity activity;
    private Button apply;
    private int height, width;

    private static final int CHECKED_COLOR = -1602017;
    private static final int NONCHECKED_COLOR = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_themes);
        shared = getSharedPreferences(Setting.NAME,MODE_PRIVATE);
        init();

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.setTheme(shared,newTheme);
                currentCheck = checkedCard;
                apply.setEnabled(false);
            }
        });
    }

    private void init() {
        apply = (Button)findViewById(R.id.btn_apply);
        int theme = Setting.getThemeSetting(shared);
        switch (theme){
            case Setting.Themes.CITY:
                checkedCard = currentCheck = R.id.cv_city;
                break;
            case Setting.Themes.COUNTRYSIDE:
                checkedCard = currentCheck = R.id.cv_countryside;
                break;
            case Setting.Themes.FOREST:
                checkedCard = currentCheck = R.id.cv_forest;
                break;
            case Setting.Themes.HIGHLAND:
                checkedCard = currentCheck = R.id.cv_highland;
                break;
        }
        changeColorCard(checkedCard);
    }


    public void finishActivity(View v){
        finish();
    }

    private void changeColorCard(int id){
        CardView card = (CardView) findViewById(checkedCard);
        card.setCardBackgroundColor(NONCHECKED_COLOR);
        card = (CardView)findViewById(id);
        card.setCardBackgroundColor(CHECKED_COLOR);
        checkedCard = id;
    }

    public void checkCard(View view){
        switch (view.getId()){
            case R.id.ibtn_city:
                changeColorCard(R.id.cv_city);
                newTheme = Setting.Themes.CITY;
                break;
            case R.id.ibtn_countryside:
                changeColorCard(R.id.cv_countryside);
                newTheme = Setting.Themes.COUNTRYSIDE;
                break;
            case R.id.ibtn_forest:
                changeColorCard(R.id.cv_forest);
                newTheme = Setting.Themes.FOREST;
                break;
            case R.id.ibtn_highland:
                changeColorCard(R.id.cv_highland);
                newTheme = Setting.Themes.HIGHLAND;
                break;
                default:
                    break;
        }
        if(checkedCard!=currentCheck){
            apply.setEnabled(true);
        }
        else{
            apply.setEnabled(false);
        }
    }
}
