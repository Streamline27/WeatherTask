# How to use it

### AWS

Docker container is deployed to aws.

```
http://18.221.110.125:8190/weather
```
To connect use this: (But you won't be able to do that without certificates anyway)
```
ssh -i ~/.ssh/MyKeyPair.pem ubuntu@18.221.110.125
```


### Docker

To launch the app use docker run or docker compose. Adjust volume and port to suit your needs.

Docker run example:
```
docker run -e "spring.profiles.active=docker" \
           -p 8190:8190 -h mintos.weathertask \
           -v "/Users/Vladislav/Development/Home/Kotlin/logs:/logs" \
           -v "/Users/Vladislav/Development/Home/Kotlin/data:/data" \
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
      - "8190:8190"
    environment:
      - "spring.profiles.active=docker"
#    volumes:
#      - "/Users/Vladislav/Development/Home/Kotlin/logs:/logs"
#      - "/Users/Vladislav/Development/Home/Kotlin/data:/data"
```

### Intellij

App can be launched from intellij by just pressing green triangle. It will be launched on port 8190 then.