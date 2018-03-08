package fr.ul.cassebrique.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import fr.ul.cassebrique.dataFactories.TextureFactory;

/**
 * Created by noahd on 02/02/2018.
 */

public class Racket {
    private GameWorld gw;
    private Vector2 pos;
    private int height;
    private int width;
    private Body bgauche;
    private Body bdroite;
    private Body bmillieu;

    public Racket(GameWorld gw){
        this.gw = gw;
        this.height = TextureFactory.getTexRacket().getHeight();
        this.width = TextureFactory.getTexRacket().getWidth();
        float x = (TextureFactory.getTexBack().getWidth() - TextureFactory.getTexBorder().getWidth())/2;
        this.pos = new Vector2();
        x -= width/2;
        this.pos.x = x;
        this.pos.y = 50;
        //Convert pixel > Meter
        float convert = GameWorld.getPixelsToMeters();
        //Body bille gauche
        Vector2 pos1 = new Vector2();
        pos1.x = pos.x + height/2;
        pos1.y = pos.y + height/2;
        BodyDef bodyDef1 = new BodyDef();
        bodyDef1.type = BodyDef.BodyType.StaticBody;
        bodyDef1.position.set(pos1.x*convert, pos1.y*convert);
        this.bgauche = gw.getWorld().createBody(bodyDef1);
        //Body bille droite
        pos1.x = pos.x + width - height/2;
        pos1.y = pos.y + height/2;
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        bodyDef2.position.set((pos1.x)*convert, (pos1.y)*convert);
        this.bdroite = gw.getWorld().createBody(bodyDef2);
        //Shape des 2 billes
        FixtureDef fixtureDef1 = new FixtureDef();
        CircleShape cgauchedroite = new CircleShape();
        cgauchedroite.setRadius((height/2)*convert);
        fixtureDef1.shape = cgauchedroite;
        fixtureDef1.density = 1f;
        fixtureDef1.restitution = 1f;
        fixtureDef1.friction = 0f;
        //Definition des shapes des body des billes
        bgauche.createFixture(fixtureDef1);
        bdroite.createFixture(fixtureDef1);
        cgauchedroite.dispose();
        //Barre milieu
        BodyDef bodyDef3 = new BodyDef();
        bodyDef3.type = BodyDef.BodyType.StaticBody;
        this.bmillieu = gw.getWorld().createBody(bodyDef3);
        FixtureDef fixtureDef2 = new FixtureDef();
        PolygonShape ps = new PolygonShape();
        float pts[] = {(pos.x+height)*convert, (pos.y)*convert, (pos.x+height)*convert, (pos.y+height)*convert, (pos.x+width-height)*convert, (pos.y+height)*convert, (pos.x+width-height)*convert, (pos.y)*convert};
        ps.set(pts);
        fixtureDef2.shape = ps;
        fixtureDef2.density = 1f;
        fixtureDef2.restitution = 1f;
        fixtureDef2.friction = 0f;
        bmillieu.createFixture(fixtureDef2);
        ps.dispose();
    }

    public void draw(SpriteBatch sb){
        sb.begin();
        Vector2 posg = getPosGauche();
        posg.x *= GameWorld.getMetersToPixels();
        posg.y *= GameWorld.getMetersToPixels();
        posg.x -= height/2;
        posg.y -= height/2;
        sb.draw(TextureFactory.getTexRacket(), posg.x, posg.y);
        sb.end();
    }

    public void moveTouch(int x){
        if ( x < getPosGauche().x*GameWorld.getMetersToPixels()){ //Superieur a Racket : Droite, Inferieur : Gauche
            //aller a gauche
            moveLeft();
        } else if (x > getPosDroite().x*GameWorld.getMetersToPixels()) {
            //aller a droite
           moveRight();
        }
    }

    public void moveRight(){
        Vector2 posd = getPosDroite();
        if ((posd.x*GameWorld.getMetersToPixels())+ 10 < TextureFactory.getTexBack().getWidth()-(TextureFactory.getTexBorder().getWidth() * 2)) {
            posd.x += 10*GameWorld.getPixelsToMeters();
            Vector2 posg = getPosGauche();
            posg.x += 10*GameWorld.getPixelsToMeters();
            Vector2 posm = getPosMillieu();
            posm.x += 10*GameWorld.getPixelsToMeters();
            setPosDroite(posd);
            setPosGauche(posg);
            setPosMillieu(posm);
        }
    }

    public void moveLeft(){
        Vector2 posg = getPosGauche();
        if ((posg.x*GameWorld.getMetersToPixels()) - 10 > TextureFactory.getTexBorder().getWidth()){
            posg.x -= 10*GameWorld.getPixelsToMeters();
            Vector2 posd = getPosDroite();
            posd.x -= 10*GameWorld.getPixelsToMeters();
            Vector2 posm = getPosMillieu();
            posm.x -= 10*GameWorld.getPixelsToMeters();
            setPosDroite(posd);
            setPosGauche(posg);
            setPosMillieu(posm);
        }
    }

    public void clearBody(){
        gw.getWorld().destroyBody(this.bdroite);
        gw.getWorld().destroyBody(this.bgauche);
        gw.getWorld().destroyBody(this.bmillieu);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getPosGauche(){
        return bgauche.getPosition();
    }

    public Vector2 getPosDroite(){
        return bdroite.getPosition();
    }

    public Vector2 getPosMillieu(){
        return bmillieu.getPosition();
    }

    public void setPosGauche(Vector2 pos){
        bgauche.setTransform(pos, 0);
    }

    public void setPosDroite(Vector2 pos){
        bdroite.setTransform(pos, 0);
    }

    public void setPosMillieu(Vector2 pos){
        bmillieu.setTransform(pos, 0);
    }

}
