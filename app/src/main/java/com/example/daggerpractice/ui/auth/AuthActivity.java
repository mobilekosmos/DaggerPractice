package com.example.daggerpractice.ui.auth;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.example.daggerpractice.R;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

// From https://www.youtube.com/playlist?list=PLgCYzUzKIBE8AOAspC3DHoBNZIBHbIOsC

public class AuthActivity extends DaggerAppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Log.d(TAG, "onCreate: " + asdfasdf);
        Log.d(TAG, "onCreate: is app null? " + isAppNull);

        mViewModel = ViewModelProviders.of(this, mViewModelProviderFactory).get(AuthViewModel.class);
        setLogo();
    }

    private void setLogo() {
        mGlideRequestManager
                .load(mLogoDrawable)
                .into((ImageView) findViewById(R.id.login_logo));
    }
}
