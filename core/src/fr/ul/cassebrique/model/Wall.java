package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

import fr.ul.cassebrique.dataFactories.TextureFactory;

/**
 * Created by noahd on 26/01/2018.
 */

public class Wall {
    private int nbL; //Lignes
    private int nbC; //Colonnes
    private Brick[][] wall; //Mur du jeu
    private ArrayList<Brick> tochange;
    private GameWorld gw;
    private Brick[][] wallInit;
    private int comptVide;

    public Wall(GameWorld gaw){
        this.gw = gaw;
        comptVide = 0;
      /*  Brick[][] init = {{new BlueBrick(1, new Vector2(50,505), gw), new GreenBrick(2, new Vector2(150, 505), gw), new BlueBrick(1, new Vector2(250, 505), gw), new GreenBrick(1, new Vector2(350, 505), gw), new BlueBrick(1, new Vector2(450, 505), gw), new BlueBrick(1, new Vector2(550, 505), gw), new GreenBrick(1, new Vector2(650, 505), gw), new BlueBrick(1, new Vector2(750, 505), gw), new GreenBrick(2, new Vector2(850, 505), gw), new BlueBrick(1, new Vector2(950, 505), gw)},
                {new BlueBrick(1, new Vector2(50, 460), gw), new BlueBrick(1, new Vector2(150, 460), gw), new GreenBrick(2, new Vector2(250, 460), gw), new BlueBrick(1, new Vector2(350, 460), gw), new GreenBrick(1, new Vector2(450, 460), gw), new GreenBrick(1, new Vector2(550, 460), gw), new BlueBrick(1, new Vector2(650, 460), gw), new GreenBrick(2, new Vector2(750, 460), gw), new BlueBrick(1, new Vector2(850, 460), gw), new BlueBrick(1, new Vector2(950, 460), gw)},
                {new BlueBrick(1, new Vector2(50, 415), gw), new BlueBrick(1, new Vector2(150, 415), gw), new BlueBrick(1, new Vector2(250, 415), gw), new GreenBrick(2, new Vector2(350, 415), gw), new BlueBrick(1, new Vector2(450, 415), gw), new BlueBrick(1, new Vector2(550, 415), gw), new GreenBrick(2, new Vector2(650, 415), gw), new BlueBrick(1, new Vector2(750, 415), gw), new BlueBrick(1, new Vector2(850, 415), gw), new BlueBrick(1, new Vector2(950, 415), gw)},
                {new BlueBrick(1, new Vector2(50, 370), gw), new BlueBrick(1, new Vector2(150, 370), gw), new BlueBrick(1, new Vector2(250, 370), gw), new BlueBrick(1, new Vector2(350, 370), gw), new GreenBrick(2, new Vector2(450, 370), gw), new GreenBrick(2, new Vector2(550, 370), gw), new BlueBrick(1, new Vector2(650, 370), gw), new BlueBrick(1, new Vector2(750, 370), gw), new BlueBrick(1, new Vector2(850, 370), gw), new BlueBrick(1, new Vector2(950, 370), gw)},
                {null, new BlueBrick(1, new Vector2(150, 325), gw), null, null, new BlueBrick(1, new Vector2(450, 325), gw), new BlueBrick(1, new Vector2(550, 325), gw), null, null, new BlueBrick(1, new Vector2(850, 325), gw), null}};*/
        Brick[][] init = {{null,null, null, null, null, null, null, null, null, null},
                {null,null, null, null, null, null, null, null, null, null},
                {null,null, null, null, null, null, null, null, null, null},
                {null,null, null, null, null, null, null, null, null, null},
                {null,null, null, null, null, null, null, new BlueBrick(1, new Vector2(750, 325), gw), new BlueBrick(1, new Vector2(850, 325), gw), null},
    };
        wallInit = init;
        this.nbL = wallInit.length;
        this.nbC = wallInit[0].length;
        this.wall = new Brick[nbL][nbC];
        this.tochange = new ArrayList<Brick>();
        setBricks(false);
    }

    public void setBricks(boolean aleat){
        if (aleat){
            Random rand = new Random();
            float nbAleat;
            int x = 50;
            int y = 505;
            for (int i = 0; i < nbL; i++){
                for (int j = 0; j < nbC; j++) {
                    nbAleat = rand.nextFloat();
                    if (nbAleat < 0.1f) {
                        wall[i][j] = null;
                        comptVide++;
                    } else if (nbAleat < 0.5f){
                        wall[i][j] = new BlueBrick(1, new Vector2(x, y), gw);
                    } else if (nbAleat < 0.9f){
                        wall[i][j] = new GreenBrick(2, new Vector2(x, y), gw);
                    } else if (nbAleat <= 1f) {
                        wall[i][j] = new GreenBrick(1, new Vector2(x, y), gw);
                    }
                    x += TextureFactory.getTexBlueBrick().getWidth();
                }
                x = 50;
                y -= TextureFactory.getTexBlueBrick().getHeight();
            }
        } else {
            for (int i = 0; i < nbL; i++){
                for (int j = 0; j < nbC; j++) {
                    if (wallInit[i][j] == null) {
                        wall[i][j] = wallInit[i][j];
                        comptVide++;
                    } else {
                        Vector2 pos = wallInit[i][j].getPosition();
                        if (wallInit[i][j] instanceof BlueBrick)
                            wall[i][j] = new BlueBrick(1, pos, gw);
                        else
                            wall[i][j] = new GreenBrick(wallInit[i][j].getNbCoups(), pos, gw);
                    }
                }
            }
        }
    }

    public void addChange(Brick change_b){
        this.tochange.add(change_b);
    }

    public void majWall(){
        for (Brick b : tochange){
            b.dispose();
            for (int i = 0; i < wall.length; i++){
                for (int j = 0; j < wall[i].length; j++){
                    if (wall[i][j] != null){
                        if (b.equals(wall[i][j])){
                            if (wall[i][j].getNbCoups() == 1) {
                                wall[i][j] = null;
                                comptVide++;
                            } else {
                                Vector2 pos = new Vector2(wall[i][j].getPosition());
                                wall[i][j] = new GreenBrick(1, pos, gw);
                            }
                        }
                    }
                }
            }
        }
        tochange.clear();
    }

    public void reset(){
        comptVide = 0;
        for (int i = 0; i < wall.length; i++){
            for (int j = 0; j < wall[i].length; j++) {
                if (wall[i][j] != null)
                    wall[i][j].dispose();
            }
        }
        setBricks(true);
    }

    public boolean isDetroy(){
        return comptVide == nbC * nbL;
    }

    public void draw(SpriteBatch sb){
        for (int i = nbL-1; i >= 0; i--){
            for (int j = nbC-1; j >= 0; j--){
                if (wall[i][j] != null) {
                    wall[i][j].draw(sb);
                }
            }
        }
       /* for (int i = 0; i <= nbL-1; i++){
            for (int j = nbC-1; j >= 0; j--) {
                text = wall[i][j].getTex();
                if (text != null)
                    sb.draw(text, x, y);
                x += addx;
            }
        x = 50;
        y -= addy;
    }*/
    }
}
