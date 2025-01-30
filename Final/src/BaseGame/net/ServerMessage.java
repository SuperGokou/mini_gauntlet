package BaseGame.net;


import BaseGame.serial.GameState;

import java.io.Serializable;
import java.util.function.Consumer;

public class ServerMessage implements Serializable {
    public enum Type implements Serializable {
        STATE,
    }

    private final Type type;
    private final Serializable data;

    public ServerMessage(Type type, Serializable data) {
        this.type = type;

        if (data == null) {
            this.data = null;
            return;
        }

        this.data = data;
    }

    public void visit(Consumer<GameState> stateConsumer) {
        switch (type) {
        case STATE:
            assert data instanceof GameState;

            if (stateConsumer != null) {
                stateConsumer.accept((GameState)data);
            }

            break;
        }
    }

    @Override
    public String toString() {
        return "ServerMessage{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }
}
