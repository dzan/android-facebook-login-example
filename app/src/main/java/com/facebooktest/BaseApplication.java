package com.facebooktest;

import android.content.Context;

import com.facebooktest.helpers.FacebookSessionHandler;

import timber.log.Timber;

public class BaseApplication extends android.app.Application
{
    // global preference keys
    public static final String MY_PREFERENCES = "pref";
    public static final String PREF_KEY_REGISTERED_BEFORE = "wasRegisteredBefore";

    // fields
    private static Context mApplicationContext;

    @Override
    public void onCreate()
    {
        super.onCreate();

        // keep static application context ref
        mApplicationContext = getBaseContext();

        // timber tree planting
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }


    /*-************************************************************************************
        Facebook Session Handler
    **************************************************************************************/
    private static FacebookSessionHandler mFacebookSessionHandler;

    public static synchronized FacebookSessionHandler getFacebookSessionHandler()
    {
        if (mFacebookSessionHandler == null)
            mFacebookSessionHandler = new FacebookSessionHandler(mApplicationContext);
        return mFacebookSessionHandler;
    }


    /*-************************************************************************************
        Track application visibility.
    **************************************************************************************/
    private static boolean activityVisible;

    public static boolean isActivityVisible()
    {
        return activityVisible;
    }

    public static void activityResumed()
    {
        activityVisible = true;
    }

    public static void activityPaused()
    {
        activityVisible = false;
    }


    /*-************************************************************************************
        A Crashlytics Timber tree
    **************************************************************************************/
    private static class CrashReportingTree extends Timber.HollowTree {
        @Override
        public void i(String message, Object... args)
        {
            // TODO e.g., Crashlytics.log(String.format(message, args));
        }

        @Override
        public void i(Throwable t, String message, Object... args)
        {
            i(message, args);
        }

        @Override
        public void e(String message, Object... args)
        {
            i("ERROR: " + message, args);
        }

        @Override
        public void e(Throwable t, String message, Object... args)
        {
            e(message, args);
            // TODO e.g., Crashlytics.logException(t);
        }
    }
}
