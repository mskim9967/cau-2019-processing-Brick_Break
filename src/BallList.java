import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import processing.core.PApplet;
import processing.core.PVector;

public class BallList {

	ArrayList<Ball> ballList = new ArrayList<Ball>();
	PVector startVector = new PVector(Main.WIDTH / 2, Main.HEIGHT);
	boolean launchFlag = false;

	public void addBall(PApplet p, float radius) {
		ballList.add(new Ball(p, ballList.size()));
		ballList.get(ballList.size() - 1).setStartVector(startVector);
		ballList.get(ballList.size() - 1).setRadius(radius);
	}

	public void drawBallSet() {
		for (int i = 0; i < ballList.size(); i++) {
			ballList.get(i).update();
		}
	}

	public boolean isLaunched() {
		return launchFlag;
	}

	public void launch(PVector vector) {
		for (int i = 0; i < ballList.size(); i++) {
			ballList.get(i).setAddVector(new PVector(vector.x,vector.y));
			ballList.get(i).setLaunchTime(System.currentTimeMillis());
			ballList.get(i).color=0;
			ballList.get(i).launchFlag=true;
		}
		launchFlag = true;
	}
	
	public boolean isReady() {
		for (int i = 0; i < ballList.size(); i++) {
			if(ballList.get(i).moveFlag==true)
				return false;
		}
		launchFlag=false;
		return true;
	}

}
