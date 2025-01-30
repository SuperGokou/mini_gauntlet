package BaseGame.enemies;

import BaseGame.BaseGame;
import BaseGame.Direction;
import BaseGame.serial.EnemyType;
import jig.ResourceManager;
import org.newdawn.slick.Animation;

public class Ghost extends BaseEnemy {
  @Override
  public EnemyType getType() {
    return EnemyType.GHOST;
  }

  public Ghost(float x, float y) {
    super(x, y);
    // Add movement
    addEnemyAnimation("MoveSouth", new Animation(ResourceManager.getSpriteSheet(BaseGame.GHOST_ENEMY_RSC,
        32, 32), 0, 0, 2, 0, true, 100, true));
    addEnemyAnimation("MoveSouthwest", new Animation(ResourceManager.getSpriteSheet(BaseGame.GHOST_ENEMY_RSC,
        32, 32), 0, 1, 2, 1, true, 100, true));
    addEnemyAnimation("MoveWest", new Animation(ResourceManager.getSpriteSheet(BaseGame.GHOST_ENEMY_RSC,
        32, 32), 0, 2, 2, 2, true, 100, true));
    addEnemyAnimation("MoveNorthwest", new Animation(ResourceManager.getSpriteSheet(BaseGame.GHOST_ENEMY_RSC,
        32, 32), 0, 3, 2, 3, true, 100, true));
    addEnemyAnimation("MoveNorth", new Animation(ResourceManager.getSpriteSheet(BaseGame.GHOST_ENEMY_RSC,
        32, 32), 0, 4, 2, 4, true, 100, true));
    addEnemyAnimation("MoveNortheast", new Animation(ResourceManager.getSpriteSheet(BaseGame.GHOST_ENEMY_RSC,
        32, 32), 0, 5, 2, 5, true, 100, true));
    addEnemyAnimation("MoveEast", new Animation(ResourceManager.getSpriteSheet(BaseGame.GHOST_ENEMY_RSC,
        32, 32), 0, 6, 2, 6, true, 100, true));
    addEnemyAnimation("MoveSoutheast", new Animation(ResourceManager.getSpriteSheet(BaseGame.GHOST_ENEMY_RSC,
        32, 32), 0, 7, 2, 7, true, 100, true));
    setDirection(Direction.SOUTH);
  }
}
