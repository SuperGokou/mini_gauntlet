package BaseGame;

import BaseGame.enemies.*;

import java.util.ArrayList;

public class Level {
    private int[][] layout;
    private int levelType;

    public Level(int[][] layouts, int levelTypes) {
        layout = layouts;
        levelType = levelTypes;
    }

    public int[][] getLayout() {return layout; }

    public int getLevelTypes() {return levelType; }

    public ArrayList<BaseEnemy> getEnemyLayout() {
        ArrayList<BaseEnemy> enemies = new ArrayList<>(50);

        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {

                int x = ((j * 32) + 16);
                int y = ((i * 32) + 16);

                switch (layout[i][j]) {
                case 20:
                    enemies.add(new BlueEnemy(x, y));
                    break;
                case 21:
                    enemies.add(new GreenEnemy(x, y));
                    break;
                case 22:
                    enemies.add(new RedEnemy(x, y));
                    break;
                case 23:
                    enemies.add(new YellowEnemy(x, y));
                    break;
                case 24:
                    enemies.add(new Ghost(x, y));
                    break;
                case 25:
                    enemies.add(new Dog(x, y));
                    break;
                }
            }
        }
        return enemies;
    }
}
