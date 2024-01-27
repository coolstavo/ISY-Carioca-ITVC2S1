package Server;

public interface Challengeable {

    void SendChallenge(String speler, String Speltype);

    void AcceptChallenge(int challengeNr);

    void ReceiveChallenge(int challengeNr);

    void DeclineChallenge(int challengeNr);

    void Subscribe(String speltype);

}
