package net.jgab.todd.to;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import net.jgab.todd.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sid on 15-02-17.
 */

public class Patient extends SugarRecord implements Serializable {

    @SerializedName("_id")
    private String apiId;
    private String name;
    private String email;
    @Ignore
    private List<Result> results = new ArrayList<>();

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPictureUrl() {
        return Constants.PICTURE_URL.replace("{email}", email);
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public List<Quiz> getNewQuizzes() {
        List<Result> results = getStoredResults();
        List<String> ids = new ArrayList<>();

        for (Result result: results) {
            ids.add(result.getQuiz().getId().toString());
        }

        return findWithQuery(Quiz.class, QUERY_GET_QUIZZES_PENDING, TextUtils.join(",", ids));
    }

    public List<Result> getDoingQuizzes() {
        return findWithQuery(Result.class, QUERY_GET_DOING_QUIZZES, getId().toString());
    }

    public List<Result> getStoredResults() {
        return Result.find(Result.class, "patient = ?", getId().toString());
    }

    private static final String QUERY_GET_QUIZZES_PENDING =
            "SELECT * FROM quiz " +
            "WHERE id NOT IN (?)";

    private static final String QUERY_GET_DOING_QUIZZES =
            "SELECT result.* FROM result " +
            "JOIN quiz on result.quiz = quiz.id " +
            "WHERE result.patient = ?";
}
