#!/bin/bash

# Default assumption is that its running on MBP for local dev
ENABLESSL=${ENABLESSL:=true}
PASSWORD_SALT_NUM_ROUNDS=${PASSWORD_SALT_NUM_ROUNDS:=10}
DEFAULT_EXPIRY_MINUTES=${DEFAULT_EXPIRY_MINUTES:=15}
HTTP_SERVER_PORT=${HTTP_SERVER_PORT:=3434}
STATS_PASSWORD=${STATS_PASSWORD:=foo}

java -jar /peekaboo.jar \
    --enable.ssl=${ENABLESSL} \
    --password.salt.num.rounds=${PASSWORD_SALT_NUM_ROUNDS} \
    --secret.default.expiry.minutes=${DEFAULT_EXPIRY_MINUTES} \
    --http.server.port=${HTTP_SERVER_PORT} \
    --password.site.stats=${STATS_PASSWORD}
