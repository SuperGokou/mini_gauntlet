package BaseGame.serial;

import BaseGame.Bits;
import BaseGame.Direction;
import BaseGame.enemies.BaseEnemy;
import jig.Vector;
import org.newdawn.slick.Animation;

import java.io.Serializable;

public class EnemyState implements Serializable {
    private final float x;
    private final float y;
    private final float vX;
    private final float vY;
    private final byte graphicState;
    private final byte actionByte;

    public EnemyState(BaseEnemy e) {
        Vector v = e.getVelocity();
        x = e.getX();
        y = e.getY();
        vX = v.getX();
        vY = v.getY();
        actionByte = e.getCurrentAction().toByte();

        Animation enemyAnimation = e.getCurrentAnimation();
        int animationFrame = (enemyAnimation != null) ? enemyAnimation.getFrame() : 0;
        byte orientationByte = e.getDirection().toByte();
        byte typeByte = e.getType().toByte();

        byte graphicState = 0;
        int bitsRemaining = Byte.SIZE;

        bitsRemaining -= Constraints.DIRECTION_BITS;
        graphicState |= orientationByte << bitsRemaining;

        bitsRemaining -= Constraints.ENEMY_TYPE_BITS;
        graphicState |= typeByte << bitsRemaining;

        bitsRemaining -= Constraints.ENEMY_ANIMATION_FRAME_BITS;
        graphicState |= animationFrame << bitsRemaining;

        assert bitsRemaining == 0;
        this.graphicState = graphicState;

        assert getType() == e.getType();
        assert getAction() == e.getCurrentAction();
        assert getDirection() == e.getDirection();
        assert getAnimationFrame() == animationFrame;
    }

    public Direction getDirection() {
        byte value = (byte)Bits.getFieldValue(
                graphicState,
                Constraints.DIRECTION_BITS,
                Constraints.ENEMY_ANIMATION_FRAME_BITS + Constraints.ENEMY_TYPE_BITS
        );
        return Direction.fromByte(value);
    }

    public EnemyType getType() {
        byte value = (byte) Bits.getFieldValue(
                graphicState,
                Constraints.ENEMY_TYPE_BITS,
                Constraints.ENEMY_ANIMATION_FRAME_BITS
        );
        return EnemyType.fromByte(value);
    }

    public int getAnimationFrame() {
        return Bits.getFieldValue(graphicState, Constraints.ENEMY_ANIMATION_FRAME_BITS, 0);
    }

    public EnemyAction getAction() {
        return EnemyAction.fromByte(actionByte);
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

    @Override
    public String toString() {
        return "EnemyState{" +
                "type=" + getType() +
                ", x=" + x +
                ", y=" + y +
                ", velocity=" + getVelocity() +
                ", direction=" + getDirection() +
                ", animationFrame=" + getAnimationFrame() +
                ", action=" + getAction() +
                '}';
    }
}
