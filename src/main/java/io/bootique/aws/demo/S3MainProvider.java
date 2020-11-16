package io.bootique.aws.demo;

import io.bootique.BQModuleProvider;
import io.bootique.di.BQModule;

public class S3MainProvider implements BQModuleProvider {

    @Override
    public BQModule module() {
        return new S3Main();
    }
}
