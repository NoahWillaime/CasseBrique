package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.ul.cassebrique.dataFactories.TextureFactory;

/**
 * Created by noahd on 26/01/2018.
 */

public class Wall {
    private int nbL; //Lignes
    private int nbC; //Colonnes
    private Brick[][] wall; //Mur du jeu
    private static final Brick[][] wallInit = {{Brick.Bleue, Brick.Verte, Brick.Bleue, Brick.VerteAbimee, Brick.Bleue, Brick.Bleue, Brick.VerteAbimee, Brick.Bleue, Brick.Verte, Brick.Bleue},
            {Brick.Bleue, Brick.Bleue, Brick.Verte, Brick.Bleue, Brick.VerteAbimee, Brick.VerteAbimee, Brick.Bleue, Brick.Verte, Brick.Bleue, Brick.Bleue},
            {Brick.Bleue, Brick.Bleue, Brick.Bleue, Brick.Verte, Brick.Bleue, Brick.Bleue, Brick.Verte, Brick.Bleue, Brick.Bleue, Brick.Bleue},
            {Brick.Bleue, Brick.Bleue, Brick.Bleue, Brick.Bleue, Brick.Verte, Brick.Verte, Brick.Bleue, Brick.Bleue, Brick.Bleue, Brick.Bleue},
            {Brick.Vide, Brick.Bleue, Brick.Vide, Brick.Vide, Brick.Bleue, Brick.Bleue, Brick.Vide, Brick.Vide, Brick.Bleue, Brick.Vide}};

    public Wall(){
        this.nbL = wallInit.length;
        this.nbC = wallInit[0].length;
        this.wall = new Brick[nbL][nbC];
        setBricks(false);
    }

    public void setBricks(boolean aleat){
        if (aleat){

        } else {
            for (int i = 0; i < nbL; i++){
                for (int j = 0; j < nbC; j++)
                    wall[i][j] = wallInit[i][j];
            }
        }
    }

    public void draw(SpriteBatch sb){
        int x = 100;
        int y = 200;
        int addx = TextureFactory.getTexBlueBrick().getWidth();
        int addy = TextureFactory.getTexBlueBrick().getHeight();
        Texture text;
        sb.begin();
        for (int i = nbL-1; i >= 0; i--){
            for (int j = nbC-1; j >= 0; j--) {
                text = wall[i][j].getTex();
                if (text != null)
                    sb.draw(text, x, y);
                x += addx;
            }
            x = 100;
            y += addy;
        }
        sb.end();
    }
}
