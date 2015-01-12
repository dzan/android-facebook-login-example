package com.facebooktest.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebooktest.BaseApplication;
import com.facebooktest.interfaces.AuthenticationListener;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class FacebookSessionHandler implements Session.StatusCallback
{
    private Context mContext;
    private List<AuthenticationListener> mListeners;

    public FacebookSessionHandler(Context mContext)
    {
        Timber.d("Facebook SessionState callbackhandler created.");
        this.mContext = mContext;
        this.mListeners = new ArrayList<>();
    }

    public void register(AuthenticationListener listener)
    {
        mListeners.add(listener);
    }

    public void unregister(AuthenticationListener listener)
    {
        mListeners.remove(listener);
    }

    @Override
    public void call(Session session, SessionState sessionState, Exception e)
    {
        if (e != null)
        {
            Timber.e("Facebook callback exception; " + e.getLocalizedMessage());
        }

        Timber.d("Facebook callback SessionState ; " + sessionState.name());

        // https://developers.facebook.com/docs/reference/android/current/class/SessionState/
        switch (sessionState)
        {
            case CLOSED_LOGIN_FAILED:
            {
                setAlreadyRegistered(mContext, false);

                for (AuthenticationListener l : mListeners)
                    l.onAuthenticationFailed();
                break;
            }

            case CLOSED:
            {
                for (AuthenticationListener l : mListeners)
                    l.onAuthenticationFailed();
                break;
            }

            case OPENED:
            {
                setAlreadyRegistered(mContext, true);

                for (AuthenticationListener l : mListeners)
                    l.onAuthenticationSuccessful();

                Timber.d("Facebook token; " + session.getAccessToken());
                break;
            }

            case OPENED_TOKEN_UPDATED:
            {
                // TODO
                Timber.d("Facebook token; " + session.getAccessToken());
                break;
            }

            default:;
        }
    }

    public static void setAlreadyRegistered(Context context, boolean value)
    {
        SharedPreferences.Editor edit = context.getSharedPreferences(
                BaseApplication.MY_PREFERENCES, context.MODE_PRIVATE).edit();
        edit.putBoolean(BaseApplication.PREF_KEY_REGISTERED_BEFORE, value);
        edit.apply();
    }
}
