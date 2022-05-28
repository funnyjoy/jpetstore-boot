#!/bin/bash

PARAM=$1

if [[ "$PARAM" == "maven" || "$PARAM" == "all" ]]; then
    mvn clean install -DskipTests
fi

if [[ "$PARAM" == "docker" || "$PARAM" == "all" ]]; then
    docker build -t jaypark00/jpetstore:msa_v2 --build-arg JAR_FILE=jpetstore-boot-0.0.1-SNAPSHOT.war .
fi

if [[ "$PARAM" == "push" || "$PARAM" == "all" ]]; then
    docker push jaypark00/jpetstore:msa_v2
fi

