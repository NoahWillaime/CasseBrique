package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import fr.ul.cassebrique.dataFactories.TextureFactory;

/**
 * Created by noahd on 26/01/2018.
 */

public abstract class Brick {
    protected Vector2 pos;
    protected int nbCoups;
    protected GameWorld gw;
    protected Body body;

    public Brick(int nbCoups, Vector2 pos, GameWorld gaw){
        this.pos = pos;
        this.nbCoups = nbCoups;
        this.gw = gaw;
        //
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        this.body = gw.getWorld().createBody(bodyDef);
        //
        FixtureDef fixtureDef = new FixtureDef();
        float convert = GameWorld.getPixelsToMeters();
        PolygonShape ps = new PolygonShape();
        float[] pts = {pos.x * convert, pos.y * convert, pos.x * convert, (pos.y + TextureFactory.getTexBlueBrick().getHeight())*convert, (pos.x+TextureFactory.getTexBlueBrick().getWidth())*convert, (pos.y + TextureFactory.getTexBlueBrick().getHeight())*convert, (pos.x+TextureFactory.getTexBlueBrick().getWidth())*convert, pos.y*convert};
        ps.set(pts);
        fixtureDef.shape = ps;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 0f;
        body.createFixture(fixtureDef);
        ps.dispose();
    }

    public int getNbCoups() {
        return nbCoups;
    }

    public Vector2 getPos(){
        return pos;
    }

    public void draw(SpriteBatch sb){
    }
    /*
    Vide(null),
    Bleue(TextureFactory.getTexBlueBrick()),
    Verte(TextureFactory.getTexGreenBrickA()),
    VerteAbimee(TextureFactory.getTexGreenBrickB());

    private Texture tex;

    Brick(Texture text){
        this.tex = text;
    }

    public Texture getTex() {
        return tex;
    }*/
}
