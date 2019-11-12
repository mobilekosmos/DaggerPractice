package com.example.daggerpractice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.daggerpractice.models.User;
import com.example.daggerpractice.ui.auth.AuthActivity;
import com.example.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager mSessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    private void subscribeObservers() {
        mSessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(final AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case LOADING:
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: " + userAuthResource.message);
                            break;
                        case AUTHENTICATED:
                            Log.d(TAG, "onChanged: LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                            break;
                        case NOT_AUTHENTICATED:
                            navLoginScreen();
                            break;
                    }
                }
            }
        });
    }

    private void navLoginScreen() {
        final Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
