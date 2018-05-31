package io.bootique.aws.demo;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.google.inject.Provider;
import io.bootique.cli.Cli;
import io.bootique.command.CommandOutcome;
import io.bootique.command.CommandWithMetadata;
import io.bootique.meta.application.CommandMetadata;

public class ListS3KeysCommand extends CommandWithMetadata {

    // AmazonS3 is actually injectable directly. Here we are storing a provider of AmazonS3 because we are within a
    // command, and idiomatic way is to use lazy dependency resolution in commands.
    private Provider<AmazonS3> s3Provider;

    public ListS3KeysCommand(Provider<AmazonS3> s3Provider) {
        super(createMetadata());
        this.s3Provider = s3Provider;
    }

    private static CommandMetadata createMetadata() {
        return CommandMetadata
                .builder(ListS3KeysCommand.class)
                .description("Lists contents of a specified S3 bucket")
                .build();
    }

    @Override
    public CommandOutcome run(Cli cli) {

        String bucket = cli.optionString(S3Main.BUCKET_OPTION);
        if (bucket == null) {
            return CommandOutcome.failed(-1, "No 'bucket' option is specified");
        }

        AmazonS3 s3 = s3Provider.get();

        for (S3ObjectSummary objectSummary : s3.listObjectsV2(bucket).getObjectSummaries()) {
            System.out.printf(" - %s (size: %d)\n", objectSummary.getKey(), objectSummary.getSize());
        }

        return CommandOutcome.succeeded();
    }
}
