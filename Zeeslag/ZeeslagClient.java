package Zeeslag;

import Server.Connection;

import java.net.ProtocolFamily;

public class ZeeslagClient {

    Game game;
    Connection connection;

    public void login(String playerName) {

    }

    public void subscribe(String type) {
        connection.SubscribeForGame(type);
    }

}