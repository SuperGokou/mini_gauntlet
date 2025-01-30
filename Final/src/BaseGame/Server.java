package BaseGame;

import BaseGame.net.ClientMessage;
import BaseGame.net.NetConfig;
import BaseGame.net.ServerMessage;
import BaseGame.net.ServerMessenger;
import BaseGame.serial.GameState;
import BaseGame.states.server.GameOverState;
import BaseGame.states.server.PlayingState;
import BaseGame.states.server.StartUpState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents the game instance running on the server.
 */
public class Server extends BaseGame {
    public Set<Integer> keysPressed;
    public ServerMessenger sender;
    public Socket senderClient;

    private Thread receiver;
    volatile boolean receiverReady;

    public Server(String title, int width, int height) {
        super(title, width, height);

        receiverReady = false;
        keysPressed = Collections.newSetFromMap(new ConcurrentHashMap<>());
        receiver = new Thread(this::receiverThread);
        receiver.start();

        Pair<ServerMessenger, Socket> serverClient = initLocalMessenger(NetConfig.SERVER_WRITE_PORT);
        sender = serverClient.left;
        senderClient = serverClient.right;

        while (!receiverReady) {
            try {
                Thread.sleep(5, 0);
            } catch (InterruptedException ignored) {
            }
        }
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new StartUpState());
        addState(new PlayingState());
        addState(new GameOverState());

        initGameState();
    }

    public static void fail(Exception e) {
        e.printStackTrace();
        System.exit(1);
    }

    /**
     * Initialize a ServerMessenger on localhost at the given port, and wait for a single client to connect.
     * @param port The port to listen on.
     * @return A non-null ServerMessenger instance and a Socket that represents a client to the messenger.
     */
    private static Pair<ServerMessenger, Socket> initLocalMessenger(int port) {
        ServerMessenger messenger = null;
        Socket client = null;

        try {
            messenger = new ServerMessenger(port);
            client = messenger.accept();
        } catch (IOException e) {
            fail(e);
        }

        return new Pair<>(messenger, client);
    }

    /**
     * Wait for an incoming connection from a client, then receive messages from the client and process them.
     */
    private void receiverThread() {
        Pair<ServerMessenger, Socket> serverClient = initLocalMessenger(NetConfig.SERVER_READ_PORT);
        ServerMessenger receiver = serverClient.left;
        Socket client = serverClient.right;
        receiverReady = true;

        // block until a message is received, then queue it for the main thread
        while (true) {
            ClientMessage inMessage = null;

            try {
                inMessage = receiver.get(client);
            } catch (IOException | ClassNotFoundException e) {
                fail(e);
            }

            if (inMessage != null) {
                System.out.println("Received client message: " + inMessage);
                inMessage.visit(keysPressed::add, keysPressed::remove);
            }
        }
    }

    @Override
    protected void postUpdateState(GameContainer container, int delta) throws SlickException {
        GameState state = new GameState(this);
        ServerMessage stateMsg = new ServerMessage(ServerMessage.Type.STATE, state);
        boolean stateSent = false;

        while (!stateSent) {
            try {
                sender.put(senderClient, stateMsg);
                stateSent = true;
            } catch (IOException e) {
                fail(e);
            }
        }

        System.out.println("Sent message: " + stateMsg);
    }

    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new Server("Gauntlet", 800, 600));
            app.setUpdateOnlyWhenVisible(false);
            app.setAlwaysRender(true);
            app.setDisplayMode(900, 680, false);
            app.setTargetFrameRate(60);
            app.setVSync(true);
            app.start();
        } catch (SlickException e) {
            fail(e);
        }
    }
}
