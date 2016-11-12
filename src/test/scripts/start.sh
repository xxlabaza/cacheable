#!/usr/bin/env bash

set -e

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

/usr/local/bin/docker-compose -f ${CURRENT_DIR}/../docker/docker-compose.yml down --volumes
/usr/local/bin/docker-compose -f ${CURRENT_DIR}/../docker/docker-compose.yml up -d

sleep 2
