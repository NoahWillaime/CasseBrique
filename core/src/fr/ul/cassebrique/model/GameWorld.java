package fr.ul.cassebrique.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;

import java.util.ArrayList;

import fr.ul.cassebrique.dataFactories.SoundFactory;
import fr.ul.cassebrique.dataFactories.TextureFactory;
import fr.ul.cassebrique.views.GameScreen;

/**
 * Created by noahd on 26/01/2018.
 */

public class GameWorld {
    private static float METERS_TO_PIXELS = 250f;
    private static float PIXELS_TO_METERS = 1f/METERS_TO_PIXELS;
    private static Vector2 VITESSE_INITIAL = new Vector2(100, 200);

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
                Vector2 r = new Vector2();
                float scalp;
                if (obj2.getType() == BodyDef.BodyType.DynamicBody){
                    Vector2 vitesse = obj2.getLinearVelocity();
                    float restitution = obj1.getFixtureList().get(0).getRestitution();
                    scalp =-2*(vitesse.x * normal.x + vitesse.y * normal.y);
                    r.x = (scalp * normal.x + vitesse.x) * restitution;
                    r.y = (scalp * normal.y + vitesse.y) * restitution;
                    if (r.y < 0 && vitesse.y > 0)
                        r.y += r.y * (1f/100f);
                    if (obj1.getUserData() instanceof Brick)
                       wall.addChange((Brick)obj1.getUserData());
                    else if (obj1.getUserData().equals("bracket")) {
                        r.x *= 1.5f ;
                    }
                    if (scalp > 0) {
                        if (obj1.getUserData().equals("bracket") || obj1.getUserData().equals("bmillieu"))
                            SoundFactory.listenImpact(0.3f);
                        else
                            SoundFactory.listenColision(0.3f);
                        obj2.setLinearVelocity(r);
                    }
                } else if (obj1.getType() == BodyDef.BodyType.DynamicBody) {
                    if (obj1.getUserData().equals("bracket") || obj1.getUserData().equals("bmillieu"))
                        SoundFactory.listenImpact(0.3f);
                    else
                        SoundFactory.listenColision(0.3f);
                    float restitution = obj2.getFixtureList().get(0).getRestitution();
                    Vector2 vitesse = obj1.getLinearVelocity();
                    scalp =-2*(vitesse.x * normal.x + vitesse.y * normal.y);
                    r.x = (scalp * normal.x + vitesse.x) * restitution;
                    r.y = (scalp * normal.y + vitesse.y) * restitution;
                    if (r.y < 0 && vitesse.y > 0)
                        r.y += r.y * (1f/100f);
                    if (obj2.getUserData() instanceof Brick)
                        wall.addChange((Brick)obj2.getUserData());
                    else if (obj2.getUserData().equals("bracket")){
                        r.x *= 1.5f ;
                    }
                    obj1.setLinearVelocity(r);
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
        pos1.y = TextureFactory.getTexBorder().getHeight()/2;
        balls.add(new Ball(this, pos1));
        Vector2 pos2 = new Vector2();
        pos2.x = pos1.x;
        pos2.y = pos1.y + TextureFactory.getTexBorder().getHeight();
        balls.add(new Ball(this, pos2));
        Vector2 pos3 = new Vector2();
        pos3.x = getRacket().getPos().x + getRacket().getWidth()/2;
        pos3.y = getRacket().getPos().y + getRacket().getHeight()+10;
        pos3.y += TextureFactory.getTexBall().getHeight()/2;
        balls.add(new Ball3D(this, pos3));
        balls.get(getNbBalls()-1).setSpeed(new Vector2(VITESSE_INITIAL.x, VITESSE_INITIAL.y));
    }

    public void draw(SpriteBatch sb){
        background.draw(sb);
        wall.draw(sb);
        racket.draw(sb);
        for (Ball b : balls)
            b.draw(sb);
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
            newBall.setSpeed(new Vector2(VITESSE_INITIAL.x, VITESSE_INITIAL.y));
            balls.add(newBall);
        }
    }

    public void replaceAddBall(){
        Ball delete = balls.get(balls.size()-1);
        delete.deleteBody();
        balls.remove(delete);
        Vector2 newpos = new Vector2();
        newpos.x = TextureFactory.getTexBack().getWidth();
        newpos.x -= TextureFactory.getTexBorder().getWidth()/2;
        newpos.y = (TextureFactory.getTexBorder().getHeight()/2);
        if (getNbBalls() > 1){
            newpos.y += TextureFactory.getTexBorder().getHeight() * getNbBalls();
        }
        balls.add(new Ball(this, newpos));
        Vector2 pos3 = new Vector2();
        pos3.x = getRacket().getPos().x + getRacket().getWidth()/2;
        pos3.y = getRacket().getPos().y + getRacket().getHeight()+10;
        pos3.y += TextureFactory.getTexBall().getHeight()/2;
        balls.add(new Ball(this, pos3));
        balls.get(getNbBalls()-1).setSpeed(new Vector2(VITESSE_INITIAL.x, VITESSE_INITIAL.y));
    }

    public void restart(Boolean wall){
        if (!wall){ //bille perdue
            racket.clearBody();
            racket = new Racket(this);
            replaceBall();
        } else { //Mur détruit
            racket.clearBody();
            racket = new Racket(this);
            replaceAddBall();
            this.wall.reset();
        }
    }

    public void reset(){
        racket.clearBody();
        racket = new Racket(this);
        balls.clear();
        balls = new ArrayList<Ball>(3);
        setBalls();
        this.wall.reset();
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

    public OrthographicCamera getCamera(){
        return gs.getCamera();
    }

    public int getNbBalls(){
        return balls.size();
    }

    public static float getPixelsToMeters() {
        return PIXELS_TO_METERS;
    }

    public static float getMetersToPixels() {
        return METERS_TO_PIXELS;
    }
}
