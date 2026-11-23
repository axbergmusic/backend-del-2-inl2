#!/usr/bin/env bash
# Startar bilverkstadsprojektet (Spring Boot) på http://localhost:8080

set -e
cd "$(dirname "$0")"

if [ -x "./mvnw" ]; then
    ./mvnw spring-boot:run
elif command -v mvn >/dev/null 2>&1; then
    mvn spring-boot:run
else
    echo "Maven hittades inte. Installera Maven eller använd Maven Wrapper (mvnw)." >&2
    exit 1
fi
