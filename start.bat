@echo off
REM Startar bilverkstadsprojektet (Spring Boot) pa http://localhost:8080

cd /d "%~dp0"

if exist mvnw.cmd (
    call mvnw.cmd spring-boot:run
) else (
    where mvn >nul 2>nul
    if errorlevel 1 (
        echo Maven hittades inte. Installera Maven eller anvand Maven Wrapper ^(mvnw^).
        exit /b 1
    )
    call mvn spring-boot:run
)
