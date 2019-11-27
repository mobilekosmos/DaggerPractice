package com.example.daggerpractice.di.auth;

import com.example.daggerpractice.models.User;
import com.example.daggerpractice.network.auth.AuthApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    @AuthScope
    @Provides
    static AuthApi provideAuthApi(final Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }

    // If the same object type is declared in different modules the project won't compile
    // so we must add a name annotation
    @AuthScope
    @Provides
    @Named("auth-user")
    static User someUser() {
        return new User();
    }
}
