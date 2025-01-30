package BaseGame;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class WarriorSlash extends Entity {
  public WarriorSlash(final float x, final float y, Direction d) {
    super(x, y);
    switch(d) {
      case NORTH:
        addImageWithBoundingBox(
            ResourceManager.getSpriteSheet(BaseGame.SLASH_RSC, 96, 96).getSprite(0, 4), new Vector(16, 16));
        break;
      case SOUTH:
        addImageWithBoundingBox(
            ResourceManager.getSpriteSheet(BaseGame.SLASH_RSC, 96, 96).getSprite(0, 0), new Vector(16, 16));
        break;
      case EAST:
        addImageWithBoundingBox(
            ResourceManager.getSpriteSheet(BaseGame.SLASH_RSC, 96, 96).getSprite(0, 6), new Vector(16, 16));
        break;
      case WEST:
        addImageWithBoundingBox(
            ResourceManager.getSpriteSheet(BaseGame.SLASH_RSC, 96, 96).getSprite(0, 2), new Vector(16, 16));
        break;
      case NORTHWEST:
        addImageWithBoundingBox(
            ResourceManager.getSpriteSheet(BaseGame.SLASH_RSC, 96, 96).getSprite(0, 3), new Vector(16, 16));
        break;
      case NORTHEAST:
        addImageWithBoundingBox(
            ResourceManager.getSpriteSheet(BaseGame.SLASH_RSC, 96, 96).getSprite(0, 5), new Vector(16, 16));
        break;
      case SOUTHWEST:
        addImageWithBoundingBox(
            ResourceManager.getSpriteSheet(BaseGame.SLASH_RSC, 96, 96).getSprite(0, 1), new Vector(16, 16));
        break;
      case SOUTHEAST:
        addImageWithBoundingBox(
            ResourceManager.getSpriteSheet(BaseGame.SLASH_RSC, 96, 96).getSprite(0, 7), new Vector(16, 16));
        break;
    }

  }
}
