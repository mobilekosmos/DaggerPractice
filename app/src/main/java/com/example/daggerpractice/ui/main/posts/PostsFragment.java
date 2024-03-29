package com.example.daggerpractice.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daggerpractice.R;
import com.example.daggerpractice.models.Post;
import com.example.daggerpractice.ui.main.Resource;
import com.example.daggerpractice.util.VerticalSpacingItemDecoration;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {
    private static final String TAG = "PostsFragment";

    private PostsViewModel mViewModel;
    private RecyclerView mRecyclerView;

    @Inject
    PostsRecyclerAdapter mAdapter;

    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mViewModel = new ViewModelProvider(this, mViewModelProviderFactory).get(PostsViewModel.class);
        initRecyclerView();
        subscribeObservers();
    }

    private void subscribeObservers() {
        mViewModel.observePosts().removeObservers(getViewLifecycleOwner());
        mViewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(final Resource<List<Post>> listResource) {
                if (listResource != null) {
                    Log.d(TAG, "onChanged: " + listResource.data);
                    switch (listResource.status) {
                        case LOADING:
                            Log.d(TAG, "onChanged: LOADING...");
                            break;
                        case SUCCESS:
                            Log.d(TAG, "onChanged: got posts...");
                            mAdapter.setPosts(listResource.data);
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: ERROR..." + listResource.message);
                            break;
                    }
                }
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(mAdapter);
    }
}
