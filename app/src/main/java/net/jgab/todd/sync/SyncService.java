package net.jgab.todd.sync;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

import net.jgab.todd.Constants;
import net.jgab.todd.R;
import net.jgab.todd.ToddApplication;
import net.jgab.todd.core.ToddApiModule;
import net.jgab.todd.patient.PatientListActivity;
import net.jgab.todd.to.Quiz;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

public class SyncService extends IntentService {

    private static final int PROGRESS_NOTIFICATION_ID = 10;
    private static final int READY_NOTIFICATION_ID = 11;

    public static final String BROADCAST_ACTION = "net.jgab.todd.synchronizator.BROADCAST";
    public static final String PROGRESS_VALUE = "progress_value";
    public static final String PROGRESS_MAX = "progress_max";

    private Builder builder;

    @Inject
    NotificationManager notificationManager;
    @Inject
    ToddApiModule.ToddApiInterface api;
    @Inject
    SharedPreferences preferences;


    public SyncService() {
        super("SyncService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ToddApplication.getComponent().inject(this);

        builder = buildProgressNotification();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            final Response<List<Quiz>> response = api.quizzes().execute();

            if (response.isSuccessful()) {
                new SyncHelper(response.body()).store(new SyncHelper.Callback() {
                    @Override
                    public void onItemStored(int current, int total) {
                        notifyProgress(current, total);
                    }

                    @Override
                    public void onReady() {
                        preferences.edit().putBoolean(Constants.SYNC_STATE, true).apply();
                        notificationManager.notify(READY_NOTIFICATION_ID, getReadyNotification());
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        notificationManager.cancel(PROGRESS_NOTIFICATION_ID);

    }

    private void notifyProgress(int progress, int max) {
        builder.setProgress(max, progress, false);
        notificationManager.notify(PROGRESS_NOTIFICATION_ID, builder.build());

        Intent intentUpdate = new Intent();
        intentUpdate.putExtra(PROGRESS_VALUE, progress);
        intentUpdate.putExtra(PROGRESS_MAX, max);
        intentUpdate.setAction(BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intentUpdate);
    }

    private Builder buildProgressNotification() {
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(getString(R.string.synchronization_progress_title))
                .setContentText(getString(R.string.synchronization_progress_text))
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setProgress(0, 0, true);
    }

    private Notification getReadyNotification() {
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(getString(R.string.synchronization_ready_title))
                .setContentText(getString(R.string.synchronization_ready_text))
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(
                        this,
                        0,
                        new Intent(this, PatientListActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT
                ))
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
    }

}
