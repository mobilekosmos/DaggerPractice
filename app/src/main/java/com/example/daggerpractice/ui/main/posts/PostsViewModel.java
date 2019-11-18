package com.example.daggerpractice.ui.main.posts;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.daggerpractice.SessionManager;
import com.example.daggerpractice.network.main.MainApi;

import javax.inject.Inject;

public class PostsViewModel extends ViewModel {
    private static final String TAG = "PostsViewModel";

    // inject
    private final SessionManager mSessionManager;
    private final MainApi mMainApi;

    @Inject
    public PostsViewModel(final SessionManager sessionManager, final MainApi mainApi) {
        mSessionManager= sessionManager;
        mMainApi = mainApi;
        Log.d(TAG, "PostViewModel: view model is working");
    }
}
