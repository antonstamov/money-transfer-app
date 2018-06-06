# Akka HTTP Application

# Backend

## Build & Deploy


To build: 

`sbt assembly`

To run app: 

``` 
export HTTP_PORT=9000 && \
export JDBC_URL=jdbc:postgresql://localhost/contracts && \
export JDBC_USER=user && \
export JDBC_PASSWORD= && \
export AWS_ACCESS_KEY= && \
export AWS_SECRET_KEY= && \
export AWS_ENDPOINT_URL= && \
export S3_REGION=us-west-2 && \
export S3_BUCKET=creditgate24 && \
export S3_PREFIX=cg24 && \
export RABBITMQ_HOST=localhost && \
export RABBITMQ_PORT=5672 && \
export RABBITMQ_USERNAME=guest && \
export RABBITMQ_PASSWORD=guest && \
export RABBITMQ_IN_QUEUE=in-queue && \
export RABBITMQ_OUT_QUEUE=out-queue && \
java -jar target/scala-2.12/cg24-contract-assembly-1.0-SNAPSHOT.jar 
```


## Credentials

All credentials stored in `src/main/resources/application.conf`, 
separated by environments
and encrypted by `utils.ConfigPimping.RichConfig.encrypt(s: String)`.

To decrypt use `utils.ConfigPimping.RichConfig.decrypt(s: String)`.


## Available ENVIRONMENT Variables

```
ENCRYPTOR_PASSWORD // password for decrypting encrypted config properties
HTTP_PORT
JDBC_URL
JDBC_USER
JDBC_PASSWORD
AWS_ACCESS_KEY
AWS_SECRET_KEY
AWS_ENDPOINT_URL
S3_REGION
S3_BUCKET
S3_PREFIX
RABBITMQ_HOST
RABBITMQ_PORT
RABBITMQ_USERNAME
RABBITMQ_PASSWORD
RABBITMQ_IN_QUEUE
RABBITMQ_OUT_QUEUE
```

# Frontend  

`cd ui`

## To change default ports
`sed -i "s/8080/7070/" config/index.js`

`sed -i "s/localhost:9000/cg24.fusionworks.md:7000/g" src/main.js`

## Build UI
`npm install`

`npm run build`

copy `dist/` directory to web-servable directory
