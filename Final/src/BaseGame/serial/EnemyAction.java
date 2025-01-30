package BaseGame.serial;

import BaseGame.Bits;

import java.util.Arrays;
import java.util.List;

public enum EnemyAction {
    STAND, MOVE;

    public byte toByte() {
        List<EnemyAction> values = Arrays.asList(EnemyAction.values());
        assert values.size() - 1 <= Bits.maxFieldValue(Constraints.ENEMY_ACTION_BITS);
        return (byte)values.indexOf(this);
    }

    public static EnemyAction fromByte(byte b) {
        List<EnemyAction> values = Arrays.asList(EnemyAction.values());
        assert b < values.size();
        return values.get(b);
    }
}
