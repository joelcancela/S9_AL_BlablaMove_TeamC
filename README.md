# AL Project: BlablaMove

BlablaMove helps students move for cheap by using space onboard other people cars to move their goods and furniture as a swarm as those other people drive around. Participants trade points for car space. Points can be use to move their own goods later on. Short trips are combined together to form a longer delivery service.

Our group focuses in a variant about system monitoring. The main objectives were :

- Track events.
- Create a monitoring dashboard (system and business).

## Getting Started

### Prerequisites

- Docker

### Installing

```bash
./install.sh # Builds docker images
docker-compose up
./load.sh # (Optional) Launches load testing using Gatling
```

- Go to [localhost:4201](localhost:4201), to use the admin and marketing UI (BlablaMove internal dashboard).
- Go to [localhost:4202](localhost:4202), to use the client UI (BlablaMove status public website), inspired by [downdetector.com](https://downdetector.com/status/reddit)

## Build separately

### InfluxDB

```bash
docker run -e INFLUXDB_ADMIN_ENABLED=true  -e INFLUXDB_ADMIN_USER=admin -e INFLUXDB_ADMIN_PASSWORD=admin  -p 8086:8086 -v influxdb:/var/lib/influxdb influxdb
```

Put this entry in your hosts file to test the db locally:

```text
127.0.0.1 influxdb
```

### Kafka

```bash
docker-compose -f docker-compose_kafka_only.yml up
```

Put this entry in your hosts file to test Kafka locally:

```text
127.0.0.1 kafka
```

### BlablaMove back-end

```bash
mvn spring-boot:run
```

### AdminMarketingUI & ClientUI

```bash
npm install && ng serve
```
