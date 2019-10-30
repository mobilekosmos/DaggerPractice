package com.example.daggerpractice;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.daggerpractice.models.User;
import com.example.daggerpractice.ui.auth.AuthResource;

import java.lang.annotation.Target;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private static final String TAG = "SessionManager";

    private MediatorLiveData<AuthResource<User>> mCachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {
    }

    public void authenticateWithId(final LiveData<AuthResource<User>> source) {
        if (mCachedUser != null) {
            mCachedUser.setValue((AuthResource.loading((User) null)));
            mCachedUser.addSource(source, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> userAuthResource) {
                    mCachedUser.setValue(userAuthResource);
                    mCachedUser.removeSource(source);
                }
            });
        }
    }

    public void logOut() {
        Log.d(TAG, "logOut: logging out...");
        mCachedUser.setValue(AuthResource.<User>logout());
    }

    public LiveData<AuthResource<User>> getAuthUser() {
        return mCachedUser;
    }
}
