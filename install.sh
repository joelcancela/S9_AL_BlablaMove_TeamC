#!/bin/bash
cd adminMarketingUI
docker build -t al/fe/admin_marketing_ui .
cd ../blablamovebackend
./compile.sh
cd ../clientUI
docker build -t al/fe/client_ui .
cd ../core/delivery
docker build -t al/fe/core/delivery .
cd ../user
docker build -t al/fe/core/user .
