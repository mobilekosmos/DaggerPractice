package com.example.daggerpractice.di.main;

import com.example.daggerpractice.network.main.MainApi;
import com.example.daggerpractice.ui.main.posts.PostsRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static PostsRecyclerAdapter provideAdapter() {
        return new PostsRecyclerAdapter();
    }

    @MainScope
    @Provides
    static MainApi provideMainApi(final Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }
}
