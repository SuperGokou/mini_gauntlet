package BaseGame.states.client;

import BaseGame.Client;
import BaseGame.enemies.BaseEnemy;
import BaseGame.net.ServerMessage;
import BaseGame.serial.EnemyState;
import BaseGame.serial.ProjectileState;
import BaseGame.states.base.BasePlayingState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.stream.Collectors;

public class PlayingState extends BasePlayingState {
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Client gg = (Client)game;
        ServerMessage inMessage = gg.inMessages.poll();

        if (inMessage != null) {
            inMessage.visit(gameState -> {
                assert gg.level == gameState.level;
                assert gg.enemies.size() == gameState.enemies.size();

                gg.p1.updateFromState(gameState.p1);

                for (int i = 0; i < gameState.enemies.size(); ++i) {
                    BaseEnemy enemy = gg.enemies.get(i);
                    EnemyState state = gameState.enemies.get(i);
                    enemy.updateFromState(state);
                }

                gg.projectiles = gameState.projectiles.stream().map(ProjectileState::getProjectile).collect(Collectors.toList());
            });
        }

        gg.p1.update(delta);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);

        Client gg = (Client)game;
    }
}

