package com.example.daggerpractice.di;

import android.app.Application;
import android.se.omapi.Session;

import com.example.daggerpractice.BaseApplication;
import com.example.daggerpractice.SessionManager;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

// @Singleton: say to Dagger that this component should stay in memory as long as this component exists, till the application is destroyed.
@Singleton
@Component(
        // Needed when you use the Android stuff
        modules = {
                // Always needed
                AndroidSupportInjectionModule.class,
                // Optional
                ActivityBuildersModule.class,
                AppModule.class,

                ViewModelFactoryModule.class
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {
    // You can think of AppComponent as a Service and BaseApplication would be the client. Using AndroidInjector<BaseApplication>
    // you are telling Dagger to inject BaseApplication into this component.

    // We want the mSessionManager to live through the entire lifetime of the application so we put this here.
    SessionManager sessionManager();

    @Component.Builder
    interface Builder {

        // Optional, the name can be anything.
        @BindsInstance
        Builder application(Application application);

        // This line is mandatory
        AppComponent build();
    }
}
