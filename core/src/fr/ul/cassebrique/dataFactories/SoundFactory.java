package fr.ul.cassebrique.dataFactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by noahd on 09/03/2018.
 */

public class SoundFactory {
    private static final Sound soundColision = Gdx.audio.newSound(Gdx.files.internal("sounds/collision.wav"));
    private static final Sound soundImpact = Gdx.audio.newSound(Gdx.files.internal("sounds/impact.mp3"));
    private static final Sound soundPerte = Gdx.audio.newSound(Gdx.files.internal("sounds/perte.mp3"));
    private static final Sound soundPerteBalle = Gdx.audio.newSound(Gdx.files.internal("sounds/perteBalle.wav"));
    private static final Sound soundVictoire = Gdx.audio.newSound(Gdx.files.internal("sounds/victoire.mp3"));

    public static void listenColision(float volume){
        soundColision.play(volume);
    }

    public static void listenImpact(float volume){
        soundImpact.play(volume);
    }

    public static void listenPerte(float volume){
        soundPerte.play(volume);
    }

    public static void listenPerteBalle(float volume){
        soundPerteBalle.play(volume);
    }

    public static void listenVictoire(float volume){
        soundVictoire.play(volume);
    }
}
