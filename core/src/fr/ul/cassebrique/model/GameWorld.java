package fr.ul.cassebrique.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;

import java.util.ArrayList;
import java.util.Vector;

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
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                WorldManifold wm = contact.getWorldManifold();
                Vector2 normal = wm.getNormal();
                Body obj1 = contact.getFixtureA().getBody();
                Body obj2 = contact.getFixtureB().getBody();
                if (obj2.getType() == BodyDef.BodyType.DynamicBody){
                    float scalp;
                    float restitution = obj1.getFixtureList().get(0).getRestitution();
                    Vector2 r = new Vector2();
                    Vector2 vitesse = obj2.getLinearVelocity();
                    scalp =-2*(vitesse.x * normal.x + vitesse.y * normal.y); //-2(d . n)
                    //-2(d . n)n
                    r.x = scalp * normal.x;
                    r.y = scalp * normal.y;
                    //-2(d . n)n + d
                    r.x = (vitesse.x + r.x) * restitution;
                    r.y = (vitesse.y + r.y) * restitution;
                    //On applique r a la ball
                    obj2.setLinearVelocity(r);
                    if (obj1.getUserData() instanceof Brick)
                       wall.addChange((Brick)obj1.getUserData());
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
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
        pos3.y = getRacket().getPos().y + getRacket().getHeight()+10;
        pos3.y += TextureFactory.getTexBall().getHeight()/2;
        balls.add(new Ball(this, pos3));
        balls.get(2).setSpeed(new Vector2(-100, 200));
    }

    public void draw(SpriteBatch sb){
        background.draw(sb);
        wall.draw(sb);
        racket.draw(sb);
     /*   for (Ball b : balls)
            b.draw(sb);*/
        balls.get(balls.size()-1).draw(sb);
        update();
    }

    public void replaceBall(){
        Ball delete = balls.get(balls.size()-1); //Balle en jeu
        delete.deleteBody();
        balls.remove(delete);
        if (balls.size() > 0) {
            balls.remove(balls.size()-1); //Ball de la reserve
            Vector2 pos = new Vector2();
            pos.x = getRacket().getPos().x + getRacket().getWidth() / 2;
            pos.y = getRacket().getPos().y + getRacket().getHeight() + 10;
            pos.y += TextureFactory.getTexBall().getHeight() / 2;
            Ball newBall =  new Ball(this, pos);
            newBall.setSpeed(new Vector2(-100, 200));
            balls.add(newBall);
        }
    }

    public void restart(Boolean wall){
        if (!wall){ //bille perdue
            racket.clearBody();
            racket = new Racket(this);
            replaceBall();
        } else {

        }
    }

    public World getWorld() {
        return world;
    }

    public void update(){
        world.step(Gdx.graphics.getDeltaTime(),6, 2);
        wall.majWall();
    }

    public Boolean wallDestroy(){
        return wall.isDetroy();
    }

    public Racket getRacket(){
        return racket;
    }

    public Ball getBall(){
        if (balls.size() > 0)
            return balls.get(balls.size()-1);
        return null;
    }

    public static float getPixelsToMeters() {
        return PIXELS_TO_METERS;
    }

    public static float getMetersToPixels() {
        return METERS_TO_PIXELS;
    }
}
