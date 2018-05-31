package io.bootique.aws.demo;

import com.google.inject.Module;
import io.bootique.BQModuleProvider;

public class S3MainProvider implements BQModuleProvider {

    @Override
    public Module module() {
        return new S3Main();
    }
}
