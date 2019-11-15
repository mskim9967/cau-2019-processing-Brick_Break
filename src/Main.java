
import ddf.minim.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Main extends PApplet {

	float speed = 10;
	static final float WIDTH = 700, HEIGHT = 900;
	static BallList ballList = new BallList();
	PVector inputVector = new PVector(0, 0 - speed);
	static BlockList blockList = new BlockList();
	static boolean finishFlag;
	static int score = 0;
	static int lastColor;
	boolean onlyOnce = true, micFlag = false;
	int tmp = 8;
	Minim minim = new Minim(this);
	AudioInput input = minim.getLineIn();
	PImage mic;

	public static void main(String[] args) {
		PApplet.main("Main");
	}

	public void settings() {
		size(700,900);
		ballList.addBall(this, HEIGHT/40);
	}

	public void set() {
		colorMode(HSB);
		mic=loadImage("mic.png");
	}

	public void draw() {

		background(255);

		if (finishFlag == true) {
			fill(0, tmp);
			tmp = tmp < 100 ? tmp + 2 : tmp;
		} else
			fill(0, 8);
		
		textAlign(CENTER, CENTER);
		if (score < 10)
			textSize(WIDTH);
		else if (score < 100)
			textSize(WIDTH / 100 * 85);
		else
			textSize(WIDTH / 100 * 63);
		text(score, WIDTH / 2, HEIGHT / 100 * 43);

		if (!ballList.isReady()) {
			onlyOnce = true;
		}
		if (ballList.isReady() && onlyOnce) {
			blockList.addLine(this);
			onlyOnce = false;
		}
		blockList.update();
		strokeWeight(WIDTH/300);
		stroke(0);
		line(WIDTH / 2, HEIGHT, WIDTH / 2 + (inputVector.x) * 10, HEIGHT + (inputVector.y) * 10);
		ballList.drawBallSet();

		if (micFlag == true) {

			mic=loadImage("mic.png");
			mic.resize((int)WIDTH/30,(int)HEIGHT/30);
			image(mic,WIDTH/100*95,HEIGHT/100*95);
			if (input.left.level() > 8.4947412E-4) {
				if (inputVector.x < speed - 0.1) {
					inputVector.rotate(input.left.level());
				}
			} else {
				if (inputVector.x > 0 - speed + 0.1) {
					inputVector.rotate(0 - input.left.level() * 100);
				}
			}
		}
		
		if (finishFlag == true) {

			for (int i = 0; i < blockList.blockList.size(); i++) {
				for (int j = 0; j < blockList.blockList.get(i).size(); j++) {
					blockList.blockList.get(i).get(j).startY += 10;

				}
			}
			ballList.ballList.get(1).moveFlag = true;
		}
	}

	public void keyPressed() {
		if (key == ' ') {
			if (ballList.isReady() && ballList.isLaunched() == false) {
				ballList.launch(inputVector);

			}
		} else if (keyCode == LEFT) {
			if (inputVector.x > 0 - speed + 0.1)
				inputVector.rotate(0 - PI / 90);
		} else if (keyCode == RIGHT) {
			if (inputVector.x < speed - 0.1)
				inputVector.rotate(PI / 90);
		} else if (key == 'c' || key == 'C') {
			for (int i = 0; i < ballList.ballList.size(); i++) {
				ballList.ballList.get(i).moveFlag = false;
				ballList.ballList.get(i).launchFlag = false;
				ballList.ballList.get(i).nowVector = new PVector(WIDTH / 2, HEIGHT);
			}
		} else if (key == '.') {
			if (speed < 14)
				speed = speed + 1;
			inputVector = new PVector(0, 0 - speed);
		} else if (key == ',') {
			if (speed > 2)
				speed = speed - 1;
			inputVector = new PVector(0, 0 - speed);
		} else if (key == 'm' || key == 'M') {
			if (micFlag == true) {
				micFlag = false;
				inputVector = new PVector(0, 0 - speed);
			}else
				micFlag = true;
		}
	}
}