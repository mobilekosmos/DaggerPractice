package com.example.daggerpractice.di;

import androidx.lifecycle.ViewModelProvider;

import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

// This is going to be responsible for generating the dependency, doing the dependency injection for the view model factory class.
@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);

    // Same as above with the only difference that we are not doing anything in the method body, so we better do it like above.
    @Provides
    static ViewModelProvider.Factory bindFactory(ViewModelProviderFactory factory) {
        return factory;
    }
}
