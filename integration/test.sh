#!/bin/bash

export CORE_NETWORK=core_net
export PROJECT=peekaboo
export container=${PROJECT}; docker stop $container; docker rm $container
export container=${PROJECT}_api; docker stop $container; docker rm $container

#docker network create ${PROJECT}_net

export HTTP_EXTERNAL=30000
export HTTP_INTERNAL=3434
export REPO=www.dockerhub.us
export LATEST_SHA=$(python get_docker_build_version.py)

docker run --name ${PROJECT}_api \
-p ${HTTP_EXTERNAL}:${HTTP_INTERNAL} \
-e DEFAULT_EXPIRY=15 \
-e SALT_NUM_ROUNDS=10 \
--net ${CORE_NETWORK} \
-d ${REPO}/${PROJECT}_api:${LATEST_SHA}

docker run -d --name ${PROJECT} \
-p 30001:80 \
-v `pwd`../peekaboo-ui/build:\
--net ${CORE_NETWORK} \
-d ${REPO}/${PROJECT}:${LATEST_SHA}

sleep 2

curl http://localhost:${HTTP_EXTERNAL}/public/health

