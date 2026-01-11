# Spring AI Demo

## Run LLM locally

```
ollama run llama3.1:8b
ollama pull nomic-embed-text:v1.5
```

## Run the app

```
./gradlew bootRun
```

## Chat with LLM

Use the `requests.http` file.

## Create Mongo database

```
podman volume create springaidemo-mongo-data

podman run -d \
  --name springaidemo-mongo \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=pass \
  -e MONGO_INITDB_DATABASE=springaidemo \
  -v springaidemo-mongo-data:/data/db \
  -p 27017:27017 \
  mongo:8.2.3

podman exec -it springaidemo-mongo mongosh -u admin -p pass

use springaidemo

db.createUser({
  user: 'springaidemo',
  pwd: 'pass',
  roles: [{ role: 'readWrite', db: 'springaidemo' }]
});
```

## Create Qdrant database

podman volume create springaidemo-qdrant-data

podman run -d \
  --name springaidemo-qdrant \
  -p 6333:6333 -p 6334:6334 \
  -v springaidemo-qdrant-data:/qdrant/storage \
  qdrant/qdrant

Qdrant dashboard is available at http://localhost:6333/dashboard