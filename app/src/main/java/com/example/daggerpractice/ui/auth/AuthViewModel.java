package com.example.daggerpractice.ui.auth;

import android.se.omapi.Session;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.daggerpractice.SessionManager;
import com.example.daggerpractice.models.User;
import com.example.daggerpractice.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private final AuthApi mAuthApi;
    private SessionManager mSessionManager;


    // First code without using the wrapper AuthResource.java

//    private MediatorLiveData<AuthResource<User>> mAuthUser = new MediatorLiveData<>();
//    public void authenticateWithId(int userId) {
//        final LiveData<User> source = LiveDataReactiveStreams.fromPublisher(
//                mAuthApi.getUser(userId)
//                        .subscribeOn(Schedulers.io())
//        );
//
//        mAuthUser.addSource(source, new androidx.lifecycle.Observer<User>() {
//            @Override
//            public void onChanged(final User user) {
//                mAuthUser.setValue(user);
//                mAuthUser.removeSource(source);
//            }
//        });
//    }

    public void authenticateWithId(int userId) {
        Log.d(TAG, "authenticateWithId: attempting to login.");
        mSessionManager.authenticateWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId) {
        return LiveDataReactiveStreams.fromPublisher(
                mAuthApi.getUser(userId)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(final Throwable throwable) throws Exception {
                                final User errorUser = new User();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        })

                        .map(new Function<User, AuthResource<User>>() {
                            @Override
                            public AuthResource<User> apply(final User user) throws Exception {
                                if (user.getId() == -1) {
                                    return AuthResource.error("Could not authenticate", (User) null);
                                }
                                return AuthResource.authenticated(user);
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );
    }

    public LiveData<AuthResource<User>> observeAuthState() {
        return mSessionManager.getAuthUser();
    }

    @Inject
    public AuthViewModel(final AuthApi authApi, final SessionManager sessionManager) {
        mAuthApi = authApi;
        mSessionManager = sessionManager;
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
