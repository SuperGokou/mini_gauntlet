package BaseGame.projectiles;

import BaseGame.BaseGame;
import BaseGame.Direction;
import BaseGame.serial.ProjectileType;
import jig.ResourceManager;
import jig.Vector;

public class WizardFireball extends Projectile {
  @Override
  public ProjectileType getType() {
    return ProjectileType.WIZARD;
  }

  public WizardFireball(float x, float y, Vector v, Direction d) {
    super(x, y, v, d);
    switch (d) {
      case NORTH:
        addImageWithBoundingBox(ResourceManager.getSpriteSheet(BaseGame.WIZARD_FIREBALL_RSC, 32, 32).getSprite(0, 4));
        break;
      case SOUTH:
        addImageWithBoundingBox(ResourceManager.getSpriteSheet(BaseGame.WIZARD_FIREBALL_RSC, 32, 32).getSprite(0, 0));
        break;
      case EAST:
        addImageWithBoundingBox(ResourceManager.getSpriteSheet(BaseGame.WIZARD_FIREBALL_RSC, 32, 32).getSprite(0, 6));
        break;
      case WEST:
        addImageWithBoundingBox(ResourceManager.getSpriteSheet(BaseGame.WIZARD_FIREBALL_RSC, 32, 32).getSprite(0, 2));
        break;
      case NORTHWEST:
        addImageWithBoundingBox(ResourceManager.getSpriteSheet(BaseGame.WIZARD_FIREBALL_RSC, 32, 32).getSprite(0, 3));
        break;
      case NORTHEAST:
        addImageWithBoundingBox(ResourceManager.getSpriteSheet(BaseGame.WIZARD_FIREBALL_RSC, 32, 32).getSprite(0, 5));
        break;
      case SOUTHWEST:
        addImageWithBoundingBox(ResourceManager.getSpriteSheet(BaseGame.WIZARD_FIREBALL_RSC, 32, 32).getSprite(0, 1));
        break;
      case SOUTHEAST:
        addImageWithBoundingBox(ResourceManager.getSpriteSheet(BaseGame.WIZARD_FIREBALL_RSC, 32, 32).getSprite(0, 7));
        break;
    }
  }
}
