package BaseGame;

import jig.Vector;

import java.util.ArrayList;
import java.util.Arrays;

public enum Direction {
  NORTH("North", new Vector(0, -1)),
  SOUTH("South", new Vector(0, 1)),
  EAST("East", new Vector(1, 0)),
  WEST("West", new Vector(-1, 0)),
  NORTHWEST("Northwest", new Vector(-1, -1)),
  NORTHEAST("Northeast", new Vector(1, -1)),
  SOUTHWEST("Southwest", new Vector(-1, 1)),
  SOUTHEAST("Southeast", new Vector(1, 1));

  private String name;
  private Vector movement;

  Direction(String name, Vector movement) {
    this.name = name;
    this.movement = movement;
  }

  public Vector getMovement() {
    return movement;
  }

  public static ArrayList<Direction> getCardinal() {
    return new ArrayList(Arrays.asList(Direction.SOUTH,
        Direction.WEST,
        Direction.NORTH,
        Direction.EAST));
  }

  public static ArrayList<Direction> getOrdinal() {
    return new ArrayList(Arrays.asList(Direction.SOUTH,
        Direction.WEST,
        Direction.NORTH,
        Direction.EAST,
        Direction.NORTHWEST,
        Direction.NORTHEAST,
        Direction.SOUTHWEST,
        Direction.SOUTHEAST));
  }

  public byte toByte() {
    switch (this) {
    case NORTHWEST:
      return 0b000;
    case NORTH:
      return 0b001;
    case NORTHEAST:
      return 0b010;
    case EAST:
      return 0b011;
    case SOUTHEAST:
      return 0b100;
    case SOUTH:
      return 0b101;
    case SOUTHWEST:
      return 0b110;
    case WEST:
      return 0b111;
    default:
      return 0b000;
    }
  }

   public static Direction fromByte(byte b) {
     assert Byte.toUnsignedInt(b) <= 0b111;

     switch (b) {
     case 0b000:
       return NORTHWEST;
     case 0b001:
       return NORTH;
     case 0b010:
       return NORTHEAST;
     case 0b011:
       return EAST;
     case 0b100:
       return SOUTHEAST;
     case 0b101:
       return SOUTH;
     case 0b110:
       return SOUTHWEST;
     case 0b111:
       return WEST;
     default:
       return NORTH;
     }
  }

  @Override
  public String toString() {
    return name;
  }
}
