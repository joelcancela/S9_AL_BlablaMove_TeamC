#!/bin/bash
cd ./kpi
docker build -t blablamove/api-gateway/core/kpi .
docker push blablamove/api-gateway/core/kpi:latest
cd ..

cd ./delivery
docker build -t blablamove/api-gateway/core/delivery .
docker push blablamove/api-gateway/core/delivery:latest
cd ..

cd ./user
docker build -t blablamove/api-gateway/core/user .
docker push blablamove/api-gateway/core/user:latest
cd ..
