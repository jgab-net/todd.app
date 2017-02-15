package net.jgab.todd.core;

import net.jgab.todd.to.Test;

import java.util.List;

import javax.inject.Singleton;

import dagger.Provides;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by sid on 14-02-17.
 */

public class ToddApiModule {

    private interface ToddApiInterface {

        @GET("tests")
        Call<List<Test>> tests();
    }

    @Provides
    @Singleton
    ToddApiInterface providesToddApiInterface(Retrofit retrofit) {
        return retrofit.create(ToddApiInterface.class);
    }
}
