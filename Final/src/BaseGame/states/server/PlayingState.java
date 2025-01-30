package BaseGame.states.server;

import BaseGame.BaseGame;
import BaseGame.Server;
import BaseGame.projectiles.Projectile;
import BaseGame.states.base.BasePlayingState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Set;

public class PlayingState extends BasePlayingState {
    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {
        Server gg = (Server)game;

        if (!gg.p1.isAttacking()) {
            Set<Integer> keysPressed = gg.keysPressed;

            if (keysPressed.contains(Input.KEY_SPACE)) {
                gg.enterState(BaseGame.PLAYINGSTATE);
                Projectile p = gg.p1.startAttack();
                if (p != null) {
                    gg.projectiles.add(p);
                }
            }
        }

    }
}
