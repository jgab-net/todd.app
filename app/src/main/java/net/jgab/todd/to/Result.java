package net.jgab.todd.to;

import android.content.Context;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import net.jgab.todd.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sid on 15-02-17.
 */

public class Result extends SugarRecord implements Serializable {

    private Patient patient;
    private Quiz quiz;
    private double percent;
    @Ignore
    private List<Answer> answers = new ArrayList<>();

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Answer> getStoredAnswers() {
        return Answer.find(Answer.class, "result = ?", getId().toString());
    }

    /*
    TODO: api 8 stream :(
     */
    public void recalculateAndSavePercent() {
        int accepted = 0;
        for (Answer answer: answers) {
            if (answer.isAnswer()) {
                accepted++;
            }
        }
        setPercent(accepted * 100.0 / answers.size());
        save();
    }

    public String getFormattedPercent(Context context) {
        return String.format(context.getString(R.string.percent), getPercent());
    }
}

