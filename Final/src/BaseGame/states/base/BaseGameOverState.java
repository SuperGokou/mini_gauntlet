package BaseGame.states.base;

import BaseGame.BaseGame;
import jig.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;


public class BaseGameOverState extends BasicGameState {


	private int timer;
	private boolean winner = false;


	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		timer = 8000;
	}

	public void setUserWin(boolean win) {
		winner = win;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
					   Graphics g) throws SlickException {

		BaseGame bg = (BaseGame)game;

		g.drawImage(ResourceManager.getImage(BaseGame.STARTSCREENLOGO), 50, 128);
		if (winner) {
			g.drawImage(ResourceManager.getImage(BaseGame.WINNER), 175, 386);
		}
		else {
			g.drawImage(ResourceManager.getImage(BaseGame.GAMEOVER), 175, 386);
		}
		g.drawImage(ResourceManager.getImage(BaseGame.RESTART), 228, 511);


	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
					   int delta) throws SlickException {

		Input input = container.getInput();
		BaseGame gg = (BaseGame) game;

		if (input.isKeyDown(Input.KEY_SPACE)) {
			gg.level = 0;
			winner = false;
			gg.enterState(BaseGame.STARTUPSTATE);
		}

		timer -= delta;
		if (timer <= 0)
			game.enterState(BaseGame.STARTUPSTATE, new EmptyTransition(), new HorizontalSplitTransition() );
	}

	@Override
	public int getID() {
		return BaseGame.GAMEOVERSTATE;
	}

}
