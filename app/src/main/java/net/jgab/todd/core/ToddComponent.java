package net.jgab.todd.core;

import net.jgab.todd.sync.SyncService;
import net.jgab.todd.patient.PatientListActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sid on 14-02-17.
 */

@Singleton
@Component(modules={NetModule.class, ToddApiModule.class, ApplicationModule.class})
public interface ToddComponent {
    void inject(PatientListActivity activity);

    void inject(SyncService service);
}
