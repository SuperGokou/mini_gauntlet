package BaseGame.projectiles;

import BaseGame.Direction;
import BaseGame.serial.ProjectileType;
import jig.Entity;
import jig.Vector;

public abstract class Projectile extends Entity {
  private final Vector velocity;
  private final Direction direction;

  public abstract ProjectileType getType();

  public Projectile(final float x, final float y, Vector v, final Direction d) {
    super(x, y);
    direction = d;
    velocity = v;
  }

  public Direction getDirection() {
    return direction;
  }

  public Vector getVelocity() {
    return velocity;
  }

  public void update(final int delta) {
    translate(velocity.scale(delta));
  }
}
