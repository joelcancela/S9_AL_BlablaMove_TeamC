FROM denvazh/gatling:2.3.1

COPY loadTestingData/conf conf
COPY loadTestingData/user-files user-files

ENTRYPOINT ip -4 route list match 0/0 | awk '{print $3" host.docker.internal"}' >> /etc/hosts && /bin/bash