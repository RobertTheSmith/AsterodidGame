package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameCredits extends BasicGameState {
	private Sound music;
	private boolean timeToGo;
	private long loadTimeOut;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		music = new Sound("art/GameOver.ogg");
		timeToGo = false;
		loadTimeOut = MainGame.getCurrentTime() + (1 * MainGame.MILLISECPERSEC);
	}


	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawString("Programming", 10, 100);
		g.drawString("|2obert", 30, 115);
		
		g.drawString("Artwork", 10, 130);
		g.drawString("~tennsoccerdr", 30, 145);

		g.drawString("Music", 10, 160);
		g.drawString("matthew.pablo", 30, 175);
		g.drawString("Mumu", 30, 190);
		g.drawString("HorrorPen", 30, 205);

		g.drawString("Sound Effects", 10, 220);
		g.drawString("dklon", 30, 235);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (!music.playing()) {
			music.play();
		}
		if (timeToGo) {
			music.stop();
			sbg.enterState(MainGame.ID_GAMEMENU);
		}

	}

	@Override
	public void keyReleased(int key, char c) {
		if (loadTimeOut < MainGame.getCurrentTime())
			timeToGo = true;
	}

	@Override
	public int getID() {
		return MainGame.ID_GAMECREDITS;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		timeToGo = false;
		loadTimeOut = MainGame.getCurrentTime() + (1 * MainGame.MILLISECPERSEC);
		super.enter(gc, sbg);
	}
}
