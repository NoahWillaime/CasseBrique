package fr.ul.cassebrique;

import com.badlogic.gdx.Game;

import fr.ul.cassebrique.views.GameScreen;

public class CasseBrique extends Game {
	private GameScreen gs;
	
	@Override
	public void create () {
		gs = new GameScreen();
		setScreen(gs);
	}

	@Override
	public void dispose () {
		gs.dispose();
	}
}
