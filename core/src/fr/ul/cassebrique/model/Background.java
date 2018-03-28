package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import fr.ul.cassebrique.dataFactories.TextureFactory;

/**
 * Created by noahd on 02/02/2018.
 */

public class Background {
    private Body body;
    private GameWorld gw;

    public Background(GameWorld gaw){
        this.gw = gaw;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        this.body = gw.getWorld().createBody(bodyDef);
        //
        FixtureDef fixtureDef = new FixtureDef();
        float convert = GameWorld.getPixelsToMeters();
        ChainShape chainShape = new ChainShape();
        //float[] pts = {50*convert, 0*convert, 50*convert, 650*convert, 1050*convert, 650*convert, 1050*convert, 0*convert};
        float[] pts2 = new float[8];
        float width = TextureFactory.getTexBorder().getWidth();
        float height = 0f;
       for (int i = 0; i < 8; i += 2){
            pts2[i] =  width * convert;
            pts2[i+1] = height * convert;
            if (i == 0) {
                height += TextureFactory.getTexBack().getHeight() - TextureFactory.getTexBorder().getHeight();
            } else if (i == 2) {
                width += TextureFactory.getTexBack().getWidth() - 3*TextureFactory.getTexBorder().getWidth();
            } else if (i == 4) {
                height -= TextureFactory.getTexBack().getHeight() - TextureFactory.getTexBorder().getHeight();
            }
        }
        chainShape.createChain(pts2);
        fixtureDef.shape = chainShape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 0f;
        body.createFixture(fixtureDef);
        chainShape.dispose();
        body.setUserData("background");
    }

    public Vector2 getPosBody(){
        return body.getPosition();
    }

    public void draw(SpriteBatch sb){
        Vector2 pos = getPosBody();
        pos.x *= GameWorld.getMetersToPixels();
        pos.y *= GameWorld.getMetersToPixels();
        sb.draw(TextureFactory.getTexBack(), pos.x, pos.y);
    }
}
