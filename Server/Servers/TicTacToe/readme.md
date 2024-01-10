# New Game Server

Game list
* Tic-tac-toe
* Reversi [1] 
* Connect4
* ... <small> soon tm </small>

HTTP server (API & Frontend-app): port 8081

Game server: port 7789

Vue development server: port 8080 (development only)

<br/>

<small>[1] : (We implement a more modern version that is more in line of Othello where players keep on playing if the other player cannot move, but the game starts with 4 fixed placed stones.) </small>

<br/>

## Running the server
Download the server and run `java -jar newgameserver-*VERSION*.jar` where `*VERSION*` is the current version.
This will start the Game Server and the HTTP server. 


**running a service on a different port:** <br/>
To change the port of the webserver and/or gameserver you should change them in `server.properties`. Read the official (dutch) manual for more information on configuration.

<br/>

## Getting Started with the code

**Building `.jar` including Frontend-app** 

Linux: `./gradlew build`

Windows: `./gradlew.bat build`

Intellij: Gradle tab => Tasks => build => double-click "build"

This will build the Vue frontend app AND the Java server and include both in the same `.jar` file.

**Running `.jar`** </br>
`java -jar build/libs/newgameserver-*VERSION*.jar` where `*VERSION*` is the current version.
This will start the Game Server and the HTTP server.

The HTTP server will handle API requests and will serve the frontend-app. 

### Api documentation
The Frontend uses a http and websocket api to get the data from the backend.
The websocket API is documented in websocket_protocol.md

### Gradle reference documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/gradle-plugin/reference/html/#build-image)
* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

