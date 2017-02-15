package net.jgab.todd;

import android.app.Application;

import com.orm.SugarContext;

import net.jgab.todd.core.ApplicationModule;
import net.jgab.todd.core.DaggerToddComponent;
import net.jgab.todd.core.NetModule;
import net.jgab.todd.core.ToddComponent;

/**
 * Created by sid on 14-02-17.
 */

public class ToddApplication extends Application {

    private static ToddComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerToddComponent.builder()
                .netModule(new NetModule(BuildConfig.BASE_ENDPOINT))
                .applicationModule(new ApplicationModule(this))
                .build();

        SugarContext.init(this);
    }

    public static ToddComponent getComponent() {
        return component;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
