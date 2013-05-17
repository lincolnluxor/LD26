package com.lincolnluxor.LD26;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Block {
	private int blockX;
	private int blockY = 600;
	private int blockWidth = 25 ;
	private int blockHeight = 25;
	private Image imageBlock;
	private String imageSquareName = "square.png";
	private Rectangle hitbox;
	
	public Block(int blockX2, int blockY2) {
		getBlockImage();
		blockX = blockX2;
		blockY = blockY2;
		hitbox = new Rectangle(blockX, blockY, blockWidth, blockHeight);
	}
	public int moveup() {
		blockY = blockY - 1;
		hitbox = new Rectangle(blockX, blockY, blockWidth, blockHeight);
		return blockY;
	}
	public int getBlockX() {
		return blockX;
	}
	public int getBlockY() {
		return blockY;
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
	public Image getBlockImage() {
		ImageIcon sii = new ImageIcon(this.getClass().getResource(imageSquareName));
		imageBlock = sii.getImage();
		return imageBlock;
	}
	public void stopBlock() {
		if (blockY < 26) {
			blockY = 25;
		}
	}
}
