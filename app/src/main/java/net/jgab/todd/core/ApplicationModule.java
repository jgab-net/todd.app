package net.jgab.todd.core;

import android.app.Application;

import dagger.Module;

/**
 * Created by sid on 14-02-17.
 */

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

}
