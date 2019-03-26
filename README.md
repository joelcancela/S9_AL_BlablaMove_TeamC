# S.A. Project: BlablaMove

BlablaMove helps students move for cheap by using space onboard other people cars to move their goods and furniture as a swarm as those other people drive around. Participants trade points for car space. Points can be use to move their own goods later on. Short trips are combined together to form a longer delivery service.

Our group focuses in a variant about system monitoring. The main objectives were :

- Track events (system and business).
- Create an appropriate dashboard.
- Try to make the app scalable as much as possible
- Measure response time perceived by the user and suggest strategies to contain it.
- Zoning: UK people move in July, rest of the Europe in August

## Getting Started

### Prerequisites

- Docker

### Installing

```bash
./install.sh # Builds docker images
cd legacy
docker-compose -f docker-compose_legacy.yml up
# In a new terminal
cd ..
./load.sh # (Optional) Launches load testing using Gatling and creates some random data (user connections, transactions)
```

- Go to [localhost:4201](http://localhost:4201), to use the admin and marketing UI (BlablaMove internal dashboard).
- Go to [localhost:4202](http://localhost:4202), to use the client UI (BlablaMove status public website), inspired by [downdetector.com](https://downdetector.com/status/reddit)

---

### API

In the folder ```core```, in every subfolder (```delivery```, ```kpi```, ```user```), there's a ```swagger.yaml```.
Otherwise you can read every ```app.py```, next to every annotation ```@app.route``` every route is declared.

---

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
# add '-c production' argument after ng serve to use production configuration
```