package com.facebooktest.controllers.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.Session;
import com.facebooktest.R;
import com.facebooktest.helpers.FacebookSessionHandler;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

public class AuthenticatedActivity extends BaseActivity
{
    public static Intent getOverviewIntent(Context context)
    {
        Intent intent = new Intent(context, AuthenticatedActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @InjectView(R.id.toolbar) Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_overview);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
    }

    @Override
    public void onAuthenticationSuccessful()
    {
        super.onAuthenticationSuccessful();
        Timber.d("FB Token; " + Session.getActiveSession().getAccessToken());
    }

    /*-************************************************************************************
                Handle option menu's
    ***************************************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_overview, menu);
        return true;
    }

    public void onMenuLogout(MenuItem item)
    {
        getFacebookSession().closeAndClearTokenInformation();
        FacebookSessionHandler.setAlreadyRegistered(this, false);
        Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
    }
}
