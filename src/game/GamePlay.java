package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GamePlay extends BasicGameState {

	private static final int SHEIGHT = 600;
	private static final int SWIDTH = 800;
	private static final int STARCOUNT = 100;
	private static final float MAXROIDSPEED = 0.5f;

	private int playerLives;
	private Movable player;
	private List<Roid> roids;
	private List<Movable> shots;
	private float[] stars;
	private Sound roidPop;
	private Sound playerPop;
	private Sound music;
	private Image lifeImage;

	private long respawnInvulnEndTime;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player = new Movable(SWIDTH / 2, SHEIGHT / 2);
		player.setDisplay(new Image("art/ship.png"));
		lifeImage = new Image("art/ship.png");
		roidPop = new Sound("art/atari_boom.wav");
		playerPop = new Sound("art/atari_boom5.wav");
		music = new Sound("art/GameMusic.ogg");
		roids = new ArrayList<Roid>();
		shots = new LinkedList<Movable>();
		stars = new float[2 * STARCOUNT];
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.white);
		for (int i = 0; i < STARCOUNT; i++) {
			float x = stars[i * 2];
			float y = stars[(i * 2) + 1];
			g.fillRect(x, y, 2, 2);
		}

		g.setColor(Color.green);
		for (Movable shot : shots) {
			float x = shot.getpX();
			float y = shot.getpY();
			g.fillRect(x, y, 5, 5);
		}

		g.setColor(Color.white);
		Image ship = player.getDisplay();
		ship.setRotation((float) (player.getHeading() * (180 / Math.PI)));
		if (respawnInvulnEndTime > MainGame.getCurrentTime()) {
			if ((respawnInvulnEndTime - MainGame.getCurrentTime()) / 100 % 2 == 0) {
				ship.drawFlash(player.getpX(), player.getpY());
			} else {
				ship.draw(player.getpX(), player.getpY());
			}
		} else {
			g.drawImage(ship, player.getpX(), player.getpY());
		}
		g.drawString("Heading: " + player.getHeading() * (180 / Math.PI), 10, 20);
		g.drawString("Velocity X: " + player.getvX(), 10, 35);
		g.drawString("Velocity Y: " + player.getvY(), 10, 50);
		for (Movable roid : roids) {
			roid.getDisplay().setRotation((float) (roid.getHeading() * (180 / Math.PI)));
			g.drawImage(roid.getDisplay(), roid.getpX(), roid.getpY());
		}

		for (int n = 0; n < playerLives; n++) {
			g.drawImage(lifeImage, (MainGame.SWIDTH - 50) - (n * 50), 10);
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (!music.playing()) {
			music.play();
		}
		if (gc.getInput().isKeyDown(Input.KEY_A)) {
			player.setHeading(player.getHeading() - 0.05F);
		}
		if (gc.getInput().isKeyDown(Input.KEY_D)) {
			player.setHeading(player.getHeading() + 0.05F);
		}

		if (gc.getInput().isKeyDown(Input.KEY_W)) {

			float newVecX = (float) Math.sin(player.getHeading());
			float newVecY = (float) Math.cos(player.getHeading());

			Vector2f newVec = new Vector2f();

			Vector2f.add(player.getVelocity(), new Vector2f(newVecX / 100, -newVecY / 100), newVec);
			newVec.setX(Math.min(newVec.getX(), 0.5F));
			newVec.setY(Math.min(newVec.getY(), 0.5F));

			newVec.setX(Math.max(newVec.getX(), -0.5F));
			newVec.setY(Math.max(newVec.getY(), -0.5F));
			player.setVelocity(newVec);

		}
		if (gc.getInput().isKeyDown(Input.KEY_S)) {
			float newVecX = (float) Math.sin(player.getHeading());
			float newVecY = (float) Math.cos(player.getHeading());

			Vector2f newVec = new Vector2f();

			Vector2f.add(player.getVelocity(), new Vector2f(-newVecX / 100, newVecY / 100), newVec);
			newVec.setX(Math.min(newVec.getX(), 0.5F));
			newVec.setY(Math.min(newVec.getY(), 0.5F));

			newVec.setX(Math.max(newVec.getX(), -0.5F));
			newVec.setY(Math.max(newVec.getY(), -0.5F));
			player.setVelocity(newVec);
		}

		if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			shots.add(new Movable(player.getCenterX(), player.getCenterY(), (float) Math.sin(player
					.getHeading()), (float) -Math.cos(player.getHeading())));
		}

		player.setpX(player.getpX() + player.getvX() * delta);
		player.setpY(player.getpY() + player.getvY() * delta);

		if (player.getCenterX() < 0) {
			player.setpX(player.getpX() + SWIDTH);
		}
		if (player.getCenterX() > SWIDTH) {
			player.setpX(player.getpX() - SWIDTH);
		}
		if (player.getCenterY() < 0) {
			player.setpY(player.getpY() + SHEIGHT);
		}
		if (player.getCenterY() > SHEIGHT) {
			player.setpY(player.getpY() - SHEIGHT);
		}

		Rectangle playerCollisionBox = player.getCollisionBox();

		for (Movable roid : roids) {
			roid.setpX(roid.getpX() + roid.getvX() * delta);
			roid.setpY(roid.getpY() + roid.getvY() * delta);
			if (roid.getCenterX() < 0) {
				roid.setpX(roid.getpX() + SWIDTH);
			}
			if (roid.getCenterX() > SWIDTH) {
				roid.setpX(roid.getpX() - SWIDTH);
			}
			if (roid.getCenterY() < 0) {
				roid.setpY(roid.getpY() + SHEIGHT);
			}
			if (roid.getCenterY() > SHEIGHT) {
				roid.setpY(roid.getpY() - SHEIGHT);
			}

			if (playerCollisionBox.intersects(roid.getCollisionBox())
					&& respawnInvulnEndTime < MainGame.getCurrentTime()) {
				// we have run into the player. Reset the player?
				respawnInvulnEndTime = MainGame.getCurrentTime()
						+ (long) (1.5 * MainGame.MILLISECPERSEC);
				player.setHeading((float) (Math.PI / 2));
				player.setVelocity(new Vector2f(0, 0));
				player.setpX(SWIDTH / 2);
				player.setpY(SHEIGHT / 2);
				playerPop.stop();
				playerPop.play();
				playerLives--;
				if (playerLives < 0) {
					roidPop.stop();
					playerPop.stop();
					music.stop();
					sbg.enterState(MainGame.ID_GAMEOVER);
				}
			}
		}
		List<Movable> shotsToBeRemoved = new ArrayList<Movable>();
		List<Roid> roidsToBeRemoved = new ArrayList<Roid>();
		List<Roid> roidsToBeAdded = new ArrayList<Roid>();
		for (Movable shot : shots) {

			shot.setpX(shot.getpX() + shot.getvX() * delta);
			shot.setpY(shot.getpY() + shot.getvY() * delta);

			if (shot.getpX() < 0 || shot.getpX() > SWIDTH || shot.getpY() < 0
					|| shot.getpY() > SHEIGHT) {
				shotsToBeRemoved.add(shot);
			}

			for (Roid roid : roids) {
				if (shot.getCollisionBox().intersects(roid.getCollisionBox())) {
					if (!roid.shrinkAndIsDead()) {
						for (int i = 0; i < 3; i++) {
							int size = roid.getSize();
							float x = roid.getCenterX();
							float y = roid.getCenterY();
							float localMaxSpeed = MAXROIDSPEED / (2 + size);
							float vX = (float) (Math.random() * localMaxSpeed) - (localMaxSpeed / 2);
							float vY = (float) (Math.random() * localMaxSpeed) - (localMaxSpeed / 2);
							float head = (float) (Math.random() * Math.PI * 2);
							Roid newRoid = new Roid(x, y, vX, vY, size);
							newRoid.setHeading(head);
							newRoid.setDisplay(new Image("art/roid" + size + ".png"));
							roidsToBeAdded.add(newRoid);
						}
					}
					roidsToBeRemoved.add(roid);
					shotsToBeRemoved.add(shot);
					roidPop.stop();
					roidPop.play();
					break;
				}
			}
		}
		shots.removeAll(shotsToBeRemoved);
		roids.removeAll(roidsToBeRemoved);
		roids.addAll(roidsToBeAdded);
		if (roids.isEmpty()) {
			roidPop.stop();
			playerPop.stop();
			music.stop();
			sbg.enterState(MainGame.ID_GAMEWIN);
		}
	}

	@Override
	public int getID() {
		return MainGame.ID_GAMEPLAY;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player.setpX(SWIDTH / 2);
		player.setpY(SHEIGHT / 2);
		roids.clear();
		for (int i = 0; i < 4; i++) {
			int size = (int) ((Math.random() * 100) % 3);
			float x = (float) ((Math.random() * SWIDTH * 3 / 4) - SWIDTH / 2);
			if (x < 0)
				x += SWIDTH;
			float y = (float) ((Math.random() * SHEIGHT * 3 / 4) - SHEIGHT / 2);
			if (y < 0)
				y += SHEIGHT;
			float localMaxSpeed = MAXROIDSPEED / (1 + size);
			float vX = (float) (Math.random() * localMaxSpeed) - (localMaxSpeed / 2);
			float vY = (float) (Math.random() * localMaxSpeed) - (localMaxSpeed / 2);
			float head = (float) (Math.random() * Math.PI * 2);
			Roid roid = new Roid(x, y, vX, vY, size);
			roid.setHeading(head);
			roid.setDisplay(new Image("art/roid" + size + ".png"));
			roids.add(roid);
		}

		stars = new float[2 * STARCOUNT];
		for (int i = 0; i < STARCOUNT; i++) {
			stars[i * 2] = (float) (Math.random() * SWIDTH);
			stars[(i * 2) + 1] = (float) (Math.random() * SHEIGHT);
		}

		shots.clear();
		respawnInvulnEndTime = 0;
		playerLives = 3;
		super.enter(gc, sbg);
	}
}
