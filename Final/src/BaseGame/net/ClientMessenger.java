package BaseGame.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientMessenger {
    private final Socket sock;

    public ClientMessenger(InetSocketAddress serverAddress) throws IOException {
        sock = new Socket();
        sock.connect(serverAddress);
    }

    public void put(ClientMessage msg) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
        out.writeObject(msg);
    }

    public ServerMessage get() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
        Object obj = in.readObject();

        if (obj instanceof ServerMessage)
            return (ServerMessage)obj;
        else
            return null;
    }

    public void close() throws IOException {
        sock.close();
    }
}
