package game;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class Movable {

	private float pX;
	private float pY;

	private Vector2f velocity;

	private float heading;

	private Image display;

	public Movable() {
		this(0, 0);
	}

	public Movable(float X, float Y) {
		this(X, Y, 0, 0);
	}

	public Movable(float X, float Y, float sX, float sY) {
		pX = X;
		pY = Y;
		velocity = new Vector2f(sX, sY);
		heading = (float) (Math.PI / 2);
	}

	public float getvX() {
		return velocity.getX();
	}

	public void setvX(float vX) {
		velocity.setX(vX);
	}

	public float getvY() {
		return velocity.getY();
	}

	public void setvY(float vY) {
		velocity.setY(vY);
	}

	public float getpX() {
		return pX;
	}

	public void setpX(float pX) {
		this.pX = pX;
	}

	public float getpY() {
		return pY;
	}

	public void setpY(float pY) {
		this.pY = pY;
	}

	public float getCenterX() {
		return pX + (getDisplay() != null ? getDisplay().getWidth() / 2 : 0);
	}

	public float getCenterY() {
		return pY + (getDisplay() != null ? getDisplay().getHeight() / 2 : 0);
	}

	public Rectangle getCollisionBox() {
		if (getDisplay() != null)
			return new Rectangle(pX, pY, getDisplay().getWidth(), getDisplay().getHeight());
		else
			return new Rectangle(pX, pY, 10, 10);

	}

	public Image getDisplay() {
		return display;
	}

	public void setDisplay(Image display) {
		this.display = display;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public float getHeading() {
		return heading;
	}

	public void setHeading(float heading) {
		this.heading = (float) (heading % (2 * Math.PI));
	}

}
