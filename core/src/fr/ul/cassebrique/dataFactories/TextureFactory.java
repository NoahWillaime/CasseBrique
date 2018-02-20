package fr.ul.cassebrique.dataFactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by noahd on 26/01/2018.
 */

public class TextureFactory {
    private static final Texture texBlueBrick = new Texture(Gdx.files.internal("images/Brique1C.png"));
    private static final Texture texGreenBrickA = new Texture(Gdx.files.internal("images/Brique2Ca.png"));
    private static final Texture texGreenBrickB = new Texture(Gdx.files.internal("images/Brique2Cb.png"));
    private static final Texture texBack = new Texture(Gdx.files.internal("images/Fond.png"));
    private static final Texture texBall = new Texture(Gdx.files.internal("images/Bille.png"));
    private static final Texture texBorder = new Texture(Gdx.files.internal("images/Contour.png"));
    private static final Texture texRacket = new Texture(Gdx.files.internal("images/Barre.png"));

    public static Texture getTexRacket() {
        return texRacket;
    }

    public static Texture getTexBorder() {
        return texBorder;
    }

    public static Texture getTexBall() {
        return texBall;
    }

    public static Texture getTexBack() {
        return texBack;
    }

    public static Texture getTexGreenBrickB() {
        return texGreenBrickB;
    }

    public static Texture getTexGreenBrickA() {
        return texGreenBrickA;
    }

    public static Texture getTexBlueBrick() {
        return texBlueBrick;
    }
}
