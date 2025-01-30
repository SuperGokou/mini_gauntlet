package BaseGame.serial;

import BaseGame.Bits;

import java.util.Arrays;
import java.util.List;

public enum ProjectileType {
    DOG, ELF, WIZARD;

    public byte toByte() {
        List<ProjectileType> values = Arrays.asList(ProjectileType.values());
        assert values.contains(this);
        assert values.size() - 1 <= Bits.maxFieldValue(Constraints.PROJECTILE_TYPE_BITS);
        return (byte)values.indexOf(this);
    }

    public static ProjectileType fromByte(byte id) {
        List<ProjectileType> values = Arrays.asList(ProjectileType.values());
        assert id < values.size();
        return values.get(id);
    }
}
