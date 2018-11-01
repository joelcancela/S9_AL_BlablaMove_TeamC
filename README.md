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

## adminMarketingUI & clientUI

```bash
npm install && ng serve
```