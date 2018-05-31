package io.bootique.aws.demo;

import com.amazonaws.services.s3.AmazonS3;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.bootique.BQCoreModule;
import io.bootique.Bootique;
import io.bootique.meta.application.OptionMetadata;

public class S3Main implements Module {

    static final String BUCKET_OPTION = "bucket";

    public static void main(String[] args) {
        Bootique.app(args).autoLoadModules().exec().exit();
    }

    @Override
    public void configure(Binder binder) {
        BQCoreModule.extend(binder)
                .addCommand(ListS3KeysCommand.class)
                .addCommand(SendTextToS3Command.class)
                .addOption(OptionMetadata.builder(BUCKET_OPTION)
                        .description("S3 bucket name with an optional folder component. E.g. 'mybucket' or 'mybucket/myfolder'.")
                        .valueRequired("s3bucket").build())
                .setApplicationDescription("A simple cli S3 client.");
    }

    @Provides
    @Singleton
    SendTextToS3Command provideUploadCommand(Provider<AmazonS3> s3Provider) {

        // AmazonS3 instance is actually injectable directly. Here we are referencing a provider of AmazonS3 because
        // an idiomatic way in Bootique is to use lazy dependency resolution inside commands, to prevent
        // opening external connections too early (e.g. when the command requested is "--help").

        return new SendTextToS3Command(s3Provider);
    }

    @Provides
    @Singleton
    ListS3KeysCommand provideListCommand(Provider<AmazonS3> s3Provider) {

        // AmazonS3 instance is actually injectable directly. Here we are referencing a provider of AmazonS3 because
        // an idiomatic way in Bootique is to use lazy dependency resolution inside commands, to prevent
        // opening external connections too early (e.g. when the command requested is "--help").

        return new ListS3KeysCommand(s3Provider);
    }
}
