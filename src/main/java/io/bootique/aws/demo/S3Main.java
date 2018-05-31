package io.bootique.aws.demo;

import com.amazonaws.services.s3.AmazonS3;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.bootique.BQCoreModule;
import io.bootique.Bootique;

public class S3Main implements Module {

    public static void main(String[] args) {
        Bootique.app(args).autoLoadModules().exec().exit();
    }

    @Override
    public void configure(Binder binder) {
        BQCoreModule.extend(binder)
                .addCommand(UploadToS3Command.class)
                .setApplicationDescription("Simple S3 client.");
    }

    @Provides
    @Singleton
    UploadToS3Command provideUploadCommand(Provider<AmazonS3> s3Provider) {

        // AmazonS3 instance is actually injectable directly. Here we are referencing a provider of AmazonS3 because
        // an idiomatic way in Bootique is to use lazy dependency resolution inside commands, to prevent
        // opening external connections too early (e.g. when the command requested is "--help").

        return new UploadToS3Command(s3Provider);
    }
}
