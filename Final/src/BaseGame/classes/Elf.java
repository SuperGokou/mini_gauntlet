package BaseGame.classes;

import BaseGame.BaseGame;
import BaseGame.Direction;
import BaseGame.projectiles.ElfArrow;
import BaseGame.projectiles.Projectile;
import BaseGame.serial.PlayerType;
import jig.ResourceManager;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import java.util.HashMap;

public class Elf extends BasePlayer {

  @Override
  public PlayerType getType() {
    return PlayerType.ELF;
  }

  @Override
  protected HashMap<Direction, Animation> getMovementAnimations() {
    SpriteSheet ss = ResourceManager.getSpriteSheet(BaseGame.ELF_RSC, 32, 32);
    HashMap<Direction, Animation> animations = new HashMap<>();

    animations.put(
            Direction.SOUTH,
            new Animation(ss, 0, 0, 2, 0, true, 100, true)
    );
    animations.put(
            Direction.SOUTHWEST,
            new Animation(ss, 0, 1, 2, 1, true, 100, true)
    );
    animations.put(
            Direction.WEST,
            new Animation(ss, 0, 2, 2, 2, true, 100, true)
    );
    animations.put(
            Direction.NORTHWEST,
            new Animation(ss, 0, 3, 2, 3, true, 100, true)
    );
    animations.put(
            Direction.NORTH,
            new Animation(ss, 0, 4, 2, 4, true, 100, true)
    );
    animations.put(
            Direction.NORTHEAST,
            new Animation(ss, 0, 5, 2, 5, true, 100, true)
    );
    animations.put(
            Direction.EAST,
            new Animation(ss, 0, 6, 2, 6, true, 100, true)
    );
    animations.put(
            Direction.SOUTHEAST,
            new Animation(ss, 0, 7, 2, 7, true, 100, true)
    );

    return animations;
  }

  @Override
  protected HashMap<Direction, Animation> getAttackAnimations() {
    SpriteSheet ss = ResourceManager.getSpriteSheet(BaseGame.ELF_RSC, 32, 32);
    HashMap<Direction, Animation> animations = new HashMap<>();

    animations.put(
            Direction.SOUTH,
            new Animation(ss, 3, 0, 7, 0, true, getAttackDuration(), true)
    );
    animations.put(
            Direction.SOUTHWEST,
            new Animation(ss, 3, 1, 7, 1, true, getAttackDuration(), true)
    );
    animations.put(
            Direction.WEST,
            new Animation(ss, 3, 2, 7, 2, true, getAttackDuration(), true)
    );
    animations.put(
            Direction.NORTHWEST,
            new Animation(ss, 3, 3, 7, 3, true, getAttackDuration(), true)
    );
    animations.put(
            Direction.NORTH,
            new Animation(ss, 3, 4, 7, 4, true, getAttackDuration(), true)
    );
    animations.put(
            Direction.NORTHEAST,
            new Animation(ss, 3, 5, 7, 5, true, getAttackDuration(), true)
    );
    animations.put(
            Direction.EAST,
            new Animation(ss, 3, 6, 7, 6, true, getAttackDuration(), true)
    );
    animations.put(
            Direction.SOUTHEAST,
            new Animation(ss, 3, 7, 7, 7, true, getAttackDuration(), true)
    );

    return animations;
  }

  @Override
  protected HashMap<Direction, Image> getStandingImages() {
    SpriteSheet ss = ResourceManager.getSpriteSheet(BaseGame.ELF_RSC, 32, 32);
    HashMap<Direction, Image> images = new HashMap<>();

    images.put(Direction.SOUTH, ss.getSprite(1, 0));
    images.put(Direction.SOUTHWEST, ss.getSprite(1, 1));
    images.put(Direction.WEST, ss.getSprite(1, 2));
    images.put(Direction.NORTHWEST, ss.getSprite(1, 3));
    images.put(Direction.NORTH, ss.getSprite(1, 4));
    images.put(Direction.NORTHEAST, ss.getSprite(1, 5));
    images.put(Direction.EAST, ss.getSprite(1, 6));
    images.put(Direction.SOUTHEAST, ss.getSprite(1, 7));

    return images;
  }

  @Override
  public Projectile createProjectile() {
    Direction d = getOrientation();
    return new ElfArrow(getX() + 16, getY() + 16, d.getMovement().scale(0.2f), d);
  }

  public Elf(final float x, final float y) {
    super(x, y, 100);
    setHasProjectile();
    setAttackPower(130);
    setHp(90);
    setSpeed(130);
  }
}
