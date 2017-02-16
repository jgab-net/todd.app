package net.jgab.todd.to;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by sid on 15-02-17.
 */

public class Answer extends SugarRecord implements Serializable {

    private Question question;
    private boolean answer;
    private Result result;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
