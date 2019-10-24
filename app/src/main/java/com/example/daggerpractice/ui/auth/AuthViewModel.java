package com.example.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.daggerpractice.network.auth.AuthApi;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private final AuthApi mAuthApi;

    @Inject
    public AuthViewModel(final AuthApi authApi) {
        mAuthApi = authApi;
        Log.d(TAG, "AuthViewModel: viewmodel is working....");

        if (mAuthApi == null) {
            Log.d(TAG, "AuthViewMode: auth API is null");
        } else {
            Log.d(TAG, "AuthViewModel: auth api is NOT NULL.");
        }
    }
}
