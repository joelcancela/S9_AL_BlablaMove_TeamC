#!/bin/bash

docker login

cd ./kpi
docker build -t blablamove/core-kpi .
docker push blablamove/core-kpi:latest
cd ..

cd ./delivery
docker build -t blablamove/core-delivery .
docker push blablamove/core-delivery:latest
cd ..

cd ./user
docker build -t blablamove/core-user .
docker push blablamove/core-user:latest
cd ..
