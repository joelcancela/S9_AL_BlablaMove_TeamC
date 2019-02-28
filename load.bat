cd loadTesting
docker build -t loadtester .
docker run -it --rm -v /loadTestingData/results:/opt/gatling/results loadtester