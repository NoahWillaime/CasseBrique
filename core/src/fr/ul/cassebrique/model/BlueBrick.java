package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import fr.ul.cassebrique.dataFactories.TextureFactory;

/**
 * Created by noahd on 02/02/2018.
 */

public class BlueBrick extends Brick {
    public BlueBrick(int nbCoups, Vector2 pos, GameWorld gw) {
        super(nbCoups, pos, gw);
    }

    @Override
    public void draw(SpriteBatch sb){
        sb.begin();
        sb.draw(TextureFactory.getTexBlueBrick(), pos.x, pos.y);
        sb.end();
    }
}
