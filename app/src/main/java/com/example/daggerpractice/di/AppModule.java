package com.example.daggerpractice.di;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

// Here are all application level dependencies for the project. Things like the retrofit instance, the glide instance, anything that won't change through the entire lifetime
// of the application.
@Module
public class AppModule {

    // The doc says to always use static here.
    @Provides
    static String someString() {
        return "this is a test string";
    }

    @Provides
    static boolean getApp(Application application) {
        return application == null;
    }
}
