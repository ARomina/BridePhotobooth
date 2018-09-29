package com.example.bridephotobooth;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Romina on 08/09/2018.
 */

@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity{

    @ViewById
    Button mButtonSplash;

    @AfterViews
    void onFirstLoad(){

    }

    @Click(R.id.mButtonSplash)
    void onClickButton(){
        MainActivity_.intent(this)
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .start();
    }
}
