package net.jgab.todd.core;

import net.jgab.todd.to.Quiz;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by sid on 14-02-17.
 */

@Module
public class ToddApiModule {

    public interface ToddApiInterface {

        @GET("tests")
        Call<List<Quiz>> tests();
    }

    @Provides
    @Singleton
    ToddApiInterface providesToddApiInterface(Retrofit retrofit) {
        return retrofit.create(ToddApiInterface.class);
    }
}
