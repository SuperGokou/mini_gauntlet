package BaseGame.states.base;

import BaseGame.BaseGame;
import BaseGame.Direction;
import BaseGame.Node;
import BaseGame.classes.*;
import BaseGame.WarriorSlash;
import BaseGame.ShamanLunge;
import BaseGame.enemies.BaseEnemy;
import BaseGame.enemies.Dog;
import BaseGame.enemies.Ghost;
import BaseGame.projectiles.ElfArrow;
import BaseGame.projectiles.Projectile;
import BaseGame.projectiles.WizardFireball;
import BaseGame.states.client.GameOverState;
import jig.Entity;
import jig.ResourceManager;
import jig.Shape;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;
import java.util.*;


public class BasePlayingState extends BasicGameState {

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		BaseGame bg = (BaseGame)game;
		int[][] levelLayout = bg.levels.get(bg.level).getLayout();

		for (int i = 0; i < levelLayout.length - 1; i++) {
			for (int j = 0; j < levelLayout[0].length - 1; j++) {
				if (levelLayout[j][i] == 30) {

					int x = (i * 32) + 15;
					int y = (j * 32);
					bg.players[0].setPosition(x, y);
				}
			}
		}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(false);
	}


	@Override
	public void render(GameContainer container, StateBasedGame game,
					   Graphics g) throws SlickException {

		BaseGame bg = (BaseGame)game;

		// Render game world
		float gameSizeX = 640;
		float gameSizeY = 640;

		BasePlayer currentPlayer = bg.players[0]; // Replace with currentPlayer for client
		float xTranslate = 0;
		float yTranslate = 0;
		if (gameSizeX / 2 > currentPlayer.getX()) {
			xTranslate = 0;
		} else if ((currentPlayer.getX() + bg.ScreenWidth / 2) > bg.levels.get(bg.level).getLayout().length * 32) {
			xTranslate = -1 * bg.levels.get(bg.level).getLayout().length * 32 + bg.ScreenWidth;
		} else {
			xTranslate = -1 * currentPlayer.getX() + gameSizeX / 2;
		}
		if (gameSizeY / 2 > currentPlayer.getY()) {
			yTranslate = 0;
		} else if ((currentPlayer.getY() + bg.ScreenHeight / 2) > bg.levels.get(bg.level).getLayout()[0].length * 32) {
			yTranslate = -1 * bg.levels.get(bg.level).getLayout()[0].length * 32 + bg.ScreenHeight;
		} else {
			yTranslate = -1 * currentPlayer.getY() + gameSizeY / 2;
		}
		g.translate(xTranslate, yTranslate);
		renderFloor(container, game, g);

		for (Projectile p: bg.projectiles) {
			p.render(g);
		}

		if (bg.slash != null) {
			bg.slash.render(g);
		}

		if (bg.lunge != null) {
			bg.lunge.render(g);
		}

		for (BasePlayer p: bg.players) {
			if (p != null) {
				p.render(g);
			}
		}

		for (BaseEnemy e : bg.enemies) {
			e.render(g);
		}

		// Render side menu
		float menuX = gameSizeX - xTranslate;
		float menuY = -yTranslate;
		float gauntletImage = gameSizeX + 45 - xTranslate;
		float playerClassName = gameSizeX + 25 - xTranslate;
		float playerClassStats = gameSizeX + 55 - xTranslate;

		g.setColor(Color.black);
		g.fillRect(menuX, menuY, menuX, menuY + bg.ScreenHeight + 40);
		g.setColor(Color.white);
		g.drawString("Level: " + (bg.level + 1), playerClassName + 60, 100 + menuY);

		g.setColor(Color.red);
//		g.drawString("Wizard ", playerClassName, 150 + menuY);
		g.drawString("Health: " + bg.players[0].getHp() , playerClassStats, 170 + menuY);
		g.drawString("Attack: " + bg.players[0].getAttackPower(), playerClassStats, 185 + menuY);
		g.drawString("Speed:  " + bg.players[0].getSpeed(), playerClassStats, 200 + menuY);
//		g.drawString("Money: ", playerClassStats, 215 + menuY);
//		g.drawString("Score: ", playerClassStats, 230 + menuY);


//		g.setColor(Color.blue);
//		g.drawString("Shaman ", playerClassName, 270 + menuY);
//		g.drawString("Health: " , playerClassStats, 290 + menuY);
//		g.drawString("Attack: ", playerClassStats, 305 + menuY);
//		g.drawString("Speed:  ", playerClassStats, 320 + menuY);
//		g.drawString("Money: ", playerClassStats, 335 + menuY);
//		g.drawString("Score: ", playerClassStats, 350 + menuY);
//
//		g.setColor(Color.yellow);
//		g.drawString("Warrior ", playerClassName, 390 + menuY);
//		g.drawString("Health: " , playerClassStats, 410 + menuY);
//		g.drawString("Attack: ", playerClassStats, 425 + menuY);
//		g.drawString("Speed:  ", playerClassStats, 440 + menuY);
//		g.drawString("Money: ", playerClassStats, 455 + menuY);
//		g.drawString("Score: ", playerClassStats, 470 + menuY);
//
//
//		g.setColor(Color.green);
//		g.drawString("Elf", playerClassName, 510 + menuY);
//		g.drawString("Health: " , playerClassStats, 530 + menuY);
//		g.drawString("Attack: ", playerClassStats, 545 + menuY);
//		g.drawString("Speed:  ", playerClassStats, 560 + menuY);
//		g.drawString("Money: ", playerClassStats, 575 + menuY);
//		g.drawString("Score: ", playerClassStats, 590 + menuY);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
					   int delta) throws SlickException {

		Input input = container.getInput();
		BaseGame bg = (BaseGame)game;
		Direction moveDir = null;

		if (bg.slashTimer > 0) {
			bg.slashTimer -= delta;
		} else {
			bg.slash = null;
		}

		if (bg.lungeTimer > 0) {
			bg.lungeTimer -= delta;
		} else {
			bg.lunge = null;
		}


		if(input.isKeyPressed(Input.KEY_1)) {
			setLevel(bg, 0);
		}

		if(input.isKeyPressed(Input.KEY_2)) {
			setLevel(bg, 1);
		}

		if(input.isKeyPressed(Input.KEY_3)) {
			setLevel(bg, 2);
		}

		if(input.isKeyPressed(Input.KEY_4)) {
			setLevel(bg, 3);
		}

		if(input.isKeyPressed(Input.KEY_5)) {
			setLevel(bg, 4);
		}



		if(input.isKeyPressed(Input.KEY_7)) {
			bg.players[0] = new Wizard(bg.players[0].getX(), bg.players[0].getY());
		}

		if(input.isKeyPressed(Input.KEY_8)) {
			bg.players[0] = new Shaman(bg.players[0].getX(), bg.players[0].getY());
		}

		if(input.isKeyPressed(Input.KEY_9)) {
			bg.players[0] = new Elf(bg.players[0].getX(), bg.players[0].getY());
		}

		if(input.isKeyPressed(Input.KEY_0)) {
			bg.players[0] = new Warrior(bg.players[0].getX(), bg.players[0].getY());
		}

		BasePlayer currentPlayer = bg.players[0]; // Replace with currentPlayer for client
		if (!currentPlayer.isAttacking()) {

			if (input.isKeyPressed(Input.KEY_SPACE)) {
				Projectile p = currentPlayer.startAttack();
				if (p != null) {
					bg.projectiles.add(p);
				}
				if (currentPlayer.getClass() == Warrior.class) {
					bg.slash = new WarriorSlash(currentPlayer.getX(), currentPlayer.getY(), currentPlayer.getOrientation());
					bg.slashTimer = 100;
				} else if (currentPlayer.getClass() == Shaman.class) {
					bg.lunge = new ShamanLunge(currentPlayer.getX(), currentPlayer.getY(), currentPlayer.getOrientation());
					bg.lungeTimer = 100;
				}
			} else if (input.isKeyDown(Input.KEY_W) && input.isKeyDown(Input.KEY_A)) {
				moveDir = Direction.NORTHWEST;
			} else if (input.isKeyDown(Input.KEY_W) && input.isKeyDown(Input.KEY_D)) {
				moveDir = Direction.NORTHEAST;
			} else if (input.isKeyDown(Input.KEY_S) && input.isKeyDown(Input.KEY_A)) {
				moveDir = Direction.SOUTHWEST;
			} else if (input.isKeyDown(Input.KEY_S) && input.isKeyDown(Input.KEY_D)) {
				moveDir = Direction.SOUTHEAST;
			} else if (input.isKeyDown(Input.KEY_W)) {
				moveDir = Direction.NORTH;
			} else if (input.isKeyDown(Input.KEY_A)) {
				moveDir = Direction.WEST;
			} else if (input.isKeyDown(Input.KEY_S)) {
				moveDir = Direction.SOUTH;
			} else if (input.isKeyDown(Input.KEY_D)) {
				moveDir = Direction.EAST;
			}
			currentPlayer.move(moveDir, delta);
			if (checkIfCollision(bg, currentPlayer)) {
				Vector playerVelocity = currentPlayer.getOrientation().getMovement().scale(0.1f * (currentPlayer.getSpeed()/100f));
				Vector oldPosition = currentPlayer.getPosition().subtract(playerVelocity.scale(delta));
				currentPlayer.setPosition(oldPosition);
			}
		}


		for (Projectile p: bg.projectiles) {
			p.update(delta);
		}

		currentPlayer.update(delta);

		int maxRange = 7;
		int[][] dijkstraOrdinal = Dijkstra(bg, Direction.getOrdinal(), maxRange);
		int[][] dijkstraCardinal = Dijkstra(bg, Direction.getCardinal(), maxRange);

		for (BaseEnemy e: bg.enemies) {
			int enemyX = (int) e.getY() / 32;
			int enemyY = (int) e.getX() / 32;

			if (e.getClass() == Dog.class ||e.getClass() == Ghost.class) {
				if (dijkstraOrdinal[enemyX][enemyY] >= 0 && dijkstraOrdinal[enemyX][enemyY] < maxRange) {
					e.setVelocity(getEnemyDirection(enemyX, enemyY, dijkstraOrdinal, Direction.getOrdinal()).unit().scale(0.1f));
				}
			} else {
				if (dijkstraCardinal[enemyX][enemyY] >= 0 && dijkstraCardinal[enemyX][enemyY] < maxRange) {
					e.setVelocity(getEnemyDirection(enemyX, enemyY, dijkstraCardinal, Direction.getCardinal()).unit().scale(0.1f));
				}
			}
			e.update(delta);
		}

		// Check for projectile collisions
		int[][] layout = bg.levels.get(bg.level).getLayout();
		ListIterator<Projectile> iter = bg.projectiles.listIterator();
		while(iter.hasNext()) {
			Projectile p = iter.next();
			int pX = (int) p.getX() / 32;
			int pY = (int) p.getY() / 32;
			if (layout[pY][pX] >= 1 && layout[pY][pX] < 15) {
				iter.remove();
				break;
			}
			ListIterator<BaseEnemy> iter2 = bg.enemies.listIterator();
			while (iter2.hasNext()) {
				BaseEnemy e = iter2.next();
				if (p.collides(e) != null) {
					if (p.getClass() == WizardFireball.class) {
						e.setHp(e.getHp() - 200);
						if (e.getHp() <= 0) {
							iter2.remove();
						}
					} else if (p.getClass() == ElfArrow.class) {
						e.setHp(e.getHp() - 130);
						if (e.getHp() <= 0) {
							iter2.remove();
						}
					}
					iter.remove();
					break;
				}
			}
		}

		ListIterator<BaseEnemy> enemyIter = bg.enemies.listIterator();
		while (enemyIter.hasNext()) {
			BaseEnemy e = enemyIter.next();
			if (bg.slash != null) {
				if (bg.slash.collides(e) != null) {
					e.setHp(e.getHp() - 150);
					if (e.getHp() <= 0) {
						enemyIter.remove();
						break;
					}
				}
			}
			if (bg.lunge != null) {
				if (bg.lunge.collides(e) != null) {
					e.setHp(e.getHp() - 100);
					if (e.getHp() <= 0) {
						enemyIter.remove();
						break;
					}
				}
			}
			for (BasePlayer p: bg.players) {
				if (p == null) {
					continue;
				}
				if (e.collides(p) != null) {
					p.setHp(p.getHp() - 10);
					enemyIter.remove();
					break;
				}
			}
		}

		if (checkIfEnd(bg, currentPlayer)) {
			bg.level = bg.level + 1;
			bg.enemies = bg.levels.get(bg.level).getEnemyLayout();

			int[][] levelLayout = bg.levels.get(bg.level).getLayout();

			for (int i = 0; i < levelLayout.length - 1; i++) {
				for (int j = 0; j < levelLayout[0].length - 1; j++) {
					if (levelLayout[j][i] == 30) {

						int x = (i * 32) + 15;
						int y = (j * 32);
						bg.players[0].setPosition(x, y);
					}
				}
			}
		}
		if ( bg.players[0].getHp() <= 0) {
			bg.players[0] = bg.players[0] = new Wizard(bg.players[0].getX(), bg.players[0].getY());
			bg.enterState(BaseGame.GAMEOVERSTATE);
		}
	}

	@Override
	public int getID() {
		return BaseGame.PLAYINGSTATE;
	}

	private Boolean checkIfEnd(StateBasedGame game, BasePlayer currentPlayer) {
		BaseGame bg = (BaseGame) game;
		int[][] levelLayout = bg.levels.get(bg.level).getLayout();

		int x = (int)(currentPlayer.getX() + 16) / 32;
		int y = (int)(currentPlayer.getY() + 16) / 32;



		if (levelLayout[y][x] == 15) {
			if (bg.level == 4) {
				((BaseGameOverState) game.getState(BaseGame.GAMEOVERSTATE)).setUserWin(true);
				bg.enterState(BaseGame.GAMEOVERSTATE);
			}
			return true;
		}

		return false;
	}

	private Boolean checkIfCollision(StateBasedGame game, BasePlayer currentPlayer) {
		BaseGame bg = (BaseGame)game;
		int[][] levelLayout = bg.levels.get(bg.level).getLayout();
		int center = levelLayout[(int) (currentPlayer.getY() + 16) / 32][(int) (currentPlayer.getX() + 16) / 32];

		if (center > 0 && center < 15) {
			return true;
		}
		return false;
	}

	private void renderFloor(GameContainer container, StateBasedGame game,
							 Graphics g) {

		BaseGame bg = (BaseGame)game;

		int[][] levelLayout = bg.levels.get(bg.level).getLayout();

		int k = 0;
		int l = 0;
		int type = bg.levels.get(bg.level).getLevelTypes();
		for (int i = 0; i < levelLayout.length; i++) {
			for (int j = 0; j < levelLayout[0].length; j++) {
				int tempx = ((l * 32));
				int tempy = ((k * 32));
				switch(type) {
					case 1:
						g.drawImage(ResourceManager.getImage(BaseGame.NORMALFLOOR), tempx, tempy);
						break;
					case 2:
						g.drawImage(ResourceManager.getImage(BaseGame.STOREFLOOR), tempx, tempy);
						break;
					case 3:
						g.drawImage(ResourceManager.getImage(BaseGame.BOSSFLOOR), tempx, tempy);
						break;
				}
				switch(levelLayout[i][j]) {
					case 1:
						g.drawImage(ResourceManager.getImage(BaseGame.WALL1), tempx, tempy);
						break;
					case 2:
						g.drawImage(ResourceManager.getImage(BaseGame.WALL2), tempx, tempy);
						break;
					case 3:
						g.drawImage(ResourceManager.getImage(BaseGame.WALL3), tempx, tempy);
						break;
					case 4:
						g.drawImage(ResourceManager.getImage(BaseGame.WALL4), tempx, tempy);
						break;
					case 5:
						g.drawImage(ResourceManager.getImage(BaseGame.WALL5), tempx, tempy);
						break;
					case 6:
						g.drawImage(ResourceManager.getImage(BaseGame.WALL6), tempx, tempy);
						break;
					case 7:
						g.drawImage(ResourceManager.getImage(BaseGame.WALL7), tempx, tempy);
						break;
					case 8:
						g.drawImage(ResourceManager.getImage(BaseGame.WALL8), tempx, tempy);
						break;
					case 9:
						g.drawImage(ResourceManager.getImage(BaseGame.WALL9), tempx, tempy);
						break;
					case 10:
						g.drawImage(ResourceManager.getImage(BaseGame.WALL10), tempx, tempy);
						break;
					case 11:
						g.drawImage(ResourceManager.getImage(BaseGame.WALL11), tempx, tempy);
						break;
					case 12:
						g.drawImage(ResourceManager.getImage(BaseGame.WALL12), tempx, tempy);
						break;
					case 13:
						g.drawImage(ResourceManager.getImage(BaseGame.WALL13), tempx, tempy);
						break;
					case 14:
						g.drawImage(ResourceManager.getImage(BaseGame.WALL14), tempx, tempy);
						break;
					case 15:
						g.drawImage(ResourceManager.getImage(BaseGame.EXIT), tempx, tempy);
						break;
				}
				l = l + 1;
			}
			k = k + 1;
			l = 0;
		}
	}

	private int[][] Dijkstra(StateBasedGame game, ArrayList<Direction> validMovements, int maxRange) {
		BaseGame bg = (BaseGame)game;

		// Get current level layout
		int[][] layout = bg.levels.get(bg.level).getLayout();
		// Perform a single Dijkstra
		int[][] dijkstra = new int[layout.length][layout[0].length];
		boolean[][] visited = new boolean[layout.length][layout[0].length];

		// Add walls
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout[0].length; j++) {
				if (layout[i][j] >= 1 && layout[i][j] < 15) {
					dijkstra[i][j] = -1;
				}
			}
		}

		// Add initial sources
		Queue<Node> q = new LinkedList<Node>();
		for (BasePlayer p: bg.players) {
			if (p != null) {
				int playerX = (int) (p.getY() + 16) / 32;
				int playerY = (int) (p.getX() + 16) / 32;
				q.add(new Node(playerX, playerY, 0));
			}
		}

		// Iterate until no more movement is feasible
		while (!q.isEmpty()) {
			Node cursor = q.peek();
			dijkstra[cursor.x][cursor.y] = cursor.cost;
			visited[cursor.x][cursor.y] = true;
			q.remove();

			if (cursor.cost > 7) {
				break;
			}

			for (Direction d: validMovements) {
				int newX = cursor.x + (int) d.getMovement().getX();
				int newY = cursor.y + (int) d.getMovement().getY();
				if (newX < 0 || newY < 0 || newX >= dijkstra.length || newY >= dijkstra[0].length) { // Out of bounds
					continue;
				} else if (dijkstra[newX][newY] == -1 || visited[newX][newY] == true) {
					continue;
				} else {
					q.add(new Node(newX, newY, cursor.cost + 1));
					visited[newX][newY] = true;
				}
			}
		}

		return dijkstra;
	}

	private void setLevel(StateBasedGame game, int level) {
		BaseGame bg = (BaseGame)game;
		bg.level = level;
		bg.enemies = bg.levels.get(bg.level).getEnemyLayout();

		int[][] levelLayout = bg.levels.get(bg.level).getLayout();

		for (int i = 0; i < levelLayout.length - 1; i++) {
			for (int j = 0; j < levelLayout[0].length - 1; j++) {
				if (levelLayout[j][i] == 30) {

					int x = (i * 32) + 15;
					int y = (j * 32);
					bg.players[0].setPosition(x, y);
				}
			}
		}
	}

	// Helper function that returns a vector representing the direction to move given a dijkstra array
	// Returns <0,0> if no movement exists
	private Vector getEnemyDirection(int x, int y, int[][] dijkstra, ArrayList<Direction> validMovements) {
		int cost = dijkstra[x][y];
		for (Direction d: Direction.values()) {
			int newX = x + (int) d.getMovement().getY();
			int newY = y + (int) d.getMovement().getX();
			if (newX < 0 || newY < 0 || newX >= dijkstra.length || newY >= dijkstra[0].length) { // Out of bounds
				break;
			}
			if (dijkstra[newX][newY] == cost -1 && cost > 0) {
				return d.getMovement();
			}
		}
		return new Vector(0, 0);
	}

}
