package io.bootique.aws.demo;

import com.amazonaws.services.s3.AmazonS3;
import com.google.inject.Provider;
import io.bootique.cli.Cli;
import io.bootique.command.CommandOutcome;
import io.bootique.command.CommandWithMetadata;
import io.bootique.meta.application.CommandMetadata;
import io.bootique.meta.application.OptionMetadata;

import java.io.File;

public class UploadToS3Command extends CommandWithMetadata {

    private static final String FILE_OPTION = "file";

    // AmazonS3 is actually injectable directly. Here we are storing a provider of AmazonS3 because we are within a
    // command, and idiomatic way is to use lazy dependency resolution in commands.
    private Provider<AmazonS3> s3Provider;

    private static CommandMetadata createMetadata() {
        return CommandMetadata
                .builder(UploadToS3Command.class)
                .description("Upload specified file to Amazon S3")
                .addOption(OptionMetadata.builder(FILE_OPTION).valueRequired("filename").build())
                .build();
    }

    public UploadToS3Command(Provider<AmazonS3> s3Provider) {
        super(createMetadata());
        this.s3Provider = s3Provider;
    }

    @Override
    public CommandOutcome run(Cli cli) {

        String fileString = cli.optionString(FILE_OPTION);
        if(fileString == null) {
            return CommandOutcome.failed(-1, "No 'file' option is specified");
        }

        File file = new File(fileString);
        if(!file.exists()) {
            return CommandOutcome.failed(-1, "File does not exist: " + fileString);
        }

        return CommandOutcome.succeeded();
    }
}
