package com.example.daggerpractice.di;

import com.example.daggerpractice.di.auth.AuthViewModelsModule;
import com.example.daggerpractice.ui.auth.AuthActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    // All of the app activities must be declared here

    // This must be abstract and return an object of type Activity
    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class}
    )
    abstract AuthActivity contributeAuthActivity();

    // With this AuthActivity is a potential client Dagger can inject dependencies to.

//    // Example how to create a dependency, just an example, normally you won't add this "Provides" directly here in this class.
//    @Provides
//    static String someString() {
//        return "this is a test string";
//    }
}
