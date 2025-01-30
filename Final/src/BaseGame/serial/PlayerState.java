package BaseGame.serial;

import BaseGame.Bits;
import BaseGame.Direction;
import BaseGame.classes.BasePlayer;
import org.newdawn.slick.Animation;

import java.io.Serializable;

public class PlayerState implements Serializable {
    private final float x;
    private final float y;
    private final byte graphicState;
    private final byte playerType;
    private final byte attackTimer;

    public PlayerState(BasePlayer player) {
        x = player.getX();
        y = player.getY();
        playerType = player.getType().toByte();

        Animation playerAnimation = player.getAnimation();
        byte orientationByte = player.getOrientation().toByte();
        byte actionByte = player.getCurrentAction().toByte();
        int animationFrame = (playerAnimation != null) ? playerAnimation.getFrame() : 0;
        int attackTimer = player.getAttackTimer();

        assert (player.getAnimation() == null) == (player.getCurrentAction() == PlayerAction.STAND);
        assert orientationByte <= Bits.maxFieldValue(Constraints.DIRECTION_BITS);
        assert actionByte <= Bits.maxFieldValue(Constraints.PLAYER_ACTION_BITS);
        assert animationFrame >= 0;
        assert animationFrame <= Bits.maxFieldValue(Constraints.PLAYER_ANIMATION_FRAME_BITS);
        assert player.getCurrentAction() != PlayerAction.ATTACK || attackTimer >= 0;
        assert attackTimer <= Bits.maxFieldValue(Constraints.PLAYER_ATTACK_TIMER_BITS);

        byte graphicState = 0;
        int bitsRemaining = Byte.SIZE;

        bitsRemaining -= Constraints.DIRECTION_BITS;
        graphicState |= orientationByte << bitsRemaining;

        bitsRemaining -= Constraints.PLAYER_ACTION_BITS;
        graphicState |= actionByte << bitsRemaining;

        bitsRemaining -= Constraints.PLAYER_ANIMATION_FRAME_BITS;
        graphicState |= animationFrame << bitsRemaining;

        assert bitsRemaining == 0;
        this.graphicState = graphicState;
        this.attackTimer = (byte)Math.max(0, attackTimer);

        assert getX() == player.getX();
        assert getY() == player.getY();
        assert getOrientation() == player.getOrientation();
        assert getAction() == player.getCurrentAction();
        assert playerAnimation == null || (getAnimationFrame() == playerAnimation.getFrame());
        assert player.isAttacking() == (getAction() == PlayerAction.ATTACK);
        assert attackTimer == this.attackTimer || attackTimer < 0;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getAnimationFrame() {
        return Bits.getFieldValue(graphicState, Constraints.PLAYER_ANIMATION_FRAME_BITS, 0);
    }

    public PlayerAction getAction() {
        byte value = (byte)Bits.getFieldValue(
                graphicState,
                Constraints.PLAYER_ACTION_BITS,
                Constraints.PLAYER_ANIMATION_FRAME_BITS
        );
        return PlayerAction.fromByte(value);
    }

    public Direction getOrientation() {
        byte value = (byte)Bits.getFieldValue(
                graphicState,
                Constraints.DIRECTION_BITS,
                Constraints.PLAYER_ANIMATION_FRAME_BITS + Constraints.PLAYER_ACTION_BITS
        );
        return Direction.fromByte(value);
    }

    public PlayerType getType() {
        return PlayerType.fromByte(playerType);
    }

    public int getAttackTimer() {
        return attackTimer;
    }

    @Override
    public String toString() {
        return "PlayerState{" +
                "x=" + x +
                ", y=" + y +
                ", orientation=" + getOrientation() +
                ", action=" + getAction() +
                ", animationFrame=" + getAnimationFrame() +
                ", attackTimer=" + getAttackTimer() +
                ", playerType=" + getType() +
                '}';
    }
}
