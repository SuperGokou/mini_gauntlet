package BaseGame.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMessenger {
    private final ServerSocket sock;

    public ServerMessenger(int port) throws IOException {
        sock = new ServerSocket(port);
    }

    public Socket accept() throws IOException {
        return sock.accept();
    }

    public void put(Socket client, ServerMessage msg) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        out.writeObject(msg);
    }

    public ClientMessage get(Socket client) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
        Object obj = in.readObject();

        if (obj instanceof ClientMessage)
            return (ClientMessage)obj;
        else
            return null;
    }

    public void close() throws IOException {
        sock.close();
    }
}
