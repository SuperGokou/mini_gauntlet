package BaseGame.classes;

import BaseGame.Direction;
import BaseGame.projectiles.Projectile;
import BaseGame.serial.PlayerAction;
import BaseGame.serial.PlayerState;
import BaseGame.serial.PlayerType;
import jig.Entity;
import jig.Vector;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.HashMap;

public abstract class BasePlayer extends Entity {
  private boolean movedInTick;
  private Direction orientation;
  private Animation currentAni;
  private Image standingPlayer;
  private PlayerAction currentAction;

  private int attackPower;
  private int hp;
  private int speed;
  private int attackTimer;
  private boolean hasProjectile;

  private final int attackDuration;
  private final HashMap<Direction, Animation> movementAnimations;
  private final HashMap<Direction, Animation> attackAnimations;
  private final HashMap<Direction, Image> standingImages;

  protected abstract HashMap<Direction, Image> getStandingImages();
  protected abstract HashMap<Direction, Animation> getAttackAnimations();
  protected abstract HashMap<Direction, Animation> getMovementAnimations();
  public abstract PlayerType getType();

  public BasePlayer(final float x, final float y, int attackDuration) {
    super(x, y);

    attackPower = 10;
    hp = 100;
    speed = 100;
    hasProjectile = false;
    this.attackDuration = attackDuration;
    movementAnimations = getMovementAnimations();
    attackAnimations = getAttackAnimations();
    standingImages = getStandingImages();

    for (Direction d : Direction.values()) {
      assert movementAnimations.containsKey(d);
      assert attackAnimations.containsKey(d);
      assert standingImages.containsKey(d);
    }

    orientation = Direction.SOUTH;
    standingPlayer = standingImages.get(orientation);
    movedInTick = false;
    currentAction = PlayerAction.STAND;
  }

  public int getAttackPower() { return attackPower; }

  public void setAttackPower(int attackPower) { this.attackPower = attackPower; }

  public int getHp() { return hp; }

  public void setHp(int hp) { this.hp = hp; }

  public int getSpeed() { return speed; }

  public void setSpeed(int speed) { this.speed = speed; }

  public void setHasProjectile() { hasProjectile = true; }

  public boolean hasProjectile() { return hasProjectile; }

  public int getAttackTimer() {
    return attackTimer;
  }

  public Direction getOrientation() {
    return orientation;
  }

  public Animation getAnimation() {
    return currentAni;
  }

  public PlayerAction getCurrentAction() {
    return currentAction;
  }

  public int getAttackDuration() {
    return attackDuration;
  }

  private boolean isAnimated() {
    return currentAni != null;
  }

  public boolean isAttacking() {
    return currentAction == PlayerAction.ATTACK;
  }

  private void stopAnimation() {
    removeAnimation(currentAni);
    currentAni = null;
  }

  private void startAnimation(Animation animation, boolean isLooping) {
    stopAnimation();
    removeImage(standingPlayer);

    currentAni = animation;
    addAnimation(currentAni);
    currentAni.setLooping(isLooping);
    currentAni.start();
  }

  public Projectile startAttack() {
    if (isAttacking()) {
      return null;
    }

    currentAction = PlayerAction.ATTACK;
    attackTimer = attackDuration;
    startAnimation(attackAnimations.get(orientation), false);
    return createProjectile();
  }

  public Projectile createProjectile() {
    return null;
  }

  private void stand() {
    stopAnimation();
    addImageWithBoundingBox(standingImages.get(orientation));
    currentAction = PlayerAction.STAND;
  }

  public void move(Direction d, int delta) {
    if (isAttacking() || d == null) {
      return;
    }

    orientation = d;
    standingPlayer = standingImages.get(orientation);
    startAnimation(movementAnimations.get(orientation), true);

    Vector velocity = orientation.getMovement().scale(0.1f * (speed/100f));
    translate(velocity.scale(delta));
    movedInTick = true;
    currentAction = PlayerAction.MOVE;
  }

  @Override
  public void render(Graphics g) {
    if (isAnimated()) {
      currentAni.draw(getX(), getY());

      if (movedInTick) {
        stand();
      }
    } else {
      standingPlayer.draw(getX(), getY());
    }

    movedInTick = false;
  }

  public void update(final int delta) {
    if (isAttacking()) {
      attackTimer -= delta;

      if (attackTimer < 0) {
        stand();
      }
    }
  }

  public void updateFromState(PlayerState state) {
    assert getType() == state.getType();

    PlayerAction stateAction = state.getAction();
    Direction stateOrientation = state.getOrientation();
    boolean actionChanged = stateAction != currentAction;

    orientation = stateOrientation;
    standingPlayer = standingImages.get(orientation);
    setPosition(state.getX(), state.getY());

    if (!actionChanged) {
      movedInTick = stateAction == PlayerAction.MOVE;
      return;
    }

    stopAnimation();
    attackTimer = state.getAttackTimer();
    currentAction = stateAction;
    movedInTick = false;

    switch (stateAction) {
      case ATTACK:
        currentAni = attackAnimations.get(orientation);
        break;
      case MOVE:
        movedInTick = true;
        currentAni = movementAnimations.get(orientation);
        currentAni.setLooping(true);
        break;
    }

    switch (currentAction) {
      case STAND:
        assert standingPlayer != null;
        addImageWithBoundingBox(standingPlayer);
        break;
      case ATTACK:
      case MOVE:
        assert currentAni != null;
        addAnimation(currentAni);
        currentAni.setCurrentFrame(state.getAnimationFrame());
        currentAni.start();
        break;
    }
  }
}
