package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.ul.cassebrique.views.GameScreen;

/**
 * Created by noahd on 26/01/2018.
 */

public class GameWorld {
    private GameScreen gs;
    private Wall wall;

    public GameWorld(GameScreen screen){
        gs = screen;
        wall = new Wall();
    }

    public void draw(SpriteBatch sb){
        wall.draw(sb);
    }
}
