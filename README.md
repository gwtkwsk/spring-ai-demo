# Spring AI Demo

## Run LLM locally

```
ollama run llama3.1:8b
```

## Verify it's running

```
curl "http://localhost:11434/api/chat" -d '{
  "model": "llama3.1:8b",
  "stream": false,
  "messages": [
    {
      "role": "user",
      "content": "why is the sky blue?"
    }
  ]
}'
```
