package fr.ul.cassebrique.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fr.ul.cassebrique.controls.Listener;
import fr.ul.cassebrique.dataFactories.SoundFactory;
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
    private Viewport vp;
    private Listener listener;

    public GameScreen(){
        gw = new GameWorld(this);
        sb = new SpriteBatch();
        gs = new GameState();
        listener = new Listener(this);
        Gdx.input.setInputProcessor(listener);
        isTimerok = true;
        timer = new Timer.Task() {
            @Override
            public void run() {
                restart();
                isTimerok = true;
            }
        };
        camera = new OrthographicCamera(TextureFactory.getTexBack().getWidth(), TextureFactory.getTexBack().getHeight());
        vp = new FitViewport(TextureFactory.getTexBack().getWidth(), TextureFactory.getTexBack().getHeight(), camera);
        vp.apply();
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        debugRenderer = new Box2DDebugRenderer();
    }

    public void render(float delta) {
        camera.update();
        sb.setProjectionMatrix(camera.combined);
        if (gs.getState() == GameState.State.Running) {
            update();
            gw.draw(sb);
        } else if (gs.getState() == GameState.State.Pause) {
            gw.draw(sb);
        } else if (gs.getState() == GameState.State.Quit){
            Gdx.app.exit();
        } else {
            gw.draw(sb);
            sb.begin();
            if (gs.getState() == GameState.State.BallLoss){
                sb.draw(TextureFactory.getTexPerteBalle(), 0, 0);
            } else if (gs.getState() == GameState.State.GameOver) {
                sb.draw(TextureFactory.getTexPerte(), 0, 0);
            } else if (gs.getState() == GameState.State.Won) {
                sb.draw(TextureFactory.getTexBravo(), 0, 0);
            }
            sb.end();
            if (isTimerok) {
                Timer.schedule(timer, 3);
                isTimerok = false;
            }
        }
        /*Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);*/
        /*debugRenderer.render(gw.getWorld(), camera.combined);*/
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
        float ratio = (float)TextureFactory.getTexBack().getWidth() / (float)Gdx.graphics.getWidth();
        boolean screenTouch = Gdx.input.isTouched();
        boolean leftTouch = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightTouch = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        float acY = Gdx.input.getAccelerometerY();
        if (acY != 0 && !screenTouch && !leftTouch && !rightTouch){
            Racket racket = gw.getRacket();
            int orientation = Gdx.input.getRotation();
            if (acY < 0)
                racket.moveLeft(orientation);
            else
                racket.moveRight(orientation);
        } else {
            if (screenTouch) {
                Racket racket = gw.getRacket();
                racket.moveTouch(Gdx.input.getX() * ratio);
            } else if (leftTouch) {
                Racket racket = gw.getRacket();
                racket.moveLeft(100);
            } else if (rightTouch) {
                Racket racket = gw.getRacket();
                racket.moveRight(100);
            }
        }
        Ball b = gw.getBall();
        if (b != null) {
            if (b.isOut()) {
                if (gw.getNbBalls() == 1) {
                    SoundFactory.listenPerte(0.3f);
                    gs.setState(GameState.State.GameOver);
                } else {
                    SoundFactory.listenPerteBalle(0.3f);
                    gs.setState(GameState.State.BallLoss);
                }
            }
        }
        if (gw.wallDestroy()) {
            SoundFactory.listenVictoire(0.3f);
            gs.setState(GameState.State.Won);
        }
    }

    @Override
    public void resize(int width, int height){
        vp.update(width, height);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
    }

    public void setState(GameState.State state){
        gs.setState(state);
    }

    public GameState.State getCurrentState(){
        return this.gs.getState();
    }

    public void dispose(){
        sb.dispose();
    }
}
