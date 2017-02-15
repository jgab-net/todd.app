package net.jgab.todd.sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import net.jgab.todd.R;
import net.jgab.todd.core.utils.ScreenUtils;
import net.jgab.todd.patient.PatientsActivity;

public class SyncActivity extends AppCompatActivity {

    private ProgressBar progress;
    private SynchronizatorReceiver receiver = new SynchronizatorReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronizator);

        progress = ScreenUtils.findViewById(this, R.id.progress);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver,
                new IntentFilter(SyncService.BROADCAST_ACTION)
        );

        startService(new Intent(this, SyncService.class));
    }

    public class SynchronizatorReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int value = intent.getIntExtra(SyncService.PROGRESS_VALUE, 0);
            int max = intent.getIntExtra(SyncService.PROGRESS_MAX, 100);
            progress.setMax(max);
            progress.setProgress(value);

            if (value == max) {
                SyncActivity.this.finish();
                startActivity(new Intent(SyncActivity.this, PatientsActivity.class));
            }
        }
    }
}
