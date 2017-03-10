    package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainGame extends StateBasedGame {
	
	// sound by dklon 

	protected static final int SHEIGHT = 600;
	protected static final int SWIDTH = 800;
	protected static final long NANOSECPERMILLISEC = 1000000;
	protected static final long MILLISECPERSEC = 1000;
	protected static final int ID_GAMEMENU = 0;
	protected static final int ID_GAMEPLAY = 1;
	protected static final int ID_GAMEOVER = 2;
	protected static final int ID_GAMEWIN = 3;
	protected static final int ID_GAMECREDITS = 4;

	public MainGame(String title) {
		super(title);
	}

	public static void main(String[] args) {

		try {
			AppGameContainer agc = new AppGameContainer(new MainGame("Howdy"));
			agc.setDisplayMode(SWIDTH, SHEIGHT, false);
			agc.setVSync(true);   
			agc.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {

		addState(new GameMenu());
		addState(new GamePlay());
		addState(new GameOver());
		addState(new GameWin());
		addState(new GameCredits());
		enterState(ID_GAMEMENU);
	}
	

	public static long getCurrentTime(){
		return System.nanoTime()/NANOSECPERMILLISEC;
	}
	
}
