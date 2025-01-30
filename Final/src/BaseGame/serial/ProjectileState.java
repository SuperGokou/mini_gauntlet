package BaseGame.serial;

import BaseGame.Bits;
import BaseGame.Direction;
import BaseGame.projectiles.DogFireball;
import BaseGame.projectiles.ElfArrow;
import BaseGame.projectiles.Projectile;
import BaseGame.projectiles.WizardFireball;
import jig.Vector;

import java.io.Serializable;

public class ProjectileState implements Serializable {
    private final float x;
    private final float y;
    private final float vX;
    private final float vY;
    private final byte type;

    public ProjectileState(Projectile p) {
        Vector v = p.getVelocity();

        byte type = (byte)(p.getType().toByte() << Constraints.DIRECTION_BITS);
        type |= p.getDirection().toByte();

        x = p.getX();
        y = p.getY();
        vX = v.getX();
        vY = v.getY();
        this.type = type;

        assert getType() == p.getType();
        assert getDirection() == p.getDirection();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector getVelocity() {
        return new Vector(vX, vY);
    }

    public Direction getDirection() {
        byte value = (byte)Bits.getFieldValue(type, Constraints.DIRECTION_BITS, 0);
        return Direction.fromByte(value);
    }

    public ProjectileType getType() {
        byte value = (byte)Bits.getFieldValue(
                type,
                Constraints.PROJECTILE_TYPE_BITS,
                Constraints.DIRECTION_BITS
        );
        return ProjectileType.fromByte(value);
    }

    public Projectile getProjectile() {
        switch (getType()) {
        case WIZARD:
            return new WizardFireball(x, y, getVelocity(), getDirection());
        case DOG:
            return new DogFireball(x, y, getVelocity(), getDirection());
        case ELF:
            return new ElfArrow(x, y, getVelocity(), getDirection());
        default:
            return null;
        }
    }
}
