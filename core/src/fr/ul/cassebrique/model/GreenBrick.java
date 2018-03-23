package fr.ul.cassebrique.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import fr.ul.cassebrique.dataFactories.TextureFactory;

/**
 * Created by noahd on 02/02/2018.
 */

public class GreenBrick extends Brick {
    private Animation<TextureRegion> anim;
    private float tempsAnime;

    public GreenBrick(int nbCoups, Vector2 pos, GameWorld gw){
        super(nbCoups, pos, gw);
        tempsAnime = 0f;
        TextureAtlas atlasGreen = new TextureAtlas(Gdx.files.internal("images/Anim2Ca.pack"));
        Array<TextureAtlas.AtlasRegion> list = atlasGreen.findRegions("Anim2Ca");
        anim = new Animation(0.1f, list, Animation.PlayMode.LOOP);
    }

    @Override
    public void draw(SpriteBatch sb) {
        tempsAnime += Gdx.graphics.getDeltaTime();
        sb.begin();
        if (nbCoups == 2){
            System.out.print(tempsAnime);
            TextureRegion image = anim.getKeyFrame(tempsAnime);
            sb.draw(image, pos.x, pos.y);
        } else if (nbCoups == 1){
            sb.draw(TextureFactory.getTexGreenBrickB(), pos.x, pos.y);
        }
        sb.end();
    }
}
