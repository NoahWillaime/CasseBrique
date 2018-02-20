package fr.ul.cassebrique.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import fr.ul.cassebrique.dataFactories.TextureFactory;
import fr.ul.cassebrique.model.GameState;
import fr.ul.cassebrique.model.GameWorld;
import fr.ul.cassebrique.model.Racket;

/**
 * Created by noahd on 26/01/2018.
 */

public class GameScreen extends ScreenAdapter {
    private GameWorld gw;
    private SpriteBatch sb;
    private GameState gs;


    public GameScreen(){
        gw = new GameWorld(this);
        sb = new SpriteBatch();
        gs = new GameState();
    }

    public void render(float delta){
        update();
        gw.draw(sb);
    }

    public void update(){
        if (Gdx.input.isTouched()){
            Racket racket = gw.getRacket();
            racket.moveTouch(Gdx.input.getX());
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            Racket racket = gw.getRacket();
            racket.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            Racket racket = gw.getRacket();
            racket.moveRight();
        }
    }

    public void dispose(){
        sb.dispose();
    }
}
