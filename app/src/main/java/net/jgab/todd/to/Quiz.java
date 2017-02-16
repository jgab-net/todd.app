package net.jgab.todd.to;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

import net.jgab.todd.core.utils.gson.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sid on 14-02-17.
 */

public class Quiz extends SugarRecord implements Serializable {

    @SerializedName("_id")
    private String apiId;
    private String title;
    @Ignore
    private List<Question> questions = new ArrayList<>();

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getStoredQuestions() {
        return Question.find(Question.class, "quiz = ?", getId().toString());
    }
}
