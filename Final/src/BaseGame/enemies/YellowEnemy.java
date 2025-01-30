package BaseGame.enemies;

import BaseGame.BaseGame;
import BaseGame.Direction;
import BaseGame.serial.EnemyType;
import jig.ResourceManager;
import org.newdawn.slick.Animation;

import java.util.ArrayList;
import java.util.Arrays;

public class YellowEnemy extends BaseEnemy {
  @Override
  public EnemyType getType() {
    return EnemyType.YELLOW;
  }

  public YellowEnemy(float x, float y) {
    super(x, y);
    // Add movement
    addEnemyAnimation("MoveSouth", new Animation(ResourceManager.getSpriteSheet(BaseGame.YELLOW_ENEMY_RSC,
        32, 32), 0, 0, 3, 0, true, 100, true));
    addEnemyAnimation("MoveWest", new Animation(ResourceManager.getSpriteSheet(BaseGame.YELLOW_ENEMY_RSC,
        32, 32), 0, 1, 3, 1, true, 100, true));
    addEnemyAnimation("MoveNorth", new Animation(ResourceManager.getSpriteSheet(BaseGame.YELLOW_ENEMY_RSC,
        32, 32), 0, 2, 3, 2, true, 100, true));
    addEnemyAnimation("MoveEast", new Animation(ResourceManager.getSpriteSheet(BaseGame.YELLOW_ENEMY_RSC,
        32, 32), 0, 3, 3, 3, true, 100, true));
    setValidMovements(new ArrayList(Arrays.asList(Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST)));
    setDirection(Direction.SOUTH);
  }
}
