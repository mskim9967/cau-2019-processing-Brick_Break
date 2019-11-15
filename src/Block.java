import java.util.Random;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PVector;

public class Block {
	float startX, startY, endX, endY, height, width, temp, op = 100;

	int color, durability, cnt = 0;
	PApplet p;
	Minim minim;
	AudioPlayer sound;
	boolean ballFlag = false;
	Random rand = new Random();

	public Block(PApplet p, int durability, int color, float startX, float startY, float width, float height) {
		this.p = p;
		this.durability = durability;
		this.color = color;
		this.startX = startX;
		this.startY = startY;
		this.width = width;
		this.height = height;

		if (rand.nextInt(3) == 0)
			ballFlag = true;

		minim = new Minim(p);
		temp = 255 / durability;
		op = 255;
		endX = startX + width;
		endY = startY + height;
		sound = minim.loadFile("c.wav");
	}

	public void setY(float startY) {
		this.startY = startY;

		endY = startY + height;
	}

	public void update() {		
		p.strokeWeight((int)Main.WIDTH/300);
		p.stroke(255);
		p.fill(color, op);
		p.rect(startX, startY, width, height);

		if (ballFlag == true) {
			p.fill(255, op);
			p.ellipse(startX + width / 2, startY + height / 2, Main.HEIGHT / 40, Main.HEIGHT / 40);
		}
		if (endY == Main.HEIGHT) {
			Main.finishFlag = true;
			Main.lastColor = color;
		}	
	}

	public boolean isCollisionSide(PVector nowVector, PVector addVector) {
		if (nowVector.sub(addVector).y >= startY && nowVector.sub(addVector).y <= endY)
			return true;
		return false;
	}

	public boolean isCollisionUpDown(PVector nowVector, PVector addVector) {
		if (nowVector.sub(addVector).x >= startX && nowVector.sub(addVector).x <= endX)
			return true;
		return false;
	}

	public boolean isCollision(PVector nowVector) {
		if (startX <= nowVector.x && endX >= nowVector.x && startY <= nowVector.y && endY >= nowVector.y) {
			switch (cnt % 8) {
			case 0:
				sound = minim.loadFile("c.wav");
				break;
			case 1:
				sound = minim.loadFile("d.wav");
				break;
			case 2:
				sound = minim.loadFile("e.wav");
				break;
			case 3:
				sound = minim.loadFile("f.wav");
				break;
			case 4:
				sound = minim.loadFile("g.wav");
				break;
			case 5:
				sound = minim.loadFile("a.wav");
				break;
			case 6:
				sound = minim.loadFile("b.wav");
				break;
			case 7:
				sound = minim.loadFile("cc.wav");
				break;

			}
			sound.play();
			durability--;
			op -= temp;
			cnt++;
			System.out.println("[SYSTEM] Ball Hitted.");
			return true;
		}
		return false;
	}
}
