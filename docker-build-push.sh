#!/bin/bash

docker login

cd ./adminMarketingUI
docker build -t blablamove/fe-admin-marketing-ui .
docker push blablamove/fe-admin-marketing-ui:latest
cd ..

cd ./clientUI
docker build -t blablamove/fe-client-ui .
docker push blablamove/fe-client-ui:latest
cd ..

cd ./blablamovebackend
docker build -t blablamove/be-dashboard-service .
docker push blablamove/be-dashboard-service:latest
cd ..
