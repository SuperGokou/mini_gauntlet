package BaseGame.enemies;

import BaseGame.Direction;
import BaseGame.Node;
import BaseGame.serial.EnemyAction;
import BaseGame.serial.EnemyState;
import BaseGame.serial.EnemyType;
import jig.Entity;
import jig.Vector;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import java.util.*;

public abstract class BaseEnemy extends Entity {
  private Vector velocity;
  private Direction direction;
  private HashMap<String, Animation> enemyAnimations;

  private Animation currentAni;
  private Image standingPlayer;
  private int detectionDistance;
  private ArrayList<Direction> validMovements;
  private ArrayList<Node> dijkstra;

  private int attackPower;
  private int hp;

  public abstract EnemyType getType();

  public BaseEnemy(final float x, final float y) {
    super(x,y);
    enemyAnimations = new HashMap<>();
    velocity = new Vector(0,0);
    detectionDistance = 8;
    validMovements = new ArrayList<Direction>();
    dijkstra = new ArrayList<Node>();
    validMovements.addAll(Arrays.asList(Direction.values()));

    attackPower = 10;
    hp = 100;
  }

  public Animation getCurrentAnimation() {
    return currentAni;
  }

  private boolean isMoving() {
    return !velocity.equals(new Vector(0f, 0f));
  }

  public EnemyAction getCurrentAction() {
    if (isMoving()) {
      return EnemyAction.MOVE;
    } else {
      return EnemyAction.STAND;
    }
  }

  public int getAttackPower() { return attackPower; }

  public void setAttackPower(int attackPower) { this.attackPower = attackPower; }

  public int getHp() { return hp; }

  public void setHp(int hp) { this.hp = hp; }

  public Vector getVelocity() {
    return velocity;
  }

  public void setVelocity(final Vector v) {
    velocity = v;
  }

  public void setDirection(Direction d) {
    direction = d;
  }

  public Direction getDirection() {return direction; }

  public void addEnemyAnimation(String action, Animation ani) {
    enemyAnimations.put(action, ani);
  }

  public void setValidMovements(ArrayList<Direction> validMovements) {
    this.validMovements = validMovements;
  }

  public void update(final int delta) {
    updateDirection();
    updateAnimation();
    translate(velocity.scale(delta));
  }

  public void updateFromState(EnemyState state) {
    assert getType() == state.getType();

    setPosition(state.getX(), state.getY());
    setVelocity(state.getVelocity());
    updateDirection();
    updateAnimation();

    if (currentAni != null) {
      currentAni.setCurrentFrame(state.getAnimationFrame());
    }
  }

  // Returns the direction of the enemy using its velocity. Returns current direction if enemy is not moving
  private void updateDirection() {
    for (Direction d: Direction.values()) {
      if (d.getMovement().equals(velocity.unit())) {
        direction = d;
      }
    }
  }

  // Handles the animation of the enemy
  private void updateAnimation() {
    if (standingPlayer != null) {
      removeImage(standingPlayer);
      standingPlayer = null;
    }

    if (currentAni != null) {
      removeAnimation(currentAni);
    }

    if (isMoving()) {
      currentAni = enemyAnimations.get("Move" + direction.toString());
      addAnimation(currentAni);
      currentAni.start();
    } else {
      // Enemy standing still
      standingPlayer = enemyAnimations.get("Move" + direction.toString()).getImage(1);
      addImageWithBoundingBox(standingPlayer);
      currentAni = null;
    }
  }

}
