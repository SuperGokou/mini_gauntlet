package BaseGame;

import BaseGame.net.ClientMessage;
import BaseGame.net.ClientMessenger;
import BaseGame.net.NetConfig;
import BaseGame.net.ServerMessage;
import BaseGame.states.client.GameOverState;
import BaseGame.states.client.PlayingState;
import BaseGame.states.client.StartUpState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Represents a client to the game server. The client performs four primary functions:
 *
 *  1. Receives user inputs
 *  2. Sends user inputs to the server
 *  3. Receives the game state from the server (partially implemented)
 *  4. Renders the game state (unimplemented)
 */
public class Client extends BaseGame {
    public ConcurrentLinkedQueue<ClientMessage> outMessages; // messages to send to the client
    public ConcurrentLinkedQueue<ServerMessage> inMessages;  // messages received from the server

    Thread sender;
    Thread receiver;

    volatile boolean senderReady;
    volatile boolean receiverReady;

    public Client(String title, int width, int height) {
        super(title, width, height);

        senderReady = false;
        receiverReady = false;

        inMessages = new ConcurrentLinkedQueue<>();
        outMessages = new ConcurrentLinkedQueue<>();
        sender = new Thread(this::senderThread);
        receiver = new Thread(this::receiverThread);

        sender.start();
        receiver.start();

        while (!senderReady || !receiverReady) {
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

    private static void fail(Exception e) {
        e.printStackTrace();
        System.exit(1);
    }

    /**
     * Initialize a ClientMessenger connecting to the server at localhost on the given port.
     * @param port The port to connect to.
     * @return A non-null ClientMessenger instance.
     */
    private static ClientMessenger initLocalMessenger(int port) {
        ClientMessenger messenger = null;

        try {
            InetAddress localhost = InetAddress.getLocalHost();
            InetSocketAddress serverAddress = new InetSocketAddress(localhost, port);
            messenger = new ClientMessenger(serverAddress);
        } catch (IOException e) {
            fail(e);
        }

        return messenger;
    }

    /**
     * Initialize a data-sending connection with the server, then send pending messages in `outMessages` to the
     * server as they arrive.
     */
    private void senderThread() {
        ClientMessenger sender = initLocalMessenger(NetConfig.CLIENT_WRITE_PORT);
        senderReady = true;

        // check for pending messages and send them if present
        while (true) {
            ClientMessage outMessage = outMessages.poll();

            if (outMessage != null) {
                try {
                    System.out.println("Sent message: " + outMessage);
                    sender.put(outMessage);
                } catch (IOException e) {
                    fail(e);
                }
            }
        }
    }

    /**
     * Initialize a data-receiving connection with the server, then receive messages from the server and store them
     * in `inMessages`.
     */
    private void receiverThread() {
        ClientMessenger receiver = initLocalMessenger(NetConfig.CLIENT_READ_PORT);
        receiverReady = true;

        // block until a message is received, then queue it for the main thread
        while (true) {
            ServerMessage inMessage = null;

            try {
                inMessage = receiver.get();
            } catch (IOException | ClassNotFoundException e) {
                fail(e);
            }

            if (inMessage != null) {
                System.out.println("Received server message: " + inMessage);
                inMessages.add(inMessage);
            }
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        ClientMessage outMessage = new ClientMessage(ClientMessage.Type.KEY_DOWN, key);
        outMessages.add(outMessage);
    }

    @Override
    public void keyReleased(int key, char c) {
        ClientMessage outMessage = new ClientMessage(ClientMessage.Type.KEY_UP, key);
        outMessages.add(outMessage);
    }

    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new Client("Gauntlet", 800, 600));
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
