package com.lincolnluxor.LD26;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Coin {
	private int coinX = 80;
	private int coinY = 80;
	private int coinWidth = 25;
	private int coinHeight = 25;
	private Image imageCoin;
	private Image imageBlueCoin;
	private String imageCoinName = "coin.png";
	private String imageBlueCoinName = "blueCoin.png";
	private Rectangle hitbox;
	private int coinCounter;
	
	public Coin(int coinX2, int coinY2, int coinCounter2) {
		coinX = coinX2;
		coinY = coinY2;
		coinCounter = coinCounter2;
		if (coinCounter % 5 == 0) {
			getBlueCoinImage();
		}
		else{
			getCoinImage();
		}
		hitbox = new Rectangle(coinX, coinY, coinWidth, coinHeight);
	}
	public Image getBlueCoinImage() {
		ImageIcon bcii = new ImageIcon(this.getClass().getResource(imageBlueCoinName));
		imageBlueCoin = bcii.getImage();
		return imageBlueCoin;
	}
	public int getCoinX() {
		return coinX;
	}
	public int getCoinY() {
		return coinY;
	}
	public int getCoinWidth() {
		return coinWidth;
	}
	public int getCoinHeight() {
		return coinHeight;
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
	public Image getCoinImage() {
		ImageIcon cii = new ImageIcon(this.getClass().getResource(imageCoinName));
		imageCoin = cii.getImage();
		return imageCoin;
	}
	public int moveup() {
		coinY = coinY - 1;
		hitbox = new Rectangle(coinX, coinY, coinWidth, coinHeight);
		return coinY;
	}
}
