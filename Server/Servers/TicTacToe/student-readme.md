# New Game Server

Welcome to one of the best parts of your Hanze study.
During this project you will write a client that can communicate with a game server.

With this game server you can play the following games:
* Tic-tac-toe
* Reversi [1]
* Connect4
* ... <small> and possibly many more </small>

<small>[1] : (We implement a more modern version that is more in line of Othello where players keep on playing if the other player cannot move, but the game starts with 4 fixed placed stones.) </small>

<br/>

## Running the server
Download the server and run `java -jar newgameserver.jar`.
This will start the Game Server and an HTTP server.

Your client will talk with the Game Server on port 7789 using the text based protocol explained in the protocol.txt <br/>
To see the games and tournaments there is a webfrontend available on port [8081](http:localhost:8081).

**Admin Account:** <br/>
To start a tournament you need an admin account on the webfrontend. Everything else can be done without an account. <br/>
The default password is: `HanzeGameserverAdmin`

**Changing the password for the admin account:** <br/>
You can set the password for this account by starting the server with a special parameter.
`java -jar newgameserver.jar --set-password mynewpassword123!`
After this you can start the server the normal way and login using your new password.

**Changing the config of the server:** <br/>
All configurations will be stored in `server.properties`. 
This file will automatically be created for you when you start the server for the first time. 
Most settings in this file can also be changed in the admin panel on the webfrontend.