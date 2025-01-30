package BaseGame.serial;

import BaseGame.Bits;

import java.util.Arrays;
import java.util.List;

public enum EnemyType {
    BLUE, GREEN, RED, YELLOW, DOG, GHOST;

    public byte toByte() {
        List<EnemyType> values = Arrays.asList(EnemyType.values());
        assert values.contains(this);
        assert values.size() - 1 <= Bits.maxFieldValue(Constraints.ENEMY_TYPE_BITS);
        return (byte)values.indexOf(this);
    }

    public static EnemyType fromByte(byte id) {
        List<EnemyType> values = Arrays.asList(EnemyType.values());
        assert id < values.size();
        return values.get(id);
    }
}
