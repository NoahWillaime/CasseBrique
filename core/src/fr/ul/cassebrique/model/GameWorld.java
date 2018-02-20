package fr.ul.cassebrique.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import fr.ul.cassebrique.dataFactories.TextureFactory;
import fr.ul.cassebrique.views.GameScreen;

/**
 * Created by noahd on 26/01/2018.
 */

public class GameWorld {
    private static float METERS_TO_PIXELS = 250f;
    private static float PIXELS_TO_METERS = 1f/METERS_TO_PIXELS;

    private World world;
    private GameScreen gs;
    private Wall wall;
    private Background background;
    private Racket racket;
    private ArrayList<Ball> balls;

    public GameWorld(GameScreen screen){
        world = new World(new Vector2(0, 0), true);
        gs = screen;
        wall = new Wall(this);
        racket = new Racket(this);
        balls = new ArrayList<Ball>(3);
        setBalls();
        background = new Background(this);
    }

    public void setBalls(){
        Vector2 pos1 = new Vector2();
        pos1.x = TextureFactory.getTexBack().getWidth();
        pos1.x -= TextureFactory.getTexBorder().getWidth()/2;
        pos1.x -= TextureFactory.getTexBall().getWidth()/2;
        pos1.y = TextureFactory.getTexBorder().getHeight()/2;
        pos1.y -= TextureFactory.getTexBall().getHeight()/2;
        balls.add(new Ball(this, pos1));
        Vector2 pos2 = new Vector2();
        pos2.x = pos1.x;
        pos2.y = pos1.y + TextureFactory.getTexBorder().getHeight();
        balls.add(new Ball(this, pos2));
        Vector2 pos3 = new Vector2();
        pos3.x = getRacket().getPos().x + getRacket().getWidth()/2;
        pos3.y = getRacket().getPos().y + getRacket().getHeight() + 200;
        pos3.y += TextureFactory.getTexBall().getHeight()/2;
        balls.add(new Ball(this, pos3));
        balls.get(2).setSpeed(new Vector2(100, -180));
    }

    public void draw(SpriteBatch sb){
        background.draw(sb);
        wall.draw(sb);
        racket.draw(sb);
     /*   for (Ball b : balls)
            b.draw(sb);*/
        balls.get(2).draw(sb);
        update();
    }

    public World getWorld() {
        return world;
    }

    public void update(){
        world.step(Gdx.graphics.getDeltaTime(),6, 2);
    }

    public Racket getRacket(){
        return racket;
    }

    public Vector2 getBallPos(){
        return balls.get(2).getPosBody();
    }

    public static float getPixelsToMeters() {
        return PIXELS_TO_METERS;
    }

    public static float getMetersToPixels() {
        return METERS_TO_PIXELS;
    }
}
