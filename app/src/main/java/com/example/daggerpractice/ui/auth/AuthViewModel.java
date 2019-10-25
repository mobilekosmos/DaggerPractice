package com.example.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.daggerpractice.models.User;
import com.example.daggerpractice.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private final AuthApi mAuthApi;

    private MediatorLiveData<User> mAuthUser = new MediatorLiveData<>();

    public void authenticateWithId(int userId) {
        final LiveData<User> source = LiveDataReactiveStreams.fromPublisher(
                mAuthApi.getUser(userId)
                .subscribeOn(Schedulers.io())
        );

        mAuthUser.addSource(source, new androidx.lifecycle.Observer<User>() {
            @Override
            public void onChanged(User user) {
                mAuthUser.setValue(user);
                mAuthUser.removeSource(source);
            }
        });
    }

    public LiveData<User> observeUser() {
        return mAuthUser;
    }

    @Inject
    public AuthViewModel(final AuthApi authApi) {
        mAuthApi = authApi;
        Log.d(TAG, "AuthViewModel: viewmodel is working....");

        if (mAuthApi == null) {
            Log.d(TAG, "AuthViewMode: auth API is null");
        } else {
            Log.d(TAG, "AuthViewModel: auth api is NOT NULL.");
        }

        // toObservable() = to convert the Flowable to an Observable
        authApi.getUser(1)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, "onNext: " + user.getEmail());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
