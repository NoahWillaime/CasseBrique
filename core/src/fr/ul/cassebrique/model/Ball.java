package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import fr.ul.cassebrique.dataFactories.TextureFactory;

/**
 * Created by noahd on 02/02/2018.
 */

public class Ball {
    private Body body;
    private static float rayon = 12;
    private GameWorld gw;
  //  private Vector2 pos;

    public Ball(GameWorld gaw, Vector2 pos){
        this.gw = gaw;
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.fixedRotation = false;
        bodydef.bullet = true;
        float convert = GameWorld.getPixelsToMeters();
        this.body = gw.getWorld().createBody(bodydef);
        //
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        Vector2 pos_phy = new Vector2();
        pos_phy.x = pos.x * convert;
        pos_phy.y = pos.y * convert;
       // shape.setPosition(pos_phy);
        shape.setRadius(rayon*convert);
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 0f;
        body.createFixture(fixtureDef);
        shape.dispose();
        //
        body.setTransform(pos_phy, 0);
    }

    public Vector2 getPosBody(){
        return body.getPosition();
    }

    public void draw(SpriteBatch sb){
        sb.begin();
        Vector2 pos = getPosBody();
        pos.x *= GameWorld.getMetersToPixels();
        pos.y *= GameWorld.getMetersToPixels();
        pos.x -= TextureFactory.getTexBall().getWidth()/2;
        pos.y -= TextureFactory.getTexBall().getHeight()/2;
        sb.draw(TextureFactory.getTexBall(), pos.x, pos.y);
        sb.end();
    }

    public void setSpeed(Vector2 v){
        v.x *= GameWorld.getPixelsToMeters();
        v.y *= GameWorld.getPixelsToMeters();
        this.body.setLinearVelocity(v);
    }
}
