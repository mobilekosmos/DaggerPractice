package com.example.daggerpractice.di;

import com.example.daggerpractice.di.auth.AuthModule;
import com.example.daggerpractice.di.auth.AuthScope;
import com.example.daggerpractice.di.auth.AuthViewModelsModule;
import com.example.daggerpractice.di.main.MainFragmentBuildersModule;
import com.example.daggerpractice.di.main.MainModule;
import com.example.daggerpractice.di.main.MainScope;
import com.example.daggerpractice.di.main.MainViewModelsModule;
import com.example.daggerpractice.ui.auth.AuthActivity;
import com.example.daggerpractice.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    // All of the app activities must be declared here

    // This must be abstract and return an object of type Activity.
    // This also defines the AuthComponent, a subComponent generated automatically using this annotation.
    @AuthScope
    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class, AuthModule.class}
    )
    abstract AuthActivity contributeAuthActivity();
    // With this AuthActivity is a potential client Dagger can inject dependencies to.

//    // Example how to create a dependency, just an example, normally you won't add this "Provides" directly here in this class.
//    @Provides
//    static String someString() {
//        return "this is a test string";
//    }

    @MainScope
    @ContributesAndroidInjector(
            modules = {MainFragmentBuildersModule.class, MainViewModelsModule.class, MainModule.class}
    )
    abstract MainActivity contributeMainActivity();

}
