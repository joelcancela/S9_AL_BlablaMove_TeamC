#!/bin/bash

docker login

cd ./kpi
docker build -t blablamove/api-gateway/core/kpi .
docker push blablamove/api-gateway/core/kpi
cd ..

cd ./delivery
docker build -t blablamove/api-gateway/core/delivery .
docker push blablamove/api-gateway/core/delivery
cd ..

cd ./user
docker build -t blablamove/api-gateway/core/user .
docker push blablamove/api-gateway/core/user
cd ..
