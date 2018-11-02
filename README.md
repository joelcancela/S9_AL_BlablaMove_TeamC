# S9_AL_BlablaMove_TeamC

## Kafka only

```bash
docker-compose -f docker-compose_kafka_only.yml up
```

Put this entry in your hosts file to test Kafka locally:

```text
127.0.0.1 kafka
```

## blablamovebackend

```bash
mvn spring-boot:run
```

How to start a local influxdb :
```bash
docker run -e INFLUXDB_ADMIN_ENABLED=true  -e INFLUXDB_ADMIN_USER=admin -e INFLUXDB_ADMIN_PASSWORD=admin  -p 8086:8086 -v influxdb:/var/lib/influxdb influxdb
```
## adminMarketingUI & clientUI

```bash
npm install && ng serve
```