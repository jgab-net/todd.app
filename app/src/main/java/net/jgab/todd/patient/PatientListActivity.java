package net.jgab.todd.patient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ViewSwitcher;

import net.jgab.todd.Constants;
import net.jgab.todd.R;
import net.jgab.todd.ToddApplication;
import net.jgab.todd.core.utils.ScreenUtils;
import net.jgab.todd.quiz.QuizzesActivity;
import net.jgab.todd.sync.SyncActivity;
import net.jgab.todd.to.Patient;

import java.util.List;

import javax.inject.Inject;

public class PatientListActivity extends AppCompatActivity
        implements PatientListAdapter.PatientListListener,
            PatientDialogFragment.PatientDialogListener{

    private static final int QUIZ_REQUEST_CODE = 1234;

    private PatientListAdapter adapter;
    private ViewSwitcher switcher;

    @Inject
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        ToddApplication.getComponent().inject(this);

        if (!preferences.getBoolean(Constants.SYNC_STATE, false)) {
            startActivity(new Intent(this, SyncActivity.class));
            finish();
        }

        setUpToolBar();
        setUpListView();
        setUpViewSwitcher();
        setUpFab();
        loadPatients();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QUIZ_REQUEST_CODE) {
            loadPatients();
        }
    }

    @Override
    public void onPatientActionClicked(Patient patient) {

        Intent intent = new Intent(this, QuizzesActivity.class);
        intent.putExtra(QuizzesActivity.EXTRA_PATIENT_ID, patient.getId());

        startActivityForResult(intent, QUIZ_REQUEST_CODE);
    }

    @Override
    public void onPatientAdded(Patient patient) {
        showListView();

        adapter.addPatient(patient);
        Snackbar.make(findViewById(R.id.activity_patients), getString(R.string.patient_added), Snackbar.LENGTH_LONG)
                .show();

    }

    private void loadPatients() {
        new AsyncTask<Void, Void, List<Patient>>() {
            @Override
            protected List<Patient> doInBackground(Void... params) {
                return populateResults(Patient.listAll(Patient.class));
            }

            @Override
            protected void onPostExecute(List<Patient> patients) {
                if (!patients.isEmpty()) {
                    showListView();
                }
                adapter.addPatients(patients);

            }
        }.execute();
    }

    /*
        TODO: fork sugar, and add support for one-to-many relation for list,
        TODO: getting a list of parent objects and loading nested objects with parent_id.
        TODO: after, change that ugly code.
        TODO: maybe lazy loading on expanded list
     */
    private List<Patient> populateResults(List<Patient> patients) {
        for (Patient patient:patients) {
            patient.setResults(patient.getStoredResults());
        }

        return patients;
    }


    private void setUpListView() {
        ExpandableListView expandableListView = ScreenUtils.findViewById(this, R.id.patient_list);
        adapter = new PatientListAdapter(this, this);
        expandableListView.setAdapter(adapter);
    }

    private void setUpViewSwitcher() {
        switcher = ScreenUtils.findViewById(this, R.id.switcher);
        switcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        switcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PatientDialogFragment dialog = new PatientDialogFragment();
                dialog.show(getSupportFragmentManager(), "PatientDialog");
            }
        });
    }

    private void showListView() {
        if (switcher.getNextView().getId() == R.id.patient_list) {
            switcher.showNext();
        }
    }
}
