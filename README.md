# MicroForum

## Config server
- URL: http://localhost:8888
- Service-specific config: http://localhost:8888/{service-name}/{profile}
- Example: http://localhost:8888/posts/prod, http://localhost:8888/discovery.server/default

    Encryption CURL:
    ```shell
    curl --location 'http://localhost:8888/encrypt' \
    --header 'Content-Type: text/plain' \
    --data 'Value to encrypt'
    ```
  
## Eureka service discovery
- URL: http://localhost:7777
- Post service shutdown curl:
  ````shell
  curl --location --request POST 'http://localhost:3100/actuator/shutdown'
  ````

## API gateway
- URL: http://localhost:9999
- All routes: http://localhost:9999/actuator/gateway/routes

## Logging, Monitoring, Tracing
#### Monitoring
All the services has monitoring enabled, see config servers from below:
- Config server actuator monitoring URL(JSON): http://localhost:8888/actuator/metrics
- Config server actuator monitoring URL(PROMETHEUS): http://localhost:8888/actuator/prometheus
