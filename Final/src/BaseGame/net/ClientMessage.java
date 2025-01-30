package BaseGame.net;

import java.io.Serializable;
import java.util.function.Consumer;

public class ClientMessage implements Serializable {
    public enum Type implements Serializable {
        KEY_DOWN,
        KEY_UP,
    }

    private final Type type;
    private final Serializable data;

    public ClientMessage(Type type, Serializable data) {
        this.type = type;

        if (data == null) {
            this.data = null;
            return;
        }

        this.data = data;
    }

    public void visit(Consumer<Integer> keyDownConsumer, Consumer<Integer> keyUpConsumer) {
        switch (type) {
        case KEY_DOWN:
            assert data instanceof Integer;

            if (keyDownConsumer != null) {
                keyDownConsumer.accept((Integer)data);
            }

            break;
        case KEY_UP:
            assert data instanceof Integer;

            if (keyUpConsumer != null) {
                keyUpConsumer.accept((Integer)data);
            }

            break;
        }
    }

    @Override
    public String toString() {
        return "ClientMessage{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }
}
