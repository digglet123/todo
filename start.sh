#!/bin/bash

cd ./todo-api
./mvnw clean install
docker build -t todo-api .
cd ..

cd ./todo-app
docker build -t todo-app .
cd ..

docker compose up -d



