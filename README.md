# Spring AI Demo

## Run LLM locally

```
ollama run llama3.1:8b
ollama pull nomic-embed-text:v1.5
```

## Run devcontainer

```
./gradlew bootRun
```

## Chat with LLM

Use the `requests.http` file.

## Create Mongo database

```
podman volume create springaidemo-mongo-data

podman exec -it springaidemo_devcontainer-springaidemo-mongo-1 mongosh -u admin -p pass

db.createUser({
  user: 'springaidemo',
  pwd: 'pass',
  roles: [{ role: 'readWrite', db: 'springaidemo' }]
});
```

## Create Qdrant database

podman volume create springaidemo-qdrant-data

Qdrant dashboard is available at http://localhost:6333/dashboard