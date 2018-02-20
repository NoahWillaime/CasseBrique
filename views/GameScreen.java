package fr.ul.cassebrique.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.ul.cassebrique.dataFactories.TextureFactory;
import fr.ul.cassebrique.model.GameWorld;

/**
 * Created by noahd on 26/01/2018.
 */

public class GameScreen extends ScreenAdapter {
    private GameWorld gw;
    private SpriteBatch sb;

    public GameScreen(){
        gw = new GameWorld(this);
        sb = new SpriteBatch();
    }

    public void render(float delta){
        gw.draw(sb);
    }

    public void dispose(){
        sb.dispose();
    }
}
