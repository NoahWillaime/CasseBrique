package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

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

    public Wall(GameWorld gaw){
        this.gw = gaw;
        Brick[][] init = {{new BlueBrick(1, new Vector2(50,505), gw), new GreenBrick(2, new Vector2(150, 505), gw), new BlueBrick(1, new Vector2(250, 505), gw), new GreenBrick(1, new Vector2(350, 505), gw), new BlueBrick(1, new Vector2(450, 505), gw), new BlueBrick(1, new Vector2(550, 505), gw), new GreenBrick(1, new Vector2(650, 505), gw), new BlueBrick(1, new Vector2(750, 505), gw), new GreenBrick(2, new Vector2(850, 505), gw), new BlueBrick(1, new Vector2(950, 505), gw)},
                {new BlueBrick(1, new Vector2(50, 460), gw), new BlueBrick(1, new Vector2(150, 460), gw), new GreenBrick(2, new Vector2(250, 460), gw), new BlueBrick(1, new Vector2(350, 460), gw), new GreenBrick(1, new Vector2(450, 460), gw), new GreenBrick(1, new Vector2(550, 460), gw), new BlueBrick(1, new Vector2(650, 460), gw), new GreenBrick(2, new Vector2(750, 460), gw), new BlueBrick(1, new Vector2(850, 460), gw), new BlueBrick(1, new Vector2(950, 460), gw)},
                {new BlueBrick(1, new Vector2(50, 415), gw), new BlueBrick(1, new Vector2(150, 415), gw), new BlueBrick(1, new Vector2(250, 415), gw), new GreenBrick(2, new Vector2(350, 415), gw), new BlueBrick(1, new Vector2(450, 415), gw), new BlueBrick(1, new Vector2(550, 415), gw), new GreenBrick(2, new Vector2(650, 415), gw), new BlueBrick(1, new Vector2(750, 415), gw), new BlueBrick(1, new Vector2(850, 415), gw), new BlueBrick(1, new Vector2(950, 415), gw)},
                {new BlueBrick(1, new Vector2(50, 370), gw), new BlueBrick(1, new Vector2(150, 370), gw), new BlueBrick(1, new Vector2(250, 370), gw), new BlueBrick(1, new Vector2(350, 370), gw), new GreenBrick(2, new Vector2(450, 370), gw), new GreenBrick(2, new Vector2(550, 370), gw), new BlueBrick(1, new Vector2(650, 370), gw), new BlueBrick(1, new Vector2(750, 370), gw), new BlueBrick(1, new Vector2(850, 370), gw), new BlueBrick(1, new Vector2(950, 370), gw)},
                {null, new BlueBrick(1, new Vector2(150, 325), gw), null, null, new BlueBrick(1, new Vector2(450, 325), gw), new BlueBrick(1, new Vector2(550, 325), gw), null, null, new BlueBrick(1, new Vector2(850, 325), gw), null}};
        wallInit = init;
        this.nbL = wallInit.length;
        this.nbC = wallInit[0].length;
        this.wall = new Brick[nbL][nbC];
        this.tochange = new ArrayList<Brick>();
        setBricks(false);
    }

    public void setBricks(boolean aleat){
        if (aleat){

        } else {
            for (int i = 0; i < nbL; i++){
                for (int j = 0; j < nbC; j++) {
                    wall[i][j] = wallInit[i][j];
                }
            }
        }
    }

    public void addChange(Brick change_b){
        this.tochange.add(change_b);
    }

    public void majWall(){
        System.out.println(tochange.size());
        for (Brick b : tochange){
            gw.getWorld().destroyBody(b.getBody());
        }
        tochange.clear();
    }

    public void draw(SpriteBatch sb){
        int x = TextureFactory.getTexBorder().getWidth();
        int y = TextureFactory.getTexBack().getHeight();
        y -= (TextureFactory.getTexBorder().getHeight()*3) + TextureFactory.getTexBlueBrick().getHeight();
        int addx = TextureFactory.getTexBlueBrick().getWidth();
        int addy = TextureFactory.getTexBlueBrick().getHeight();
        for (int i = nbL-1; i >= 0; i--){
            for (int j = nbC-1; j >= 0; j--){
                if (wall[i][j] != null)
                    wall[i][j].draw(sb);
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
