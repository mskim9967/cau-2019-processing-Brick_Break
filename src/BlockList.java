import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;

public class BlockList {
	static int MAXBLOCK=6;
	ArrayList<Block> blockLine=new ArrayList<Block>();
	ArrayList<ArrayList<Block>> blockList=new ArrayList<ArrayList<Block>>();
	ArrayList <Integer> numBlockInLine=new ArrayList<Integer>();
	static float BLOCKHEIGHT=Main.HEIGHT/10;
	PApplet p;

	
	public void addLine(PApplet p) {
		p.colorMode(p.HSB);
		int numBlock=0;
		blockLine=new ArrayList<Block>();
		
		Random rand=new Random();
		rand.setSeed(System.currentTimeMillis());
		
		for(int i=0;i<blockList.size();i++) {
			for(int j=0;j<blockList.get(i).size();j++) {
				blockList.get(i).get(j).setY(
						blockList.get(i).get(j).startY+BLOCKHEIGHT);
			}
		}
		
		for(int i=0;i<MAXBLOCK;i++)
		{
			if(rand.nextBoolean()) {
				blockLine.add(new Block(p,blockList.size()+1,p.color((blockList.size()*10)%255,255,255,170)
						,i*Main.WIDTH/MAXBLOCK,0,Main.WIDTH/MAXBLOCK,BLOCKHEIGHT));
				numBlock++;
			}
		}
		if(numBlock==0) {
			int i=rand.nextInt(MAXBLOCK);
			blockLine.add(new Block(p,blockList.size()+1,p.color(blockList.size()*10,255,255,170)
					,i*Main.WIDTH/MAXBLOCK,0,Main.WIDTH/MAXBLOCK,BLOCKHEIGHT));
			numBlock++;
		}
		numBlockInLine.add(numBlock);
		blockList.add(blockLine);
		this.p=p;
	}
	
	public void update() {
		for(int i=0;i<blockList.size();i++) {
			for(int j=0;j<blockList.get(i).size();j++) {
				if(blockList.get(i).get(j).durability<=0) {
					if(blockList.get(i).get(j).ballFlag==true) {
						Main.ballList.addBall(p, Main.HEIGHT/40);
						System.out.println("[SYSTEM] Ball Added. Now num of ball:"+Main.ballList.ballList.size());
					}
					blockList.get(i).remove(j);
					System.out.println("[SYSTEM] Block Crushed.");
					Main.score++;
					numBlockInLine.set(i,numBlockInLine.get(i)-1);
					if(numBlockInLine.get(i)==-1) {
						blockList.remove(i);
						numBlockInLine.remove(i);
				}
				}else {
					blockList.get(i).get(j).update();
				}
			}
		}
	}
	
}
