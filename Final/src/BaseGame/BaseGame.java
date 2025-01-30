package BaseGame;

import BaseGame.classes.*;
import BaseGame.enemies.BaseEnemy;
import BaseGame.projectiles.Projectile;
import BaseGame.states.base.BaseGameOverState;
import BaseGame.states.base.BasePlayingState;
import BaseGame.states.base.BaseStartUpState;
import jig.Entity;
import jig.ResourceManager;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

public class BaseGame extends StateBasedGame {

	public static final int STARTUPSTATE = 0;
	public static final int PLAYINGSTATE = 1;
	public static final int GAMEOVERSTATE = 2;

	// Players
	public static final String WARRIOR_RSC = "BaseGame/resource/player_classes/warrior/sprites.png";
  public static final String WIZARD_RSC = "BaseGame/resource/player_classes/wizard/WizardSpriteSheet.png";
  public static final String SHAMAN_RSC = "BaseGame/resource/player_classes/shaman/shamenSpriteSheet.png";
  public static final String ELF_RSC = "BaseGame/resource/player_classes/elf/ElfSpriteSheet.png";

  // Enemies
  public static final String BLUE_ENEMY_RSC = "BaseGame/resource/enemies/blue/BlueSpriteSheet.png";
  public static final String GREEN_ENEMY_RSC = "BaseGame/resource/enemies/green/greenSpriteSheet.png";
  public static final String RED_ENEMY_RSC = "BaseGame/resource/enemies/red/redSpriteSheet.png";
  public static final String YELLOW_ENEMY_RSC = "BaseGame/resource/enemies/yellow/yellowSpriteSheet.png";
  public static final String DOG_ENEMY_RSC = "BaseGame/resource/enemies/dog/doggySpriteSheet.png";
  public static final String GHOST_ENEMY_RSC = "BaseGame/resource/enemies/ghost/GhostSpriteSheet.png";

  // Projectiles
  public static final String ELF_ARROWS_RSC = "BaseGame/resource/player_classes/elf/arrows.png";
  public static final String WIZARD_FIREBALL_RSC = "BaseGame/resource/player_classes/wizard/WizardFireballs.png";
  public static final String WIZARD_SPECIAL_RSC = "BaseGame/resource/player_classes/wizard/bigFireball.png";
  public static final String DOGGY_FIREBALL_RSC = "BaseGame/resource/enemies/dog/doggyFireballSpriteSheet.png";

  public static final String SLASH_RSC = "BaseGame/resource/player_classes/warrior/warriorAttack.png";
  public static final String LUNGE_RSC = "BaseGame/resource/player_classes/shaman/shamanAttack.png";

  // Background Tiles
	public static final String NORMALFLOOR = "BaseGame/resource/background/normalFL.png";
	public static final String BOSSFLOOR = "BaseGame/resource/background/bossFL.png";
	public static final String STOREFLOOR = "BaseGame/resource/background/storeFL.png";
	public static final String WALL1 = "BaseGame/resource/background/W1.png";
	public static final String WALL2 = "BaseGame/resource/background/W2.png";
	public static final String WALL3 = "BaseGame/resource/background/W3.png";
	public static final String WALL4 = "BaseGame/resource/background/W4.png";
	public static final String WALL5 = "BaseGame/resource/background/W5.png";
	public static final String WALL6 = "BaseGame/resource/background/W6.png";
	public static final String WALL7 = "BaseGame/resource/background/W7.png";
	public static final String WALL8 = "BaseGame/resource/background/W8.png";
	public static final String WALL9 = "BaseGame/resource/background/W9.png";
	public static final String WALL10 = "BaseGame/resource/background/W10.png";
	public static final String WALL11 = "BaseGame/resource/background/W11.png";
	public static final String WALL12 = "BaseGame/resource/background/W12.png";
	public static final String WALL13 = "BaseGame/resource/background/W13.png";
	public static final String WALL14 = "BaseGame/resource/background/W14.png";
	public static final String EXIT = "BaseGame/resource/background/EX.png";

	public static final String STARTSCREENLOGO = "BaseGame/resource/background/logo.png";
	public static final String WINNER = "BaseGame/resource/background/winner.png";
	public static final String PLAYER1 = "BaseGame/resource/background/play1.png";
	public static final String PLAYER2 = "BaseGame/resource/background/play2.png";
	public static final String CREDITS = "BaseGame/resource/background/credit.png";
	public static final String QUITGAME = "BaseGame/resource/background/quitgame.png";
	public static final String STARGAME= "BaseGame/resource/background/startgame.png";
	public static final String GAMEOVER= "BaseGame/resource/background/gameover.png";
	public static final String RESTART= "BaseGame/resource/background/restartgame.png";
	public static final String VIEWPORT = "BaseGame/resource/extra/viewport.png";

	public final int ScreenWidth;
	public final int ScreenHeight;

  public int playerCount;
  public BasePlayer[] players;
  public BasePlayer p1;

	public List<BaseEnemy> enemies;
	public List<Projectile> projectiles;
	public WarriorSlash slash;
	public int slashTimer;
	public ShamanLunge lunge;
	public int lungeTimer;

	public int level;
	public ArrayList<Level> levels = new ArrayList<Level>(0);

	public BaseGame(String title, int width, int height) {
		super(title);
		ScreenHeight = height;
		ScreenWidth = width;

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
	}

	protected void initGameState() {
		ResourceManager.loadImage(WARRIOR_RSC);
		ResourceManager.loadImage(WIZARD_RSC);
		ResourceManager.loadImage(SHAMAN_RSC);
		ResourceManager.loadImage(ELF_RSC);

		ResourceManager.loadImage(BLUE_ENEMY_RSC);
		ResourceManager.loadImage(GREEN_ENEMY_RSC);
		ResourceManager.loadImage(RED_ENEMY_RSC);
		ResourceManager.loadImage(YELLOW_ENEMY_RSC);
		ResourceManager.loadImage(DOG_ENEMY_RSC);
		ResourceManager.loadImage(GHOST_ENEMY_RSC);

		ResourceManager.loadImage(ELF_ARROWS_RSC);
		ResourceManager.loadImage(WIZARD_FIREBALL_RSC);
		ResourceManager.loadImage(WIZARD_SPECIAL_RSC);
		ResourceManager.loadImage(DOGGY_FIREBALL_RSC);

    ResourceManager.loadImage(SLASH_RSC);
    ResourceManager.loadImage(LUNGE_RSC);

		ResourceManager.loadImage(NORMALFLOOR);
		ResourceManager.loadImage(BOSSFLOOR);
		ResourceManager.loadImage(STOREFLOOR);
		ResourceManager.loadImage(WALL1);
		ResourceManager.loadImage(WALL2);
		ResourceManager.loadImage(WALL3);
		ResourceManager.loadImage(WALL4);
		ResourceManager.loadImage(WALL5);
		ResourceManager.loadImage(WALL6);
		ResourceManager.loadImage(WALL7);
		ResourceManager.loadImage(WALL8);
		ResourceManager.loadImage(WALL9);
		ResourceManager.loadImage(WALL10);
		ResourceManager.loadImage(WALL11);
		ResourceManager.loadImage(WALL12);
		ResourceManager.loadImage(WALL13);
		ResourceManager.loadImage(WALL14);
		ResourceManager.loadImage(EXIT);
		ResourceManager.loadImage(VIEWPORT);
		ResourceManager.loadImage(STARTSCREENLOGO);
		ResourceManager.loadImage(PLAYER1);
		ResourceManager.loadImage(PLAYER2);
		ResourceManager.loadImage(CREDITS);
		ResourceManager.loadImage(QUITGAME);
		ResourceManager.loadImage(STARGAME);
		ResourceManager.loadImage(GAMEOVER);
		ResourceManager.loadImage(RESTART);
		ResourceManager.loadImage(WINNER);

		setupLevels();

		level = 0;
		enemies = levels.get(level).getEnemyLayout();
		projectiles = new ArrayList<>();
		playerCount = 0;
		players = new BasePlayer[4];
		slash = null;
		slashTimer = 0;
		lunge = null;
		lungeTimer = 0;

		players[playerCount] = new Wizard(300, 300);
		playerCount++;

	}

	public void setupLevels() {
		for (int i = 0; i < 5; ++i) {
			levels.add(i, MapCreation.createLevel(i));
		}
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		addState(new BaseStartUpState());
		addState(new BasePlayingState());
		addState(new BaseGameOverState());

		initGameState();
		setupLevels();
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new BaseGame("Gauntlet", 640, 640));
			app.setUpdateOnlyWhenVisible(false);
			app.setAlwaysRender(true);
			app.setDisplayMode(900, 680, false);
			app.setTargetFrameRate(60);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
