#!/bin/bash

# J2E FE/BE

cd ./adminMarketingUI
docker build -t blablamove/fe-admin-marketing-ui .
cd ..

cd ./clientUI
docker build -t blablamove/fe-client-ui .
cd ..

cd ./blablamovebackend
chmod +x build.sh
./build.sh
cd ..

# Core

cd ./kpi
docker build -t blablamove/core-kpi .
cd ..

cd ./delivery
docker build -t blablamove/core-delivery .
cd ..

cd ./user
docker build -t blablamove/core-user .
cd ..
