package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends BasicGameState {
	private Image gameOverImage;
	private Sound music;
	private boolean timeToGo;
	private long loadTimeOut;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gameOverImage = new Image("art/GameOver.png");
		music = new Sound("art/GameOver.ogg");
		timeToGo = false;
		loadTimeOut = MainGame.getCurrentTime() + (1 * MainGame.MILLISECPERSEC);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(gameOverImage, 0, 0);

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
		return MainGame.ID_GAMEOVER;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		timeToGo = false;
		loadTimeOut = MainGame.getCurrentTime() + (1 * MainGame.MILLISECPERSEC);
		super.enter(gc, sbg);
	}

}
