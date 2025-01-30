package BaseGame.serial;

import BaseGame.Bits;

import java.util.Arrays;
import java.util.List;

public enum PlayerType {
    ELF, SHAMAN, WARRIOR, WIZARD;

    public byte toByte() {
        List<PlayerType> values = Arrays.asList(PlayerType.values());
        assert values.contains(this);
        assert values.size() - 1 <= Bits.maxFieldValue(Constraints.PLAYER_TYPE_BITS);
        return (byte)values.indexOf(this);
    }

    public static PlayerType fromByte(byte id) {
        List<PlayerType> values = Arrays.asList(PlayerType.values());
        assert id < values.size();
        return values.get(id);
    }
}
