package com.facebooktest.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebooktest.BaseApplication;
import com.facebooktest.interfaces.AuthenticationListener;

public abstract class BaseActivity extends ActionBarActivity implements AuthenticationListener
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        BaseApplication.getFacebookSessionHandler().register(this);
        mFacebookUiHelper = new UiLifecycleHelper(this, BaseApplication.getFacebookSessionHandler());
        mFacebookUiHelper.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        BaseApplication.activityPaused();
        mFacebookUiHelper.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        BaseApplication.activityResumed();
        mFacebookUiHelper.onResume();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        BaseApplication.getFacebookSessionHandler().unregister(this);
        mFacebookUiHelper.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mFacebookUiHelper.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookUiHelper.onActivityResult(requestCode, resultCode, data);
    }

    /*-************************************************************************************
            Facebook login
    **************************************************************************************/
    private UiLifecycleHelper mFacebookUiHelper;

    @Override
    public void onAuthenticationFailed()
    {
        startActivity(LoginActivity.getSplashIntent(this));
        finish();
    }

    @Override
    public void onAuthenticationSuccessful() {}

    protected Session getFacebookSession()
    {
        return Session.getActiveSession();
    }
}
