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
2. Start postgresql docker container: `docker-compose up`
3. Create database table using [script](./db/create_table_record_log.sql)
4. Build project using command: `mvn clean compile assembly:single`
5. Run project using command: `java -cp target/FinalProject1-1.0-SNAPSHOT-jar-with-dependencies.jar com.jul.Main --db`


Folders:

| Name    | Description                                |
|---------|--------------------------------------------|
| input   | folder with input text transfers           |
| output  | folder with text log file                  |
| test    | folder with transfer examples              |
| archive | folder with processed input text transfers |
| db      | folder with accounts data and sql script   |

Architecture:

![](./Диаграмма.svg "Архитектура")