package net.jgab.todd.core.utils.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class AnnotationExclusionStrategy implements ExclusionStrategy {
    private Mode mode;

    public AnnotationExclusionStrategy(Mode mode) {
        this.mode = mode;
    }
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        Exclude annotation = f.getAnnotation(Exclude.class);
        if(annotation!=null) {
            if (this.mode.equals(Mode.SERIALIZE)) {
                return annotation.serialize();
            } else {
                return annotation.deserialize();
            }
        }
        return false;
    }
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

    public enum Mode { SERIALIZE, DESERIALIZE }
}