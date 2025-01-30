package BaseGame.serial;

import BaseGame.Bits;

import java.util.Arrays;
import java.util.List;

public enum PlayerAction {
    STAND, MOVE, ATTACK;

    public byte toByte() {
        List<PlayerAction> values = Arrays.asList(PlayerAction.values());
        assert values.size() <= Bits.maxFieldValue(Constraints.PLAYER_ACTION_BITS);
        return (byte)values.indexOf(this);
    }

    public static PlayerAction fromByte(byte b) {
        List<PlayerAction> values = Arrays.asList(PlayerAction.values());
        assert b < values.size();
        return values.get(b);
    }
}
