package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import fr.ul.cassebrique.dataFactories.TextureFactory;

/**
 * Created by noahd on 02/02/2018.
 */

public class GreenBrick extends Brick {
    public GreenBrick(int nbCoups, Vector2 pos, GameWorld gw){
        super(nbCoups, pos, gw);
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.begin();
        if (nbCoups == 2){
            sb.draw(TextureFactory.getTexGreenBrickA(), pos.x, pos.y);
        } else if (nbCoups == 1){
            sb.draw(TextureFactory.getTexGreenBrickB(), pos.x, pos.y);
        }
        sb.end();
    }
}
