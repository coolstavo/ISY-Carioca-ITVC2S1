package Server;

// ServerEvents.java
/**
 * The `ServerEvents` enum represents various event types that the server can send to clients.
 * Each event type corresponds to a specific action or state in the game server's communication protocol.
 */
public enum ServerEvents {
    GAME_LIST,
    PLAYER_LIST,
    NEW_MATCH,
    YOUR_TURN,
    MOVE,
    LOSE,
    WIN,
    DRAW,
    CHALLENGE,
    CHALLENGE_CANCEL,
    ERROR,
    OK
}
