#!/usr/bin/env bash

docker volume create crv_mysql

docker run \
    --env-file .env \
    --mount type=volume,src=crv_mysql,dst=/var/lib/mysql \
    -p 3306:3306 \
    -d \
    mysql:latest