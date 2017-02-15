package net.jgab.todd.core;

import dagger.Module;

/**
 * Created by sid on 14-02-17.
 */

@Module
public class NetModule {

    private String baseUrl;

    public NetModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
