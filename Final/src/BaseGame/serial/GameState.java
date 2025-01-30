package BaseGame.serial;

import BaseGame.BaseGame;
import BaseGame.Bits;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class GameState implements Serializable {
    public final PlayerState p1;
    public final List<EnemyState> enemies;
    public final List<ProjectileState> projectiles;
    public final byte level;

    public GameState(BaseGame game) {
        assert game.projectiles != null;
        assert game.level <= Bits.maxFieldValue(Constraints.GAME_LEVEL_BITS);

        p1 = new PlayerState(game.p1);
        enemies = game.enemies.stream().map(EnemyState::new).collect(Collectors.toList());
        projectiles = game.projectiles.stream().map(ProjectileState::new).collect(Collectors.toList());
        level = (byte)game.level;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "level=" + level +
                ", p1=" + p1 +
                ", enemies=" + enemies +
                ", projectiles=" + projectiles +
                '}';
    }
}
