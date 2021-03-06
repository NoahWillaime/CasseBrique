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
    private static final Texture texBravo = new Texture(Gdx.files.internal("images/Bravo.bmp"));
    private static final Texture texPerteBalle = new Texture(Gdx.files.internal("images/PerteBalle.bmp"));
    private static final Texture texPerte = new Texture(Gdx.files.internal("images/Perte.bmp"));
    private static final Texture texBoule = new Texture(Gdx.files.internal("badlogic.jpg"));

    public static Texture getTexBoule() {return texBoule;}

    public static Texture getTexPerte() {return texPerte;}

    public static Texture getTexPerteBalle() {return texPerteBalle;}

    public static Texture getTexBravo() {return texBravo;}

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
