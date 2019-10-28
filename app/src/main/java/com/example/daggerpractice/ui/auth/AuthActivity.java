package com.example.daggerpractice.ui.auth;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.example.daggerpractice.R;
import com.example.daggerpractice.models.User;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

// From https://www.youtube.com/playlist?list=PLgCYzUzKIBE8AOAspC3DHoBNZIBHbIOsC

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthActivity";

    @Inject
    String asdfasdf;

    @Inject
    boolean isAppNull;

    @Inject
    Drawable mLogoDrawable;

    @Inject
    RequestManager mGlideRequestManager;

    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;

    private AuthViewModel mViewModel;

    private EditText mUserId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mUserId = findViewById(R.id.user_id_input);
        findViewById(R.id.login_button).setOnClickListener(this);
        Log.d(TAG, "onCreate: " + asdfasdf);
        Log.d(TAG, "onCreate: is app null? " + isAppNull);

        mViewModel = ViewModelProviders.of(this, mViewModelProviderFactory).get(AuthViewModel.class);
        setLogo();
        subscribeObservers();
    }

    private void setLogo() {
        mGlideRequestManager
                .load(mLogoDrawable)
                .into((ImageView) findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.login_button:
                attempLogin();
                break;
        }
    }

    private void attempLogin() {
        if (TextUtils.isEmpty(mUserId.getText().toString())) {
            return;
        }
        mViewModel.authenticateWithId(Integer.parseInt(mUserId.getText().toString()));
    }

    private void subscribeObservers() {
        mViewModel.observeUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(final User user) {
                if (user != null) {
                    Log.d(TAG, "onChanged: " + user.getEmail());
                }
            }
        });
    }
}
