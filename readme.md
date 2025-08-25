## Prerequisites

1. Maven version 3.9.11
2. Java version 20
3. Docker compatible with 26.1.1 version


## How to run using file log

1. Open command line
2. Build project using command: `mvn clean compile assembly:single`
3. Run project using command: `java -cp target/FinalProject1-1.0-SNAPSHOT-jar-with-dependencies.jar com.jul.Main`

## How to run using db log

1. Open command line
2. Start postgresql docker container: `docker-compose up -d`
3. Create database table using [script](./db/create_table_record_log.sql)
4. Build project using command: `mvn clean compile assembly:single`
5. Run project using command: `java -cp target/FinalProject1-1.0-SNAPSHOT-jar-with-dependencies.jar com.jul.Main --db`


## Test Case

1. Move all files from folder [./test/success](./test/success) to folder [./input](./input)
2. Select operation with code 1
3. Make sure that the [./archive](./archive) folder contains files
4. Select operation with code 2
5. Make sure that console has log records
6. Move all files from folder [./test/failure](./test/failure) to folder [./input](./input)
7. Select operation with code 1
8. Make sure that the [./archive](./archive) folder contains files
9. Select operation with code 3
10. Print dates in format dd.MM.yyyy HH.mm.ss, for example (25.08.2025 22:55:00)
11. Make sure that console has log records
12. Select operation with code 0 to exit program

## Folders

| Name    | Description                                |
|---------|--------------------------------------------|
| input   | folder with input text transfers           |
| output  | folder with text log file                  |
| test    | folder with transfer examples              |
| archive | folder with processed input text transfers |
| db      | folder with accounts data and sql script   |

## Architecture

![](./Диаграмма.svg "Архитектура")