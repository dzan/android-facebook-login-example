package com.facebooktest.controllers.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;
import com.facebooktest.BaseApplication;
import com.facebooktest.R;
import com.facebooktest.helpers.FacebookSessionHandler;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends BaseActivity
{
    @InjectView(R.id.login_button) LoginButton mFacebookButton;

    public static Intent getSplashIntent(Context context)
    {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // check if we need a login or passthrough
        SharedPreferences prefs = getSharedPreferences(BaseApplication.MY_PREFERENCES, MODE_PRIVATE);
        if (prefs.getBoolean(BaseApplication.PREF_KEY_REGISTERED_BEFORE, false))
        {
            startActivity(AuthenticatedActivity.getOverviewIntent(this));
            finish();
        }

        // initialize layout
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);

        // add necessary permissions
        mFacebookButton.setReadPermissions(
                "public_profile",
                "email",
                "user_friends"
        );
    }

    @Override
    public void onAuthenticationFailed()
    {
        // We don't want the default behaviour of opening the splash on logout... we already have
        // a visible splash screen!
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        Session session = Session.getActiveSession();
        if (session != null
            && session.getState() != SessionState.CLOSED_LOGIN_FAILED
            && session.getState() != SessionState.CLOSED)
        {
            // TODO check if all necessary permissions were granted
            // Session.getActiveSession().getDeclinedPermissions();

            // TODO get rid of this, callback OPENED should fire!!!
            FacebookSessionHandler.setAlreadyRegistered(this, true);

            startActivity(AuthenticatedActivity.getOverviewIntent(this));
            finish();
        }
    }
}
