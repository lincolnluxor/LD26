package com.lincolnluxor.LD26;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Player {
	private int score = 0;
	private int playerX = 100;
	private int playerY = 100;
	private int playerWidth = 25;
	private int playerHeight = 25;
	private Image imagePlayer;
	private String imageCircleName = "circle.png";
	@SuppressWarnings("unused")
	private Rectangle hitbox;
	private boolean activeGame = true;
	
	public Player(Board board) {
		getPlayerImage();
		hitbox = new Rectangle(playerX, playerY, playerWidth, playerHeight);
		
	}
	
	public int getScore(){
		return score;
	}
	public int movePlayer(){
		playerY++;
		checkYBounds();
		return playerY;
	}
	public int getPlayerX() {
		return playerX;
	}
	public int getPlayerY() {
		return playerY;
	}
	public int getPlayerWidth() {
		return playerWidth;
	}
	public int getPlayerHeight() {
		return playerHeight;
	}
	public Image getPlayerImage() {
		ImageIcon cii = new ImageIcon(this.getClass().getResource(imageCircleName));
		imagePlayer = cii.getImage();
		return imagePlayer;
	}

	public void move() {
		// TODO Auto-generated method stub

	}
	public void checkCollision(ArrayList<Coin> coinsArray, ArrayList<Block> blocksArray, ArrayList<Hitbox> hitboxArray, ArrayList<Triangle> triangleArray, int coinCounter) {
		for (int i = 0; i < hitboxArray.size(); i++) {
			if (this.getPlayerY() + this.getPlayerHeight() >= hitboxArray.get(i).getHitboxY() && this.getPlayerX() == hitboxArray.get(i).getHitboxX() && this.getPlayerY() <= hitboxArray.get(i).getHitboxY() + hitboxArray.get(i).getHitboxHeight()){
				if (hitboxArray.get(i).getType() == "coin") {
					if (coinCounter % 5 == 0) {
						score+=5;
					}
					else {
						score++;
					}
					coinsArray.remove(hitboxArray.get(i).getID());
					hitboxArray.remove(i);
					SoundEffects.COIN.play();
				}
				else if (hitboxArray.get(i).getType() == "triangle") {
					SoundEffects.DAMAGE.play();
					activeGame = false;
				}
				else {
					playerY = hitboxArray.get(i).getHitboxY() - playerHeight -1;
				}
			}
		}
	}
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			playerX -= 25;
			checkXBounds();
			hitbox = new Rectangle(playerX, playerY, playerWidth, playerHeight);
			//System.out.println(Integer.toString(hitbox.x) +" "+ Integer.toString(hitbox.y)+" "+ Integer.toString(hitbox.width)+" "+ Integer.toString(hitbox.height));
		}
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			playerX += 25;
			checkXBounds();
			hitbox = new Rectangle(playerX, playerY, playerWidth, playerHeight);
			//System.out.println(Integer.toString(hitbox.x) +" "+ Integer.toString(hitbox.y)+" "+ Integer.toString(hitbox.width)+" "+ Integer.toString(hitbox.height));
		}

		/*
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			playerY -= 25;
			checkYBounds();
			hitbox = new Rectangle(playerX, playerY, playerWidth, playerHeight);
			//System.out.println(Integer.toString(hitbox.x) +" "+ Integer.toString(hitbox.y)+" "+ Integer.toString(hitbox.width)+" "+ Integer.toString(hitbox.height));
		}
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			playerY += 25;
			checkYBounds();
			hitbox = new Rectangle(playerX, playerY, playerWidth, playerHeight);
			//System.out.println(Integer.toString(hitbox.x) +" "+ Integer.toString(hitbox.y)+" "+ Integer.toString(hitbox.width)+" "+ Integer.toString(hitbox.height));
		}
		*/
	}
	public int checkXBounds() {
		if (playerX < 25) {
			playerX = 25;
		}
		else if (playerX > 750) {
			playerX = 750;
		}
		return playerX;
	}
	public int checkYBounds() {
		if (playerY > 525) {
			playerY = 525;
		}
		return playerY;
	}
	public void checkTopCollision() {
		if (playerY < 26) {
			SoundEffects.DAMAGE.play();
			activeGame = false;
		}
	}
	public boolean checkGame() {
		return activeGame;
	}
	public void reset() {
		score = 0;
		playerX = 100;
		playerY = 100;
		activeGame = true;
	}
}
