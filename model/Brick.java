package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.Texture;

import fr.ul.cassebrique.dataFactories.TextureFactory;

/**
 * Created by noahd on 26/01/2018.
 */

public enum Brick {
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
    }
}
