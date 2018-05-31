# bootique-aws-demo

A simple Bootique app demonstrating the usage of `bootique-aws` to access
Amazone Web Services. Specifically shows how to access AWS S3 with
`bootique-aws-s3`.

## Building

```
git clone git@github.com:bootique-examples/bootique-aws-demo.git
mvn clean package
```

## Configuration

Get an Amazon account that you can play with. Take a note of access key
and secret key. Make a copy of the sample config file, and enter both keys
in there:

```
cp config.sample.yml config.yml

# open config.yml in an editor and enter your keys and an AWS region for
# which they have permissions.
```


## Running

To see available options, run the app with `--help`:

```
java -jar target/bootique-aws-demo-1.0-SNAPSHOT.jar  -h
```

To list a bucket contents run `--list` command:

```
java -jar target/bootique-aws-demo-1.0-SNAPSHOT.jar \
    -c config.yml \
    -l \
    -b mybucket
```

To send a string of text to a bucket run `--send-text-to-s3` command:

```
java -jar target/bootique-aws-demo-1.0-SNAPSHOT.jar \
    -c config.yml \
    -s  \
    -b mybucket \
    -t "hello aws" \
    -p 'myfolder/myfile.txt'
```
