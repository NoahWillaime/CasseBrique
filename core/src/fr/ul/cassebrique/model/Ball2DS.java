package fr.ul.cassebrique.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by noahd on 28/03/2018.
 */

public class Ball2DS extends Ball  {
    private Array<Sprite> imgBoule;
    private int numIm;

    public Ball2DS(GameWorld gaw, Vector2 pos) {
        super(gaw, pos);
        numIm = 0;
        TextureAtlas atlasB;
        atlasB = new TextureAtlas(Gdx.files.internal("images/Boule.pack"));
        imgBoule = atlasB.createSprites("boule");
    }

    @Override
    public void draw(SpriteBatch sb){
        Sprite sp = imgBoule.get(numIm);
        sp.setRotation(3);
        sp.setOriginCenter();
        Vector2 pos = getPosBody();
        pos.x *= GameWorld.getMetersToPixels();
        pos.y *= GameWorld.getMetersToPixels();
        pos.x -= rayon;
        pos.y -= rayon;
        sp.setBounds(pos.x, pos.y, 2*rayon, 2*rayon);
        sp.draw(sb);
    }
}
