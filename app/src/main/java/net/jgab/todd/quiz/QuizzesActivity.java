package net.jgab.todd.quiz;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;

import net.jgab.todd.R;
import net.jgab.todd.core.utils.ScreenUtils;
import net.jgab.todd.to.Answer;
import net.jgab.todd.to.Patient;
import net.jgab.todd.to.Result;

import java.util.List;

public class QuizzesActivity extends AppCompatActivity
        implements QuizListAdapter.QuizListListener {

    public static final String EXTRA_PATIENT_ID = "patient_id";

    private Patient patient = new Patient();
    private QuizListAdapter adapter;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes);

        if (getIntent().hasExtra(EXTRA_PATIENT_ID)) {
            patient = Patient.findById(Patient.class, getIntent().getLongExtra(EXTRA_PATIENT_ID, 1));
        }

        setUpToolBar();
        setUpListView();
        loadPendingQuizzes();
    }

    @Override
    public void onAnswerChangedClicked(Result result, int answerPosition, boolean isChecked) {
        /*
            this solution using object by reference, its clean,
            but the developer have to know the context.
        */
        //answer.setAnswer(isChecked);
        //answer.save();
        //result.recalculateAndSavePercent();

        saveQuizAnswer(result, answerPosition, isChecked);
    }

    /*
        this solution is ugly, but easily understood
     */
    private void saveQuizAnswer(Result result, int answerPosition, boolean isChecked) {
        Answer answer = result.getAnswers().get(answerPosition);
        answer.setAnswer(isChecked);
        answer.save();

        result.recalculateAndSavePercent();
        adapter.notifyDataSetChanged();
    }

    private void setUpListView() {
        ExpandableListView expandableListView = ScreenUtils.findViewById(this, R.id.quiz_list);
        adapter = new QuizListAdapter(this, this);
        expandableListView.setAdapter(adapter);
    }

    private void loadPendingQuizzes() {

        new AsyncTask<Void, Void, List<Result>>() {
            @Override
            protected List<Result> doInBackground(Void... params) {
                return new ResultHelper(patient).getAllPendingQuizzes();
            }

            @Override
            protected void onPostExecute(List<Result> results) {
                adapter.addResults(results);
            }
        }.execute();
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(String.format(getString(R.string.title_activity_quizzes), patient.getName()));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

}
