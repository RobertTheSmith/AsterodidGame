package game;

public class Roid extends Movable {

	private int size;

	public Roid(float x, float y, int s) {
		super(x, y);
		size = s;
	}

	public Roid(float x, float y, float vX, float vY, int s) {
		super(x, y, vX, vY);
		size = s;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean shrinkAndIsDead() {
		size--;
		return size < 0;
	}

}
