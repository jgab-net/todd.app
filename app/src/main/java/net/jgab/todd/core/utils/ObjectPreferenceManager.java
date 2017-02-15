package net.jgab.todd.core.utils;

import android.content.SharedPreferences;
import android.util.Base64;

import net.jgab.todd.core.utils.exceptions.PreferenceNotFoundException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by sid on 30/11/16.
 */

public class ObjectPreferenceManager {

    private SharedPreferences sharedPreferences;

    public ObjectPreferenceManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean hasPreference(String key) {
        return sharedPreferences.contains(key);
    }

    public void setPreference(String key, Serializable serializable){
        sharedPreferences.edit().putString(key, serialize(serializable)).apply();
    }

    public <Type extends Serializable> Type getPreference(String key) throws PreferenceNotFoundException {
        if (sharedPreferences.contains(key)) {
            Type object = (Type) deseralize(sharedPreferences.getString(key, ""));
            if (object == null) {
                throw new PreferenceNotFoundException("Preference " + key + " not found");
            }
            return object;
        }
        throw new PreferenceNotFoundException("Preference " + key + " not found");
    }


    public String serialize(Serializable serializable){
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(stream);
            objectStream.writeObject(serializable);
            objectStream.close();

            return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
        } catch (IOException e) {
            return "";
        }
    }

    public Serializable deseralize(String serialized){
        try {
            ObjectInputStream objectStream = new ObjectInputStream(
                    new ByteArrayInputStream(Base64.decode(serialized.getBytes(), Base64.DEFAULT))
            );
            Serializable object = null;
            try {
                object = (Serializable) objectStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            objectStream.close();
            return object;
        } catch (IOException e) {
            return null;
        }
    }
}
