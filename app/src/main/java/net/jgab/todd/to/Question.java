package net.jgab.todd.to;

import java.io.Serializable;

/**
 * Created by sid on 14-02-17.
 */

public class Question implements Serializable {

    private String id;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
