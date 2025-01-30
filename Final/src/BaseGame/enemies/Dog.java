package BaseGame.enemies;

import BaseGame.BaseGame;
import BaseGame.Direction;
import BaseGame.projectiles.DogFireball;
import BaseGame.projectiles.Projectile;
import BaseGame.serial.EnemyType;
import jig.ResourceManager;
import org.newdawn.slick.Animation;

public class Dog extends BaseEnemy {
  @Override
  public EnemyType getType() {
    return EnemyType.DOG;
  }

  public Dog(float x, float y) {
    super(x, y);
    // Add movement
    addEnemyAnimation("MoveSouth", new Animation(ResourceManager.getSpriteSheet(BaseGame.DOG_ENEMY_RSC,
        32, 32), 0, 0, 2, 0, true, 100, true));
    addEnemyAnimation("MoveSouthwest", new Animation(ResourceManager.getSpriteSheet(BaseGame.DOG_ENEMY_RSC,
        32, 32), 0, 1, 2, 1, true, 100, true));
    addEnemyAnimation("MoveWest", new Animation(ResourceManager.getSpriteSheet(BaseGame.DOG_ENEMY_RSC,
        32, 32), 0, 2, 2, 2, true, 100, true));
    addEnemyAnimation("MoveNorthwest", new Animation(ResourceManager.getSpriteSheet(BaseGame.DOG_ENEMY_RSC,
        32, 32), 0, 3, 2, 3, true, 100, true));
    addEnemyAnimation("MoveNorth", new Animation(ResourceManager.getSpriteSheet(BaseGame.DOG_ENEMY_RSC,
        32, 32), 0, 4, 2, 4, true, 100, true));
    addEnemyAnimation("MoveNortheast", new Animation(ResourceManager.getSpriteSheet(BaseGame.DOG_ENEMY_RSC,
        32, 32), 0, 5, 2, 5, true, 100, true));
    addEnemyAnimation("MoveEast", new Animation(ResourceManager.getSpriteSheet(BaseGame.DOG_ENEMY_RSC,
        32, 32), 0, 6, 2, 6, true, 100, true));
    addEnemyAnimation("MoveSoutheast", new Animation(ResourceManager.getSpriteSheet(BaseGame.DOG_ENEMY_RSC,
        32, 32), 0, 7, 2, 7, true, 100, true));
    setDirection(Direction.SOUTH);
  }

  public Projectile attack() {
    Direction d = getDirection();
    return new DogFireball(getX() + 16, getY() + 16, d.getMovement().scale(0.2f), d);
  }
}
