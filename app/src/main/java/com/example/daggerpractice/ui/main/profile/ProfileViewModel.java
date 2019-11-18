package com.example.daggerpractice.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.daggerpractice.SessionManager;
import com.example.daggerpractice.models.User;
import com.example.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {
    private static final String TAG = "ProfileViewModel";

    private final SessionManager mSessionManager;

    @Inject
    public ProfileViewModel(final SessionManager sessionManager) {
        mSessionManager = sessionManager;
        Log.d(TAG, "ProfileViewModel: view model is ready...");
    }

    public LiveData<AuthResource<User>> getAuthenticatedUser() {
        return mSessionManager.getAuthUser();
    }
}
