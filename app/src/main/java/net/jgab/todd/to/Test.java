package net.jgab.todd.to;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sid on 14-02-17.
 */

public class Test implements Serializable {

    private String id;
    private String title;
    private List<Question> questions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
