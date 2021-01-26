#!/bin/bash

mvn clean package -DskipTests
scp -i ~/Public/KeyAWS/Key081020.pem runner/target/config-runner-jar-with-dependencies.jar  ubuntu@13.213.13.187:/home/ubuntu/workspace
