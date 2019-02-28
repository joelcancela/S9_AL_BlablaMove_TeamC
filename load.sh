#!/bin/bash
if [ $(uname -s) == "MINGW64_NT-10.0" ]
then
cd loadTesting
MSYS_NO_PATHCONV=1 docker run -it --rm -v ${PWD}/loadTestingData/conf:/opt/gatling/conf \
-v ${PWD}/loadTestingData/user-files:/opt/gatling/user-files \
-v ${PWD}/loadTestingData/results:/opt/gatling/results \
denvazh/gatling:2.3.1
else
cd loadTesting
docker build -t loadtester .
docker run -it --rm -v ${PWD}/loadTestingData/results:/opt/gatling/results loadtester
fi
cd ..