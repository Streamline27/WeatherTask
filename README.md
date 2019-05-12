# How to use it


### Docker

To launch the app use docker run or docker compose. Adjust volume and port to suit your needs.

Docker run example:
```
docker run -e "spring.profiles.active=docker" \
           -p 8190:8080 -h mintos.weathertask \
           -v "/Users/Vladislav/Development/Home/Kotlin/logs:/logs" \
           streamline27/mintos.weathertask:latest

```
Docker compose example:
```
version: '3.5'
services:
  mintos.weathertask:
    build: ./
    image: streamline27/mintos.weathertask:latest
    container_name: mintos.weathertask
    ports:
      - "8190:8080"
    environment:
      - "spring.profiles.active=docker"
#    volumes:
#      - "/Users/Vladislav/Development/Home/Kotlin/logs:/logs"
```

### Intellij

App can be launched from intellij by just pressing green triangle. It will be launched on port 8190 then.