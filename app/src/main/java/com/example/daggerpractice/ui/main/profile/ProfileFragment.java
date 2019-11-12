package com.example.daggerpractice.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.daggerpractice.R;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProfileFragment extends DaggerFragment {

    private ProfileViewModel mViewModel;

    @Inject
    ViewModelProviderFactory mProviderFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Toast.makeText(getActivity(), "Profile Fragment", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ProfileFragment was created...");
        mViewModel = ViewModelProviders.of(this, mProviderFactory).get(ProfileViewModel.class);

        super.onViewCreated(view, savedInstanceState);

    }
}
