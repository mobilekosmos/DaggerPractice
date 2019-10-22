package com.example.daggerpractice.di;

import android.app.Application;

import com.example.daggerpractice.BaseApplication;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(
        // Needed when you use the Android stuff
        modules = {
                // Always needed
                AndroidSupportInjectionModule.class,
                //
                ActivityBuildersModule.class,
                AppModule.class,
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {
    // You can think of AppComponent as a Service and BaseApplication would be the client. Using AndroidInjector<BaseApplication>
    // you are telling Dagger to inject BaseApplication into this component.

    @Component.Builder
    interface Builder {

        // Optional, the name can be anything.
        @BindsInstance
        Builder application(Application application);

        // This line is mandatory
        AppComponent build();
    }
}
