<h1 align="center"> Bank Microservices </h1> <br>

<p align="center">
  x bank microservice is responsible to handle all bank requests
</p>

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Testing](#testing)
- [API](#requirements)

## Introduction

x-bank-java-service is responsible to hold the services to retrieve and manage all bank entities

## Features

* authenticate and authorize user requests
* connect to the bank database
* retrieve accounts details

## Requirements

The application can be run locally, the requirements for each setup are listed below.

### Local

* [Java 8 SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven](https://maven.apache.org/download.cgi)

### Run Local

```bash
$ mvn spring-boot:run
```

Application will run by default on port `9090`

Configure the port by changing `server.port` in __application.yml__

## Testing

Test cases are provided along with the code using Junit

## API

Swagger Ui is provided to show all api services

## SonarQube

Download [Docker](https://docs.docker.com/get-docker/)
Download [SonarQube](https://www.sonarqube.org/downloads/)

## or with docker by :

docker run-d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube