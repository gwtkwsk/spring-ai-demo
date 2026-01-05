# Spring AI Demo

## Run LLM locally

```
ollama run llama3.1:8b
```

## Run the app

```
./gradlew bootRun
```

## Chat with LLM

Use the `requests.http` file.

## Create Mongo database

```
docker volume create springaidemo-data

podman run -d \
   --name springaidemo-mongo \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=pass \
  -e MONGO_INITDB_DATABASE=springaidemo \
  -v springaidemo-data:/data/db \
  mongo:8.2.3
```
