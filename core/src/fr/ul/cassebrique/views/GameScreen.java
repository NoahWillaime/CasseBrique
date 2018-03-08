package fr.ul.cassebrique.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Timer;
import fr.ul.cassebrique.dataFactories.TextureFactory;
import fr.ul.cassebrique.model.Ball;
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
    private Timer.Task timer;
    private Boolean isTimerok;
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;

    public GameScreen(){
        gw = new GameWorld(this);
        sb = new SpriteBatch();
        gs = new GameState();
        isTimerok = true;
        timer = new Timer.Task() {
            @Override
            public void run() {
                restart();
                isTimerok = true;
            }
        };
        camera = new OrthographicCamera(Gdx.graphics.getWidth()*GameWorld.getPixelsToMeters(), Gdx.graphics.getHeight()*GameWorld.getPixelsToMeters());
        camera.translate(new Vector2((Gdx.graphics.getWidth()/2)*GameWorld.getPixelsToMeters(), (Gdx.graphics.getHeight()/2)*GameWorld.getPixelsToMeters()));
        debugRenderer = new Box2DDebugRenderer();
    }

    public void render(float delta) {
        if (gs.getState() == GameState.State.Running) {
            update();
            gw.draw(sb);
        } else {
            if (gs.getState() == GameState.State.BallLoss){
                sb.begin();
                sb.draw(TextureFactory.getTexPerteBalle(), 0, 0);
                sb.end();
            } else if (gs.getState() == GameState.State.GameOver) {
                sb.begin();
                sb.draw(TextureFactory.getTexPerte(), 0, 0);
                sb.end();
            } else if (gs.getState() == GameState.State.Won) {
                sb.begin();
                sb.draw(TextureFactory.getTexBravo(), 0, 0);
                sb.end();
            }
            if (isTimerok) {
                Timer.schedule(timer, 3);
                isTimerok = false;
            }
        }
        /*Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        sb.setProjectionMatrix(camera.combined);
        debugRenderer.render(gw.getWorld(), camera.combined);*/
    }

    public void restart(){
        if (gs.getState() == GameState.State.BallLoss)
            gw.restart(false);
        else if (gs.getState() == GameState.State.Won) {
            gw.restart(true);
        } else if (gs.getState() == GameState.State.GameOver){
            gw.reset();
        }
        gs.setState(GameState.State.Running);
    }

    public void update() {
        gw.update();
        if (Gdx.input.isTouched()) {
            Racket racket = gw.getRacket();
            racket.moveTouch(Gdx.input.getX());
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            Racket racket = gw.getRacket();
            racket.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            Racket racket = gw.getRacket();
            racket.moveRight();
        }
        Ball b = gw.getBall();
        if (b != null) {
            if (b.isOut()) {
                if (gw.getNbBalls() == 1) {
                    gs.setState(GameState.State.GameOver);
                } else {
                    gs.setState(GameState.State.BallLoss);
                }
            }
        }
        if (gw.wallDestroy())
            gs.setState(GameState.State.Won);
    }

    public void dispose(){
        sb.dispose();
    }
}
