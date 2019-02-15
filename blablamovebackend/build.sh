#!/bin/bash

mvn clean package

docker build -t blablamove/be-dashboard-service .
