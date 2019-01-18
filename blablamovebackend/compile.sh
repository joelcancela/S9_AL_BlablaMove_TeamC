#!/bin/bash

mvn clean package

docker build -t "al/ws/dashboardservice" .