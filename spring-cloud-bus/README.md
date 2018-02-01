# Spring Cloud Bus
Store properties file in git repo with spring cloud config, spring cloud bus to publish event between config server and client

## Getting Started

### In server
The application.yml config :
```
server:
  port: 8888

spring:
  cloud:
    config:
      server:
        git:
          # git repo store properties file
          uri: <git repo>
          ignoreLocalSshSettings: true
          privateKey: <ssh private key>
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest

security:
  user:
    name: root
    password: s3cr3t
```
root/s3cr3t: will config in bootstrap file of client to connect config server

### In client
You must be config bootstrap.[yml/properties] follow [this](http://cloud.spring.io/spring-cloud-static/spring-cloud-config/1.4.0.RELEASE/multi/multi__spring_cloud_config_client.html) in client server.

With this example scheduler module:
```
spring:
  application:
  	# config name of client module
    name: scheduler
  profiles:
    active: alpha
  cloud:
    config:
      uri: http://localhost:8888
      # username/password of config-server
      username: root
      password: s3cr3t
```

Push properties file to here with name of properties file follow syntax:

```
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties

```
example: client's name is scheduler and run with profile alpha will call api:
```
curl -X GET http://root:s3cr3t@localhost:8888/scheduler/alpha
or
curl -X GET http://localhost:8888/scheduler/alpha -H 'authorization: Basic cm9vdDpzM2NyM3Q='
```


If you want to refresh properties after modify properties file on server, you need config:
* Add @RefreshScope for class holder value of properties is modified
* Call
```
curl -X POST \
  http://localhost:8888/monitor \
  -H 'authorization: Basic cm9vdDpzM2NyM3Q=' \
  -H 'content-type: application/x-www-form-urlencoded' \
  -d path=scheduler
```

If you are developer and hope running server with changed properties without push changed to git repo store properties file you need run server with native profile with "spring.profiles.active=native".