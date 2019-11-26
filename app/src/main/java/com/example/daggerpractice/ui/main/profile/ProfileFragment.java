package com.example.daggerpractice.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.daggerpractice.R;
import com.example.daggerpractice.models.User;
import com.example.daggerpractice.ui.auth.AuthResource;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProfileFragment extends DaggerFragment {

    private ProfileViewModel mViewModel;
    private TextView mEmail, mUsername, mWebsite;

    @Inject
    ViewModelProviderFactory mProviderFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "Profile Fragment", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ProfileFragment was created...");

        mEmail = view.findViewById(R.id.email);
        mUsername = view.findViewById(R.id.username);
        mWebsite = view.findViewById(R.id.website);

//        mViewModel = ViewModelProviders.of(this, mProviderFactory).get(ProfileViewModel.class);
        mViewModel = new ViewModelProvider(this, mProviderFactory).get(ProfileViewModel.class);

        super.onViewCreated(view, savedInstanceState);
        subscribeObservers();
    }

    private void subscribeObservers() {

        // This is new and should only be needed in fragments and not in activities I think.
        mViewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());

        mViewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case AUTHENTICATED:
                            setUserDetails(userAuthResource.data);
                            break;
                        case ERROR:
                            setErrorDetails(userAuthResource.message);
                            break;
                    }
                }
            }
        });
    }

    private void setErrorDetails(final String message) {
        mEmail.setText(message);
        mUsername.setText("error");
        mWebsite.setText("error");
    }

    private void setUserDetails(final User data) {
        mEmail.setText(data.getEmail());
        mUsername.setText(data.getUsername());
        mWebsite.setText(data.getWebsite());
    }


}
