package fr.ul.cassebrique.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import fr.ul.cassebrique.dataFactories.TextureFactory;

/**
 * Created by noahd on 21/03/2018.
 */

public class Ball3D extends Ball {
    private ModelInstance mod3D;
    private Environment env;
    private ModelBatch mb;
    private long timeSav;

    public Ball3D(GameWorld gaw, Vector2 pos) {
        super(gaw, pos);
        timeSav = System.currentTimeMillis();
        ModelBuilder mb = new ModelBuilder();
        Model modele = mb.createSphere(2, 2, 2, 40, 40,
                          new Material(TextureAttribute.createDiffuse(TextureFactory.getTexBoule())),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        mod3D = new ModelInstance(modele);
        this.mb = new ModelBatch();
        env = new Environment();
        ColorAttribute ambiance = new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f);
        ColorAttribute speculaire = new ColorAttribute(ColorAttribute.Specular, 0.0f, 0.7f, 0.0f, 1f);
        env.set(ambiance, speculaire);
        env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, 1f, -0.2f));
    }

    @Override
    public void draw(SpriteBatch sb){
        Quaternion rotation = new Quaternion();
        Quaternion rotLoc = new Quaternion();
        Vector2 vit = body.getLinearVelocity();
        float ray = rayon * GameWorld.getPixelsToMeters();
        long tEcoule = System.currentTimeMillis() - timeSav;
        timeSav = System.currentTimeMillis();
        rotLoc.set(new Vector3(vit.x, vit.y, 0).nor(), vit.len() * tEcoule / ray);
        rotation.mulLeft(rotLoc);
        Vector2 pos = body.getPosition();
        pos.x *= GameWorld.getMetersToPixels();
        pos.y *= GameWorld.getMetersToPixels();
        Vector3 pos3D = new Vector3(pos.x, pos.y, 0);
        mod3D.transform.set(pos3D, rotation);
     //   mod3D.transform.scale(rayon, rayon, rayon);

        mb.begin(gw.getCamera());
        mb.render(mod3D);
        mb.end();
    }
}
