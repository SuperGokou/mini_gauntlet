package BaseGame.states.base;

import BaseGame.BaseGame;
import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;



public class BaseStartUpState extends BasicGameState {
    private int lastXCord;
    private int lastYCord;
    private int lastX;
    private int lastY;

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        lastX = 0;
        lastY = 0;
        lastYCord = 0;
        lastXCord = 0;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        container.setSoundOn(false);
    }


    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException {

        BaseGame bg = (BaseGame)game;

        g.drawImage(ResourceManager.getImage(BaseGame.STARTSCREENLOGO), 50, 128);
        g.drawImage(ResourceManager.getImage(BaseGame.PLAYER1), 143, 390);
        g.drawImage(ResourceManager.getImage(BaseGame.CREDITS), 136, 460);
        g.drawImage(ResourceManager.getImage(BaseGame.STARGAME), 50, 530);
        g.drawImage(ResourceManager.getImage(BaseGame.QUITGAME), 85, 600);
        g.drawImage(ResourceManager.getImage(BaseGame.PLAYER2), 596, 390);
        g.drawImage(ResourceManager.getImage(BaseGame.CREDITS), 589, 460);
        g.drawImage(ResourceManager.getImage(BaseGame.STARGAME), 503, 530);
        g.drawImage(ResourceManager.getImage(BaseGame.QUITGAME), 541, 600);

        g.setColor(Color.white);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {

        Input input = container.getInput();
        BaseGame gg = (BaseGame) game;


        if (input.isKeyDown(Input.KEY_SPACE))
            gg.enterState(BaseGame.PLAYINGSTATE);

    }

    @Override
    public int getID () {
        return BaseGame.STARTUPSTATE;
    }

}