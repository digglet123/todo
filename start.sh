#!/bin/bash

cd ./todo-api
./mvnw clean install
docker build -t example/todo .
cd ..
docker compose up -d



