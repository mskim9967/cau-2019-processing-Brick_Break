
import processing.core.PApplet;
import processing.core.PVector;

public class Ball {

	int myNumber;
	float radius;
	int color = 0;
	PVector addVector, startVector, nowVector;
	boolean moveFlag, launchFlag;
	PApplet p;
	long launchTime;

	Ball(PApplet p, int myNumber) {
		this.myNumber = myNumber;
		this.p = p;
		moveFlag = false;
		addVector = new PVector(0, 0);
		radius = 15;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setStartVector(PVector vector) {
		startVector = vector;
		nowVector = startVector;
	}

	public void setAddVector(PVector vector) {
		addVector = vector;
	}

	public void setMoveFlag(boolean flag) {
		moveFlag = flag;
	}

	public void setLaunchTime(long time) {
		launchTime = time;
	}

	public void update() {
		p.noStroke();
		p.fill(color);
		p.ellipse(nowVector.x, nowVector.y, radius, radius);

		if (launchFlag == true) {

			if (moveFlag == false) {
				if (System.currentTimeMillis() - launchTime >= myNumber * 100) {
					moveFlag = true;
					setStartVector(new PVector(Main.WIDTH / 2, Main.HEIGHT));
				}
			} else {
				nowVector.add(addVector);

				if (nowVector.y > Main.HEIGHT + 20) {
					moveFlag = false;
					launchFlag = false;
				} else {

					if (nowVector.x >= Main.WIDTH&&addVector.x>0 
							|| nowVector.x <= 0&&addVector.x<0) {
						PVector tempVec = addVector;
						tempVec.x = 0 - tempVec.x;
						setAddVector(tempVec);

					} else if (nowVector.y <= 0&&addVector.y<0) {
						PVector tempVec = addVector;
						tempVec.y = 0 - tempVec.y;
						setAddVector(tempVec);
					}

					for (int i = 0; i < Main.blockList.blockList.size(); i++) {
						for (int j = 0; j < Main.blockList.blockList.get(i).size(); j++) {
							if (Main.blockList.blockList.get(i).get(j).isCollision(nowVector)) {
								{
									color = Main.blockList.blockList.get(i).get(j).color;

									if (Main.blockList.blockList.get(i).get(j).isCollisionUpDown(nowVector,
											addVector)) {
										PVector tempVec = addVector;
										tempVec.y = 0 - tempVec.y;
										setAddVector(tempVec);
									} else if (Main.blockList.blockList.get(i).get(j).isCollisionSide(nowVector,
											addVector)) {
										PVector tempVec = addVector;
										tempVec.x = 0 - tempVec.x;
										setAddVector(tempVec);
									} else {

										PVector tempVec = addVector;
										tempVec.x = 0 - tempVec.x;
										tempVec.y = 0 - tempVec.y;
										setAddVector(tempVec);
									}

								}
							}
						}
					}
				}
			}

		}
	}
}
