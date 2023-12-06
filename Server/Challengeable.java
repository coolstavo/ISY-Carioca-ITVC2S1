package Server;

public interface Challengeable {

    public void SendChallenge(String speler, String Speltype);

    public void AcceptChallenge(int challengeNr);

    public void ReceiveChallenge(int challengeNr);

    public void DeclineChallenge(int challengeNr);

    public void Subscribe(String speltype);

}
