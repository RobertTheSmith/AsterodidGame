package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameMenu extends BasicGameState {
	private static final int OPTION_START = 0;
	private static final int OPTION_CREDITS = 1;
	private static final int OPTION_EXIT = 2;
	private static final int TOTALMENUOPTIONS = 3;
	private Image titleImage;
	private Sound music;
	private int selectMenuOption;
	private long loadTimeOut;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		titleImage = new Image("art/TitlePage.png");
		music = new Sound("art/MenuMusic.ogg");
		selectMenuOption = 0;
		loadTimeOut = MainGame.getCurrentTime() + (1 * MainGame.MILLISECPERSEC);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(titleImage, 0, 0);
		g.drawString("Menu: " + selectMenuOption, 10, 40);

		if (selectMenuOption == OPTION_START)
			g.setColor(Color.cyan);
		else
			g.setColor(Color.white);
		g.drawString("Start", MainGame.SWIDTH / 2 - 10, MainGame.SHEIGHT / 2 + 100);

		if (selectMenuOption == OPTION_CREDITS)
			g.setColor(Color.cyan);
		else
			g.setColor(Color.white);
		g.drawString("Credits", MainGame.SWIDTH / 2 - 10, MainGame.SHEIGHT / 2 + 130);

		if (selectMenuOption == OPTION_EXIT)
			g.setColor(Color.cyan);
		else
			g.setColor(Color.white);
		g.drawString("Exit", MainGame.SWIDTH / 2 - 10, MainGame.SHEIGHT / 2 + 160);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (!music.playing()) {
			music.play();
		}
		if (gc.getInput().isKeyPressed(Input.KEY_W) || gc.getInput().isKeyPressed(Input.KEY_UP)) {
			menuMove(1);
		}
		if (gc.getInput().isKeyPressed(Input.KEY_S) || gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
			menuMove(-1);
		}

		if ((gc.getInput().isKeyPressed(Input.KEY_SPACE) 
				|| gc.getInput().isKeyPressed(Input.KEY_ENTER))
				&& loadTimeOut < MainGame.getCurrentTime()) {
			switch (selectMenuOption) {
			case OPTION_START:
				music.stop();
				sbg.enterState(MainGame.ID_GAMEPLAY);
				break;

			case OPTION_CREDITS:
				music.stop();
				sbg.enterState(MainGame.ID_GAMECREDITS);
				break;
				
			case OPTION_EXIT:
				music.stop();
				gc.exit();
				break;

			default:
				break;
			}
		}

	}

	private void menuMove(int change) {
		selectMenuOption -= change;
		if (selectMenuOption < 0) {
			selectMenuOption += TOTALMENUOPTIONS;
		}
		selectMenuOption %= TOTALMENUOPTIONS;
	}

	@Override
	public int getID() {
		return MainGame.ID_GAMEMENU;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		selectMenuOption = 0;
		loadTimeOut = MainGame.getCurrentTime() + (1 * MainGame.MILLISECPERSEC);
		super.enter(gc, sbg);
	}
}
