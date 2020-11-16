package io.bootique.aws.demo;

import com.amazonaws.services.s3.AmazonS3;
import io.bootique.cli.Cli;
import io.bootique.command.CommandOutcome;
import io.bootique.command.CommandWithMetadata;
import io.bootique.meta.application.CommandMetadata;
import io.bootique.meta.application.OptionMetadata;

import javax.inject.Provider;

public class SendTextToS3Command extends CommandWithMetadata {

    private static final String TEXT_OPTION = "text";
    private static final String PATH_OPTION = "path";


    // AmazonS3 is actually injectable directly. Here we are storing a provider of AmazonS3 because we are within a
    // command, and idiomatic way is to use lazy dependency resolution in commands.
    private Provider<AmazonS3> s3Provider;

    private static CommandMetadata createMetadata() {
        return CommandMetadata
                .builder(SendTextToS3Command.class)
                .description("Save a piece of text to Amazon S3")
                .addOption(OptionMetadata.builder(TEXT_OPTION).valueRequired("text_to_save").build())
                .addOption(OptionMetadata.builder(PATH_OPTION).valueRequired("path").description("S3 folder and file path").build())
                .build();
    }

    public SendTextToS3Command(Provider<AmazonS3> s3Provider) {
        super(createMetadata());
        this.s3Provider = s3Provider;
    }

    @Override
    public CommandOutcome run(Cli cli) {

        String text = cli.optionString(TEXT_OPTION);
        if(text == null) {
            return CommandOutcome.failed(-1, "No 'text' option is specified");
        }

        String bucket = cli.optionString(S3Main.BUCKET_OPTION);
        if(bucket == null) {
            return CommandOutcome.failed(-1, "No 'bucket' option is specified");
        }

        String path = cli.optionString(PATH_OPTION);
        if(path == null) {
            return CommandOutcome.failed(-1, "No 'path' option is specified");
        }

        AmazonS3 s3 = s3Provider.get();
        s3.putObject(bucket, path, text);
        return CommandOutcome.succeeded();
    }
}
