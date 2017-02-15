package net.jgab.todd.core;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import net.jgab.todd.core.utils.ObjectPreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sid on 14-02-17.
 */

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);

    }

    @Provides
    @Singleton
    ObjectPreferenceManager providesNewsPreferenceManager(SharedPreferences sharedPreferences) {
        return new ObjectPreferenceManager(sharedPreferences);
    }

    @Provides
    @Singleton
    NotificationManager providesNotificationManager(Application application) {
        return (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
