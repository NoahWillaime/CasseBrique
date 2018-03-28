package fr.ul.cassebrique.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by noahd on 20/03/2018.
 */

public class Ball2DA extends Ball {
    private Array<Sprite> imgBoule;
    private long timeSav;
    private float angle;
    private int numIm;

    public Ball2DA(GameWorld gaw, Vector2 pos) {
        super(gaw, pos);
        timeSav = System.currentTimeMillis();
        numIm = 0;
        TextureAtlas atlasB;
        atlasB = new TextureAtlas(Gdx.files.internal("images/Boule.pack"));
        imgBoule = atlasB.createSprites("boule");
    }

    public void calculAnimation(){
        long time = System.currentTimeMillis() - timeSav;
        timeSav = System.currentTimeMillis();
        float dist = body.getLinearVelocity().len() * time;
        angle = dist / (rayon * GameWorld.getPixelsToMeters());
        double imageParcourues = angle * 32 / 2 * Math.PI;
        numIm = (int)(numIm + imageParcourues) % 32;
    }

    @Override
    public void draw(SpriteBatch sb){
        calculAnimation();
        Sprite sp = imgBoule.get(numIm);
        sp.setRotation(3);
        sp.setOriginCenter();
        Vector2 pos = getPosBody();
        pos.x *= GameWorld.getMetersToPixels();
        pos.y *= GameWorld.getMetersToPixels();
        pos.x -= rayon;
        pos.y -= rayon;
        sp.setBounds(pos.x, pos.y, 2*rayon, 2*rayon);
        sb.begin();
        sp.draw(sb);
        sb.end();
    }
}
