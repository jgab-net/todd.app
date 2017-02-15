package net.jgab.todd.core;

import net.jgab.todd.test.TestsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sid on 14-02-17.
 */

@Singleton
@Component(modules={NetModule.class, ApplicationModule.class})
public interface ToddComponent {
    void inject(TestsActivity activity);
}
